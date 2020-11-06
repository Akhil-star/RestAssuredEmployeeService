package com.cg.restassured;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {

    int id;
    String name;
    String gender;
    double salary;
    LocalDate startDate;


    public EmployeePayrollData(int id, String name,String gender,double salary, LocalDate startDate) {
        this.id=id;
        this.name=name;
        this.gender = gender;
        this.salary=salary;
        this.startDate=startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id &&
                Double.compare( that.salary, salary ) == 0 &&
                Objects.equals( name, that.name ) &&
                Objects.equals( startDate, that.startDate );
    }
}

