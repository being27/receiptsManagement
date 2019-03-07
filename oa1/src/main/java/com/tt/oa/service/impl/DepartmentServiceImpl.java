package com.tt.oa.service.impl;

import com.tt.oa.dao.DepartmentDao;
import com.tt.oa.entity.Department;
import com.tt.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public Department getDepartment(String id) {
        return departmentDao.getDepartment(id);
    }

    public List<Department> listDepartment() {
        return departmentDao.listDepartment();
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
