package tv.mixiong.ds.service.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import tv.mixiong.ds.service.im.co.ImMessageBody;
import tv.mixiong.ds.service.im.co.ImUserSignCo;
import tv.mixiong.ds.service.im.co.OfflinePushInfo;
import tv.mixiong.ds.service.im.co.TencentImMsg;
import tv.mixiong.utils.http.MxHttpClient;
import tv.mixiong.xinge.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * TencentImDs
 *
 */
@Component
public class TencentImDs {

    private static final String BASE_URL = "https://console.tim.qq.com/v4/";
    private static final String OPENIM_CONTEXT = "openim/";
    private static final String SENDMSG = OPENIM_CONTEXT + "sendmsg";  // 单对单消息
    private static final String BATCHSENDMSG = OPENIM_CONTEXT + "batchsendmsg";//批量单聊消息接口
    private static final String IM_PUSH = OPENIM_CONTEXT + "im_push";//推送接口
    private static final String GROUP_CONTEXT = "group_open_http_svc/";
    private static final String SEND_GROUP_MSG = GROUP_CONTEXT + "send_group_msg";
    private static final Random random = new Random();
    private static final Integer MX_PRODUCT_ID = 1;

    private Logger logger = LoggerFactory.getLogger(TencentImDs.class);

    @Autowired
    private ImUserSignDs imUserSignDs;

    private String getUrl(String action, ImUserSignCo imUserSign) {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(action).append("?usersig=").append(imUserSign.getUserSign())
                .append("&identifier=").append(imUserSign.getUserName())
                .append("&sdkappid=").append(imUserSign.getSdkAppId())
                .append("&contenttype=json");
        return url.toString();
    }

    public void sendGroupMsg(int productId, String imGroupId, String fromAccount,int role,TencentImMsg msg, OfflinePushInfo pushInfo) {
        ImUserSignCo imUserSign = imUserSignDs.createImUserSign(productId);
        String url = getUrl(SEND_GROUP_MSG, imUserSign);
        Map<String, Object> params = Maps.newHashMap();
        params.put("GroupId", imGroupId);
        ImMessageBody body = ImMessageBody.from(msg);
        List<ImMessageBody> lists=Lists.newArrayList();
        lists.add(body);
        if(fromAccount!=null){
            params.put("From_Account",fromAccount);
            if(role!=0){
                TencentImMsg msg2=new TencentImMsg();
                msg2.setActionParam("memberRoleInGroup="+role);
                msg2.setUserAction("2007");
                ImMessageBody body2 = ImMessageBody.from(msg2);
                lists.add(body2);
            }
        }
        params.put("MsgBody", lists);
        params.put("Random", random.nextInt(100000000));
        if (pushInfo != null) {
            params.put("OfflinePushInfo", pushInfo);
        }
        String req = JSON.toJSONString(params);
        logger.info(String.format("params is:%s",req));
        try {
            if (msg.isLog()) {
                logger.info("send tencent im group msg : " + req);
            }
            String rst = MxHttpClient.postWithBody(url, req);
            if (!StringUtils.isEmpty(rst)) {
                JSONObject rstObj = JSON.parseObject(rst);
                if (EventConstants.SUCCESS_RESULT_STATUS.equals(rstObj.getString("ActionStatus"))) {
                    if (msg.isLog()) {
                        logger.info(String.format("send tencent im group msg success, group id %s", imGroupId));
                    }
                }
            }
        } catch (Exception e) {
            logger.error(String.format("send tencent im group msg success, group id %s, req %s", imGroupId, req), e);
        }
    }

    public boolean batchSendMsg(int productId, String sender, List<String> receiver, ImMessageBody body, OfflinePushInfo pushInfo) {
        if (receiver == null || receiver.isEmpty()) {
            logger.error("receiver is empty, msg {}", JSON.toJSONString(body));
            return false;
        }
        int begin = 0;
        int end = receiver.size();
        int size = 400;
        while (begin < end) {
            int to = (begin + size) >= end ? end : (begin + size);
            List<String> subList = receiver.subList(begin, to);
            send(productId, sender, subList, body, pushInfo);
            begin += size;
            logger.info("batch send msg, count : " + begin);
        }
        return true;
    }

    public boolean sendMsg(int productId, int syncToSender,String sender, String receiver, ImMessageBody body, OfflinePushInfo pushInfo) {
        return sendSingle(productId,syncToSender,sender,receiver,body,pushInfo);
    }

    public JSONObject sendToAll(String sender, Map<String, Object> condition, ImMessageBody body, OfflinePushInfo pushInfo) {
        ImUserSignCo imUserSign = imUserSignDs.createImUserSign(MX_PRODUCT_ID);
        String url = getUrl(IM_PUSH, imUserSign);
        Map<String, Object> params = Maps.newHashMap();
        params.put("From_Account", sender);
        params.put("MsgBody", Lists.newArrayList(body));
        params.put("MsgRandom", random.nextInt(100000000));
        params.put("MsgLifeTime", 7 * 24 * 3600);
        if (pushInfo != null) {
            params.put("OfflinePushInfo", pushInfo);
        }
        if (condition != null) {
            params.put("Condition", condition);
        }
        try {
            String rst = MxHttpClient.postWithBody(url, JSON.toJSONString(params));
            if (!StringUtils.isEmpty(rst)) {
                JSONObject rstObj = JSON.parseObject(rst);
                logger.info("send msg result : " + rst);
                if (EventConstants.SUCCESS_RESULT_STATUS.equals(rstObj.getString("ActionStatus"))) {
                    logger.info(String.format("batch send message success, sender %s, params %s", params.get("From_Account"), JSON.toJSONString(params)));
                    return rstObj;
                } else {
                    logger.error(String.format("batch send message fail, sender %s, message %s", params.get("From_Account"), JSON.toJSONString(params)));
                }
            }
        } catch (Exception e) {
            logger.error(String.format("batch send message fail, sender %s, message %s", params.get("From_Account"), params.get("MsgBody")), e);
        }
        return null;
    }

    public boolean send(int productId, String sender, List<String> receiver, ImMessageBody body, OfflinePushInfo pushInfo) {
        ImUserSignCo imUserSign = imUserSignDs.createImUserSign(MX_PRODUCT_ID);
        String url = getUrl(BATCHSENDMSG, imUserSign);
        Map<String, Object> params = Maps.newHashMap();
        params.put("From_Account", sender);
        params.put("To_Account", receiver);
        params.put("MsgBody", Lists.newArrayList(body));
        params.put("MsgRandom", random.nextInt(100000000));
        if (pushInfo != null) {
            params.put("OfflinePushInfo", pushInfo);
        }
        return doIMHttpRequest(url, params);
    }

    public boolean sendSingle(int productId, int syncToSender,String sender, String receiver, ImMessageBody body, OfflinePushInfo pushInfo){
        ImUserSignCo imUserSign = imUserSignDs.createImUserSign(productId);
        String url = getUrl(SENDMSG, imUserSign);
        Map<String, Object> params = Maps.newHashMap();
        params.put("SyncOtherMachine",syncToSender);
        params.put("From_Account", sender);
        params.put("To_Account", receiver);
        params.put("MsgBody", Lists.newArrayList(body));
        params.put("MsgRandom", random.nextInt(100000000));
        if (pushInfo != null) {
            params.put("OfflinePushInfo", pushInfo);
        }
        return doIMHttpRequest(url, params);
    }

    private boolean doIMHttpRequest(String url, Map<String, Object> params) {
        try {
            String rst = MxHttpClient.postWithBody(url, JSON.toJSONString(params));
            if (!StringUtils.isEmpty(rst)) {
                JSONObject rstObj = JSON.parseObject(rst);
                logger.info("send msg result : " + rst);
                if (EventConstants.SUCCESS_RESULT_STATUS.equals(rstObj.getString("ActionStatus"))) {
                    logger.info(String.format("batch send message success, sender %s, params %s", params.get("From_Account"), JSON.toJSONString(params)));
                    return true;
                } else {
                    logger.error(String.format("batch send message fail, sender %s, message %s", params.get("From_Account"), JSON.toJSONString(params)));
                }
            }
        } catch (Exception e) {
            logger.error(String.format("batch send message fail, sender %s, message %s", params.get("From_Account"), params.get("MsgBody")), e);
        }
        return false;
    }
}
