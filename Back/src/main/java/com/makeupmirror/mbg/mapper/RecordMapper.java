package com.makeupmirror.mbg.mapper;

import com.makeupmirror.mbg.utils.MyMapper;
import com.makeupmirror.mbg.pojo.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface RecordMapper extends MyMapper<Record> {

    @Select("select record_url from record where user_id = #{userid}")
    List<String> findAllRecordurl(@Param("userid") Integer userid);

    @Select("select record_time from record where user_id = #{userid}")
    List<Date> findAllRecordtime(@Param("userid") Integer userid);

    @Update("update record_url from record where user_id = #{userid}, record_url = #{recordurl}")
    void uploadurl(@Param("userid") Integer userid, @Param("recordurl") List<String> recordurl);

    @Update("update record_url from record where user_id = #{userid}, record_time = #{recordtime}")
    void uploaddate(@Param("userid") Integer userid, @Param("recordtime") List<Date> recordtime);

}
