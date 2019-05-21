package tv.mixiong.channel.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import tv.mixiong.channel.BaseChannel;
import tv.mixiong.channel.Channel;
import tv.mixiong.ds.service.im.TencentImDs;
import tv.mixiong.ds.service.im.co.MxMessageDto;
import tv.mixiong.ds.service.im.co.OfflinePushInfo;
import tv.mixiong.ds.service.im.co.TencentImMsg;
import tv.mixiong.dubbo.service.PushService;
import tv.mixiong.utils.UrlUtil;
import tv.mixixiong.im.bean.ImCommonParam;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.PushInfo;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.enums.ChannelEnum;
import tv.mixixiong.im.enums.TemplateType;
import tv.mixixiong.im.response.CommonResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;


@Channel(name = ChannelEnum.IM_GROUP_MSG,supportTemplate = true)
@Component
public class ImGroupMsgChannel extends BaseChannel {

    private Logger logger = LoggerFactory.getLogger(PushService.class);

    @Autowired
    private TencentImDs tencentImDs;

    @Override
    public void send(int productId, List<String> imGroupIds, MessageServiceRequest messageRequest) {
        logger.info("groups:"+imGroupIds.toString());
        logger.info("send message");
        if (!imGroupIds.isEmpty()) {
            imGroupIds.stream().forEach(imGroupId -> {
                TencentImMsg imMsg = new TencentImMsg();
                imMsg.setUserAction(messageRequest.getImCommonParam().getUserAction());
                MxMessageDto msg = new MxMessageDto();

                if (messageRequest.getGroupProgram() != null) {
                    msg.setGroupProgram(JSON.parseObject(messageRequest.getGroupProgram()));
                }

                if (messageRequest.getBlackboard() != null) {
                    msg.setBlackboard(JSON.parseObject(messageRequest.getBlackboard()));
                }

                if (messageRequest.getGroup() != null) {
                    msg.setGroupCard(JSON.parseObject(messageRequest.getGroup()));
                }

                if (messageRequest.getChangeGroupMemberInfo() != null) {
                    msg.setChangeGroupMemberInfo(JSON.parseObject(messageRequest.getChangeGroupMemberInfo()));
                }

                if(messageRequest.getMaterial() != null){
                    msg.setMaterial(JSON.parseObject(messageRequest.getMaterial()));
                }

                if(messageRequest.getPostWorks()!=null){
                    msg.setPostWork(JSON.parseObject(messageRequest.getPostWorks()));
                }
                imMsg.setActionParam(UrlUtil.encodeUtf8(JSON.toJSONString(msg)));

                OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
                if(messageRequest.isNeedAlert()==true){
                    offlinePushInfo.setPushFlag(0);
                }else{
                    offlinePushInfo.setPushFlag(1);
                }
                PushInfo pushInfo = messageRequest.getPushInfo();
                if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getAlertContent())) {
                    offlinePushInfo.setDesc(pushInfo.getAlertContent());
                }
                String passport;
                int role;
                try{
                    Long.parseLong(messageRequest.getImCommonParam().getAccount());
                    passport=messageRequest.getImCommonParam().getAccount();
                    JSONObject tags=JSONObject.parseObject(messageRequest.getTags());
                    role=(Integer) tags.get("role");
                }catch (Exception e){
                    passport=null;
                    role=0;
                }
                logger.info("role is:%s",role);
                tencentImDs.sendGroupMsg(productId, imGroupId,passport,role, imMsg, offlinePushInfo);
            });
        }
    }

    @Override
    public void sendByTemplate(int productId, List<String> imGroupIds, TemplateServiceRequest templateServiceRequest) {
        TencentImMsg imMsg = buildTemplateTencentImMsg(templateServiceRequest);
        ImCommonParam imCommonParam = templateServiceRequest.getImCommonParam();
        OfflinePushInfo offlinePushInfo = buildTemplateOfflinePushInfo(templateServiceRequest);
        logger.info("groups:"+imGroupIds.toString());
        imGroupIds.stream().forEach(imGroupId -> {
            tencentImDs.sendGroupMsg(productId, imGroupId, imCommonParam.getAccount(),0, imMsg, offlinePushInfo);
        });
    }

    @Override
    public void sendSingle(int productId, String receiver, MessageServiceRequest messageRequest) {

    }

    @Override
    public CommonResponse sendTemplateToAll(TemplateServiceRequest templateServiceRequest) {
        return null;
    }

    @Override
    public void sendToAll(MessageServiceRequest messageServiceRequest) {

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

    public static void main(String[] agrs){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("url", "http://pic.mixiong.tv/uploadImages/0000277c/PictureUnlock_shijue_10779_0311bde647341aea2241c95459053904_16:9.pictureunlock.jpg_12.jpg");
//        jsonObject.put("name", "1.jpg");
//        jsonObject.put("cover_url","http://pic.mixiong.tv/uploadImages/0000277c/PictureUnlock_shijue_10779_0311bde647341aea2241c95459053904_16:9.pictureunlock.jpg_12.jpg");
//        jsonObject.put("type",2);
//        jsonObject.put("action_url", "mxl://action.cmd?%7B%22payload%22%3A%7B%22passport%22%3A%22214807%22%2C%22group_id%22%3A%2218419%22%7D%2C%22event%22%3A%22openGroupFileListPage%22%7D");
//        MessageServiceRequest serviceRequest = MessageServiceRequest.builder()
//                .targets("18419")
//                .channel(ChannelEnum.IM_GROUP_MSG.getBit())
//                .material(jsonObject.toJSONString())
//                .imCommonParam(ImCommonParam.from("20023", "214807"))
//                .pushInfo(PushInfo.from("", String.format("课件%s已上传到「学习资料库」，请点击查看。","VID_20180717_155651.mp4"), "mxl://action.cmd?%7B%22payload%22%3A%7B%7D%2C%22event%22%3A%22openMessageTabPage%22%7D"))
//                .needAlert(true)
//                .build();
//        TencentImMsg imMsg = new TencentImMsg();
//        TencentImMsg imMsg2 = new TencentImMsg();
//        imMsg.setUserAction(serviceRequest.getImCommonParam().getUserAction());
//        MxMessageDto msg = new MxMessageDto();
//        msg.setMaterial(JSON.parseObject(serviceRequest.getMaterial()));
//        imMsg.setActionParam(UrlUtil.encodeUtf8(JSON.toJSONString(msg)));
//        OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
//        offlinePushInfo.setPushFlag(0);
//        PushInfo pushInfo = serviceRequest.getPushInfo();
//        if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getAlertContent())) {
//            offlinePushInfo.setDesc(pushInfo.getAlertContent());
//        }
//        imMsg2.setActionParam("memberRoleInGroup=1");
//        imMsg2.setUserAction("2007");
//        String passport=serviceRequest.getImCommonParam().getAccount();
//        ImMessageBody body = ImMessageBody.from(imMsg);
//        ImMessageBody body2=ImMessageBody.from(imMsg2);
//        System.out.println(Lists.newArrayList(body).toString());
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("GroupId", "mx_discussion_group_18419");
//        params.put("From_Account","214807");
//        params.put("MsgBody", Lists.newArrayList(body,body2));
//        Random random = new Random();
//        params.put("Random", random.nextInt(100000000));
//        if (pushInfo != null) {
//            params.put("OfflinePushInfo", pushInfo);
//        }
//        System.out.println(JSON.toJSONString(params));
    }
}
