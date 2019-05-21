//package tv.mixiong.api.module.program.service;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.stereotype.Service;
//import tv.mixiong.api.module.program.vo.ProgramFormBean;
//import tv.mixiong.dubbo.service.IProgramService;
//import tv.mixixiong.entity.Program;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Service
//public class ProgramService {
//
//    @Reference
//    private IProgramService programService;
//
//    public void saveOrUpdate(String passport, ProgramFormBean param){
//
//
//    }
//
//    private void saveProgram(ProgramFormBean param){
//        Long discussionGroupId = null;
//        String episodeInfoJson = param.getEpisodeInfoJson();
//        JSONArray array = JSONArray.parseArray(episodeInfoJson);
//        Set<String> users = new HashSet<>();
//        if (array != null && array.size() > 0) {
//            for (int i = 0; i < array.size(); i++) {
//                JSONObject guest = array.getJSONObject(i);
//                if (guest != null) {
//                    JSONObject ss = guest.getJSONObject("guest");
//                    if (ss != null && ss.get("passport") != null) {
//                        users.add((String) ss.get("passport"));
//                    }
//                }
//            }
//        }
//        for (String user : users) {
//            Map<String, Object> param1 = new HashMap<>();
//            param1.put("isGuest", true);
////            groupMemberDs.saveOrUpdateDiscussionGroupMember(ProductEnum.MI_XIONG_APP.productId(), discussionGroupId,
////                    user, true, param1);
//        }
//        Program program = param.toProgram(param);
//        programService.saveProgram(program);
//
//    }
//}
