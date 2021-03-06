package com.cg.restassured;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
        requestSpecification.header( "Content-Type","application/json" );
        requestSpecification.body( empJson ) ;
        return requestSpecification.post("/employees");
    }


    @Test
    public void givenNewEmployee_WhenAdded_ShouldMatch201ResponseAndCount(){
        EmployeePayrollService employeePayrollService;
        EmployeePayrollData[] arrayOfEmps = getEmployeeList();
        employeePayrollService = new EmployeePayrollService( Arrays.asList(arrayOfEmps));
        EmployeePayrollData employeePayrollData = null;
        employeePayrollData = new EmployeePayrollData( 0,"Mark Zuckerberg","M",
                                                                 300000.00, LocalDate.now() );
        Response response = addEmployeeToJsonServer( employeePayrollData );
        int statusCode = response.getStatusCode();
        Assert.assertEquals( 201,statusCode );

        employeePayrollData = new Gson().fromJson(response.asString(),EmployeePayrollData.class);
        employeePayrollService.addEmployeeToPayroll(employeePayrollData,REST_IO);
        long entries = employeePayrollService.countEntries( REST_IO );
        Assert.assertEquals( 3,entries);
    }

    @Test
    public void givenMultipleEmployees_WhenAdded_ShouldMatch201ResponseAndCount() {
        EmployeePayrollService employeePayrollService;
        EmployeePayrollData[] arrayOfEmps = getEmployeeList();
        employeePayrollService = new EmployeePayrollService( Arrays.asList( arrayOfEmps ) );

        EmployeePayrollData[] arrayOfEmpPayrolls = {
                new EmployeePayrollData( 0, "Sunder", "M", 600000.00, LocalDate.now()),
                new EmployeePayrollData( 0, "Mukesh", "M", 100000.00, LocalDate.now())
        };

        for (EmployeePayrollData employeePayrollData : arrayOfEmpPayrolls) {
            Response response = addEmployeeToJsonServer( employeePayrollData );
            int statusCode = response.getStatusCode();
            Assert.assertEquals( 201, statusCode );

            employeePayrollData = new Gson().fromJson( response.asString(), EmployeePayrollData.class );
            employeePayrollService.addEmployeeToPayroll( employeePayrollData, REST_IO );
        }

            long entries = employeePayrollService.countEntries( REST_IO );
            Assert.assertEquals( 5, entries );
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldMatch200Response() {
        EmployeePayrollService employeePayrollService;
        EmployeePayrollData[] arrayOfEmps = getEmployeeList();
        employeePayrollService = new EmployeePayrollService( Arrays.asList( arrayOfEmps ) );

        employeePayrollService.updateEmployeeSalary("Bill Gates",3000000.00,REST_IO);
        EmployeePayrollData employeePayrollData = employeePayrollService.getEmployeePayrollData("Bill Gates");

        String empJson = new Gson().toJson( employeePayrollData );
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header( "Content-Type","application/json" );
        requestSpecification.body( empJson ) ;
        Response response = requestSpecification.put("/employees/"+employeePayrollData.id);
        int statusCode = response.getStatusCode();
        Assert.assertEquals( 200, statusCode );

    }

    @Test
    public void givenEmployeeDataInJsonServer_WhenRetrieved_ShouldMatchTheCount() {
        EmployeePayrollData[] arrayOfEmps = getEmployeeList();
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService( Arrays.asList(arrayOfEmps));
        long entries = employeePayrollService.countEntries( REST_IO );
        Assert.assertEquals( 5,entries);
    }

    @Test
    public void givenEmployeeToDelete_WhenDeleted_ShouldMatch200ResponseAndCount() {
        EmployeePayrollService employeePayrollService;
        EmployeePayrollData[] arrayOfEmps = getEmployeeList();
        employeePayrollService = new EmployeePayrollService( Arrays.asList( arrayOfEmps ) );
        EmployeePayrollData employeePayrollData = employeePayrollService.getEmployeePayrollData("Mark Zuckerberg");
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header( "Content-Type","application/json" );
        Response response = requestSpecification.delete("/employees/"+employeePayrollData.id);
        int statusCode = response.getStatusCode();
        Assert.assertEquals( 200, statusCode );
        employeePayrollService.deleteEmployeePayroll(employeePayrollData.name,REST_IO);
        long entries = employeePayrollService.countEntries( REST_IO );
        Assert.assertEquals( 2, entries );

    }
}

