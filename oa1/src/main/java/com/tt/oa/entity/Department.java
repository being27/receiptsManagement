package com.tt.oa.entity;

public class Department {
    private String id;
    private String departmentName;
    private String departmentPosition;

    public Department() {
    }

    public Department(String id, String departmentName, String departmentPosition) {
        this.id = id;
        this.departmentName = departmentName;
        this.departmentPosition = departmentPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentPosition() {
        return departmentPosition;
    }

    public void setDepartmentPosition(String departmentPosition) {
        this.departmentPosition = departmentPosition;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentPosition='" + departmentPosition + '\'' +
                '}';
    }
}
