package tv.mixiong.video.dao;

import org.springframework.stereotype.Repository;
import tv.mixiong.video.entity.Video;

import java.util.List;

@Repository
public class VideoDao {
    public void save(Video video){

    }

    public List<Video> getByObjId(long objId, int type) {

        return null;
    }

    public void deleteVideoByObjId(Long objId, int type){

    }
}
