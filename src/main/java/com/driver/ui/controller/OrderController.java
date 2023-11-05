package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.Transformer.OrderTransformer;
import com.driver.exception.OrderNotFoundException;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	@GetMapping(path="/{id}")
	public ResponseEntity getOrder(@PathVariable String id) throws Exception {
		try {
			OrderDto resultDto = orderService.getOrderById(id);
			return new ResponseEntity(OrderTransformer.OrderDto_To_OrderDetailsResponse(resultDto), HttpStatus.CREATED);
		}
		catch (OrderNotFoundException e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/place-order")
	public ResponseEntity createOrder(@RequestBody OrderDetailsRequestModel order) throws Exception {
		OrderDto orderDto = OrderTransformer.OrderDetailsRequestModel_To_OrderDto(order);

		try {
			OrderDto resultOrder = orderService.createOrder(orderDto);
			return new ResponseEntity(OrderTransformer.OrderDto_To_OrderDetailsResponse(resultOrder), HttpStatus.CREATED);
		}
		catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
		
	@PutMapping(path="/{id}")
	public ResponseEntity updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		try {
			OrderDto resultDto = orderService.updateOrderDetails(id, OrderTransformer.OrderDetailsRequestModel_To_OrderDto(order));
			return new ResponseEntity(OrderTransformer.OrderDto_To_OrderDetailsResponse(resultDto), HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Deleting Order");
		try {
			orderService.deleteOrder(id);
			operationStatusModel.setOperationResult("SUCCESS");
			return new ResponseEntity(operationStatusModel,HttpStatus.CREATED);
		}
		catch (OrderNotFoundException e){
			operationStatusModel.setOperationResult("FAILED");
			return new ResponseEntity(operationStatusModel,HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/get-allOrders")
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDetailsResponse> orderDetailsResponses = new ArrayList<>();
		List<OrderDto> orderDtoList = orderService.getOrders();
		for(OrderDto orderDto : orderDtoList){
			orderDetailsResponses.add(OrderTransformer.OrderDto_To_OrderDetailsResponse(orderDto));
		}
		return orderDetailsResponses;
	}
}
