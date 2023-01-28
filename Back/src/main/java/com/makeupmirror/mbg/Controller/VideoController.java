package com.makeupmirror.mbg.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.makeupmirror.mbg.Service.UserService;
import com.makeupmirror.mbg.Service.VideoService;
import com.makeupmirror.mbg.mapper.UserMapper;
import com.makeupmirror.mbg.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    @Resource
    private VideoService videoService;

    @PostMapping("/list")
    @ResponseBody
    public PageInfo<String> videoList(@RequestParam(defaultValue = "0", value = "pagenum") int pageNum,
                                      @RequestParam(defaultValue = "6", value = "pagesize") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(videoService.videoList(pageNum, pageSize));
    }

    @PostMapping("/recommend")
    @ResponseBody
    public PageInfo<String> videoListRecommend(@RequestParam(defaultValue = "0", value = "pagenum") int pageNum,
                                               @RequestParam(defaultValue = "6", value = "pagesize") int pageSize,
                                               @RequestParam(value = "isFace") boolean isFace,
                                               @RequestParam(value = "isEye") boolean isEye,
                                               @RequestParam(value = "isMouth") boolean isMouth,
                                               @RequestParam(value = "isNose") boolean isNose,
                                               @RequestParam(value = "userId") int userId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(videoService.videoListRecommend(pageNum, pageSize, userId, isFace, isEye, isMouth, isNose));
    }

    @PostMapping("/title")
    @ResponseBody
    public String videoTitle(@RequestParam(value = "videoUrl") String videoUrl) {
        return this.videoService.videoTitle(videoUrl);
    }

    @PostMapping("/image")
    @ResponseBody
    public String videoImage(@RequestParam(value = "videoUrl") String videoUrl) {
        return this.videoService.videoImage(videoUrl);
    }

    @PostMapping("/id")
    @ResponseBody
    public Integer videoId(@RequestParam(value = "videoUrl") String videoUrl) {
        return this.videoService.videoId(videoUrl);
    }

    @PostMapping("/detail")
    @ResponseBody
    public List<String> videoDetail(@RequestParam(value = "videoUrl") String videoUrl) {
        return this.videoService.videoDetail(videoUrl);
    }
}
