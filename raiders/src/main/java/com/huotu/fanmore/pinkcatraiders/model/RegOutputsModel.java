package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 登录接口http请求model
 */
public class RegOutputsModel extends BaseModel {

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
