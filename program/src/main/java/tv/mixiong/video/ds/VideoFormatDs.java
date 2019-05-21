package tv.mixiong.video.ds;

import org.springframework.stereotype.Component;
import tv.mixiong.video.entity.VideoFormat;

import java.util.List;

@Component
public class VideoFormatDs {

    public void save(VideoFormat videoFormat){

    }

    public List<VideoFormat> getByObjId(Long itemId, int type) {
        return null;
    }

    public void deleteByObjId(Long objId, int type) {
//        videoFormatDao.deleteByObjId(objId, type);
    }
}
