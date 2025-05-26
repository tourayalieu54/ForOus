package org.nettadx.security;
import org.nettadx.exception.ResourceNotFoundException;
import org.nettadx.models.AppUser;
import org.nettadx.models.Employee;
import org.nettadx.repositories.AppUserRepository;
import org.nettadx.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final AppUserRepository userRepository;
  private final EmployeeRepository employeeRepository;

  public CustomUserDetailsService(AppUserRepository userRepository, EmployeeRepository employeeRepository) {
    this.userRepository = userRepository;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
    AppUser user = userRepository.findUserByEmailAddress(emailAddress);

    if(user != null) {
      return new CustomAppUserDetails(user);
    }
    return null;
  }

  public void addUser(AppUser user) throws UsernameNotFoundException{
    //Checking if the employee exist before creating an account for him
    Employee employee = employeeRepository.findByEmailAddress(user.getEmailAddress());
    if(employee == null) {
      throw new UsernameNotFoundException("No employee exist with that email address!");
    }
    if(employee.getPosition().toLowerCase().contains("ceo"))
    {
      user.setRole("ADMIN");
    }else{
      user.setRole("STAFF");
    }
    user.setEmployee(employee);
    this.userRepository.save(user);
  }
}
