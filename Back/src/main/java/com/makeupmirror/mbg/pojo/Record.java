package com.makeupmirror.mbg.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`record`")
public class Record {
    @Id
    @Column(name = "record_id")
    private Integer recordId;

    @Column(name = "record_url")
    private String recordUrl;

    @Column(name = "record_time")
    private Date recordTime;

    @Column(name = "user_id")
    private Long userId;

    /**
     * @return record_id
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * @param recordId
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * @return record_url
     */
    public String getRecordUrl() {
        return recordUrl;
    }

    /**
     * @param recordUrl
     */
    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    /**
     * @return record_time
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * @param recordTime
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
