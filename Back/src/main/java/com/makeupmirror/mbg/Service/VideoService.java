package com.makeupmirror.mbg.Service;

import com.makeupmirror.mbg.pojo.Video;

import java.util.List;

public interface VideoService {

    List<String> videoList(int pagenum, int pagesize);

    List<String> videoListRecommend(int pagenum, int pagesize, int userId, boolean isFace, boolean isEye,
                                    boolean isMouth, boolean isNose);

    String videoTitle(String videoUrl);

    String videoImage(String videoUrl);

    Integer videoId(String videoUrl);

    List<String> videoDetail(String videoUrl);
}
