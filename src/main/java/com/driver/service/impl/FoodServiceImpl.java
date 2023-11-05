package com.driver.service.impl;

import com.driver.Transformer.FoodTransformer;
import com.driver.exception.FoodNotAvailbleException;
import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        food.setFoodId(String.valueOf(UUID.randomUUID()));
        FoodEntity foodEntity = FoodTransformer.FoodDto_To_FoodEntity(food);
        FoodEntity savedEntity = foodRepository.save(foodEntity);
        return FoodTransformer.FoodEntity_To_FoodDto(savedEntity);
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        Optional<FoodEntity> foodEntityOptional = Optional.ofNullable(foodRepository.findByFoodId(foodId));
        if(foodEntityOptional.isEmpty()){
            throw new FoodNotAvailbleException("Invalid FoodID");
        }
        FoodEntity foodEntity = foodEntityOptional.get();
        return FoodTransformer.FoodEntity_To_FoodDto(foodEntity);
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        Optional<FoodEntity> foodEntityOptional = Optional.ofNullable(foodRepository.findByFoodId(foodId));
        if(foodEntityOptional.isEmpty()){
            throw new FoodNotAvailbleException("Invalid FoodID");
        }
        FoodEntity foodEntity = foodEntityOptional.get();
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());

        FoodEntity savedEntity = foodRepository.save(foodEntity);

        return FoodTransformer.FoodEntity_To_FoodDto(savedEntity);
    }

    @Override
    public void deleteFoodItem(String foodId) throws Exception {
     Optional<FoodEntity> foodEntityOptional = Optional.ofNullable(foodRepository.findByFoodId(foodId));
     if(foodEntityOptional.isEmpty()){
         throw new FoodNotAvailbleException("Invalid Food ID");
     }
     Long id = foodEntityOptional.get().getId();
     foodRepository.deleteById(id);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> foodDtoList = new ArrayList<>();
        List<FoodEntity> foodEntityList = (List<FoodEntity>) foodRepository.findAll();
        for(FoodEntity foodEntity : foodEntityList){
            foodDtoList.add(FoodTransformer.FoodEntity_To_FoodDto(foodEntity));
        }
        return foodDtoList;
    }
}