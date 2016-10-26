/**
 * Created by pactera on 16/10/20.
 */

import com.ippteam.fish.dao.IUserDao;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.ibatis.session.SqlSession;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Testing {

    // 测试 Mybatis
    @org.junit.Test
    public void testMybatis() {
        SqlSessionFactory sqlSessionFactory;
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("Configure.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            User user = (User) session.selectOne(
                    "IUserDao.GetUserByID", 1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试Spring和Mybatis集成
    @org.junit.Test
    public void testSpringMybatis1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = (User) session.selectOne(
                    "IUserDao.GetUserByID", 1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // 测试Spring和Mybatis 映射器
    @org.junit.Test
    public void testSpringMybatis2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        IUserDao iu = (IUserDao) context.getBean("IUser");
        try {
            User user = iu.getUserByID(1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test
    public void testUserServiceSelect() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("UserServiceImpl");
        if (userService.login("isunimp", "123456")) {
            System.out.println("login suss");
        } else {
            System.out.println("login fail");
        }
    }

    @Test
    public void testUserServiceInsert() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("UserServiceImpl");
        if (userService != null) {
            User user = new User();
            user.setUserName("test");
            user.setPassword("123456");
            user.setRegisterDate("2016-10-24 20:52:00");
            user.setRegisterIP("127.0.0.1");
            if (userService.register(user)) {
                System.out.println("insert suss.");
            } else {
                System.out.println("insert fail.");
            }
        }
    }
}
