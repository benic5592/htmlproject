package cn.com.benic.model;

import cn.com.benic.dto.SellerDto;

/**
 * Created by Administrator on 2017/6/21.
 */
public class SellerModel extends SellerDto {
    private String perFrom;
    private String perTo;

    public String getPerFrom() {
        return perFrom;
    }

    public void setPerFrom(String perFrom) {
        this.perFrom = perFrom;
    }

    public String getPerTo() {
        return perTo;
    }

    public void setPerTo(String perTo) {
        this.perTo = perTo;
    }


}
