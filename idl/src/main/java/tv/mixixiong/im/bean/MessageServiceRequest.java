package tv.mixixiong.im.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.mixixiong.im.bean.template.CommonMsg;

import java.io.Serializable;

/**
 * 消息服务请求对象
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageServiceRequest implements Serializable {

    private String targets;

    private int channel;

    private String program;

    private String channelMessages; // 频道消息

    private ImCommonParam imCommonParam;

    private boolean needAlert;

    private String picture;

    private String groupProgram;

    private String blackboard;

    private String material;

    @JSONField(name = "post_work")
    private String postWorks;

    private String group;

    private String applyJoinGroup;

    private String changeGroupMemberInfo;

    private String groupSystemMsg;

    private PushInfo pushInfo;

    private CommonMsg commonMsg;

    private Integer productId;

    private String tags;

    private Integer syncOtherMachine;

    @Override
    public String toString() {
        return "MessageServiceRequest{" +
                "targets='" + targets + '\'' +
                ", channel=" + channel +
                ", program='" + program + '\'' +
                ", channelMessages='" + channelMessages + '\'' +
                ", imCommonParam=" + imCommonParam +
                ", needAlert=" + needAlert +
                ", picture='" + picture + '\'' +
                ", groupProgram='" + groupProgram + '\'' +
                ", blackboard='" + blackboard + '\'' +
                ", material='" + material + '\'' +
                ", post_works='" + postWorks + '\'' +
                ", group='" + group + '\'' +
                ", applyJoinGroup='" + applyJoinGroup + '\'' +
                ", changeGroupMemberInfo='" + changeGroupMemberInfo + '\'' +
                ", groupSystemMsg='" + groupSystemMsg + '\'' +
                ", pushInfo=" + pushInfo +
                ", commonMsg=" + commonMsg +
                ", productId=" + productId +
                ", syncOtherMachine=" +syncOtherMachine+
                '}';
    }
}
