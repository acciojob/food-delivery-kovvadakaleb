package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.Transformer.FoodTransformer;
import com.driver.exception.FoodNotAvailbleException;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodService foodService;

	@PostMapping("/create-food")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto = FoodTransformer.FoodDetailsRequestModel_To_FoodDto(foodDetails);
		FoodDto resultDto = foodService.createFood(foodDto);
		return FoodTransformer.FoodDto_To_FoodDetailsResponse(resultDto);
	}

	@GetMapping(path="/get-food/{foodId}")
	public ResponseEntity getFood(@PathVariable String foodId) throws Exception{
		try {
			FoodDto foodDto = foodService.getFoodById(foodId);
			return new ResponseEntity(FoodTransformer.FoodDto_To_FoodDetailsResponse(foodDto), HttpStatus.CREATED);
		}
		catch (FoodNotAvailbleException e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path="/update-foodDetails/{foodId}")
	public ResponseEntity updateFood(@PathVariable String foodId, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
        try{
			FoodDto foodDto = FoodTransformer.FoodDetailsRequestModel_To_FoodDto(foodDetails);
			FoodDto resultDto = foodService.updateFoodDetails(foodId,foodDto);
			return new ResponseEntity(FoodTransformer.FoodDto_To_FoodDetailsResponse(resultDto),HttpStatus.CREATED);
		}
		catch (FoodNotAvailbleException e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/delete-food/{foodId}")
	public ResponseEntity deleteFood(@PathVariable String foodId) throws Exception{
        OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete Food Details");
		try{
			foodService.deleteFoodItem(foodId);
			operationStatusModel.setOperationResult("SUCCESS");
			return  new ResponseEntity(operationStatusModel,HttpStatus.CREATED);
		}
		catch(FoodNotAvailbleException e){
			operationStatusModel.setOperationResult("FAILED");
			return new ResponseEntity(operationStatusModel,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get-allFoodsAvailable")
	public List<FoodDetailsResponse> getFoods() {
        List<FoodDto> foodDtoList = foodService.getFoods();
		List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();
		for(FoodDto foodDto : foodDtoList){
			foodDetailsResponseList.add(FoodTransformer.FoodDto_To_FoodDetailsResponse(foodDto));
		}
		return foodDetailsResponseList;
	}
}
