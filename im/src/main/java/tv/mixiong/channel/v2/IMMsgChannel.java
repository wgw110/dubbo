package tv.mixiong.channel.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import tv.mixiong.ds.service.im.TencentImDs;
import tv.mixiong.ds.service.im.co.*;
import tv.mixiong.channel.BaseChannel;
import tv.mixiong.channel.Channel;
import tv.mixiong.utils.UrlUtil;
import tv.mixiong.xinge.EventConstants;
import tv.mixixiong.im.bean.ImCommonParam;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.PushInfo;
import tv.mixixiong.im.bean.template.CommonMsg;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.enums.ChannelEnum;
import tv.mixixiong.im.enums.TemplateType;
import tv.mixixiong.im.response.ResponseStatus;
import tv.mixixiong.im.response.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * im msg channel
 * *
 */
@Channel(name = ChannelEnum.IM_MSG, supportTemplate = true)
@Component
public class IMMsgChannel extends BaseChannel {

    @Autowired
    private TencentImDs tencentImDs;

    @Override
    public void send(int productId, List<String> passports, MessageServiceRequest messageRequest) {
        System.out.println("channel:"+messageRequest);
        TencentImMsg imMsg = buildTencentImMsg(messageRequest);
        ImCommonParam imCommonParam = messageRequest.getImCommonParam();
        OfflinePushInfo offlinePushInfo = buildOfflinePushInfo(messageRequest);
        tencentImDs.batchSendMsg(productId, imCommonParam.getAccount(), passports, ImMessageBody.from(imMsg), offlinePushInfo);
    }

    @Override
    public void sendSingle(int productId, String receiver, MessageServiceRequest messageRequest) {

    }

    @Override
    public void sendByTemplate(int productId, List<String> targets, TemplateServiceRequest templateServiceRequest) {
        TencentImMsg imMsg = buildTemplateTencentImMsg(templateServiceRequest);
        ImCommonParam imCommonParam = templateServiceRequest.getImCommonParam();
        OfflinePushInfo offlinePushInfo = buildTemplateOfflinePushInfo(templateServiceRequest);
        tencentImDs.batchSendMsg(productId, imCommonParam.getAccount(), targets, ImMessageBody.from(imMsg), offlinePushInfo);
    }

    @Override
    public CommonResponse sendTemplateToAll(TemplateServiceRequest templateServiceRequest) {
        TencentImMsg imMsg = buildTemplateTencentImMsg(templateServiceRequest);
        ImCommonParam imCommonParam = templateServiceRequest.getImCommonParam();
        OfflinePushInfo offlinePushInfo = buildTemplateOfflinePushInfo(templateServiceRequest);
        Map<String, Object> condition = null;
        String tags = templateServiceRequest.getTags();
        if (!StringUtils.isEmpty(tags)) {
            List<String> tagList = Lists.newArrayList(tags.split(","));
            condition = new HashMap<String, Object>() {{
                put("TagsAnd", tagList);
            }};
        }
        JSONObject jsonObject=tencentImDs.sendToAll(imCommonParam.getAccount(), condition, ImMessageBody.from(imMsg), offlinePushInfo);
        if(jsonObject!=null && EventConstants.SUCCESS_RESULT_STATUS.equals(jsonObject.getString("ActionStatus"))){
            CommonResponse thriftCommonResponse=new CommonResponse();
            thriftCommonResponse.setStatus(ResponseStatus.SUCCESS_STATUS.getStatus());
            thriftCommonResponse.setData(jsonObject.toJSONString());
            thriftCommonResponse.setStatusText(ResponseStatus.SUCCESS_STATUS.getDesc());
            return thriftCommonResponse;
        }else{
            CommonResponse thriftCommonResponse=new CommonResponse();
            thriftCommonResponse.setStatus(ResponseStatus.SERVICE_UNAVAILABLE.getStatus());
            thriftCommonResponse.setStatusText(ResponseStatus.SERVICE_UNAVAILABLE.getDesc());
            return thriftCommonResponse;
        }
    }


    @Override
    public void sendToAll(MessageServiceRequest messageRequest) {
        TencentImMsg imMsg = buildTencentImMsg(messageRequest);
        OfflinePushInfo offlinePushInfo = buildOfflinePushInfo(messageRequest);
        ImCommonParam imCommonParam = messageRequest.getImCommonParam();
        String tags = messageRequest.getTags();
        Map<String, Object> condition = null;
        if (!StringUtils.isEmpty(tags)) {
            List<String> tagList = Lists.newArrayList(tags.split(","));
            condition = new HashMap<String, Object>() {{
                put("TagsAnd", tagList);
            }};
        }
        tencentImDs.sendToAll(imCommonParam.getAccount(), condition, ImMessageBody.from(imMsg), offlinePushInfo);
    }

    private TencentImMsg buildTemplateTencentImMsg(TemplateServiceRequest templateServiceRequest) {
        TencentImMsg imMsg = new TencentImMsg();
        ImCommonParam imCommonParam = templateServiceRequest.getImCommonParam();
        imMsg.setUserAction(imCommonParam.getUserAction());
        MxMessageDto msg = new MxMessageDto();
        JSONObject template = null;
        int templateType = templateServiceRequest.getType();
        if (templateType == TemplateType.SINGLE_PICTURE.getType()) {
            template = JSON.parseObject(JSON.toJSONString(templateServiceRequest.getSinglePictureTemplate()));
        } else if (templateType == TemplateType.MULTIPLE_PICTURE.getType()) {
            template = JSON.parseObject(JSON.toJSONString(templateServiceRequest.getMultiplePictureTemplate()));
        } else if (templateType == TemplateType.NOTICE.getType()) {
            template = JSON.parseObject(JSON.toJSONString(templateServiceRequest.getNoticeTemplate()));
        }
        msg.setTemplate(template);
        imMsg.setActionParam(UrlUtil.encodeUtf8(JSON.toJSONString(msg)));
        return imMsg;
    }

    private OfflinePushInfo buildTemplateOfflinePushInfo(TemplateServiceRequest templateServiceRequest) {
        OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
        if (!templateServiceRequest.isNeedPush()) {
            offlinePushInfo.setPushFlag(1);
        } else {
            offlinePushInfo.setPushFlag(0);
        }
        PushInfo pushInfo = templateServiceRequest.getPushInfo();
        if (pushInfo != null) {
            offlinePushInfo.setDesc(pushInfo.getAlertContent());
            JSONObject alertActionUrl = new JSONObject();
            alertActionUrl.put("action_url", pushInfo.getAlertActionUrl());
            if (pushInfo.getPayload() != null) {
                alertActionUrl.put("payload", JSON.parseObject(pushInfo.getPayload()));
            }
            offlinePushInfo.setExt(alertActionUrl.toJSONString());
            JSONObject androidInfo = new JSONObject();
            offlinePushInfo.setAndroidInfo(androidInfo);
        }
        return offlinePushInfo;
    }

    private TencentImMsg buildTencentImMsg(MessageServiceRequest messageRequest) {
        TencentImMsg imMsg = new TencentImMsg();
        ImCommonParam imCommonParam = messageRequest.getImCommonParam();
        imMsg.setUserAction(imCommonParam.getUserAction());

        MxMessageDto msg = new MxMessageDto();

        if (messageRequest.getApplyJoinGroup() != null) {
            msg.setApplyJoinGroup(JSONObject.parseObject(messageRequest.getApplyJoinGroup()));
        }

        if (messageRequest.getChangeGroupMemberInfo() != null) {
            msg.setChangeGroupMemberInfo(JSONObject.parseObject(messageRequest.getChangeGroupMemberInfo()));
        }

        if (messageRequest.getGroupSystemMsg() != null) {
            msg.setGroupSystemMsg(JSONObject.parseObject(messageRequest.getGroupSystemMsg()));
        }

        if (messageRequest.getPicture() != null) {
            msg.setPicture(JSONObject.parseObject(messageRequest.getPicture()));
        }

        CommonMsg commonMsg = messageRequest.getCommonMsg();
        if (commonMsg != null) {
            if (commonMsg.getContent() != null) {
                msg.setContent(commonMsg.getContent());
            }

            if (commonMsg.getTitle() != null) {
                msg.setTitle(commonMsg.getTitle());
            }
        }
        imMsg.setActionParam(UrlUtil.encodeUtf8(JSON.toJSONString(msg)));
        return imMsg;
    }

    private OfflinePushInfo buildOfflinePushInfo(MessageServiceRequest messageRequest) {
        OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
        PushInfo pushInfo = messageRequest.getPushInfo();
        offlinePushInfo.setPushFlag(messageRequest.isNeedAlert() ? 0 : 1);
        if (pushInfo != null) {
            if (!StringUtils.isEmpty(pushInfo.getAlertContent())) {
                offlinePushInfo.setDesc(pushInfo.getAlertContent());
            }
            JSONObject alertActionUrl = new JSONObject();
            alertActionUrl.put("action_url", pushInfo.getAlertActionUrl());
            alertActionUrl.put("payload", pushInfo.getPayload());
            offlinePushInfo.setExt(alertActionUrl.toJSONString());
            JSONObject androidInfo = new JSONObject();
            androidInfo.put("title", pushInfo.getAlertTitle());
            offlinePushInfo.setAndroidInfo(androidInfo);
        }
        return offlinePushInfo;
    }

    public static void main(String[] args){
        String alertContent3 = String.format("您购买的课程【%s】正在直播", "蛋炒饭的做法");

        JSONObject json = new JSONObject();
        json.put("subject", "强势爆浆！芝士流心塔");
        json.put("title", "您购买的课程正在直播");
        json.put("button","立即观看");
        json.put("type", "1");

        TemplateServiceRequest templateServiceRequest3 = TemplateServiceRequest.builder()
                .pushInfo(PushInfo.from("直播", alertContent3, "mxl://action.cmd?%7B%22payload%22%3A%7B%22room_id%22%3A%221343629%22%7D%2C%22event%22%3A%22openInteractiveLivePage%22%7D", json.toJSONString()))
                .imCommonParam(ImCommonParam.from("20018", "pusher"))
                .channel(ChannelEnum.IM_MSG.getBit())
                .build();
        IMMsgChannel imMsgChannel=new IMMsgChannel();
        ImUserSignCo imUserSignCo=new ImUserSignCo();
        TencentImMsg imMsg = imMsgChannel.buildTemplateTencentImMsg(templateServiceRequest3);
        OfflinePushInfo offlinePushInfo = imMsgChannel.buildTemplateOfflinePushInfo(templateServiceRequest3);
        Map<String, Object> params = Maps.newHashMap();
        List<String> receiver=Lists.newArrayList();
        receiver.add("214807");
        receiver.add("214836");
        Random random=new Random();
        params.put("From_Account", "pusher");
        params.put("To_Account", receiver);
        params.put("MsgBody", Lists.newArrayList(ImMessageBody.from(imMsg)));
        params.put("MsgRandom", random.nextInt(100000000));
        if (offlinePushInfo != null) {
            params.put("OfflinePushInfo", offlinePushInfo);
        }
        System.out.println(JSON.toJSONString(params));
        StringBuilder url = new StringBuilder("https://console.tim.qq.com/v4/"+"batchsendmsg");
        System.out.println(url.append("?usersig=").append(imUserSignCo.getUserSign())
                .append("&identifier=").append(imUserSignCo.getUserName())
                .append("&sdkappid=").append(imUserSignCo.getSdkAppId())
                .append("&contenttype=json").toString());
    }

}
