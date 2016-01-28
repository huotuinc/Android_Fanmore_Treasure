package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 登录接口http请求model
 */
public class LoginOutputsModel extends BaseModel {

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
        private List<AppAddressModel> addresses;

        public AppUserModel getUser() {
            return user;
        }

        public void setUser(AppUserModel user) {
            this.user = user;
        }

        public List<AppAddressModel> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<AppAddressModel> addresses) {
            this.addresses = addresses;
        }
    }
}
