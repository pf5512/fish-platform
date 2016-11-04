package com.ippteam.fish.service;

import com.ippteam.fish.dao.DeveloperMapper;
import com.ippteam.fish.entity.Developer;
import com.ippteam.fish.entity.DeveloperExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 16/11/3.
 */

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    DeveloperMapper developerDao;

    public String getSecurityKeyByAppkey(String appKey) {
        DeveloperExample developerExample = new DeveloperExample();
        developerExample.or().andAppKeyEqualTo(appKey);

        List<Developer> developers = developerDao.selectByExample(developerExample);
        if (!developers.isEmpty()){
            return developers.get(0).getSecurityKey();
        }
        return null;
    }
}
