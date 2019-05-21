package tv.mixiong.saas.thirdpay.utils;

import mobi.mixiong.util.qrcode.ErrorCorrectLevel;
import mobi.mixiong.util.qrcode.QRCode;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

/**
 */
public class QrCodeUtil {

    public final static String BASE64_PREFIX = "data:image/png;base64,";

    public static Optional<String> generate(String baseUrl) {
        if (StringUtils.isEmpty(baseUrl)) {
            return Optional.empty();
        }
        try {
            QRCode qrcode = QRCode.getMinimumQRCode(baseUrl, ErrorCorrectLevel.L);
          //  BASE64Encoder encoder = new BASE64Encoder();

            BufferedImage bi = qrcode.createImage(10, 0);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", out);
            String data ="";
            return Optional.of(BASE64_PREFIX + data);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
