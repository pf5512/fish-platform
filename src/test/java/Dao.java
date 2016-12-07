import com.ippteam.fish.dao.nosql.mongodb.FileDao;
import com.ippteam.fish.dao.nosql.mongodb.FishingDao;
import com.ippteam.fish.dao.nosql.mongodb.MomentDao;
import com.ippteam.fish.dao.nosql.mongodb.WeatherDao;
import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.*;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
        List<Fishing> fishings = fishingDao.findAll();
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

    @Test
    public void moment() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("Fish-servlet.xml");
        MomentDao momentDao = (MomentDao) context.getBean("MomentDao");

        List<String> photos = new ArrayList<String>();
        photos.add("www.baidu.com");
        photos.add("www.sina.com");

        List<Comment> comments = new ArrayList<Comment>();
        Comment comment = new Comment();
        comment.setTo("1");
        comment.setFrom("2");
        comment.setContent("saas");
        comment.setDate(new Date());
        comments.add(comment);

        Moment moment = new Moment();
        moment.setContent("测试");
        moment.setDate(new Date());
        moment.setPhotos(photos);
        moment.setPublisher("1");
        moment.setLike(1);
        moment.setComments(comments);

        moment.setLocation(new Location(1, 2));


        momentDao.insert(moment);
        momentDao.insert(moment);
        momentDao.insert(moment);

        momentDao.getMoments(1, 2);

//            momentDao.addComment("58463417e0b00c0bf509a895", comment);

        BasicDBObject object = new BasicDBObject();
        object.put("1", 1);
        object.put("2", 2);
        BasicDBObject object1 = new BasicDBObject();
        object1.put("3", 3);
        object1.put("4", 4);

        BasicDBList list = new BasicDBList();
        list.add(object);
        list.add(object1);

        BasicDBObject objectlist = new BasicDBObject();
        objectlist.put("list", list);

        DBCollection collection = momentDao.mongoTemplate.getCollection("test");
//        collection.insert(objectlist);


    }
}