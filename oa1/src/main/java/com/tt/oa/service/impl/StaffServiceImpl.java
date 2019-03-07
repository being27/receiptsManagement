package com.tt.oa.service.impl;

import com.tt.oa.service.StaffService;
import com.tt.oa.dao.StaffDao;
import com.tt.oa.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    public Staff getStaff(String id, String password) {
        return staffDao.getStaff(id, password);
    }

    public Staff getStaffById(String id) {
        return staffDao.getStaffById(id);
    }

    public List<Staff> listStaff() {
        return staffDao.listStaff();
    }

    public void addStaff(Staff staff) {
        staff.setPassword("000000");
        staffDao.addStaff(staff);
    }

    public void deleteStaff(String id) {
        staffDao.deleteStaff(id);
    }

    public void updateStaff(Staff staff) {
        staffDao.updateStaff(staff);
    }
}
