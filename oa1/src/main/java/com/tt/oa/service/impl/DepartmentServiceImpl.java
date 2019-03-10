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

    //通过部门id查找部门这个操作还是挺多的，因此使用 DEPARTMENTKEY + departmentId 作为key
    public Department getDepartment(String id) {
        //把redis里面的字符串转化为实体遍历查找这个department
        ObjectMapper mapper = new ObjectMapper();
        List<Department> departmentList = null;
        String jsonString = jedisStrings.get(DEPARTMENTKEY);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Department.class);
        try {
            departmentList = mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Department department : departmentList){
            if (department.getId().equals(id))
                return department;
        }
        return null;
    }

    //因为部门的信息不经常变动，因此拟将department集合的数据存放到redis缓存中去
    @Transactional
    public List<Department> listDepartment() {
        String key = DEPARTMENTKEY;
        List<Department> departmentList = null;
        //两个逻辑即redis中是否有departmentList数据，有就直接查询，没有就在mysql中查询后再添加进redis中去
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)) {    //如果redis中不存在departmentList这个key
            departmentList = departmentDao.listDepartment();
            //将list对象转化为json字符串的格式
            jedisStrings.set(key, toJsonString(departmentList));
        } else { //如果redis中存在departmentList这个key，则直接在redis中获取value
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

    @Transactional
    public void deleteDepartment(String id) {
        //因为进入delete功能之前肯定要执行查询list操作，所以key肯定存在
        departmentDao.deleteDepartment(id);
        //新值替换旧值
        jedisStrings.set(DEPARTMENTKEY, toJsonString(departmentDao.listDepartment()));
    }

    @Transactional
    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
        jedisStrings.set(DEPARTMENTKEY, toJsonString(departmentDao.listDepartment()));
    }

    //添加部门可以使用在部门key对应的value后面添加字符，使用jedisStrings.append(key, value);方法
    @Transactional
    public void addDepartment(Department department) {
        departmentDao.addDepartment(department);
        //添加之前需要判断是否有departmentList这个key，因为可能部门列表为空
        if (!jedisKeys.exists(DEPARTMENTKEY)) {  //如果不存在这个key
            List<Department> departmentList = departmentDao.listDepartment();
            jedisStrings.set(DEPARTMENTKEY, toJsonString(departmentList));
        } else {     //如果已经存在这个key了，则直接在value后面添加字符串
            jedisStrings.set(DEPARTMENTKEY, toAppendDepartment(jedisStrings.get(DEPARTMENTKEY), department));
        }
    }

    private String toJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private String toAppendDepartment(String jsonString, Department department) {
        StringBuilder stringBuilder = new StringBuilder(jsonString.substring(0, jsonString.length() - 1));
        stringBuilder.append(",");
        stringBuilder.append(toJsonString(department));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
