package com.sapient.ms.learning.attendanceproducersvc.respository;

import com.sapient.ms.learning.attendanceproducersvc.entity.Attendance;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttendnceRepository extends CassandraRepository<Attendance, Integer> {

    @Query("select * from attendance where emp_id =?0")
    List<Attendance> findByempId(int empId);

    @Query("select * from attendance where swipe_type =?0")
    List<Attendance> findByempswipeType( String swipe_type);

//    @Query("select * from attendance where emp_id =1 and swipe_type = 'SWIPE_IN' LIMIT 1")
//    List<Attendance> findByempIdAndswipeType(int emp_id, String swipe_type);

    @Query("select * from attendance where emp_id =:emp_id and swipe_type = :swipe_type LIMIT 1")
    List<Attendance> findByempIdAndswipeType(int emp_id, String swipe_type);

    @Query("SELECT * FROM attendance where emp_id =:emp_id and swipe_type = :swipe_type order by swipe_time desc LIMIT 1")
    List<Attendance> findByempIdAndswipeTypeDesc(int emp_id, String swipe_type);
}
