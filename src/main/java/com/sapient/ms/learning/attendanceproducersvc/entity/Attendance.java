package com.sapient.ms.learning.attendanceproducersvc.entity;




import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

//@Data
//@AllArgsConstructor
@Table("attendance")
public class Attendance {

    @PrimaryKey
    AttendanceKey attendanceKey;

    public Attendance(AttendanceKey attendanceKey) {
        this.attendanceKey = attendanceKey;
    }

    public AttendanceKey getAttendanceKey() {
        return attendanceKey;
    }

    public void setAttendanceKey(AttendanceKey attendanceKey) {
        this.attendanceKey = attendanceKey;
    }
}

