package com.sapient.ms.learning.attendanceproducersvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@PrimaryKeyClass
public class AttendanceKey {
    @PrimaryKeyColumn(name = "emp_id", type = PrimaryKeyType.PARTITIONED)
    int empId;

    @PrimaryKeyColumn(name = "swipe_type", type = PrimaryKeyType.CLUSTERED)
    String swipeType;

    @PrimaryKeyColumn(name = "swipe_time", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    Timestamp swipeTime;


}
