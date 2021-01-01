package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusGoods;
import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.domain.BusUserShoppingCart;

public class BusUserShoppingCartDto extends BusUserShoppingCart {
    private BusGoods busGoods;
    private String remark;
    private String specificationName;
    private BusGoodsSpecification busGoodsSpecification;

    public String getRemark() {
        return remark;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BusGoods getBusGoods() {
        return busGoods;
    }

    public void setBusGoods(BusGoods busGoods) {
        this.busGoods = busGoods;
    }

    public BusGoodsSpecification getBusGoodsSpecification() {
        return busGoodsSpecification;
    }

    public void setBusGoodsSpecification(BusGoodsSpecification busGoodsSpecification) {
        this.busGoodsSpecification = busGoodsSpecification;
    }
}
