package com.tt.oa.service;

import com.tt.oa.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department getDepartment(String id);
    List<Department> listDepartment();
    void deleteDepartment(String id);
    void updateDepartment(Department department);
    void addDepartment(Department department);
}
