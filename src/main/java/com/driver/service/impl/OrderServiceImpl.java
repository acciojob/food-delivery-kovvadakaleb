package com.driver.service.impl;

import com.driver.Transformer.OrderTransformer;
import com.driver.exception.FoodNotAvailbleException;
import com.driver.exception.OrderNotFoundException;
import com.driver.exception.UserNotFoundException;
import com.driver.io.entity.FoodEntity;
import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.io.repository.OrderRepository;
import com.driver.io.repository.UserRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    FoodRepository foodRepository;
    @Override
    public OrderDto createOrder(OrderDto order) throws Exception {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByUserId(order.getUserId()));
        if(userEntityOptional.isEmpty()){
            throw new UserNotFoundException("Invalid User");
        }
        String[] foodItems = order.getItems();
        UserEntity userEntity = userEntityOptional.get();
        List<FoodEntity> foodEntities = (List<FoodEntity>) foodRepository.findAll();
        double cost = 0;
        for(int i=0;i<foodItems.length;i++) {
            boolean available= false;
            for (FoodEntity foodEntity : foodEntities) {
               if(foodItems[i].equals(foodEntity.getFoodName())){
                   cost += foodEntity.getFoodPrice();
                   available = true;
               }
            }
            if(available==false){
                throw new FoodNotAvailbleException("Dishes You are Ordering Not Available");
            }
        }

        OrderEntity orderEntity = OrderTransformer.OrderDto_To_OrderEntity(order,cost);
        orderEntity.setUserEntity(userEntity);
        OrderEntity savedEntity = orderRepository.save(orderEntity);

        return OrderTransformer.OrderEntity_To_OrderDto(savedEntity);
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        Optional<OrderEntity> optionalOrderEntity = Optional.ofNullable(orderRepository.findByOrderId(orderId));
        if(optionalOrderEntity.isEmpty()){
            throw new OrderNotFoundException("OrderId Invalid");
        }
        OrderEntity orderEntity = optionalOrderEntity.get();

        return OrderTransformer.OrderEntity_To_OrderDto(orderEntity);
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        Optional<OrderEntity> optionalOrderEntity = Optional.ofNullable(orderRepository.findByOrderId(orderId));
        if(optionalOrderEntity.isEmpty()){
            throw new OrderNotFoundException("OrderId Invalid");
        }

        Optional<UserEntity> optionalUserEntity = Optional.ofNullable(userRepository.findByUserId(order.getUserId()));
        if(optionalUserEntity.isEmpty()){
            throw new UserNotFoundException("UserId Not Found");
        }
       List<FoodEntity> foodEntities = (List<FoodEntity>) foodRepository.findAll();
       String items[] = order.getItems();
       int cost = 0;
       for(int i=0;i<items.length;i++){
           boolean found = false;
           for(FoodEntity foodEntity : foodEntities){
               if(foodEntity.getFoodName().equals(items[i])){
                   found = true;
                   cost += foodEntity.getFoodPrice();
               }
           }

           if(found==false){
               throw new FoodNotAvailbleException("Food you are selecting Not Available");
           }
       }

        OrderEntity orderEntity = optionalOrderEntity.get();
        orderEntity.setUserId(order.getUserId());
        orderEntity.setUserEntity(optionalUserEntity.get());
        orderEntity.setItems(order.getItems());
        orderEntity.setCost(cost);
        OrderEntity savedEntity = orderRepository.save(orderEntity);

        return OrderTransformer.OrderEntity_To_OrderDto(savedEntity);
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        Optional<OrderEntity> optionalOrderEntity = Optional.ofNullable(orderRepository.findByOrderId(orderId));
        if(optionalOrderEntity.isEmpty()){
            throw new OrderNotFoundException("OrderId Invalid");
        }
        Long id = optionalOrderEntity.get().getId();
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<OrderEntity> orderEntityList = (List<OrderEntity>) orderRepository.findAll();
        for(OrderEntity orderEntity : orderEntityList){
            orderDtoList.add(OrderTransformer.OrderEntity_To_OrderDto(orderEntity));
        }
        return orderDtoList;
    }
}