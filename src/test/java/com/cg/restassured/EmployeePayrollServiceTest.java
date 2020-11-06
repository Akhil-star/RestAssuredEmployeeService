package com.cg.restassured;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import static com.cg.restassured.EmployeePayrollService.IOService.REST_IO;


public class EmployeePayrollServiceTest {
    @Before
    public void setUp()  {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    private EmployeePayrollData[] getEmployeeList() {
        Response response = RestAssured.get("employees");
        System.out.println("Employee payroll entries in json server\n" +response.asString());
        EmployeePayrollData[] arrayOfEmps = new Gson().fromJson(response.asString(),EmployeePayrollData[].class);
        return arrayOfEmps;
    }

    public Response addEmployeeToJsonServer(EmployeePayrollData employeePayrollData){
        String empJson = new Gson().toJson( employeePayrollData );
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header( "Content_Type","application/json" );
        requestSpecification.body( empJson ) ;
        return requestSpecification.post("/employees");
    }

    @Test
    public void givenEmployeeDataInJsonServer_WhenRetrieved_ShouldMatchTheCount() {
        EmployeePayrollData[] arrayOfEmps = getEmployeeList();
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService( Arrays.asList(arrayOfEmps));
        long entries = employeePayrollService.countEntries( REST_IO );
        Assert.assertEquals( 2,arrayOfEmps.length );
    }
}
