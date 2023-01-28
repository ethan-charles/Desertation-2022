package com.makeupmirror.mbg.mapper;

import com.makeupmirror.mbg.pojo.User;
import com.makeupmirror.mbg.pojo.Video;
import com.makeupmirror.mbg.utils.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface  VideoMapper {

    @Select("select video_url,video_id from video ORDER BY `video_id`")
    List<String> listAllVideo();

    @Select("select video_id from video ORDER BY `video_id` Limit #{pageStart}, #{pageSize}")
    List<Integer> listVideoId(@Param("pageStart") int pageStart,
                              @Param("pageSize") int pageSize);

    @Select("select video_url,video_id from video ORDER BY `video_id` Limit #{pageStart}, #{pageSize}")
    List<String> listVideoUrl(@Param("pageStart") int pageStart,
                              @Param("pageSize") int pageSize);

    @Select("select video_image, video_id from video ORDER BY `video_id` Limit #{pageStart}, #{pageSize}")
    List<String> listVideoCover(@Param("pageStart") int pageStart,
                                @Param("pageSize") int pageSize);

    @Select("select video_facialdata, video_id from video ORDER BY `video_id` Limit #{pageStart}, #{pageSize}")
    List<String> listVideoFacialData(@Param("pageStart") int pageStart,
                                     @Param("pageSize") int pageSize);

    @Select("SELECT video_url FROM video WHERE video_id = #{videoId}")
    String getVideoUrlById(@Param("videoId") int videoId);

    @Select("SELECT video_image FROM video WHERE video_id = #{videoId}")
    String getVideoCoverById(@Param("videoId") int videoId);

    @Select("SELECT video_title FROM video WHERE video_url = #{videoUrl}")
    String getVideoTitle(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_image FROM video WHERE video_url = #{videoUrl}")
    String getVideoImage(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_id FROM video WHERE video_url = #{videoUrl}")
    Integer getVideoId(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_collection_number FROM video WHERE video_url = #{videoUrl}")
    String getVideoCollectionNumber(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_descrption FROM video WHERE video_url = #{videoUrl}")
    String getVideoDescription(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_author FROM video WHERE video_url = #{videoUrl}")
    String getVideoAuthor(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_likes_number FROM video WHERE video_url = #{videoUrl}")
    Long getVideoLikesNumber(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_view_number FROM video WHERE video_url = #{videoUrl}")
    Long getVideoViewNumber(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_comment_number FROM video WHERE video_url = #{videoUrl}")
    Long getVideoCommentNumber(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_facialdata FROM video WHERE video_url = #{videoUrl}")
    String getVideoFacialdata(@Param("videoUrl") String videoUrl);

    @Select("SELECT video_section FROM video WHERE video_url = #{videoUrl}")
    String getVideoSection(@Param("videoUrl") String videoUrl);
}
