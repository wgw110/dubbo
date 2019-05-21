package mobi.mixiong.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.exception.HttpException;
import mobi.mixiong.http.HttpClientUtil;

@Slf4j
public class DingtalkUtil {

    public static void notice(String message) {

        JSONObject data = new JSONObject();
        data.put("msgtype", "text");
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("content", message);
        data.put("text", messageJSON);
        data.put("at", new JSONObject());
        try {
            HttpClientUtil.buildPostRequest(
                    "https://oapi.dingtalk.com/robot/send?access_token=3d4078f1dc83c95425d8cfe7331ea92d8e56474c4c32afa0b1cf90b07ab12c32")
                    .addHeader("Content-Type", "application/json; charset=utf-8").setBody(data.toJSONString())
                    .executeToJson();
        } catch (HttpException e) {
            log.error("fail to notify by Dingtalk", e);
        }
    }
}
