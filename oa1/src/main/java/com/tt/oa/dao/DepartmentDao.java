package com.tt.oa.dao;

import com.tt.oa.entity.Department;

import java.util.List;

public interface DepartmentDao {
    Department getDepartment(String id);
    List<Department> listDepartment();
    void deleteDepartment(String id);
    void updateDepartment(Department department);
    void addDepartment(Department department);
}
