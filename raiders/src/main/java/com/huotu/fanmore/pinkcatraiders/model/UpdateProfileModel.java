package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 更新资料model
 */
public
class UpdateProfileModel extends BaseModel {

    private UpdateProfileInnerModel resultData;

    public UpdateProfileInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(UpdateProfileInnerModel resultData) {
        this.resultData = resultData;
    }

    public class UpdateProfileInnerModel
    {
        private AppUserModel user;

        public
        AppUserModel getUser ( ) {

            return user;
        }

        public
        void setUser ( AppUserModel user ) {

            this.user = user;
        }
    }
}
