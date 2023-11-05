package com.driver.Transformer;

import com.driver.io.entity.OrderEntity;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.shared.dto.OrderDto;

import java.util.UUID;

public class OrderTransformer {

    public static OrderDto OrderDetailsRequestModel_To_OrderDto(OrderDetailsRequestModel model){
        OrderDto orderDto = new OrderDto();
        orderDto.setItems(model.getItems());
        orderDto.setUserId(model.getUserId());
        return orderDto;
    }

    public static OrderEntity OrderDto_To_OrderEntity(OrderDto orderDto,double cost){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(String.valueOf(UUID.randomUUID()));
        orderEntity.setCost((float) cost);
        orderEntity.setStatus(true);
        orderEntity.setItems(orderDto.getItems());
        orderEntity.setUserId(orderDto.getUserId());
        return orderEntity;
    }

    public static OrderDto OrderEntity_To_OrderDto(OrderEntity orderEntity){
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(orderEntity.getUserId());
        orderDto.setCost(orderEntity.getCost());
        orderDto.setId(orderEntity.getId());
        orderDto.setStatus(orderEntity.isStatus());
        orderDto.setOrderId(orderEntity.getOrderId());
        orderDto.setItems(orderEntity.getItems());
        return orderDto;
    }

    public static OrderDetailsResponse OrderDto_To_OrderDetailsResponse(OrderDto orderDto){
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
       orderDetailsResponse.setCost(orderDto.getCost());
       orderDetailsResponse.setItems(orderDto.getItems());
       orderDetailsResponse.setOrderId(orderDto.getOrderId());
       orderDetailsResponse.setStatus(orderDto.isStatus());
       orderDetailsResponse.setUserId(orderDto.getUserId());
       return orderDetailsResponse;
    }

}
