package com.ippteam.fish.dao.nosql.mongodb.util;

import com.ippteam.fish.util.JSON;
import com.mongodb.BasicDBObjectBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.*;
import java.util.List;

/**
 * Created by isunimp on 16/11/25.
 */
public abstract class MongoBaseDao<T> {

    private Class<T> entityClass;

    @Autowired
    public MongoTemplate mongoTemplate;

    public MongoBaseDao() {

        try {
            Type sType = getClass().getGenericSuperclass();
            Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
            entityClass = (Class<T>) generics[0];
        } catch (ClassCastException exception) {
            // MongoBaseDao的派生子类没有指定泛型类型
            throw exception;
        }

        JSON.addSerializer(ObjectId.class, new CustomMongoIdSerializer());
    }

    public List<T> all() {
        return mongoTemplate.find(null, entityClass);
    }

    public List<T> find(T entity) throws Exception {
        Method[] methods = entityClass.getMethods();
        Field[] fields = entityClass.getDeclaredFields();

        // BasicDBObjectBuilder rootDBObject = BasicDBObjectBuilder.start();
        Criteria criteria = new Criteria();

        for (Field field : fields) {
            String name = field.getName();
            if (name.equals("_id")) continue;

            String getName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Class type = field.getType();

            if (List.class.isAssignableFrom(type)) {

            } else if (List.class.isAssignableFrom(type)) {

            } else {
                Method m = entityClass.getMethod(getName);
                // 调用getter方法获取属性值
                Object value = m.invoke(entity);
                // rootDBObject.add(name, value);
                criteria.and(name).is(value);
            }
        }
        System.out.println(new Query(criteria));
        return mongoTemplate.find(new Query(criteria), entityClass);
    }

    /**
     * 插入一个文档对象
     * 如果文档所属的集合不存在，则自动创建，并返回集合，便于添加索引之类的操作
     * 如果文档所属的集合已经存在，则返回null
     *
     * @param entity 文档对象
     */
    public void insert(T entity) {
        mongoTemplate.insert(entity);
    }
}
