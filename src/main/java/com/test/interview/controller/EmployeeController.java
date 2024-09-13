package com.test.interview.controller;

import com.test.interview.dto.EmployeeDTO;
import com.test.interview.entity.Employee;
import com.test.interview.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1.0")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/api/employees")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeDTO employee) {
        Employee savedEmployee = service.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/api/employees/{employeeId}/tax-deductions")
    public ResponseEntity<Double> calculateTax(@PathVariable String employeeId) {
        double tax = service.calculateEmployeeTax(employeeId);
        return ResponseEntity.ok(tax);
    }

    @GetMapping("/api/getEmployee")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(service.getAllEmployee());
    }
    @GetMapping("/message")
    public String message(){
        return "Hello!";
    }

}
