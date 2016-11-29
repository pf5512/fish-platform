package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.util.Fix;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by isunimp on 16/11/24.
 */

@Repository("FileDao")
public class FileDao extends MongoBaseDao {

    @Autowired
    GridFsTemplate gridFsTemplate;

    public InputStream file(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        List<GridFSDBFile> files = gridFsTemplate.find(query);
        GridFSDBFile file = Fix.list(files, 0);
        return file.getInputStream();
    }

    public String save(InputStream inputStream, String fileName, String owner) throws Exception {
        GridFSFile gridFSFile = gridFsTemplate.store(inputStream, fileName, new BasicDBObject("owner", owner));
        return gridFSFile.getId().toString();
    }
}
