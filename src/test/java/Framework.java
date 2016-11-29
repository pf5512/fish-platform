import com.ippteam.fish.dao.DeveloperMapper;
import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.dao.nosql.redis.*;
import com.ippteam.fish.entity.Developer;
import com.ippteam.fish.entity.DeveloperExample;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.entity.nosql.mongodb.Location;
import com.ippteam.fish.service.AuthCodeService;
import com.ippteam.fish.service.FishingService;
import com.ippteam.fish.service.UserServiceImpl;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
        DB db = mongoTemplate.getDb();
        System.out.println(db.getName());
        GridFS gridFS = new GridFS(db);
        File file = new File("/Users/pactera/Gridfs-Test.txt");
        GridFSInputFile inputFile = gridFS.createFile(file);
        inputFile.save();
        DBCursor cursor = gridFS.getFileList();

        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            System.out.println(object);
        }
    }

    @Test
    public void gridFsTemplate() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        GridFsTemplate gridFsTemplate = (GridFsTemplate) context.getBean("gridFsTemplate");
        List<GridFSDBFile> files = gridFsTemplate.find(new Query(new Criteria("filename").is("1")));

        try {
            File file = new File("/Users/pactera/Desktop/1");
            InputStream inputStream = new FileInputStream(file);
            gridFsTemplate.store(inputStream, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(files.size());
    }
}
