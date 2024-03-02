package com.sapient.ms.learning.attendanceproducersvc.service;

import com.sapient.ms.learning.attendanceproducersvc.entity.AttendanceEntity;
import com.sapient.ms.learning.attendanceproducersvc.entity.AttendanceKey;
import com.sapient.ms.learning.attendanceproducersvc.enums.AttendanceStatusEnum;
import com.sapient.ms.learning.attendanceproducersvc.enums.SwipeTypeEnum;
import com.sapient.ms.learning.attendanceproducersvc.kafkamessage.Attendance;
import com.sapient.ms.learning.attendanceproducersvc.respository.AttendnceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {

    Logger logger = LoggerFactory.getLogger(AttendanceService.class);
    @Autowired
    AttendnceRepository repository;

    @Autowired
    KafkaTemplate<String, Attendance> kafkaTemplate;

    private static final String TOPIC = "NewTopic6";


    public Map getTotalHors(int empId) {
        Map<String, Object> map = new HashMap<>();
        logger.info("Inside getTotalHors() methitd with empId:{}", empId);
        Timestamp firstSwipeInTimestamp = getFirstSwipeInTimestamp(empId);
        Date date = Date.valueOf(firstSwipeInTimestamp.toLocalDateTime().toLocalDate());
        Timestamp lastSwipeOutTimestamp = getLastSwipeOutTimestamp(empId);
        double totalMins = (lastSwipeOutTimestamp.getTime() - firstSwipeInTimestamp.getTime()) / (1000 * 60 );
        logger.info("Inside getTotalHors() totalHours : {}", totalMins);
        map.put("totalMins", totalMins);
        map.put("date", date);
        return map;

    }


    private Timestamp getLastSwipeOutTimestamp(int empId) {
        List<AttendanceEntity> list;
        list = repository.findByempIdAndswipeTypeDesc(empId, SwipeTypeEnum.SWIPE_OUT.name());
        AttendanceEntity attendance = list.get(0);

        Timestamp date2 = attendance.getAttendanceKey().getSwipeTime();

        logger.info("Date2:{}", date2);
        return date2;
    }

    private Timestamp getFirstSwipeInTimestamp(int empId) {
        logger.info("Retrieving first swipe in time empId:{}", empId);
        List<AttendanceEntity> list = repository.findByempIdAndswipeType(empId, SwipeTypeEnum.SWIPE_IN.name());
        logger.info("list:{}", list);
        Timestamp date1 = list.get(0).getAttendanceKey().getSwipeTime();
        logger.info("Date1:{}", date1);
        return date1;
    }

    public AttendanceEntity saveSwipeIn(int empId) {
        logger.info("Savin data into database");
        return repository.save(new AttendanceEntity(new AttendanceKey(empId, SwipeTypeEnum.SWIPE_IN.name(),
                new Timestamp(System.currentTimeMillis()))));
    }

    public AttendanceEntity saveSwipeOut(int empId) {
        logger.info("Savin data into database");
        return repository.save(new AttendanceEntity(new AttendanceKey(empId, SwipeTypeEnum.SWIPE_OUT.name(),
                new Timestamp(System.currentTimeMillis()))));
    }


    public void sendDataToKafkaka(double totalMins, int empId, Date date) {
        AttendanceStatusEnum attendanceStatus = getAttendanceStatus(totalMins/60);
        Attendance attendance = getAttndance(empId, attendanceStatus, date,totalMins);
        logger.info("Sending data to kafka : {}", attendance);
        kafkaTemplate.send(TOPIC, attendance);
        logger.info("sent data to kafka : {}");
    }

    private Attendance getAttndance(int empId, AttendanceStatusEnum attendanceStatus, Date date, double totalMins) {
        return new Attendance(empId, attendanceStatus.name(), date, totalMins,"");
    }

    private AttendanceStatusEnum getAttendanceStatus(double totalMins) {
       double totalHours = totalMins/60;

        if (totalHours < 4l) {
            return AttendanceStatusEnum.Absent;
        } else if (totalHours > 4l && totalHours < 8) {
            return AttendanceStatusEnum.Half_day;
        } else {
            return AttendanceStatusEnum.Present;
        }
    }

}