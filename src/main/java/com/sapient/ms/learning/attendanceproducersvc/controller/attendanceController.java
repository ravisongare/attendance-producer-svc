package com.sapient.ms.learning.attendanceproducersvc.controller;

import com.sapient.ms.learning.attendanceproducersvc.entity.Attendance;
import com.sapient.ms.learning.attendanceproducersvc.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = {"attendance"})
public class attendanceController {
    Logger logger = LoggerFactory.getLogger(attendanceController.class);

    @Autowired
    AttendanceService attendanceService;

    //http://localhost:8080/attendance/swipe-in/2
    @PostMapping("swipe-in/{emp-id}")
    public ResponseEntity swipeIn(@PathVariable("emp-id") int empId) {
        logger.info("Inside swipIn with empId : {}", empId);
       Attendance response = attendanceService.saveSwipeIn(empId);
        Optional<Attendance> optionalResponse= Optional.of(response);
        return ResponseEntity.ok(optionalResponse.isPresent()?"empId:"+
                optionalResponse.get().getAttendanceKey().getEmpId():"Data not Saved");
    }

    //http://localhost:8080/attendance/swipe-out/2
    @PostMapping("swipe-out/{emp-id}")
    public ResponseEntity swipeOut(@PathVariable("emp-id") int empId) {
        logger.info("Inside swipeOut with empId : {}", empId);
        Attendance response = attendanceService.saveSwipeOut(empId);
       Optional<Attendance> optionalResponse= Optional.of(response);
        return ResponseEntity.ok(optionalResponse.isPresent()?"empId:"+
                optionalResponse.get().getAttendanceKey().getEmpId():"Data not Saved");
    }

    //http://localhost:8080/attendance/total-hours/1
    @GetMapping("total-hours/{emp-id}")
    public ResponseEntity totalHours(@PathVariable("emp-id") int empId) {
        logger.info("Inside totalHours with empId : {}", empId);
        double totalHours = attendanceService.getTotalHors(empId);
        return ResponseEntity.ok(totalHours);
    }
}
