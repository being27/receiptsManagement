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
