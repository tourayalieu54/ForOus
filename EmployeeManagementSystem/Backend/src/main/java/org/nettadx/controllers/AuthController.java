package org.nettadx.controllers;
import org.nettadx.models.AppUser;
import org.nettadx.repositories.AppUserRepository;
import org.nettadx.security.CustomUserDetailsService;
import org.nettadx.security.JwtUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AppUserRepository userRepository;
  private final CustomUserDetailsService service;

  public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AppUserRepository userRepository, CustomUserDetailsService service){
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
    this.service = service;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody AppUser authRequest) {
    System.out.println("The user details for login are: username: " + authRequest.getEmailAddress() + " and password: " + authRequest.getPassword());
    try {
      // Authenticate the user
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getEmailAddress(), authRequest.getPassword()));

      // Fetch the user details (including role and id) from the database
      AppUser user = userRepository.findUserByEmailAddress(authRequest.getEmailAddress());  // Adjust based on your repository
      if (user == null) {
        throw new RuntimeException("User not found");
      }

      // Generate token using the user's id, emailAddress, and role
      String token = jwtUtil.generateToken(user.getId(), user.getEmailAddress(), user.getRole());
      token = token.trim();

      // Prepare the response
      Map<String, String> response = new HashMap<>();
      response.put("token", token);

      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      System.out.println(e);
      throw new RuntimeException("Invalid username/password");
    }
  }


  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody AppUser newUser) throws DataAccessException, UsernameNotFoundException{
    //Check if the user exists
    AppUser user = userRepository.findUserByEmailAddress(newUser.getEmailAddress());
    if(user != null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    try{
      service.addUser(newUser);
    }catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
