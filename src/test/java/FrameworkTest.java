import com.ippteam.fish.dao.DeveloperMapper;
import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.dao.nosql.mongodb.FishingDao;
import com.ippteam.fish.dao.nosql.redis.*;
import com.ippteam.fish.entity.Developer;
import com.ippteam.fish.entity.DeveloperExample;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.service.AuthCodeService;
import com.ippteam.fish.service.UserServiceImpl;
import com.ippteam.fish.util.JSON;
import com.mongodb.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.print.DocFlavor;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.geoNear;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * Created by isunimp on 16/11/2.
 */

@EnableWebMvc
public class FrameworkTest {
    // 测试 Mybatis
    @org.junit.Test
    public void Mybatis() {
        SqlSessionFactory sqlSessionFactory;
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            User user = (User) session.selectOne("com.ippteam.fish.dao.UserMapper.selectByPrimaryKey", 13);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试Spring和Mybatis集成
    @org.junit.Test
    public void SpringMybatis1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = (User) session.selectOne(
                    "com.ippteam.fish.dao.UserMapper.selectByPrimaryKey", 13);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // 测试Spring和Mybatis 映射器
    @org.junit.Test
    public void SpringMybatis2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserMapper userDao = (UserMapper) context.getBean("UserDao");
        try {
            User user = userDao.selectByPrimaryKey(10);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test
    public void UserServiceSelect() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("UserService");
        if (userService.login("ansheck", "123456") != null) {
            System.out.println("login suss");
        } else {
            System.out.println("login fail");
        }
    }

    @Test
    public void UserServiceInsert() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("UserService");
        if (userService != null) {
            User user = new User();
            user.setUserName("test");
            user.setPassword("123456");
            user.setRegisterTime(new Date());
            user.setRegisterIp(127001);
            User ruser = userService.register(user);
            if (ruser != null) {
                System.out.println("insert suss." + "id=" + ruser.getId());
            } else {
                System.out.println("insert fail.");
            }
        }
    }

    @Test
    public void DeveloperService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        DeveloperMapper developerDao = (DeveloperMapper) context.getBean("DeveloperDao");
        DeveloperExample developerExample = new DeveloperExample();
        developerExample.createCriteria().andAppKeyEqualTo("2d201754");
        List<Developer> developers = developerDao.selectByExample(developerExample);
        System.out.println(developers.size());
    }

    @Test
    public void developerDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        DeveloperMapper developerDao = (DeveloperMapper) context.getBean("DeveloperDao");
        DeveloperExample developerExample = new DeveloperExample();
        developerExample.createCriteria().andAppKeyEqualTo("2d201754");
        List<Developer> developers = developerDao.selectByExample(developerExample);
        Developer developer = developers.get(0);
        System.out.println(developers.size());
    }

    @Test
    public void redisPool() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        ShardedJedisPool redisPool = (ShardedJedisPool) context.getBean("shardedJedisPool");
        ShardedJedis redis = redisPool.getResource();
        String value = redis.get("k1");
        System.out.println(redis.set("k2", "v2"));
        redis.close();
        System.out.println(value);
    }

    @Test
    public void redisDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        Redis redis = (Redis) context.getBean("redisDao");
        System.out.println(redis.get("k1"));
        System.out.println(redis.exists("k1"));
    }

    @Test
    public void redisService() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        AuthCodeService redis = (AuthCodeService) context.getBean("AuthCodeService");
        System.out.println(redis.generate("ansheck@163.com"));
        System.out.println(redis.generate("ansheck@163.com"));
        System.out.println(redis.generate("ansheck@163.com"));
        System.out.println(redis.generate("ansheck@163.com"));
//        System.out.println(redis.verify("3923221", "ansheck@163.com"));
    }

    @Test
    public void mongo() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");


    }

    @Test
    public void fishingGroundDao() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        FishingDao fishingDao = (FishingDao) context.getBean("FishingGroundDao");
        List<Fishing> fishings = fishingDao.near(103.45, 30.42, 0);
        System.out.println(fishings.size());
    }
}
