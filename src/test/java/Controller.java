import com.ippteam.fish.controller.*;
import com.ippteam.fish.model.AuthCode;
import com.ippteam.fish.model.RegNew;
import com.ippteam.fish.model.RegisterWay;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

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