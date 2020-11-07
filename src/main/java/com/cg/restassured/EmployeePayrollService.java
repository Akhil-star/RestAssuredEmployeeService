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
        employeePayrollList.add( employeePayrollData );
    }

}
