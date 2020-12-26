package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusOrderGoods;
import com.doctor.visit.domain.BusOrderGoodsTotal;
import com.doctor.visit.domain.BusUserShippingAddress;

import java.util.List;

public class BusOrderGoodsTotalDto extends BusOrderGoodsTotal {

    private List<BusOrderGoods> busOrderGoods;
    private BusUserShippingAddress shippingAddress;

    public BusUserShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(BusUserShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<BusOrderGoods> getBusOrderGoods() {
        return busOrderGoods;
    }

    public void setBusOrderGoods(List<BusOrderGoods> busOrderGoods) {
        this.busOrderGoods = busOrderGoods;
    }
}
