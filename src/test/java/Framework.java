import com.ippteam.fish.dao.DeveloperMapper;
import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.dao.nosql.redis.*;
import com.ippteam.fish.entity.Developer;
import com.ippteam.fish.entity.DeveloperExample;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.entity.nosql.mongodb.Location;
import com.ippteam.fish.service.AuthCodeService;
import com.ippteam.fish.service.FishingServise;
import com.ippteam.fish.service.UserServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * Created by isunimp on 16/11/2.
 */

@EnableWebMvc
public class Framework {
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
    public void mongo() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
    }
}
