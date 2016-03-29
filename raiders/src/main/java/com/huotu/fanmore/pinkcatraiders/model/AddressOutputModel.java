package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 地址列表获取model
 */
public
class AddressOutputModel extends BaseModel {

    private AddressInnerModel resultData;

    public AddressInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(AddressInnerModel resultData) {
        this.resultData = resultData;
    }

    public class AddressInnerModel
    {

        private List< MyAddressListModel > list;

        public
        List< MyAddressListModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< MyAddressListModel > list ) {

            this.list = list;
        }
    }
}
