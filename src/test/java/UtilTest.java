import com.ippteam.fish.util.*;
import com.ippteam.fish.util.api.entity.Sign;
import org.junit.Test;

import javax.print.DocFlavor;
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

    @Test
    public void testRegex(){
        boolean b = Verify.phone("18090910728");
        System.out.print(b);
    }


    @Test
    public void sign() {
        String securityKey = "0e5b78c1ff6f4ed4a169fea3d3eda528";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        /* 时间戳 */
        sb.append("\"expiredTime\":");
        sb.append("\"" + System.currentTimeMillis() + "\"");
        sb.append(",");
        /* body */
        sb.append("\"body\":");
        StringBuilder loginBody = new StringBuilder();
        loginBody.append("{");
        loginBody.append("\"userName\":\"admin\",");
        loginBody.append("\"password\":\"123456\"");
        loginBody.append("}");
        System.out.println(loginBody.toString());
        sb.append(loginBody.toString());
        // token

        sb.append("}");
        System.out.println(sb.toString());

        byte[] buff = AES.encrypt(sb.toString(), securityKey);
        // 转换成字符串
        String hexString = ConvertByte.parseByte2HexStr(buff);
        // 转换层buff
        byte[] buff1 = ConvertByte.parseHexStr2Byte(hexString);
        System.out.println(hexString);

//        for (byte b : buff) {
//            System.out.print(b);
//        }
//        System.out.println();
//        for (byte b : buff1) {
//            System.out.print(b);
//        }
    }
}