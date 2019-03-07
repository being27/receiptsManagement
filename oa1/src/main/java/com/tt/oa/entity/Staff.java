package com.tt.oa.entity;

public class Staff {
    private String id;
    private String password;
    private String name;
    private String duty;
//    private String departmentId;
    private Department department;  //员工和部门一对一的关系，我要知道当前员工的所属部门

    public Staff() {
    }

    public Staff(String name, String duty) {
        this.name = name;
        this.duty = duty;
    }

    public Staff(String password, String name, String duty) {
        this.password = password;
        this.name = name;
        this.duty = duty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", duty='" + duty + '\'' +
                ", department=" + department +
                '}';
    }
}
