
import java.security.PrivateKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import tw.hyin.java.utils.security.JwtUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author H-yin on 2021.
 */
public class JwtTest {

    @Test
    public void generateToken() {
        try {
            PrivateKey privateKey = RSAUtil.loadPrivateKeyFromJAR("privateKey.jks");
            JwtUtil.generateToken("JwtTest", privateKey, 1000);
            System.out.println("--------------gen token success.----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
