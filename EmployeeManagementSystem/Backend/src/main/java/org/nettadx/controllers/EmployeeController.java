package org.nettadx.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;
import org.nettadx.exception.ResourceNotFoundException;
import org.nettadx.models.AppUser;
import org.nettadx.models.Employee;
import org.nettadx.repositories.AppUserRepository;
import org.nettadx.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class EmployeeController {


  private final EmployeeRepository employeeRepository;
  private final AppUserRepository appUserRepository;

  public EmployeeController(EmployeeRepository employeeRepository, AppUserRepository appUserRepository){
    this.employeeRepository = employeeRepository;
    this.appUserRepository = appUserRepository;
  }


  // get all employees
  @GetMapping("/employees")
  public List<Employee> getAllEmployees(){
    return employeeRepository.findAll();
  }

  @GetMapping("/employees/email/{emailAddress}")
  public Employee getEmployeeByEmailAddress(@PathVariable String emailAddress){
    return employeeRepository.findByEmailAddress(emailAddress);
  }

  // create employee rest api
  @PostMapping("/employees")
  @Transactional
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeRepository.save(employee);
  }

  // get employee by id rest api
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
    return ResponseEntity.ok(employee);
  }

  // update employee rest api

  @PutMapping("/employees/{id}")
  @Transactional
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
    Employee employee = employeeRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
    if(!employee.getEmailAddress().equals(employeeDetails.getEmailAddress())){
      AppUser userAccount = appUserRepository.findUserByEmailAddress(employee.getEmailAddress());
      if(userAccount != null){
        userAccount.setEmailAddress(employeeDetails.getEmailAddress());
        appUserRepository.save(userAccount);
      }
    }
    employeeDetails.setId(id);
    employee.setFirstName(employeeDetails.getFirstName());
    employee.setLastName(employeeDetails.getLastName());
    employee.setEmailAddress(employeeDetails.getEmailAddress());
    employee.setMiddleName(employeeDetails.getMiddleName());
    employee.setPosition(employeeDetails.getPosition());

    Employee updatedEmployee = employeeRepository.save(employee);
    return ResponseEntity.ok(updatedEmployee);
  }

  // delete employee rest api
  @DeleteMapping("/employees/{id}")
  @Transactional
  public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
    Employee employee = employeeRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
    // delete the user account first if he/she has one
    AppUser userAccount = appUserRepository.findUserByEmailAddress(employee.getEmailAddress());
    if(userAccount!=null){
      appUserRepository.delete(userAccount);
    }
    employeeRepository.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }


}
