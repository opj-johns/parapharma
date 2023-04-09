package com.example.parapharma.repository;

import com.example.parapharma.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByUsername(String username);
}
