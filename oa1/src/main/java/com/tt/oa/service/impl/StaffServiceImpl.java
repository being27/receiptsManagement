package com.tt.oa.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tt.oa.cache.JedisUtil;
import com.tt.oa.entity.Department;
import com.tt.oa.service.DepartmentService;
import com.tt.oa.service.StaffService;
import com.tt.oa.dao.StaffDao;
import com.tt.oa.entity.Staff;
import com.tt.oa.utils.JsonStringDeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private DepartmentService departmentService;

    private static String STAFFKEY = "staffKey";
    /**
     * 对登录进行判断，如果返回值不为null则说明用户存在
     * @param id
     * @param password
     * @return
     */
    public Staff login(String id, String password) {
        Staff staff = staffDao.getStaffById(id);
        if (password.equals(staff.getPassword()))
            return staff;
        return null;
    }

    public Staff getStaffById(String id){
        return  staffDao.getStaffById(id);
    }

    public List<Staff> listStaff() {
        List<Staff> staffList = null;
        if (!jedisKeys.exists(STAFFKEY)){
            staffList = staffDao.listStaff();
            jedisStrings.set(STAFFKEY, JsonStringDeal.toJsonString(staffList));
        }else {
            String jsonString = jedisStrings.get(STAFFKEY);
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Staff.class);
            try {
                staffList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return staffList;
    }

    public void addStaff(Staff staff) {
        staff.setPassword("000000");
        Department department = departmentService.getDepartment(staff.getDepartment().getId());
        staff.setDepartment(department);
        staffDao.addStaff(staff);
        if (!jedisKeys.exists(STAFFKEY)){
            List<Staff> staffList = staffDao.listStaff();
            jedisStrings.set(STAFFKEY, JsonStringDeal.toJsonString(staffList));
        }else {
            jedisStrings.set(STAFFKEY, JsonStringDeal.toAppendDepartment(jedisStrings.get(STAFFKEY), staff));
        }
    }

    public void deleteStaff(String id) {
        staffDao.deleteStaff(id);
        jedisKeys.del(STAFFKEY);
    }

    public void updateStaff(Staff staff) {
        staffDao.updateStaff(staff);
        jedisKeys.del(STAFFKEY);
    }
}
