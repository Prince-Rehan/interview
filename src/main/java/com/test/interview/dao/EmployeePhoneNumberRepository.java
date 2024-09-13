package com.test.interview.dao;

import com.test.interview.entity.EmployeePhoneNumbers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePhoneNumberRepository extends JpaRepository<EmployeePhoneNumbers,Long> {
}
