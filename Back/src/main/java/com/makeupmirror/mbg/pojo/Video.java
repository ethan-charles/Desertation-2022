package com.makeupmirror.mbg.pojo;

import javax.persistence.*;

@Table(name = "`video`")
public class Video {
    @Id
    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "video_title")
    private String videoTitle;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "video_collection_number")
    private Long videoCollectionNumber;

    @Column(name = "video_descrption")
    private String videoDescription;

    @Column(name = "video_author")
    private String videoAuthor;

    @Column(name = "video_image")
    private String videoImage;

    @Column(name = "video_view_number")
    private Long videoViewNumber;

    @Column(name = "video_likes_number")
    private Long videoLikesNumber;

    @Column(name = "video_comment_number")
    private Long videoCommentNumber;

    @Column(name = "video_section")
    private String videoSection;

    @Column(name = "video_facialdata")
    private String videoFacialdata;

    /**
     * @return video_id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * @param videoId
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    /**
     * @return video_title
     */
    public String getVideoTitle() {
        return videoTitle;
    }

    /**
     * @param videoTitle
     */
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    /**
     * @return video_url
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * @param videoUrl
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * @return video_collection_number
     */
    public Long getVideoCollectionNumber() {
        return videoCollectionNumber;
    }

    /**
     * @param videoCollectionNumber
     */
    public void setVideoCollectionNumber(Long videoCollectionNumber) {
        this.videoCollectionNumber = videoCollectionNumber;
    }

    /**
     * @return video_descrption
     */
    public String getvideoDescription() {
        return videoDescription;
    }

    /**
     * @param videoDescription
     */
    public void setvideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    /**
     * @return video_author
     */
    public String getVideoAuthor() {
        return videoAuthor;
    }

    /**
     * @param videoAuthor
     */
    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    /**
     * @return video_image
     */
    public String getVideoImage() {
        return videoImage;
    }

    /**
     * @param videoImage
     */
    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    /**
     * @return video_view_number
     */
    public Long getVideoViewNumber() {
        return videoViewNumber;
    }

    /**
     * @param videoViewNumber
     */
    public void setVideoViewNumber(Long videoViewNumber) {
        this.videoViewNumber = videoViewNumber;
    }

    /**
     * @return video_likes_number
     */
    public Long getVideoLikesNumber() {
        return videoLikesNumber;
    }

    /**
     * @param videoLikesNumber
     */
    public void setVideoLikesNumber(Long videoLikesNumber) {
        this.videoLikesNumber = videoLikesNumber;
    }

    /**
     * @return video_comment_number
     */
    public Long getVideoCommentNumber() {
        return videoCommentNumber;
    }

    /**
     * @param videoCommentNumber
     */
    public void setVideoCommentNumber(Long videoCommentNumber) {
        this.videoCommentNumber = videoCommentNumber;
    }

    public String getVideoSection() {
        return videoSection;
    }

    public void setVideoSection(String videoSection) {
        this.videoSection = videoSection;
    }

    /**
     * @return video_facialdata
     */
    public String getVideoFacialdata() {
        return videoFacialdata;
    }

    /**
     * @param videoFacialdata
     */
    public void setVideoFacialdata(String videoFacialdata) {
        this.videoFacialdata = videoFacialdata;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoCollectionNumber=" + videoCollectionNumber +
                ", videoDescription='" + videoDescription + '\'' +
                ", videoAuthor='" + videoAuthor + '\'' +
                ", videoImage='" + videoImage + '\'' +
                ", videoViewNumber=" + videoViewNumber +
                ", videoLikesNumber=" + videoLikesNumber +
                ", videoCommentNumber=" + videoCommentNumber +
                ", videoSection='" + videoSection + '\'' +
                ", videoFacialdata='" + videoFacialdata + '\'' +
                '}';
    }

    public Video(Long videoId, String videoTitle, String videoUrl, Long videoCollectionNumber, String videoDescription, String videoAuthor, String videoImage, Long videoViewNumber, Long videoLikesNumber, Long videoCommentNumber) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
        this.videoCollectionNumber = videoCollectionNumber;
        this.videoDescription = videoDescription;
        this.videoAuthor = videoAuthor;
        this.videoImage = videoImage;
        this.videoViewNumber = videoViewNumber;
        this.videoLikesNumber = videoLikesNumber;
        this.videoCommentNumber = videoCommentNumber;
    }

    public Video() {
    }

    public Video(Long videoId, String videoTitle, String videoUrl, String videoImage) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
        this.videoImage = videoImage;
    }
}