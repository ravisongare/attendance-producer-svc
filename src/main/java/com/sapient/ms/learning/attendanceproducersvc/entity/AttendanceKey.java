package com.sapient.ms.learning.attendanceproducersvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import javax.naming.Name;
import java.sql.Timestamp;


//@Data
//@AllArgsConstructor
@PrimaryKeyClass
public class AttendanceKey {
    @PrimaryKeyColumn(name="emp_id",type = PrimaryKeyType.PARTITIONED)
    int empId;

    @PrimaryKeyColumn(name="swipe_type",type = PrimaryKeyType.CLUSTERED)
    String swipeType;

    public AttendanceKey(int empId, String swipeType, Timestamp swipeTime) {
        this.empId = empId;
        this.swipeType = swipeType;
        this.swipeTime = swipeTime;
    }

    @PrimaryKeyColumn(name="swipe_time",type = PrimaryKeyType.CLUSTERED,ordering = Ordering.ASCENDING)
    Timestamp swipeTime;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getSwipeType() {
        return swipeType;
    }

    public void setSwipeType(String swipeType) {
        this.swipeType = swipeType;
    }

    public Timestamp getSwipeTime() {
        return swipeTime;
    }

    public void setSwipeTime(Timestamp swipeTime) {
        this.swipeTime = swipeTime;
    }
}
