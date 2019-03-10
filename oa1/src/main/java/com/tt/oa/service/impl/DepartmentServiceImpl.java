package com.tt.oa.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tt.oa.cache.JedisUtil;
import com.tt.oa.dao.DepartmentDao;
import com.tt.oa.entity.Department;
import com.tt.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    //定义部门信息的key
    private static String DEPARTMENTKEY = "departmentList";

    public Department getDepartment(String id) {
        return departmentDao.getDepartment(id);
    }

    //因为部门的信息不经常变动，因此拟将department集合的数据存放到redis缓存中去
    @Transactional
    public List<Department> listDepartment() {
        String key = DEPARTMENTKEY;
        List<Department> departmentList = null;
        //两个逻辑即redis中是否有departmentList数据，有就直接查询，没有就在mysql中查询后再添加进redis中去
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)){    //如果redis中不存在departmentList这个key
            departmentList = departmentDao.listDepartment();
            //将list对象转化为json字符串的格式
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(departmentList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedisStrings.set(key, jsonString);
        }else { //如果redis中存在departmentList这个key，则直接在redis中获取value
            String jsonString = jedisStrings.get(key);
            //将字符串转化为java对象
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Department.class);
            try {
                departmentList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return departmentList;
    }

    public void deleteDepartment(String id) {
        departmentDao.deleteDepartment(id);
    }

    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }

    public void addDepartment(Department department) {
        departmentDao.addDepartment(department);
    }
}
