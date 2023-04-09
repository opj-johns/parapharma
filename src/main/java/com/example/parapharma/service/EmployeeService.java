package com.example.parapharma.service;

import com.example.parapharma.domain.Employee;
import com.example.parapharma.domain.datamodels.LoginCredentials;
import com.example.parapharma.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    // provide the repository to the service, create employee repo variable
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    // dependency injection
    public EmployeeService(EmployeeRepository employeeRepository){
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.employeeRepository = employeeRepository;
    }

    // method or function to get all employees
    public List<Employee> getAll(){
        return this.employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee){
        String encodedPassword = this.passwordEncoder.encode("kenham");
        employee.setPassword(encodedPassword);
       return this.employeeRepository.save(employee);
    }


    public Employee fetchEmployee(Long id){
        return this.employeeRepository.getReferenceById(id);
    }

    public void deleteEmployee(Employee employee){
        this.employeeRepository.delete(employee);
    }

    public Employee verifyCredentials(LoginCredentials credential){
        Employee employee = this.employeeRepository.findEmployeeByUsername(credential.getUsername());
        if(employee!=null){
            Boolean isValid = this.passwordEncoder
                    .matches(credential.getPassword(), employee.getPassword());
            if(isValid){
                return employee;
            }
        }
        Employee fakeEmployee = new Employee();
        fakeEmployee.setUsername("fakeEmployee");
        return fakeEmployee;
    }


}
