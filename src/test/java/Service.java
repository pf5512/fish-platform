import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.entity.nosql.mongodb.Location;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import com.ippteam.fish.service.*;
import com.ippteam.fish.util.JSON;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by isunimp on 16/11/23.
 */


public class Service {
    ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");

    @Test
    public void user() throws Exception {
        UserService userService = (UserService) context.getBean("UserService");

//        User user = userService.getUserById(17);
//        user.setPassword("654321");
//        userService.update(user);X
//        System.out.println(user.getId() + ":" + user.getPassword());

//        userService.report("1", "测试举报", null);
    }

    @Test
    public void fishing_insertOne() {
        FishingService fishingService = (FishingService) context.getBean("FishingService");

        Fishing fishing = new Fishing();
        fishing.setAdder(13);
        fishing.setSummary("测试渔场");
        fishing.setLocation(new Location(1, 1));

        fishingService.add(fishing);
    }

    @Test
    public void fishing_find() throws Exception {
        FishingService fishingService = (FishingService) context.getBean("FishingService");
        List<Fishing> fishings = fishingService.near(1, 1, 111111111);
        for (Fishing fishing : fishings) {
            System.out.println(fishing.get_id());
        }
    }

    @Test
    public void fishing_report() throws Exception {
        FishingService fishingService = (FishingService) context.getBean("FishingService");
        String id = "586f080b477a3e7449de36f9";
        fishingService.report(new Report(id, "1", "", null));
    }

    @Test
    public void fishing_banned() {
        FishingService fishingService = (FishingService) context.getBean("FishingService");
//        fishingService.banned("586f0882477a3e7460d3bdac", "586f080b477a3e7449de36f9");
    }

    @Test
    public void moment_insert() {
        MomentServiceImpl momentService = (MomentServiceImpl) context.getBean("MomentService");
        Moment moment = new Moment();
        moment.setDate(new Date());
        moment.setContent("测试内容");
        moment.setPublisher("1");
        moment.setLocation(new Location(1, 1));

        momentService.addMoment(moment);
        momentService.addMoment(moment);
    }

    @Test
    public void moment_find() throws Exception {
        MomentServiceImpl momentService = (MomentServiceImpl) context.getBean("MomentService");
        List<Moment> moments = momentService.getMoments(1, 1);
        for (Moment moment : moments) {
            System.out.println(moment.get_id());
        }
    }

    @Test
    public void moment_report() throws Exception {
        MomentServiceImpl momentService = (MomentServiceImpl) context.getBean("MomentService");
        momentService.report(new Report("", "1", "", null));
    }

    @Test
    public void moment_banned() throws Exception {
        MomentServiceImpl momentService = (MomentServiceImpl) context.getBean("MomentService");
        momentService.banned("586df6a0477a3e647162b497");
    }

    @Test
    public void weather() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        WeatherServiceImpl weatherService = (WeatherServiceImpl) context.getBean("WeatherService");
        Map data = weatherService.weather("1222", new Date());
        System.out.println(data);
    }
}