package tv.mixiong.ds.service.im.co;

import com.alibaba.fastjson.JSONObject;


public class TencentImMsg {
    private String userAction;

    private String actionParam;

    private String actionExt;

    private JSONObject groupProgram;

    public JSONObject getGroupProgram() {
        return groupProgram;
    }

    public void setGroupProgram(JSONObject groupProgram) {
        this.groupProgram = groupProgram;
    }

    private boolean log = true;

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public String getActionExt() {
        return actionExt;
    }

    public void setActionExt(String actionExt) {
        this.actionExt = actionExt;
    }

    public boolean isLog() {
        return log;
    }

    public void setLog(boolean log) {
        this.log = log;
    }
}
