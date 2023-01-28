package com.makeupmirror.mbg.Service.Impl;

import com.makeupmirror.mbg.Service.RecordService;
import com.makeupmirror.mbg.Service.VideoService;
import com.makeupmirror.mbg.mapper.RecordMapper;
import com.makeupmirror.mbg.mapper.UserMapper;
import com.makeupmirror.mbg.mapper.VideoMapper;
import com.makeupmirror.mbg.pojo.Record;
import com.makeupmirror.mbg.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<String> backup(List<String> videoRecord, Integer userid) throws ParseException {

        List<String> videoRecordurlcloud = this.recordMapper.findAllRecordurl(userid);
        List<Date> videoRecordtimecloud = this.recordMapper.findAllRecordtime(userid);

        int cloudlength = videoRecordtimecloud.size()*2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> videoRecordcloud = new ArrayList<>(cloudlength);

        for(int i = 0; i< cloudlength; i ++){
            if(i % 2 == 0){
                videoRecordcloud.add(videoRecordurlcloud.get(i/2));
            }else {
                videoRecordcloud.add(sdf.format(videoRecordtimecloud.get((1-1)/2)));
            }
        }

        List<String> total = new ArrayList<>();

        total.addAll(videoRecord);
        total.addAll(videoRecordcloud);

        for(int i = 0; i < total.size(); i += 2){
            String videourl = total.get(i);
            for (int j = i; j < total.size(); j += 2){
                if(videourl == total.get(j)){
                    total.remove(j);
                    total.remove(j+1);
                }
            }
        }

        if (upload(total,userid)){
            return total;
        }else {
            return null;
        }

    }

    public boolean upload(List<String> videoList, Integer userid) throws ParseException {

        List<String> videourl = new ArrayList<>();
        List<Date> videodate = new ArrayList<>();

        for(int i = 0; i < videoList.size(); i ++){
            if(i % 2 == 0){
                videourl.add(videoList.get(i));
            }else{
                videodate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(videoList.get(i)));
            }
        }
        this.recordMapper.uploadurl(userid, videourl);
        this.recordMapper.uploaddate(userid, videodate);

        return true;
    }

}
