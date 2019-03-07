package com.tt.oa.service;

import com.tt.oa.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffService {
    Staff getStaff(String id, String password);
    Staff getStaffById(String id);
    List<Staff> listStaff();
    void addStaff(Staff staff);
    void deleteStaff(String id);
    void updateStaff(Staff staff);
}
