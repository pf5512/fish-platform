import com.ippteam.fish.controller.UserController;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.entity.nosql.mongodb.Location;
import com.ippteam.fish.service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void fishing() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        FishingService fishingService = (FishingService) context.getBean("FishingService");

        List coordinates = new ArrayList<Double>();
        coordinates.add(1);
        coordinates.add(1);

        Location location = new Location();
        location.setType("Point");
        location.setCoordinates(coordinates);

        Fishing fishing = new Fishing();
        fishing.setAdder(13);
        fishing.setSummary("测试渔场");
        fishing.setLocation(location);

        fishingService.add(fishing);
    }

    @Test
    public void weather() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        WeatherServiceImpl weatherService = (WeatherServiceImpl) context.getBean("WeatherService");
        String data = weatherService.weather("绵阳", new Date());
        System.out.println();
    }
}