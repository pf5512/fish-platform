import com.ippteam.fish.util.AES;
import com.ippteam.fish.util.ConvertByte;
import com.ippteam.fish.util.HTTP;
import com.ippteam.fish.util.JSON;
import com.ippteam.fish.util.api.entity.Sign;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UtilTest {
    @Test
    public void newSecretKey() {
        System.out.println(AES.newSecretKey());
    }

    @Test
    public void AES() {
        String content = "test";
        String password = "12345678";
        System.out.println("加密前：" + content);
        byte[] encryptResult = AES.encrypt(content, password);
        System.out.println(ConvertByte.parseByte2HexStr(encryptResult));

        byte[] decryptResult = AES.decrypt(encryptResult, password);
        System.out.println("解密后：" + new String(decryptResult));
    }

    @Test
    public void Base64() {

    }

    @Test
    public void JSON() {
        Sign sign = new Sign();
        sign.setBody("thisbody");
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setToken("1234567890");

        try {
            String string = JSON.stringify(sign);
            System.out.println("josnstring: " + string);
            Sign s = JSON.parse(string, sign.getClass());
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHTTP() {
        try {
            Map param = new HashMap();
            param.put("sa", "sa");
            param.put("id", 123);
            System.out.println(HTTP.doGet("http://www.baidu.com", param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}