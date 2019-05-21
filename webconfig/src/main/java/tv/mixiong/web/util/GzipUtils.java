package tv.mixiong.web.util;


import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

public class GzipUtils {

    /**
     * 缓冲区的大小 ;
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * GZIP 解密
     *
     * @param str
     * @return
     */
    public static String decryptGZIP(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            byte[] decode = str.getBytes();
            byte[] base64Decode = Base64Utils.decode(decode);
            //gzip 解压缩
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Decode);
            GZIPInputStream gzip = new GZIPInputStream(bais);
            byte[] buf = new byte[BUFFER_SIZE];
            int len = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = gzip.read(buf, 0, BUFFER_SIZE)) != -1) {
                baos.write(buf, 0, len);
            }
            gzip.close();
            baos.flush();
            decode = baos.toByteArray();
            baos.close();
            return new String(decode);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
