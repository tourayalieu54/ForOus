import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../service/employee.service';
import { AuthService } from '../../service/auth.service';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
import { AttendanceService } from '../../service/attendance.service';
import { getLocaleDateFormat } from '@angular/common';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './employee-dashboard.component.html',
  styleUrl: './employee-dashboard.component.css'
})
export class EmployeeDashboardComponent implements OnInit{
  employee: any;
  attendance: any = null;

  constructor(private employeeService: EmployeeService, private authService: AuthService, private router: Router, private attendanceService: AttendanceService){}

  ngOnInit(): void{
    this.fetchEmployee();
  }

  fetchEmployee(): void{
    const token = this.authService.getToken();
    const decodedToken = jwtDecode(token || '') as any;
    this.employeeService.findEmployeeByEmail(decodedToken.sub).subscribe({
      next: response => {
        console.log(response)
        this.employee = response;
        this.fetchAttendanceForClockOut();
        console.log('Employee fetched:', response);
      },
      error : error => {
        console.error('Error fetching employee by email:', error);
      }
    })
  }

  resign(): void{
    this.employeeService.deleteEmployee(this.employee.id).subscribe({
      next: next => {
        alert('Resignation submitted successfully, we are saddened to part with, wishing you all the best!');
        this.router.navigate(['/login'])
      }
    })
  }

  clockIn(employeeId: number): void{
    this.attendanceService.clockIn(employeeId).subscribe({
      next: response => {
        alert('Clocked in successfully!');
        console.log(response)
      },
      error: error => {
        alert('Error submitting your clock in request, try again!')
        console.log('Error clocking in: ', error)
      }
    })
  }

  fetchAttendanceForClockOut(){
    const today = new Date();
    const dateString = today.toISOString().split('T')[0]; // "YYYY-MM-DD"
    this.attendanceService.getAttendanceByEmployeeIdAndDate(this.employee.id, dateString).subscribe({
      next: response => {
      this.attendance = response;
      console.log('Attendance has been fetched and the attendance is: ', this.attendance);
      },
      error: error => {
      console.error('Error fetching attendance:', error);
      }
    })
  }

  clockOut(): void{
    this.attendanceService.clockOut(this.attendance.attendanceId).subscribe({
      next: response => {
        alert("You've successfully clocked out!")
      },
      error: error => {
        console.log('Clocking out attendance with ID: ', this.attendance.id);
        console.log('Error clocking out', error);
        alert('Error submitting your clock in request!')
      }
    })
  }

}
