package com.driver.Transformer;

import com.driver.io.entity.FoodEntity;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.shared.dto.FoodDto;

public class FoodTransformer {

    public static FoodDto FoodDetailsRequestModel_To_FoodDto(FoodDetailsRequestModel foodDetailsRequestModel) {
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodCategory(foodDetailsRequestModel.getFoodCategory());
        foodDto.setFoodName(foodDetailsRequestModel.getFoodName());
        foodDto.setFoodPrice(foodDetailsRequestModel.getFoodPrice());
        return foodDto;
    }

    public static FoodEntity FoodDto_To_FoodEntity(FoodDto foodDto){
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodId(foodDto.getFoodId());
        foodEntity.setFoodCategory(foodDto.getFoodCategory());
        foodEntity.setFoodName(foodDto.getFoodName());
        foodEntity.setFoodPrice(foodDto.getFoodPrice());
        return foodEntity;
    }
    public static FoodDto FoodEntity_To_FoodDto(FoodEntity foodEntity){
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodId(foodEntity.getFoodId());
        foodDto.setId(foodEntity.getId());
        foodDto.setFoodPrice(foodEntity.getFoodPrice());
        foodDto.setFoodCategory(foodEntity.getFoodCategory());
        foodDto.setFoodName(foodEntity.getFoodName());
        return foodDto;
    }

    public static FoodDetailsResponse FoodDto_To_FoodDetailsResponse(FoodDto foodDto){
        FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
        foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
        foodDetailsResponse.setFoodId(foodDto.getFoodId());
        foodDetailsResponse.setFoodName(foodDto.getFoodName());
        foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());
        return foodDetailsResponse;
    }
}
