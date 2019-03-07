package com.tt.oa.dao;

import com.tt.oa.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffDao {
    Staff getStaff(@Param("id") String id, @Param("password")String password);
    Staff getStaffById(@Param("id")String id);
    List<Staff> listStaff();
    void addStaff(Staff staff);
    void deleteStaff(String id);
    void updateStaff(Staff staff);
}
