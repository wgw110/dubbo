package tv.mixiong.xinge;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import tv.mixiong.xinge.json.JSONObject;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;


public class PushSender {
    private static Logger logger = getLogger(PushSender.class);

    public static void queryTokenTags(XingeApp xingeApp, String tags) {
        JSONObject object = xingeApp.queryInfoOfToken(tags);
        System.out.println(object.toString());
    }

    public static void batchPushToAndroid(XingeApp xingeApp, List<String> tokens, PushParam param) {
        if (tokens == null || tokens.size() == 0) {
            return;
        }
        List<List<String>> partitions = Lists.partition(tokens, 1000);
        for (List<String> subList: partitions) {
            logger.info("batch send android push , size : " + subList.size());
            logger.info("batch send android push, token : {}", subList);
            xingeApp.buildAndroidClient().batchAndroidPushByTokens(subList, param);
        }
    }

    public static void batchPushToIOS(XingeApp xingeApp, List<String> tokens, PushParam param){
        if (tokens == null || tokens.size() == 0) {
            return;
        }
        List<List<String>> partitions = Lists.partition(tokens, 1000);
        for (List<String> subList: partitions) {
            logger.info("batch send ios push , size : " + subList.size());
            logger.info("batch send ios push, token : {}", subList);
            xingeApp.buildIosClient().batchIOSPushByTokens(subList, param);
        }
    }

    public static void pushToSingleAndroid(XingeApp xingeApp, PushParam param){
        JSONObject json = xingeApp.pushTokenAndroid(param);
        logger.info("发送android push返回: " + json.toString());
    }

    public static void pushToSingleIOS(XingeApp xingeApp, PushParam param){
        JSONObject json = xingeApp.pushTokenIos(param);
        logger.info("发送ios push返回: " + json.toString());
    }

    public static void batchSetTagAndroid(XingeApp xingeApp, List<String> tokens, String tag) {
        List<TagTokenPair> pairs = tokens.stream().map(token -> new TagTokenPair(tag, token)).collect(Collectors.toList());
        xingeApp.BatchSetTag(pairs);
    }

    public static void batchSetTagIos(XingeApp xingeApp, List<String> tokens, String tag) {
        List<TagTokenPair> tokenPairs = tokens.stream().map(token -> new TagTokenPair(tag, token)).collect(Collectors.toList());
        xingeApp.BatchSetTag(tokenPairs);
    }

    public static void batchRemoveTag(XingeApp xingeApp, List<String> tokens, String tag, int type){
        List<TagTokenPair> tokenPairs = tokens.stream().map(token -> new TagTokenPair(tag, token)).collect(Collectors.toList());
        xingeApp.BatchDelTag(tokenPairs);
    }

    public static String pushByTagToIos(XingeApp xingeApp, PushParam param){
        JSONObject jsonObject = xingeApp.pushTagIos(param);
        return getPushResult(jsonObject);
    }

    private static String getPushResult(JSONObject jsonObject) {
        int retCode = jsonObject.getInt("ret_code");
        if (retCode == EventConstants.SUCCESS) {
            PushResponse pushResponse = JSON.parseObject(jsonObject.get("result").toString(), PushResponse.class);
            return pushResponse.getPushId();
        } else {
            return EventConstants.EMPTY;
        }
    }

    public static TokenInfo getInfoByToken(XingeApp xingeApp, String token, int platform) {
        JSONObject object = xingeApp.queryInfoOfToken(token);
        return JSON.parseObject(object.get("result").toString(), TokenInfo.class);
    }


    public static String pushByTagToAndroid(XingeApp xingeApp, PushParam param) {
        JSONObject jsonObject = xingeApp.pushTagAndroid(param);
        return getPushResult(jsonObject);
    }

    public static void cancel(XingeApp xingeApp, String account) {
        //2044334256
        JSONObject jsonObject = xingeApp.queryTokensOfAccount(account);
        System.out.println(jsonObject.toString());
    }


    private static List<TagTokenPair> toTagTokenPairs(Map<String, List<String>> tokenTagMap) {
        List<TagTokenPair> pairs = Lists.newArrayList();
        Iterator it = tokenTagMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String tag = entry.getKey().toString();
            List<String> tokens = (List<String>) entry.getValue();
            (tokens).forEach((token) -> pairs.add(new TagTokenPair(tag, token)));
        }
        return pairs;
    }
}
