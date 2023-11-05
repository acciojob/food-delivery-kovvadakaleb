package com.driver.service.impl;

import com.driver.Transformer.UserTransformer;
import com.driver.exception.UserNotFoundException;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        user.setUserId(String.valueOf(UUID.randomUUID()));
        UserEntity userEntity = UserTransformer.UserDto_To_UserEntity(user);
        UserEntity savedUser = userRepository.save(userEntity);
        return UserTransformer.UserEntity_To_UserDto(savedUser);
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if(userEntityOptional.isEmpty()){
            throw new UserNotFoundException("Invalid Email ID");
        }

        UserEntity userEntity = userEntityOptional.get();
        return UserTransformer.UserEntity_To_UserDto(userEntity);
    }
    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
       Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByUserId(userId));
       if(userEntityOptional.isEmpty()){
          throw new UserNotFoundException("Invalid User ID");
       }

       UserEntity userEntity = userEntityOptional.get();
       return UserTransformer.UserEntity_To_UserDto(userEntity);

    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByUserId(userId));
        if(userEntityOptional.isEmpty()){
            throw new UserNotFoundException("Invalid User ID");
        }
        UserEntity userEntity = userEntityOptional.get();
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());

        UserEntity savedUser = userRepository.save(userEntity);

        return UserTransformer.UserEntity_To_UserDto(savedUser);
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByUserId(userId));
        if (userEntityOptional.isEmpty()) {
           throw new UserNotFoundException("Invalid UserId");
        }
        UserEntity userEntity = userEntityOptional.get();
        long id = userEntity.getId();
        userRepository.deleteById(id);
    }
    @Override
    public List<UserDto> getUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<UserEntity> userEntityList = (List<UserEntity>) userRepository.findAll();
        for(UserEntity userEntity : userEntityList){
            userDtoList.add(UserTransformer.UserEntity_To_UserDto(userEntity));
        }
        return  userDtoList;
    }
}