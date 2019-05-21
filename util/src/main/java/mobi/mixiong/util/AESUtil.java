package mobi.mixiong.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Map;

@Slf4j
public class AESUtil {

    private static final String ENCRYPT_KEY = "koetYHoCsTORHjAe84jY9g==";

    private static SecretKeySpec securekey;

//    private static byte[] IV = new byte[16];
//
//    private static IvParameterSpec ivParameterSpec;

    static {
//        ivParameterSpec = new IvParameterSpec(IV);
        removeCryptographyRestrictions();
        try {
            securekey = new SecretKeySpec(base64Decode(ENCRYPT_KEY), "AES");
        } catch (Exception e) {
            log.error("init aes error", e);
        }
    }

    public static void removeCryptographyRestrictions() {
        if (!isRestrictedCryptography()) {
            return;
        }
        try {
            /*
             * Do the following, but with reflection to bypass access checks:
             *
             * JceSecurity.isRestricted = false;
             * JceSecurity.defaultPolicy.perms.clear();
             * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
             */
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

        } catch (final Exception e) {
        }
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }

    /**
     * AES加密
     *
     * @param content 待加密的内容
     * @param iv
     * @return 加密后的字符串
     */
    public static String encrypt(String content, byte[] iv) {
        if (content == null) {
            return null;
        }
        if (iv.length != 16) {
            log.error("iv size should be 16");
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, ivParameterSpec);
            byte[] encrypt = cipher.doFinal(content.getBytes("utf-8"));
            return base64Encode(encrypt);
        } catch (Exception e) {
            log.error("encrypt error, content : " + content, e);
        }
        return null;
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     */
    private static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null :
                Base64.decodeBase64(base64Code);
    }

    /**
     * AES解密
     *
     * @param encrypt 待解密的byte[]
     * @param iv
     * @return 解密后的String
     */
    public static String decrypt(String encrypt, byte[] iv) {
        if (encrypt == null) {
            return null;
        }
        if (iv.length != 16) {
            log.error("iv size should be 16");
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            byte[] encryptBytes = base64Decode(encrypt);
            if (encryptBytes == null) {
                return null;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, ivParameterSpec);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            log.error("decrypt error, content : {}, message : {}", encrypt, e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(AESUtil.decrypt("+QEViFZSP3Wx6luQRxSffrgjdAJ+EQYpVWsIdejK5XeryKXRPqBLhM78INzvB7XMtZUC9eFTU9iCtHTnvX2q8Q==", "c2c822696e13ce00".getBytes()));
        System.out.println(AESUtil.decrypt("SRhB1tBhEfrRJqklA/0zWYm5Cyo4NTpsbUFJ7dgsxImIYHnZxESaSJioav4BQcwDlf0UedzrC/kjteT9/NWrAw==", "1234567890qwerty".getBytes()));
    }
}
