package org.nettadx.repositories;

import org.nettadx.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Employee findByEmailAddress(String emailAddress);
}
