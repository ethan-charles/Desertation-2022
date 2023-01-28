package com.makeupmirror.mbg.Service;

import com.makeupmirror.mbg.pojo.Record;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

public interface RecordService {

    List<String> backup(List<String> videoRecord, Integer userid) throws ParseException;

}
