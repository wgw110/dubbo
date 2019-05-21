package tv.mixiong.ds.service.im.co;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * MxMessageDto
 *
 */
public class MxMessageDto {

    @JSONField(name = "create_group_card")
    private JSONObject groupCard;

    @JSONField(name = "blackboard")
    private JSONObject blackboard;

    @JSONField(name = "material")
    private JSONObject material;

    @JSONField(name = "post_work")
    private JSONObject postWork;

    @JSONField(name = "picture")
    private JSONObject picture;

    @JSONField(name = "apply_join_group")
    private JSONObject applyJoinGroup;

    @JSONField(name = "template")
    private JSONObject template;

    @JSONField(name = "group_program")
    private JSONObject groupProgram;
    
    @JSONField(name = "change_group_member_info")
    private JSONObject changeGroupMemberInfo;
    
    @JSONField(name = "group_system_msg")
    private JSONObject groupSystemMsg;

    @JSONField(name = "msg_title")
    private String title;

    @JSONField(name = "msg_content")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject getGroupSystemMsg() {
        return groupSystemMsg;
    }

    public void setGroupSystemMsg(JSONObject groupSystemMsg) {
        this.groupSystemMsg = groupSystemMsg;
    }

    public JSONObject getChangeGroupMemberInfo() {
        return changeGroupMemberInfo;
    }

    public void setChangeGroupMemberInfo(JSONObject changeGroupMemberInfo) {
        this.changeGroupMemberInfo = changeGroupMemberInfo;
    }

    public JSONObject getGroupProgram() {
        return groupProgram;
    }

    public void setGroupProgram(JSONObject groupProgram) {
        this.groupProgram = groupProgram;
    }

    public JSONObject getGroupCard() {
        return groupCard;
    }

    public void setGroupCard(JSONObject groupCard) {
        this.groupCard = groupCard;
    }

    public JSONObject getBlackboard() {
        return blackboard;
    }

    public void setBlackboard(JSONObject blackboard) {
        this.blackboard = blackboard;
    }

    public JSONObject getPicture() {
        return picture;
    }

    public void setPicture(JSONObject picture) {
        this.picture = picture;
    }

    public JSONObject getApplyJoinGroup() {
        return applyJoinGroup;
    }

    public void setApplyJoinGroup(JSONObject applyJoinGroup) {
        this.applyJoinGroup = applyJoinGroup;
    }

    public JSONObject getTemplate() {
        return template;
    }

    public void setTemplate(JSONObject template) {
        this.template = template;
    }

    public JSONObject getMaterial() { return material; }

    public void setMaterial(JSONObject material) { this.material = material; }

    public JSONObject getPostWork() { return postWork; }

    public void setPostWork(JSONObject postWork) { this.postWork = postWork; }
}
