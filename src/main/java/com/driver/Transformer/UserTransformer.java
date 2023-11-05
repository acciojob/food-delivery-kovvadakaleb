package com.driver.Transformer;

import com.driver.io.entity.UserEntity;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.UserResponse;
import com.driver.shared.dto.UserDto;

import java.util.Random;
import java.util.UUID;

public class UserTransformer {

    public static UserDto UserDetailsRequestModel_To_UserDto(UserDetailsRequestModel userDetailsRequestModel) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userDetailsRequestModel.getEmail());
        userDto.setFirstName(userDetailsRequestModel.getFirstName());
        userDto.setLastName(userDetailsRequestModel.getLastName());

        return userDto;
    }

    public static UserEntity UserDto_To_UserEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setUserId(userDto.getUserId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        return userEntity;
    }

    public static UserDto UserEntity_To_UserDto(UserEntity userEntity){
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setUserId(userEntity.getUserId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        return userDto;
    }

    public static UserResponse UserDto_To_UserResponse(UserDto userDto){
         UserResponse userResponse = new UserResponse();
         userResponse.setEmail(userDto.getEmail());
         userResponse.setFirstName(userDto.getFirstName());
         userResponse.setUserId(userDto.getUserId());
         userResponse.setLastName(userDto.getLastName());
         return userResponse;
    }
}

