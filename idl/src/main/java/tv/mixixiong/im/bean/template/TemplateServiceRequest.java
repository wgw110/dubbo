package tv.mixixiong.im.bean.template;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.mixixiong.im.bean.ImCommonParam;
import tv.mixixiong.im.bean.PushInfo;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateServiceRequest implements Serializable {

    private int type;

    private SinglePictureTemplate singlePictureTemplate;

    private MultiplePictureTemplate multiplePictureTemplate;

    private NoticeTemplate noticeTemplate;

    private BaseTemplate template;

    private String targets;

    private int channel;

    private boolean needPush;

    private PushInfo pushInfo;

    private ImCommonParam imCommonParam;

    private Integer productId;

    private String tags;
}
