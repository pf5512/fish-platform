package com.ippteam.fish.dao.nosql.mongodb.util;

import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.util.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
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
    @Autowired
    protected MappingMongoConverter mappingMongoConverter;

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

    protected Query queryById(String id) {
        return new Query(Criteria.where("_id").is(new ObjectId(id)));
    }

    protected DBCollection getDBCollection() {
        DBCollection collection = null;
        if (!mongoTemplate.collectionExists(entityClass)) {
            mongoTemplate.createCollection(entityClass);
        }
        String collectionName = mongoTemplate.getCollectionName(entityClass);
        return mongoTemplate.getCollection(collectionName);
    }

    protected void createIndex(String fieldKey, String indexType) {
        DBCollection collection = getDBCollection();
        if (collection != null) {
            collection.createIndex(new BasicDBObject(fieldKey, indexType));
        }
    }

    public List<T> findAll() {
        return mongoTemplate.find(null, entityClass);
    }

    public T findById(String id) {
        return mongoTemplate.findOne(queryById(id), entityClass);
    }

    public List<T> find(T entity) throws Exception {
        Method[] methods = entityClass.getMethods();
        Field[] fields = entityClass.getDeclaredFields();

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
        return mongoTemplate.find(new Query(criteria), entityClass);
    }

    /**
     * 插入一个文档对象
     * 如果文档所属的集合已经存在，则返回null
     *
     * @param entity 文档对象
     */
    public void insert(T entity) {
        mongoTemplate.insert(entity);
    }
}
