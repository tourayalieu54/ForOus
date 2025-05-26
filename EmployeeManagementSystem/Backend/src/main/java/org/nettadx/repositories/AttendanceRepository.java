package org.nettadx.repositories;

import org.nettadx.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
  List<Attendance> findByEmployeeId(Long id);
  List<Attendance> findByEmployeeIdAndDate(Long id, LocalDate date);
}
