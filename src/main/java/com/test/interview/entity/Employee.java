package com.test.interview.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Employee ID is mandatory")
    @Pattern(regexp = "^[A-Z]{1}\\d{5}$", message = "Employee ID must follow the format X12345 (1 letter followed by 5 digits)")
    private String employeeId;

    @NotNull(message = "Required : First name")
    @Size(min = 1, message = "Cannot be empty")
    private String firstName;

    @NotNull(message = "Required : Last name")
    @Size(min = 1, message = "Cannot be empty")
    private String lastName;

    @NotNull(message = "Required : Email")
    @Email(message = "Invalid Format")
    private String email;


    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format : Date of joining should be in the format YYYY-MM-DD")
    private String doj;

    @NotNull(message = "Required : Salary")
    @Positive(message = "Required format : Negative not allowed")
    private Double salary;

    @ElementCollection
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeePhoneNumbers> phoneNumbers;

    public Temporal getDoj() {
        LocalDate localDate = LocalDate.parse(this.doj);
        return localDate;
    }
}
