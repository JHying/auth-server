package tw.hyin.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import tw.hyin.java.utils.Log;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.RSAUtil;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author rita6 on 2021.
 */
@Configuration
public class KeyConfig {

    public static PrivateKey privateKey = null;
    public static PublicKey publicKey = null;
    public static SecretKey secretKey = null;

    static {
        try {
            privateKey = RSAUtil.loadPrivateKeyFromJAR("privateKey.jks");
            publicKey = RSAUtil.loadPublicKeyFromJAR("publicKey.jks");
            secretKey = AESUtil.loadSecretKeyFromJAR("secretKey.jks");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

}
