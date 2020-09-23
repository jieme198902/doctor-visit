package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusOrderGoods;
import com.doctor.visit.domain.BusOrderGoodsTotal;

import java.util.List;

public class BusOrderGoodsTotalDto extends BusOrderGoodsTotal {

    private List<BusOrderGoods> busOrderGoods;

    public List<BusOrderGoods> getBusOrderGoods() {
        return busOrderGoods;
    }

    public void setBusOrderGoods(List<BusOrderGoods> busOrderGoods) {
        this.busOrderGoods = busOrderGoods;
    }
}
