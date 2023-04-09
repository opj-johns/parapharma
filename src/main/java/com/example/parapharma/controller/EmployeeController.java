package com.example.parapharma.controller;

import com.example.parapharma.domain.Employee;
import com.example.parapharma.domain.datamodels.LoginCredentials;
import com.example.parapharma.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/api/employee")
public class EmployeeController {

    // create employee service variable
    private EmployeeService employeeService;

    // use DI to assign employee service instance
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @RequestMapping("/all")
    public ResponseEntity<List<Employee>> fetchEmployees(){
        List<Employee> employees = this.employeeService.getAll() ;
        ResponseEntity responseEntity = new ResponseEntity(employees, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        Employee savedEmpl = this.employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmpl);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long emplId){
        Employee employee = this.employeeService.fetchEmployee(emplId);
        return ResponseEntity.ok(employee);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deleteEmpl(@RequestBody Employee empl){
        this.employeeService.deleteEmployee(empl);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody LoginCredentials credentials){
        Employee employee =  this.employeeService.verifyCredentials(credentials);
        return ResponseEntity.ok(employee);
    }





}
