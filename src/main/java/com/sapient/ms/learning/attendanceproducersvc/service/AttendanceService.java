package com.sapient.ms.learning.attendanceproducersvc.service;

import com.sapient.ms.learning.attendanceproducersvc.SwipeTypeEnum;
import com.sapient.ms.learning.attendanceproducersvc.entity.Attendance;
import com.sapient.ms.learning.attendanceproducersvc.entity.AttendanceKey;
import com.sapient.ms.learning.attendanceproducersvc.respository.AttendnceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AttendanceService {

    Logger logger = LoggerFactory.getLogger(AttendanceService.class);
    @Autowired
    AttendnceRepository repository;

    public double getTotalHors(int empId) {
        logger.info("Inside getTotalHors() methitd with empId:{}",empId);
        Timestamp firstSwipeInTimestamp = getFirstSwipeInTimestamp(empId);
        List<Attendance> list;
        Timestamp lastSwipeOutTimestamp = getLastSwipeOutTimestamp(empId);
        return (lastSwipeOutTimestamp.getTime() - firstSwipeInTimestamp.getTime()) / (1000 * 60 * 60);

    }

    private Timestamp getLastSwipeOutTimestamp(int empId) {
        List<Attendance> list;
        list=repository.findByempIdAndswipeTypeDesc(empId,SwipeTypeEnum.SWIPE_OUT.name());
        Attendance attendance = list.get(0);

        Timestamp date2 = attendance.getAttendanceKey().getSwipeTime();

        logger.info("Date2:{}",date2);
        return date2;
    }

    private Timestamp getFirstSwipeInTimestamp(int empId) {
        logger.info("Retrieving first swipe in time empId:{}", empId);
        List<Attendance> list = repository.findByempIdAndswipeType(empId, SwipeTypeEnum.SWIPE_IN.name());
        logger.info("list:{}", list);
        Timestamp date1 = list.get(0).getAttendanceKey().getSwipeTime();
        logger.info("Date1:{}", date1);
        return date1;
    }

    public Attendance saveSwipeIn(int empId) {
        logger.info("Savin data into database");
        return repository.save(new Attendance(new AttendanceKey(empId, SwipeTypeEnum.SWIPE_IN.name(),
                new Timestamp(System.currentTimeMillis()))));
    }

    public Attendance saveSwipeOut(int empId) {
        logger.info("Savin data into database");
        return repository.save(new Attendance(new AttendanceKey(empId, SwipeTypeEnum.SWIPE_OUT.name(),
                new Timestamp(System.currentTimeMillis()))));
    }


}
