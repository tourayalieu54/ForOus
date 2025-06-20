package org.nettadx.repositories;

import org.nettadx.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  AppUser findUserByEmailAddress(String emailAddress);
}

