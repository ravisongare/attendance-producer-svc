package com.sapient.ms.learning.attendanceproducersvc.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("attendance")
public class Attendance {

    @PrimaryKey
    AttendanceKey attendanceKey;


}

