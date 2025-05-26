package org.nettadx.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long attendanceId;

  @Column(nullable = false)
  private LocalTime clockInTime;

  private LocalTime clockOutTime;

  @Column(nullable = false)
  private String status; // "PRESENT", "ABSENT", "LATE", etc.

  @Column(nullable = false)
  private Long employeeId;

  @Column(nullable = false)
  private LocalDate date;

  public Attendance() {}

  public Attendance(LocalTime clockInTime, LocalDate date, String status, Long employeeId) {
    this.clockInTime = clockInTime;
    this.status = status;
    this.employeeId = employeeId;
    this.date = date;
  }

  public Long getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(Long attendanceId) {
    this.attendanceId = attendanceId;
  }

  public LocalTime getClockInTime() {
    return clockInTime;
  }

  public void setClockInTime(LocalTime clockInTime) {
    this.clockInTime = clockInTime;
  }

  public LocalTime getClockOutTime() {
    return clockOutTime;
  }

  public void setClockOutTime(LocalTime clockOutTime) {
    this.clockOutTime = clockOutTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status){
    this.status = status;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Attendance that)) return false;
    return Objects.equals(attendanceId, that.attendanceId) && Objects.equals(clockInTime, that.clockInTime) && Objects.equals(clockOutTime, that.clockOutTime) && Objects.equals(status, that.status) && Objects.equals(employeeId, that.employeeId) && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attendanceId, clockInTime, clockOutTime, status, employeeId, date);
  }
}
