package tv.mixiong.ds.service.uc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tv.mixiong.ds.dao.entity.ImUserSign;
import tv.mixiong.ds.service.tls.TlsSigature;
import tv.mixiong.dubbo.exception.ServiceException;

import java.security.Security;


@Component
public class UserCenterDs {

    private Logger logger = LoggerFactory.getLogger(UserCenterDs.class);

    private final long DAYS_30_IN_SECONDS = 30 * 24 * 60 * 60l;

    @Value("${qcloud.ilvb.sdkAppid}")
    long tlsAppid;
    @Value("${qcloud.ilvb.privStr}")
    String tlsPrivStr;
    @Value("${qcloud.ilvb.pubStr}")
    String tlsPubStr;

    public ImUserSign getAdminUserSign(String passport) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            TlsSigature.GenTLSSignatureResult result = TlsSigature.GenTLSSignatureEx(tlsAppid, passport, tlsPrivStr , DAYS_30_IN_SECONDS);
            TlsSigature.CheckTLSSignatureResult checkResult = TlsSigature.CheckTLSSignatureEx(result.urlSig, tlsAppid, passport,tlsPubStr);
            if (checkResult.verifyResult == false) {
                throw new ServiceException("生成签名失败");
            } else {
                ImUserSign imUserSign = new ImUserSign();
                imUserSign.setExpireInS(Long.valueOf(checkResult.expireTime).intValue());
                imUserSign.setSignAt(Long.valueOf(checkResult.initTime).intValue());
                imUserSign.setUserSign(result.urlSig);
                return imUserSign;
            }
        } catch (Exception e) {
            logger.error("生成im签名失败",e);
            throw new ServiceException("生成签名失败");
        }
    }
}