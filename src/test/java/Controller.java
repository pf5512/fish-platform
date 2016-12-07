import com.ippteam.fish.controller.*;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.pojo.RegNew;
import com.ippteam.fish.pojo.RegisterWay;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by isunimp on 16/11/23.
 */
public class Controller {

    <T> T getBean(Class<T> clazz) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        return (T) context.getBean(clazz.getSimpleName());
    }

    @Test
    public void user() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserController userController = (UserController) context.getBean("UserController");
        RegNew regNew = new RegNew();
        regNew.setAccount("ansheck@163.com");
        regNew.setPassword("123456");
        regNew.setAuthCode("414227");
        regNew.setRegisterWay(RegisterWay.EMAIL);
        userController.regNew(regNew, null);
    }

    @Test
    public void authCode() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        AuthCodeController authCodeController = (AuthCodeController) context.getBean("AuthCodeController");

        authCodeController.email("ansheck@163.com");
    }

    @Test
    public void moment() {
        MomentController momentController = getBean(MomentController.class);

        // add
        Moment moment = new Moment();
        moment.setContent("测试内容");
        moment.setSound("www.baidu.com");
    }
}