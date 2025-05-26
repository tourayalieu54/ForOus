package org.nettadx.controllers;

import org.nettadx.models.Attendance;
import org.nettadx.repositories.AttendanceRepository;
import org.nettadx.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

  private final AttendanceRepository attendanceRepository;

  public AttendanceController(AttendanceRepository attendanceRepository) {
    this.attendanceRepository = attendanceRepository;
  }

  @GetMapping("/employee/{id}")
  public List<Attendance> getAllAttendanceOfAnEmployee(@PathVariable Long id) {
    return attendanceRepository.findByEmployeeId(id);
  }

  @GetMapping("/employee")
  public Attendance getAttendanceByDate(@RequestParam Long employeeId, @RequestParam String date) {
    return attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.parse(date)).stream().findFirst().orElse(null);
  }

  @PostMapping("/clock-in")
  public Attendance createAttendance(@RequestBody Attendance request) {
    Attendance attendance = new Attendance();
    attendance.setEmployeeId(request.getEmployeeId());
    System.out.println("The attendance is has an employee id of "+ attendance.getEmployeeId());
    attendance.setDate(LocalDate.now());
    attendance.setClockInTime(LocalTime.now());
    attendance.setStatus("Present");
    return attendanceRepository.save(attendance);
  }

  @PutMapping("/clock-out/{attendanceId}")
  public Attendance clockOut(@RequestBody Attendance request, @PathVariable Long attendanceId) {
    Attendance attendance = attendanceRepository.findById(attendanceId)
      .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + attendanceId));

    if (attendance.getClockOutTime() != null) {
      throw new IllegalStateException("Clock-out time is already set for this attendance record.");
    }

    attendance.setClockOutTime(LocalTime.now());
    return attendanceRepository.save(attendance);
  }

  @PutMapping("/edit")
  public Attendance updateAttendance(@RequestBody Attendance attendance) {
    return attendanceRepository.save(attendance);
  }
}
