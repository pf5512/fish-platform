import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.UserServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.Reader;
import java.util.Date;

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
            reader = Resources.getResourceAsReader("Configure.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            User user = (User) session.selectOne("com.ippteam.fish.dao.UserMapper.selectByPrimaryKey", 10);
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
                    "com.ippteam.fish.dao.UserMapper.selectByPrimaryKey", 10);
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
        if (userService.login("ansheck", "123456")) {
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
            user.setRegisterDate(new Date());
            user.setRegisterIp("127.0.0.1");
            if (userService.register(user)) {
                System.out.println("insert suss.");
            } else {
                System.out.println("insert fail.");
            }
        }
    }
}
