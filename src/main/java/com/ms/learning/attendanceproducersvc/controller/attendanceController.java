package com.ms.learning.attendanceproducersvc.controller;

import com.ms.learning.attendanceproducersvc.entity.AttendanceEntity;
import com.ms.learning.attendanceproducersvc.service.AttendanceService;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping(path = {"attendance"})
public class attendanceController {
    Logger logger = LoggerFactory.getLogger(attendanceController.class);

    @Autowired
    AttendanceService attendanceService;

    //http://localhost:8080/attendance/swipe-in/2
    @PostMapping("swipe-in/{emp-id}")
    public ResponseEntity swipeIn(@PathVariable("emp-id") int empId) {
        logger.info("Inside swipIn with empId : {}", empId);
        AttendanceEntity response = attendanceService.saveSwipeIn(empId);
        Optional<AttendanceEntity> optionalResponse = Optional.of(response);
        return ResponseEntity.ok(optionalResponse.isPresent() ? "empId:" +
                optionalResponse.get().getAttendanceKey().getEmpId() : "Data not Saved");
    }

    //http://localhost:8080/attendance/swipe-out/2
    @PostMapping("swipe-out/{emp-id}")
    public ResponseEntity swipeOut(@PathVariable("emp-id") int empId) {
        logger.info("Inside swipeOut with empId : {}", empId);
        AttendanceEntity response = attendanceService.saveSwipeOut(empId);
        Optional<AttendanceEntity> optionalResponse = Optional.of(response);
        return ResponseEntity.ok(optionalResponse.isPresent() ? "empId:" +
                optionalResponse.get().getAttendanceKey().getEmpId() : "Data not Saved");
    }

    //http://localhost:8080/attendance/total-hours/1
    @GetMapping("total-hours/{emp-id}")
    public ResponseEntity totalHours(@PathVariable("emp-id") int empId) {
        logger.info("Inside totalHours with empId : {}", empId);
        Map<Field.Str,Object> map = attendanceService.getTotalHors(empId);
        attendanceService.sendDataToKafkaka((double)map.get("totalMins"),empId,(Date) map.get("date"));
        return ResponseEntity.ok("Total Mins:"+(double)map.get("totalMins"));
    }


}
