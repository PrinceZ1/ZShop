package com.princez1.ZShop.service;

import com.princez1.ZShop.dtos.OrderDetailDTO;
import com.princez1.ZShop.entities.OrderDetail;
import com.princez1.ZShop.exceptions.DataNotFoundException;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
            throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
