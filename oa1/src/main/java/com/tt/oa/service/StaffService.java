package com.tt.oa.service;

import com.tt.oa.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffService {
    //懒惰的原因，不考虑业务层的逻辑而制定对应的方法，只是复制Dao层的方法
    Staff login(String id, String password);
    Staff getStaffById(@Param("id")String id);
    List<Staff> listStaff();
    void addStaff(Staff staff);
    void deleteStaff(String id);
    void updateStaff(Staff staff);
}
