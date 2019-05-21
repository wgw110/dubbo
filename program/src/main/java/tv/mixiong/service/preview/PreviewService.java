package tv.mixiong.service.preview;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.ds.preview.PreviewDs;
import tv.mixiong.entity.preview.Preview;
import tv.mixiong.enums.VideoObjectTypeEnum;
import tv.mixiong.video.ds.VideoDs;
import tv.mixixiong.entity.ProgramFormBean;

@Component
public class PreviewService {

    @Autowired
    PreviewDs previewDs;
    @Autowired
    VideoDs videoDs;

    public void saveOrUpdatePreview(ProgramFormBean param,String passport) {
        if (StringUtils.isEmpty(param.getPreviewUrl())) {
            return;
        }
        Preview dbPreview = previewDs.queryByProgramId(param.getProgramId());
        if (dbPreview == null) {
            dbPreview = Preview.builder()
                    .previewUrl(param.getPreviewUrl())
                    .cover(param.getCover())
                    .passport(passport)
                    .programId(param.getProgramId()).build();
            // 保存后对象中会有本次的自增id
            previewDs.savePreview(dbPreview);
            previewDs.savePreviewVideo(dbPreview.getId(), param.getPreviewDuration(),
                    param.getCover(), param.getPreviewUrl(), param.getPreviewHeight(), param.getPreviewWidth());
        } else {
            videoDs.deleteVideoByObjId(dbPreview.getId(), VideoObjectTypeEnum.PROGRAM_PREVIEW.type());
            previewDs.savePreviewVideo(dbPreview.getId(), param.getPreviewDuration(),
                    param.getCover(), param.getPreviewUrl(), param.getPreviewHeight(), param.getPreviewWidth());
            previewDs.update(dbPreview);
        }
    }
}
