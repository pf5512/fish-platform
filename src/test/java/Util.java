import com.ippteam.fish.pojo.Login;
import com.ippteam.fish.pojo.RegNew;
import com.ippteam.fish.pojo.RegisterWay;
import com.ippteam.fish.util.*;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.email.*;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.*;
import org.bson.*;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    @Test
    public void newSecretKey() {
        System.out.println(AES.newSecretKey());
    }

    @Test
    public void AES() throws Exception {
        String content = "test";
        String password = "0e5b78c1ff6f4ed4";
        System.out.println("加密前：" + content);
        byte[] encryptResult = AES.encrypt(content, password);
        System.out.println(Convert.parseByte2HexStr(encryptResult));
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
    public void redis() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("k1", "hhhh");
        String s = jedis.get("k1");
        System.out.println(s);
    }

    @Test
    public void mongo() {
        try {
            //通过连接认证获取MongoDB连接
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            //连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("fish");
            MongoCollection<Document> collection = mongoDatabase.getCollection("fishing_ground");
            BasicDBObject geo = new BasicDBObject("type", "Point");
            BasicDBList coordinates = new BasicDBList();
            coordinates.add(1);
            coordinates.add(2);
            geo.put("coordinates", coordinates);

            BasicDBObject object = new BasicDBObject("loc",
                    new BasicDBObject("$near",
                            new BasicDBObject("$geometry", geo)
                    )
            );

            FindIterable<Document> findIterable = collection.find(object);
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next());
            }
            System.out.println("Connect to database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void sign(Sign sign) {
        String securityKey = "0e5b78c1ff6f4ed4a169fea3d3eda528";
        try {
            String signString = JSON.stringify(sign);
            byte[] buff = AES.encrypt(signString, securityKey);
            // 转换成hex字符串
            String hexString = Convert.parseByte2HexStr(buff);
            System.out.println(signString);
            System.out.println(hexString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void regNewSign() {
        RegNew regNew = new RegNew();
        regNew.setRegisterWay(RegisterWay.EMAIL);
        regNew.setAccount("ansheck");
        regNew.setPassword("123456");
        regNew.setAuthCode("");

        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/user/regnew");
        sign.setBody(regNew);

        sign(sign);
    }

    @Test
    public void loginSign() {
        Login login = new Login();
        login.setAccount("ansheck@163.com");
        login.setPassword("123456");

        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/session/login");
        sign.setBody(login);
        sign(sign);
    }

    @Test
    public void upload(){
        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/file/upload");
        sign.setToken("0cb9e047458343b790c675c31211e826");
        sign(sign);
    }

    @Test
    public void file(){
        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/file/583e8e13e3a15e4ea781c7b1");
        sign.setToken("0cb9e047458343b790c675c31211e826");
        sign(sign);
    }

    @Test
    public void authCode() {
        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/authcode/email");
        sign(sign);
    }

    @Test
    public void test() {
        String ipStr = "127.0.0.1";
        long ipLong = Convert.ipToLong(ipStr);
        System.out.println(Convert.longToIP(0));
    }
}