package com.stylefeng.guns.rest.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.order.OrderService;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = Service.class)
public class OrderServiceImpl implements OrderService {

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public String getSoldSeatsByFileId(Integer fileId) {
        return null;
    }
}
