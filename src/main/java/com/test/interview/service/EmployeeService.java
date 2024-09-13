package com.test.interview.service;

import com.test.interview.dao.EmployeeRepository;
import com.test.interview.dto.EmployeeDTO;
import com.test.interview.entity.Employee;
import com.test.interview.entity.EmployeePhoneNumbers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;
    @Transactional
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDoj(employeeDTO.getDateOfJoining());
        employee.setSalary(employeeDTO.getSalary());
        employee.setEmployeeId(employeeDTO.getEmployeeId());

        // Add phone numbers
        List<EmployeePhoneNumbers> phoneNumbers = employeeDTO.getPhoneNumbers().stream()
                .map(phone -> {
                    EmployeePhoneNumbers phoneNumber = new EmployeePhoneNumbers();
                    phoneNumber.setPhoneNumber(phone);
                    phoneNumber.setEmployee(employee);
                    return phoneNumber;
                })
                .collect(Collectors.toList());

        employee.setPhoneNumbers(phoneNumbers);

        // Save the employee, phone numbers will be saved due to CascadeType.ALL
        return employeeRepo.save(employee);
    }

    public List<Employee> getAllEmployee(){
        return employeeRepo.findAll();
    }

    public double calculateEmployeeTax(String employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate currentDate = LocalDate.now();
        long monthsWorked = ChronoUnit.MONTHS.between(employee.getDoj(), currentDate);

        if (monthsWorked > 12) monthsWorked = 12;

        double yearlySalary = employee.getSalary() * monthsWorked;

        double tax = 0;

        if (yearlySalary <= 250000) {
            return tax; // No tax
        }

        if (yearlySalary > 250000 && yearlySalary <= 500000) {
            tax = (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary > 500000 && yearlySalary <= 1000000) {
            tax = (250000 * 0.05) + (yearlySalary - 500000) * 0.1;
        } else if (yearlySalary > 1000000) {
            tax = (250000 * 0.05) + (500000 * 0.1) + (yearlySalary - 1000000) * 0.2;
        }

        if (yearlySalary > 2500000) {
            double cess = (yearlySalary - 2500000) * 0.02;
            tax += cess;
        }
        return tax;
    }

}