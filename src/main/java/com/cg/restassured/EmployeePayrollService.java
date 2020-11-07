package com.cg.restassured;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {


    public enum IOService{DB_IO,REST_IO}

    private List<EmployeePayrollData> employeePayrollList;

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList){
        this.employeePayrollList=new ArrayList<>(employeePayrollList);
    }

    public long countEntries(IOService ioService) {
        if(ioService.equals( IOService.REST_IO ))
            return employeePayrollList.size();
        return 0;
    }

    public void addEmployeeToPayroll(EmployeePayrollData employeePayrollData, IOService ioService) {
        if(ioService.equals( IOService.REST_IO ))
           employeePayrollList.add( employeePayrollData );
    }

    public void updateEmployeeSalary(String name, double salary,IOService ioService) {
        if(ioService.equals(IOService.REST_IO )) {
            EmployeePayrollData employeePayrollData = this.getEmployeePayrollData( name );
            if (employeePayrollData != null)
                employeePayrollData.salary = salary;
        }
    }

    public EmployeePayrollData getEmployeePayrollData(String name) {
        EmployeePayrollData employeePayrollData;
        employeePayrollData = this.employeePayrollList.stream()
                .filter( employeepayrollDataItem -> employeepayrollDataItem.name.equals( name ))
                .findFirst().orElse( null );
        return employeePayrollData;
    }

    public void deleteEmployeePayroll(String name, IOService ioService) {
        if(ioService.equals(IOService.REST_IO )) {
            EmployeePayrollData employeePayrollData = this.getEmployeePayrollData( name );
            employeePayrollList.remove( employeePayrollData );
        }
    }
}
