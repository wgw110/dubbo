package tv.mixiong.channel.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import tv.mixiong.ds.service.im.TencentImDs;
import tv.mixiong.ds.service.im.co.ImMessageBody;
import tv.mixiong.ds.service.im.co.OfflinePushInfo;
import tv.mixiong.ds.service.im.co.TencentImMsg;
import tv.mixiong.channel.BaseChannel;
import tv.mixiong.channel.Channel;
import tv.mixiong.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tv.mixixiong.im.bean.ImCommonParam;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.PushInfo;
import tv.mixixiong.im.bean.template.CommonMsg;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.enums.ChannelEnum;
import tv.mixixiong.im.response.CommonResponse;

import java.util.List;

/**
 * im c2c通用
 *
 */
@Channel(name = ChannelEnum.IM_C2C_MSG)
@Component
public class IMc2cChannel extends BaseChannel {

    @Autowired
    private TencentImDs tencentImDs;

    @Override
    public void send(int productId, List<String> targets, MessageServiceRequest messageRequest) {
        TencentImMsg imMsg = new TencentImMsg();
        ImCommonParam imCommonParam = messageRequest.getImCommonParam();
        imMsg.setUserAction(imCommonParam.getUserAction());
        CommonMsg commonMsg = messageRequest.getCommonMsg();
        JSONObject actionParam = JSONObject.parseObject(commonMsg.getContent());
        imMsg.setActionParam(UrlUtil.encodeUtf8(JSON.toJSONString(actionParam)));

        JSONObject actionUrl = new JSONObject();
        actionUrl.put("action_url", commonMsg.getActionUrl());
        imMsg.setActionExt(actionUrl.toJSONString());

        OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
        PushInfo pushInfo = messageRequest.getPushInfo();
        offlinePushInfo.setPushFlag(messageRequest.isNeedAlert() ? 0 : 1);
        if (pushInfo != null) {
            if (!StringUtils.isEmpty(pushInfo.getAlertContent())) {
                offlinePushInfo.setDesc(pushInfo.getAlertContent());
            }
            JSONObject alertActionUrl = new JSONObject();
            alertActionUrl.put("action_url", pushInfo.getAlertActionUrl());
            offlinePushInfo.setExt(alertActionUrl.toJSONString());

            JSONObject androidInfo = new JSONObject();
            androidInfo.put("title", pushInfo.getAlertTitle());
            offlinePushInfo.setAndroidInfo(androidInfo);
        }
        tencentImDs.batchSendMsg(productId, imCommonParam.getAccount(), targets, ImMessageBody.from(imMsg), offlinePushInfo);
    }

    @Override
    public void sendSingle(int productId, String receiver, MessageServiceRequest messageRequest) {

    }

    @Override
    public void sendByTemplate(int productId, List<String> targets, TemplateServiceRequest templateServiceRequest) {

    }

    @Override
    public CommonResponse sendTemplateToAll(TemplateServiceRequest templateServiceRequest) {
        return null;
    }

    @Override
    public void sendToAll(MessageServiceRequest messageServiceRequest) {

    }

}
