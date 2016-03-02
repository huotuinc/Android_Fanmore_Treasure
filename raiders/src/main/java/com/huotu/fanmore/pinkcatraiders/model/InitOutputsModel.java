package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 初始化http请求接受model
 */
public class InitOutputsModel extends BaseModel {

    private InitInnerModel resultData;

    public InitInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(InitInnerModel resultData) {
        this.resultData = resultData;
    }

    public class InitInnerModel
    {
        private GlobalModel global;
        private UpdateModel update;
        private AppUserModel user;

        public GlobalModel getGlobal() {
            return global;
        }

        public void setGlobal(GlobalModel global) {
            this.global = global;
        }

        public UpdateModel getUpdate() {
            return update;
        }

        public void setUpdate(UpdateModel update) {
            this.update = update;
        }

        public AppUserModel getUser() {
            return user;
        }

        public void setUser(AppUserModel user) {
            this.user = user;
        }

        public class GlobalModel
        {
            private String customerServicePhone;
            private String helpURL;
            private String serverUrl;
            private boolean voiceSupported;

            public String getHelpURL() {
                return helpURL;
            }

            public void setHelpURL(String helpURL) {
                this.helpURL = helpURL;
            }

            public String getServerUrl() {
                return serverUrl;
            }

            public void setServerUrl(String serverUrl) {
                this.serverUrl = serverUrl;
            }

            public boolean isVoiceSupported() {
                return voiceSupported;
            }

            public void setVoiceSupported(boolean voiceSupported) {
                this.voiceSupported = voiceSupported;
            }

            public String getCustomerServicePhone() {

                return customerServicePhone;
            }

            public void setCustomerServicePhone(String customerServicePhone) {
                this.customerServicePhone = customerServicePhone;
            }
        }

        public class UpdateModel
        {
            private String updateMD5;
            private String updateTips;
            private String updateUrl;
            private String updateType;

            public String getUpdateType() {
                return updateType;
            }

            public void setUpdateType(String updateType) {
                this.updateType = updateType;
            }

            public String getUpdateMD5() {
                return updateMD5;
            }

            public void setUpdateMD5(String updateMD5) {
                this.updateMD5 = updateMD5;
            }

            public String getUpdateTips() {
                return updateTips;
            }

            public void setUpdateTips(String updateTips) {
                this.updateTips = updateTips;
            }

            public String getUpdateUrl() {
                return updateUrl;
            }

            public void setUpdateUrl(String updateUrl) {
                this.updateUrl = updateUrl;
            }

            public class UpdateType
            {
                private String name;
                private int value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }
        }
    }
}
