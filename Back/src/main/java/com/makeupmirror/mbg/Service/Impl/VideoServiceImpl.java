package com.makeupmirror.mbg.Service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.makeupmirror.mbg.Service.VideoService;
import com.makeupmirror.mbg.mapper.VideoMapper;
import com.makeupmirror.mbg.mapper.UserMapper;
import com.makeupmirror.mbg.pojo.Video;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> videoList(int pageNum, int pageSize) {
        List<String> videoPart = this.videoMapper.listAllVideo();
        int pageStart = (pageNum - 1) * pageSize;
        List<String> coverPart = this.videoMapper.listVideoCover(pageStart, pageSize);
        List<String> videoList = ListUtils.sum(videoPart, coverPart);
        return videoList;
    }

    @Override
    public List<String> videoListRecommend(int pageNum, int pageSize, int userId, boolean isFace, boolean isEye,
                                           boolean isMouth, boolean isNose) {
        List<String> videoPart = this.videoMapper.listAllVideo();

        /*
        获取用户的面部数据
         */
//        System.out.println("---"+userId+"---");
        String userFacialData = this.userMapper.getFacialData(userId);
//        System.out.println("---"+userId+"---");
//        System.out.println(userFacialData);
        List<String> userFaceAttribute = facialDataToFaceAttribute(userFacialData);

        /*
        获取视频的面部数据
         */
        int pageStart = (pageNum - 1) * pageSize;
        List<String> facialDataPart = this.videoMapper.listVideoFacialData(pageStart, pageSize);
        List<Integer> idPart = this.videoMapper.listVideoId(pageStart, pageSize);
        Queue<Integer[]> videoIdPQueue = new PriorityQueue<>(
                (e1, e2) -> {
                    if (e1[1] == e2[1]) return e1[2] - e2[2];
                    return e1[1] - e2[1];
                });
        int iMax = facialDataPart.size();
        for (int i = 0; i < iMax; i++) {
            String facialData = facialDataPart.get(i);
            Integer id = idPart.get(i);
            if (facialData != null) {
                List<String> faceAttribute = facialDataToFaceAttribute(facialData);
                List<Integer> faceAttributeScore = calculateFaceAttributeScore(userFaceAttribute, faceAttribute);
                int ttlScore = calculateTotalScore(faceAttributeScore, isFace, isEye, isMouth, isNose);
                videoIdPQueue.add(new Integer[]{id, ttlScore, new Random().nextInt()});
            }
        }
        List<String> videoList = new LinkedList<>();
        for (int i = 0; i < iMax; i++) {
            int id = videoIdPQueue.remove()[0];
            videoList.add(this.videoMapper.getVideoUrlById(id));
            videoList.add(this.videoMapper.getVideoCoverById(id));
        }
        return videoList;
    }

    private List<String> facialDataToFaceAttribute(String facialData) {
        JSONObject facialDataJson = JSONObject.parseObject(facialData);
        JSONObject faceAttributesJson = facialDataJson.getJSONArray("media_info_list")
                                                      .getJSONObject(0)
                                                      .getJSONObject("media_extra")
                                                      .getJSONArray("faces")
                                                      .getJSONObject(0)
                                                      .getJSONObject("face_attributes");
        List<String> faceAttribute = new ArrayList<>(8);
        faceAttribute.add(faceAttributesJson.getJSONObject("cheek_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("eye_distance").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("eye_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("face_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("jaw_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("mouth_thickness").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("nose_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("lip_peak").getString("value"));
        return faceAttribute;
    }

    private List<Integer> calculateFaceAttributeScore(List<String> userFaceAttribute, List<String> faceAttribute) {
        List<Integer> faceAttributeScore = new ArrayList<>(4);
        faceAttributeScore.add(0);
        faceAttributeScore.add(0);
        faceAttributeScore.add(0);
        faceAttributeScore.add(0);

        if (userFaceAttribute.get(3).equals(faceAttribute.get(3))) ;
        else if (((userFaceAttribute.get(3).equals("ga01") || userFaceAttribute.get(3).equals("ga02")) &&
                (faceAttribute.get(3).equals("ga01") || faceAttribute.get(3).equals("ga02")))
                || ((userFaceAttribute.get(3).equals("ga03") || userFaceAttribute.get(3).equals("ga04")) &&
                (faceAttribute.get(3).equals("ga03") || faceAttribute.get(3).equals("ga04")))
                || ((userFaceAttribute.get(3).equals("ga05") || userFaceAttribute.get(3).equals("ga06")) &&
                (faceAttribute.get(3).equals("ga05") || faceAttribute.get(3).equals("ga06")))
                || ((userFaceAttribute.get(3).equals("ga07") || userFaceAttribute.get(3).equals("ga08")) &&
                (faceAttribute.get(3).equals("ga07") || faceAttribute.get(3).equals("ga08")))
                || ((userFaceAttribute.get(3).equals("ga09") || userFaceAttribute.get(3).equals("ga10")) &&
                (faceAttribute.get(3).equals("ga09") || faceAttribute.get(3).equals("ga10"))))
            faceAttributeScore.set(0, faceAttributeScore.get(0) + 1);
        else faceAttributeScore.set(0, faceAttributeScore.get(0) + 4);
        if (userFaceAttribute.get(0).equals(faceAttribute.get(0))) ;
        else faceAttributeScore.set(0, faceAttributeScore.get(0) + 1);
        if (userFaceAttribute.get(4).equals(faceAttribute.get(4))) ;
        else faceAttributeScore.set(0, faceAttributeScore.get(0) + 1);

        int a = 0, b = 0;

        if (userFaceAttribute.get(2).equals("ba01")) a = 0;
        else if (userFaceAttribute.get(2).equals("ba02")) a = -1;
        else if (userFaceAttribute.get(2).equals("ba03")) a = 2;
        else if (userFaceAttribute.get(2).equals("ba04")) a = -4;
        else a = -2;
        if (faceAttribute.get(2).equals("ba01")) b = 0;
        else if (faceAttribute.get(2).equals("ba02")) b = -1;
        else if (faceAttribute.get(2).equals("ba03")) b = 2;
        else if (faceAttribute.get(2).equals("ba04")) b = -4;
        else b = -2;
        faceAttributeScore.set(1, faceAttributeScore.get(1) + Math.abs(a - b));
        if (userFaceAttribute.get(1).equals("ad01")) a = 1;
        else if (userFaceAttribute.get(1).equals("ad02")) a = -1;
        else a = 0;
        if (faceAttribute.get(1).equals("ad01")) b = 1;
        else if (faceAttribute.get(1).equals("ad02")) b = -1;
        else b = 0;
        faceAttributeScore.set(1, faceAttributeScore.get(1) + Math.abs(a - b));

        if (userFaceAttribute.get(5).equals("da01")) a = 0;
        else if (userFaceAttribute.get(5).equals("da02")) a = 1;
        else if (userFaceAttribute.get(5).equals("da03")) a = 2;
        else if (userFaceAttribute.get(5).equals("da04")) a = -1;
        else a = -2;
        if (faceAttribute.get(5).equals("da01")) b = 0;
        else if (faceAttribute.get(5).equals("da02")) b = 1;
        else if (faceAttribute.get(5).equals("da03")) b = 2;
        else if (faceAttribute.get(5).equals("da04")) b = -1;
        else b = -2;
        faceAttributeScore.set(2, faceAttributeScore.get(2) + Math.abs(a - b));
        if (userFaceAttribute.get(7).equals("db01")) a = 1;
        else if (userFaceAttribute.get(7).equals("db02")) a = -1;
        else a = 0;
        if (faceAttribute.get(7).equals("db01")) b = 1;
        else if (faceAttribute.get(7).equals("db02")) b = -1;
        else b = 0;
        faceAttributeScore.set(2, faceAttributeScore.get(2) + Math.abs(a - b));

        if (userFaceAttribute.get(6).equals("ca01")) a = -1;
        else if (userFaceAttribute.get(6).equals("ca02")) a = 0;
        else if (userFaceAttribute.get(6).equals("ca03")) a = 1;
        else a = 2;
        if (faceAttribute.get(6).equals("ca01")) b = -1;
        else if (faceAttribute.get(6).equals("ca02")) b = 0;
        else if (faceAttribute.get(6).equals("ca03")) b = 1;
        else b = 2;
        faceAttributeScore.set(3, faceAttributeScore.get(3) + Math.abs(a - b));

        return faceAttributeScore;
    }

    private int calculateTotalScore(List<Integer> faceAttributeScore, boolean isFace, boolean isEye,
                                    boolean isMouth, boolean isNose) {
        int ttlScore = 0;
        if (isFace) ttlScore += faceAttributeScore.get(0)*20; // 6
        if (isEye) ttlScore += faceAttributeScore.get(1)*9; // 8
        if (isMouth) ttlScore += faceAttributeScore.get(2)*4; // 6
        if (isNose) ttlScore += faceAttributeScore.get(3)*8; // 3
        // ttl = 20a+9b+4c+8d
        return ttlScore;
    }

    @Override
    public String videoTitle(String videoUrl) {
        System.out.println(videoUrl);
        String videoTitle = videoMapper.getVideoTitle(videoUrl);
        System.out.println(videoTitle);
        return videoTitle;
    }

    @Override
    public String videoImage(String videoUrl) {
        System.out.println(videoUrl);
        String videoImage = videoMapper.getVideoImage(videoUrl);
        System.out.println(videoImage);
        return videoImage;
    }

    @Override
    public Integer videoId(String videoUrl) {
        System.out.println(videoUrl);
        Integer videoId = videoMapper.getVideoId(videoUrl);
        System.out.println(videoId);
        return videoId;
    }

    @Override
    public List<String> videoDetail(String videoUrl) {
        System.out.println(videoUrl);
        List<String> videoDetail = new ArrayList<>();

        String videoTitle = videoMapper.getVideoTitle(videoUrl);
        if (videoTitle != null){
            videoDetail.add(videoTitle);
        }
        String collectionNumber = videoMapper.getVideoCollectionNumber(videoUrl);
        if (collectionNumber != null){
            videoDetail.add(collectionNumber);
        }
        String description = videoMapper.getVideoDescription(videoUrl);
        if (description != null){
            videoDetail.add(description);
        }
        String author = videoMapper.getVideoAuthor(videoUrl);
        if (videoTitle != null){
            videoDetail.add(author);
        }
        Long likesNumber = videoMapper.getVideoLikesNumber(videoUrl);
        if (likesNumber != null){
            videoDetail.add(Long.toString(likesNumber));
        }
        Long viewNumber = videoMapper.getVideoViewNumber(videoUrl);
        if (viewNumber != null){
            videoDetail.add(Long.toString(viewNumber));
        }
        Long commentNumber = videoMapper.getVideoCommentNumber(videoUrl);
        if (commentNumber != null){
            videoDetail.add(Long.toString(commentNumber));
        }
        String facialData = videoMapper.getVideoFacialdata(videoUrl);
        if (facialData != null){
            videoDetail.add(facialData);
        }
        String section = videoMapper.getVideoSection(videoUrl);
        if (section != null){
            videoDetail.add(section);
        }
        System.out.print(videoDetail);
        return videoDetail;
    }
}
