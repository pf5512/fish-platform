import com.ippteam.fish.model.Login;
import com.ippteam.fish.util.*;
import com.ippteam.fish.util.api.model.Sign;
import com.ippteam.fish.util.email.*;
import org.junit.Test;
import org.springframework.beans.factory.config.EmbeddedValueResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Login login = new Login();
        login.setAccount("ansheck");
        login.setPassword("123456");

        Sign sign = new Sign();
        sign.setBody(login);
        sign.setExpiredTime(System.currentTimeMillis());

        try {
            String string1 = JSON.stringify(sign);
            System.out.println("josnstring: " + string1);

            Sign string2 = JSON.parse(string1, sign.getClass());
            System.out.println(string2);

            Map map = JSON.parse(string1, Map.class);
            System.out.println(map);

            String[] ss = {"1", "2", "3"};
            String sss = JSON.stringify(ss);
            Object obj = JSON.parse(sss, Object.class);
            System.out.print("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals() {

        List list1 = new ArrayList<Number>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List list2 = new ArrayList<Number>();
        list2.add(3);
        list2.add(1);
        list2.add(2);

        Map map1 = new HashMap<Number, Object>();
        map1.put(3, "1");
        map1.put(4, "1");
        map1.put(1, "");
        map1.put(2, "2");
        map1.put(10, list1);

        Map map2 = new HashMap<String, Object>();
        map2.put(2, "2");
        map2.put(1, null);
        map2.put(3, "1");
        map2.put(4, "1");
        map2.put(10, list2);

        Object obj1 = map1;
        Object obj2 = map2;

        System.out.print(Verify.equals(obj1, obj2));
    }

    @Test
    public void HTTP() {
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
    public void Regex() {
        boolean b = Verify.phone("18090910728");
        System.out.print(b);
    }

    @Test
    public void random() {
        System.out.println(Random.UUIDString());
        System.out.println(Random._32String().length());
        System.out.println(Random.UUIDString().length());
    }

    @Test
    public void email() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setServerHost("smtp.163.com");
        serverInfo.setUserName("ansheck@163.com");
        serverInfo.setPassword("renguiquanyy1");
        serverInfo.setNick("isunimp");

        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setToAddress("minghui.chi@changhong.com");
        emailInfo.setSubject("测试主题");
        emailInfo.setContent("测试内容");

        try {
            UserAgent userAgent = new UserAgent(serverInfo);
            userAgent.sendTextMail(emailInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sign() {
        String securityKey = "0e5b78c1ff6f4ed4a169fea3d3eda528";

        Sign sign = new Sign();
        Login login = new Login();
        login.setAccount("ansheck");
        login.setPassword("123456");
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setBody(login);

        try {
            String signString = JSON.stringify(sign);
            byte[] buff = AES.encrypt(signString, securityKey);
            // 转换成hex字符串
            String hexString = ConvertByte.parseByte2HexStr(buff);
            System.out.println(signString);
            System.out.println(hexString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        System.out.println(Random._6Number());
    }
}