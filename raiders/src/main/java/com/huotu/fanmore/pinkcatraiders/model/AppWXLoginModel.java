package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AppWXLoginModel extends BaseModel {

    public AppReback getResultData() {
        return resultData;
    }

    public void setResultData(AppReback resultData) {
        this.resultData = resultData;
    }

    public AppReback resultData;


    public class AppReback {
        AppUserModel user;
        List<AppAddressModel> addresses;

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
