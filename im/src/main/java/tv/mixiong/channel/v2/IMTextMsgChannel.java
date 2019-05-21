package tv.mixiong.channel.v2;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import tv.mixiong.channel.BaseChannel;
import tv.mixiong.channel.Channel;
import tv.mixiong.ds.service.im.TencentImDs;
import tv.mixiong.ds.service.im.co.ImMessageBody;
import tv.mixiong.ds.service.im.co.OfflinePushInfo;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.PushInfo;
import tv.mixixiong.im.bean.template.CommonMsg;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.enums.ChannelEnum;
import tv.mixixiong.im.response.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * im 普通文本消息
 *
 */
@Channel(name = ChannelEnum.IM_TEXT_MSG)
@Component
public class IMTextMsgChannel extends BaseChannel {

    @Autowired
    private TencentImDs tencentImDs;

    @Override
    public void send(int productId, List<String> targets, MessageServiceRequest messageRequest) {
        CommonMsg commonMsg = messageRequest.getCommonMsg();
        OfflinePushInfo offlinePushInfo = buildOfflinePushInfo(messageRequest);
        tencentImDs.batchSendMsg(productId, messageRequest.getImCommonParam().getAccount(), targets, ImMessageBody.fromText(commonMsg.getContent()), offlinePushInfo);
    }

    @Override
    public void sendByTemplate(int productId, List<String> targets, TemplateServiceRequest templateServiceRequest) {
        return;
    }

    @Override
    public void sendSingle(int productId, String receiver, MessageServiceRequest messageRequest) {
        CommonMsg commonMsg = messageRequest.getCommonMsg();
        OfflinePushInfo offlinePushInfo = buildOfflinePushInfo(messageRequest);
        tencentImDs.sendSingle(productId,messageRequest.getSyncOtherMachine(),messageRequest.getImCommonParam().getAccount(),receiver,ImMessageBody.fromText(commonMsg.getContent()), offlinePushInfo);
    }

    @Override
    public CommonResponse sendTemplateToAll(TemplateServiceRequest templateServiceRequest) {
        return null;
    }


    @Override
    public void sendToAll(MessageServiceRequest messageServiceRequest) {
        CommonMsg commonMsg = messageServiceRequest.getCommonMsg();
        OfflinePushInfo offlinePushInfo = buildOfflinePushInfo(messageServiceRequest);
        String tags = messageServiceRequest.getTags();
        Map<String, Object> condition = null;
        if (!StringUtils.isEmpty(tags)) {
            List<String> tagList = Lists.newArrayList(tags.split(","));
            condition = new HashMap<String, Object>() {{
                put("TagsAnd", tagList);
            }};
        }
        tencentImDs.sendToAll(messageServiceRequest.getImCommonParam().getAccount(), condition, ImMessageBody.fromText(commonMsg.getContent()), offlinePushInfo);
    }


    private OfflinePushInfo buildOfflinePushInfo(MessageServiceRequest messageRequest) {
        JSONObject actionUrl = new JSONObject();
        CommonMsg commonMsg = messageRequest.getCommonMsg();
        actionUrl.put("action_url", commonMsg.getActionUrl());
        OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
        offlinePushInfo.setPushFlag(messageRequest.isNeedAlert() ? 0 : 1);
        PushInfo pushInfo = messageRequest.getPushInfo();
        if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getAlertContent())) {
            offlinePushInfo.setDesc(pushInfo.getAlertContent());
        }
        offlinePushInfo.setExt(actionUrl.toJSONString());
        return offlinePushInfo;
    }


}
