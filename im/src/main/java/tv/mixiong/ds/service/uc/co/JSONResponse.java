package tv.mixiong.ds.service.uc.co;

/**
 * Created by wuzbin on 16/5/27.
 */
public abstract class JSONResponse<T>{
    private int status;
    private String statusText;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
