package com.sapient.ms.learning.attendanceproducersvc.controller;

import com.sapient.ms.learning.attendanceproducersvc.kafkamessage.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ApacheKafkaProducer {
    @Autowired
    KafkaTemplate<String, Attendance> kafkaTemplate;

    private static final String TOPIC = "NewTopic6";

    //Dummy Endpoint for testing purpose
    @GetMapping("/produce")
    public ResponseEntity produce() {
        Attendance attendance = new Attendance(50, "Present", new Date(System.currentTimeMillis()), 8*60,"No Remark");
        // kafkaTemplate.send(TOPIC, "This remark is from attendance producer");
        kafkaTemplate.send(TOPIC, attendance);
        return ResponseEntity.ok().build();
    }
}
