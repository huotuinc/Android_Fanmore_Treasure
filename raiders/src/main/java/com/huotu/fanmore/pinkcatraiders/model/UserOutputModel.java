package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/3/10.
 */
public class UserOutputModel extends BaseModel{
    private LoginInnerModel resultData;

        public LoginInnerModel getResultData() {
            return resultData;
        }

        public void setResultData(LoginInnerModel resultData) {
            this.resultData = resultData;
        }

public class LoginInnerModel
{
    private AppUserModel user;


    public AppUserModel getUser() {
        return user;
    }

    public void setUser(AppUserModel user) {
        this.user = user;
    }


}
}
