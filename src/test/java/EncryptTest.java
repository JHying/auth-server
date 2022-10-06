
import javax.crypto.SecretKey;

import tw.hyin.demo.AuthServerApplication;
import org.junit.jupiter.api.Test;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.GenerateKeyUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author H-yin on 2021.
 */
public class EncryptTest {

    @Test
    public void generateKey() {
        try {
            System.out.println("-------------generateKey start.-------------");
            RSAUtil.generateKey("publicKey.jks", "privateKey.jks", AuthServerApplication.class);
            AESUtil.generateKey("secretKey.jks", AuthServerApplication.class);
            System.out.println("-------------generateKey finished.-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void genRandomKey() {
        try {
            System.out.println("--------------printKey start.---------------");
            System.out.println("custom Key: " + GenerateKeyUtil.KEY);
            System.out.println("-------------printKey finished.-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptTest() {
        try {
            //原文
            String plainText = "test123";
            //生成 AES 金鑰
            SecretKey key = AESUtil.loadSecretKeyFromJAR("secretKey.jks");
            System.out.println("--------------encrypt test start.----------------");
            System.out.println("plainText: " + plainText);
            //使用 AES 加密原文
            String encodeText = AESUtil.encrypt(plainText, key);
            System.out.println("AES encodeText: " + encodeText);
            //使用 RSA 加密金鑰
            String encodeKey = RSAUtil.encrypt(AESUtil.keyToString(key).getBytes(), RSAUtil.loadPublicKeyFromJAR("publicKey.jks"));
            System.out.println("RSA encode AES key.");
            System.out.println("--------------decrypt test start.----------------");
            //使用 RSA 解密金鑰
            String decodeKey = RSAUtil.decrypt(encodeKey.getBytes(), RSAUtil.loadPrivateKeyFromJAR("privateKey.jks"));
            //使用 AES 金鑰解密
            String decodeText = AESUtil.decrypt(encodeText.getBytes(), AESUtil.getSecretKey(decodeKey));
            System.out.println("RSA decode AES key.");
            System.out.println("AES decodeText: " + decodeText);
            System.out.println("--------------------finished.--------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
