package tv.mixiong.ds.service.uc.co;

import tv.mixiong.ds.dao.entity.ImUserSign;

/**
 * UserSignResponse
 * Created by scooler on 16/8/5.
 */
public class UserSignResponse extends JSONResponse<ImUserSign> {

    private ImUserSign data;

    public ImUserSign getData() {
        return data;
    }

    public void setData(ImUserSign data) {
        this.data = data;
    }
}
