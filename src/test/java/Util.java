
import com.ippteam.fish.pojo.Login;
import com.ippteam.fish.pojo.RegNew;
import com.ippteam.fish.pojo.RegisterWay;
import com.ippteam.fish.pojo.User;
import com.ippteam.fish.util.*;
import com.ippteam.fish.util.Random;
import com.ippteam.fish.util.aes.AES;
import com.ippteam.fish.util.aes.AESHelper;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.email.*;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bson.*;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

public class Util {

    @Test
    public void AES() {
        try {
            String secretKey = "qwertyuiopasdfgh";
            String str1 = "";
            String hex = AESHelper.encryptToHex(str1, "sa");
            System.out.println(hex);
            String original = AESHelper.decryptToStr("11111", "sasa");
            System.out.println(original);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Base64() throws Exception {

        String host = "http://www.qf106.com";
        String path = "/sms.aspx";
        String method = "GET";

        Map<String, String> headers = new HashMap<String, String>();

        Map<String, String> querys = new HashMap<String, String>();
        querys.put("userid", "14960");
        querys.put("account", "tz112");
        querys.put("password", "we1234");
        querys.put("mobile", "18190910728");
        querys.put("content", Long.toString(123456));
        querys.put("sendTime", null);
        querys.put("action", "send");
        querys.put("checkcontent", "0");
        querys.put("taskName", null);
        querys.put("countnumber", "1");
        querys.put("mobilenumber", "1");
        querys.put("telephonenumber", "0");

        HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
        String XMLString = EntityUtils.toString(response.getEntity());
        System.out.println(XMLString);
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

    static String securityKey = "0e5b78c1ff6f4ed4";

    public void sign(Sign sign) {
        try {
            String signString = JSON.stringify(sign);
            String hexString = AESHelper.encryptToHex(signString, securityKey);
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
    public void loginSign() throws Exception {
        Login login = new Login();
        login.setAccount("lbj1234");
        login.setPassword(AESHelper.encryptToHex("123456",securityKey));

        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/session/login");
        sign.setBody(login);
        sign(sign);
    }

    @Test
    public void user(){
        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/user/");
        sign.setToken("c91dbb8bc2474d1b9cabb13fbae0442c");
        sign(sign);
    }

    @Test
    public void upload() {
        Sign sign = new Sign();
        sign.setExpiredTime(System.currentTimeMillis());
        sign.setApi("/v1/file/upload");
        sign.setToken("0cb9e047458343b790c675c31211e826");
        sign(sign);
    }

    @Test
    public void file() {
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
    public void test() throws Exception {
        User user = new User();
        user.setId(1);
        user.setGender("男");
        User userUpdate = new User();
        userUpdate.setNikeName("12");
        userUpdate.setFullName("任贵权");
        userUpdate.setIcon("www.baidu.com");
        userUpdate.setBirthdate(new Date());
        userUpdate.setQq(137858649);
        userUpdate.setWeibo("isunimp");

        Reflection.objectValueTransfer(user, userUpdate, true);

        System.out.println();
    }
}