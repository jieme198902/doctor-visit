package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusGoods;
import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.domain.BusUserShoppingCart;

public class BusUserShoppingCartDto extends BusUserShoppingCart {
    private BusGoods busGoods;
    private BusGoodsSpecification busGoodsSpecification;

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
