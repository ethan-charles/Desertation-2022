package com.makeupmirror.mbg.Controller;

import com.makeupmirror.mbg.Service.RecordService;
import com.makeupmirror.mbg.Service.UserService;
import com.makeupmirror.mbg.mapper.UserMapper;
import com.makeupmirror.mbg.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    @Resource
    private RecordService recordService;

    @PostMapping ("/backup")
    @ResponseBody
    public List<String> backup(@RequestParam(value = "videoRecord") List<String> videoRecord,
                          @RequestParam(value = "userid") Integer userid) throws ParseException {

        List<String> backup = this.recordService.backup(videoRecord, userid);
        System.out.print(backup);
        return backup;
    }

}
