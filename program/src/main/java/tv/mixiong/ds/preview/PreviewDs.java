package tv.mixiong.ds.preview;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.dao.preview.PreviewDao;
import tv.mixiong.entity.preview.Preview;
import tv.mixiong.enums.MediaFormatEnum;
import tv.mixiong.enums.VideoObjectTypeEnum;
import tv.mixiong.enums.VideoStatus;
import tv.mixiong.video.ds.VideoDs;
import tv.mixiong.video.vo.VideoFileVo;
import tv.mixiong.video.vo.VideoUrlVo;

@Component
public class PreviewDs {

    @Autowired
    PreviewDao previewDao;

    @Autowired
    VideoDs videoDs;

    public void savePreview(Preview preview){

    }

    public void savePreviewVideo(Long previewId, int duration, String imageUrl, String url, int height, int width) {
        videoDs.deleteVideoByObjId(previewId, VideoObjectTypeEnum.PROGRAM_PREVIEW.type());
        VideoFileVo videoFileVo = new VideoFileVo();
        videoFileVo.setDuration(duration);
        videoFileVo.setImageUrl(imageUrl);
        videoFileVo.setStatus(VideoStatus.SAVE.value());

        VideoUrlVo videoUrlVo = new VideoUrlVo();
        videoUrlVo.setDefinition(0);
        videoUrlVo.setSize(0L);
        videoUrlVo.setUrl(url);
        videoUrlVo.setVbitrate(0);
        videoUrlVo.setRecordMediaType(MediaFormatEnum.MP4.getFormat());
        videoUrlVo.setVheight(height);
        videoUrlVo.setVwidth(width);
        videoFileVo.setPlaySet(Lists.newArrayList(videoUrlVo));
        videoDs.saveVideo(Lists.newArrayList(videoFileVo), previewId, VideoObjectTypeEnum.PROGRAM_PREVIEW);
    }

    public Preview queryByProgramId(Long programId){
        return null;
    }

    public void update(Preview preview){

    }
}
