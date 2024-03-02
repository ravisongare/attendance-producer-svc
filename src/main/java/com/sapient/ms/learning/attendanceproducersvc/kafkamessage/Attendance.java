package com.sapient.ms.learning.attendanceproducersvc.kafkamessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class Attendance {
    int empId;
    String attendanceStatus;
    Date date;
    double totalMins;
    String remark;
}
