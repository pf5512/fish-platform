import com.ippteam.fish.controller.UserController;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by isunimp on 16/11/23.
 */


public class Service {

    @Test
    public void user() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        UserService userService = (UserService) context.getBean("UserService");

        User user = userService.getUserById(17);
        user.setPassword("654321");
        userService.update(user);
        System.out.println(user.getId() + ":" + user.getPassword());
    }
}