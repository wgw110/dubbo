package tv.mixiong.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class MxActionProtocolUtil {
    public static String build(String cmd, Map<String, String> params) {
        Map<String, Object> actionQuery = new HashMap<>();
        actionQuery.put("event", cmd);
        actionQuery.put("payload", params);
        String action_url = "mxl://action.cmd?" + UrlUtil.encodeUtf8(JSON.toJSONString(actionQuery));
        return action_url;
    }

    public static Map<String, String> getParams(String actionUrl) {
        try {
            int index = actionUrl.indexOf("?");
            String query = URLDecoder.decode(actionUrl.substring(index + 1), "utf-8");
            JSONObject jsonObject = JSON.parseObject(query).getJSONObject("payload");
            if (jsonObject != null) {
                return JSON.parseObject(jsonObject.toJSONString(), HashMap.class);
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCmd(String actionUrl) {
        try {
            int index = actionUrl.indexOf("?");
            String query = URLDecoder.decode(actionUrl.substring(index + 1), "utf-8");
            return JSON.parseObject(query).getString("event");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String cmd = "openProgramDetailPage";
        Map<String, String> params = new HashMap<>();
        params.put("program_id", "1001137");
        String actionUrl = build(cmd, params);
        System.out.println(actionUrl);
        System.out.println(getCmd(actionUrl));
        System.out.println(getParams(actionUrl));
    }
}
