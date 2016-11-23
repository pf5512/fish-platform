import com.ippteam.fish.controller.*;
import com.ippteam.fish.pojo.AuthCode;
import com.ippteam.fish.pojo.RegNew;
import com.ippteam.fish.pojo.RegisterWay;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by isunimp on 16/11/23.
 */
public class Controller {

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
        AuthCode authCode = new AuthCode();
        authCode.setEmail("ansheck@163.com");
        authCodeController.email(authCode);
    }
}