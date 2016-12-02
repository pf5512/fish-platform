import com.ippteam.fish.dao.nosql.mongodb.FileDao;
import com.ippteam.fish.dao.nosql.mongodb.FishingDao;
import com.ippteam.fish.dao.nosql.mongodb.WeatherDao;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.entity.nosql.mongodb.Location;
import com.ippteam.fish.entity.nosql.mongodb.Weather;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by isunimp on 16/11/25.
 */
public class Dao {
    @Test
    public void fishingDao() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        FishingDao fishingDao = (FishingDao) context.getBean("FishingDao");
        List<Fishing> fishings = fishingDao.all();
        Fishing fishing = fishings.get(1);
        fishing.setSummary("测试更新22222222222");
        fishing.setLocation(new Location(1, 2));
        fishing.setAdder(2222);
        fishingDao.update(fishing);
        System.out.println();
    }

    @Test
    public void fileDao() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        FileDao fileDao = (FileDao) context.getBean("FileDao");
        File file = new File("/Users/pactera/Desktop/2");
        String id = fileDao.save(new FileInputStream(file), null, "ansheck");
        InputStream inputStream = fileDao.file(id);

        byte[] bytes = new byte[1];
        int off = 0;
        while (off >= 0) {
            off = inputStream.read(bytes, 0, 1);
            System.out.println(new String(bytes));
        }
    }

    @Test
    public void weather() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        WeatherDao weatherDao = (WeatherDao) context.getBean("WeatherDao");

        Weather weathers1 = weatherDao.last();
        System.out.println();
    }
}