package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/3/9.
 */
public class UserUnwrapOutput extends BaseModel {
    private UserUnwrapInnerModel resultData;

    public UserUnwrapInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(UserUnwrapInnerModel resultData) {
        this.resultData = resultData;
    }

    public class UserUnwrapInnerModel {
        public AppUserModel getData() {
            return data;
        }

        public void setData(AppUserModel data) {
            this.data = data;
        }

        private AppUserModel data;


    }
}
