package tv.mixiong.video.ds;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tv.mixiong.enums.MediaFormatEnum;
import tv.mixiong.enums.ProgramCreateType;
import tv.mixiong.enums.VideoObjectTypeEnum;
import tv.mixiong.video.dao.VideoDao;
import tv.mixiong.video.entity.Video;
import tv.mixiong.video.entity.VideoFormat;
import tv.mixiong.video.vo.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class VideoDs {

    @Autowired
    VideoDao videoDao;
    @Autowired
    VideoFormatDs videoFormatDs;

    public Integer saveVideo(List<VideoFileVo> videoFileVoList, Long objId, VideoObjectTypeEnum typeEnum) {
        return saveVideo(videoFileVoList, objId, typeEnum, null,null);
    }

    public Integer saveVideo(List<VideoFileVo> videoFileVoList, Long objId, VideoObjectTypeEnum typeEnum,
                             DownloadVideoInfo downloadVideoInfo, DownloadVideoInfo sdownloadVideoInfo) {
        int count = 0;
        if (videoFileVoList != null && videoFileVoList.size() > 0) {
            int orderIdx = 1;
            for (VideoFileVo videoFileVo : videoFileVoList) {
                Video video = new Video();
                BeanUtils.copyProperties(videoFileVo, video);
                video.setVid(String.valueOf(objId));
                video.setLiveId(objId);
                video.setOrderIdx(orderIdx);
                video.setCreateType(ProgramCreateType.USER_CREATE.getType());
                video.setType(typeEnum.type());
                video.setObjId(objId);
                video.setCoverUrl(videoFileVo.getImageUrl());
                if (downloadVideoInfo != null) {
                    video.setDownloadUrl(downloadVideoInfo.getUrl());
                    video.setDownloadSize(downloadVideoInfo.getSize());
                    video.setDownloadMd5(downloadVideoInfo.getMd5());
                }
                if (sdownloadVideoInfo != null) {
                    video.setSdownloadUrl(sdownloadVideoInfo.getUrl());
                    video.setSdownloadSize(sdownloadVideoInfo.getSize());
                    video.setSdownloadMd5(sdownloadVideoInfo.getMd5());
                    video.setDataLength(sdownloadVideoInfo.getDataLength());
                    video.setEncKey(sdownloadVideoInfo.getEncKey());
                    video.setMarsKey(sdownloadVideoInfo.getMarsKey());
                }
                save(video);
                List<VideoUrlVo> urls = videoFileVo.getPlaySet();
                if (urls != null && urls.size() > 0) {
                    for (VideoUrlVo url : urls) {
                        saveVideoFormat(url, objId, video.getId(), typeEnum);
                    }
                }
                count++;
                orderIdx++;
            }
        }
        return count;
    }

    public Long save(Video video) {
        videoDao.save(video);
        return video.getId();
    }

    public void saveVideoFormat(VideoUrlVo url, Long objId, Long videoId, VideoObjectTypeEnum typeEnum) {
        VideoFormat format = new VideoFormat();
        BeanUtils.copyProperties(url, format);
        format.setType(typeEnum.type());
        format.setObjId(objId);
        format.setVideoId(videoId);
        format.setLiveId(objId);
        format.setSize(url.getSize());
        format.setDefinition(0);
        format.setVbitrate(0);
        if (url.getRecordMediaType() == null) {
            // 默认MP4格式
            format.setMediaFormat(MediaFormatEnum.MP4.getFormat());
        } else {
            format.setMediaFormat(url.getRecordMediaType());
        }
        videoFormatDs.save(format);
    }

    public List<VideoVo> getVideos(Long itemId, VideoObjectTypeEnum typeEnum) {
        if (itemId == null) {
            return Lists.newArrayList();
        }
        List<Video> videos = videoDao.getByObjId(itemId, typeEnum.type());
        List<VideoFormat> videoFormats = videoFormatDs.getByObjId(itemId, typeEnum.type());
        List<VideoVo> videoVos = new ArrayList<>();
        for (Video video : videos) {
            VideoVo videoVo = new VideoVo();
            videoVo.setDuration(video.getDuration());
            videoVo.setType(video.getType());
            videoVo.setCoverUrl(video.getCoverUrl());
            videoVo.setCreateType(video.getCreateType());
            List<VideoFormatVo> videoFormatVos = new ArrayList<>();
            Iterator<VideoFormat> iterator = videoFormats.iterator();
            while (iterator.hasNext()) {
                VideoFormat videoFormat = iterator.next();
                if (videoFormat.getVideoId().equals(video.getId())) {
                    videoFormatVos.add(VideoFormatVo.from(videoFormat));
                    iterator.remove();
                }
            }
            //部分历史数据，存在只有video没有format的情况，导致播放失败。
            if(videoFormatVos.size()==0){
                continue;
            }

            VideoFormatVo downloadFormat = null;
            if (!StringUtils.isEmpty(video.getDownloadUrl())) {
                downloadFormat = new VideoFormatVo();
                downloadFormat.setUrl(video.getDownloadUrl());
                downloadFormat.setSize(video.getDownloadSize());
                downloadFormat.setMd5(video.getDownloadMd5());
            } else {
                Optional<VideoFormatVo> urlOptional = getDownloadFormat(videoFormatVos);
                if (urlOptional.isPresent()) {
                    downloadFormat = urlOptional.get();
                }
            }
            VideoFormatVo sFormat = null;
            if (!StringUtils.isEmpty(video.getSdownloadUrl())) {
                sFormat = new VideoFormatVo();
                sFormat.setUrl(video.getSdownloadUrl());
                sFormat.setSize(video.getSdownloadSize());
                sFormat.setMd5(video.getSdownloadMd5());
                sFormat.setEncKey(video.getEncKey());
                sFormat.setDataLength(video.getDataLength()+"");
                sFormat.setMarsKey(video.getMarsKey());
            }
            videoVo.setSFormat(sFormat);
            videoVo.setDownloadFormat(downloadFormat);
            videoVo.setFormats(videoFormatVos);
            videoVos.add(videoVo);
        }
        return videoVos;
    }

    private Optional<VideoFormatVo> getDownloadFormat(List<VideoFormatVo> videoFormats) {
        if (videoFormats != null) {
            for (VideoFormatVo formatVo : videoFormats) {
                //返回第一个mp4文件
                if (formatVo.getMediaFormat() == MediaFormatEnum.MP4.getFormat()) {
                    return Optional.of(formatVo);
                }
            }
        }
        return Optional.empty();
    }

    public void deleteVideoByObjId(Long objId, int type) {
        videoDao.deleteVideoByObjId(objId, type);
        videoFormatDs.deleteByObjId(objId, type);
    }
}
