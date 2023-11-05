package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.Transformer.UserTransformer;
import com.driver.exception.UserNotFoundException;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
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
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/create-user")
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserTransformer.UserDetailsRequestModel_To_UserDto(userDetails);
		UserDto responseDto = userService.createUser(userDto);
		return UserTransformer.UserDto_To_UserResponse(responseDto);
	}

	@GetMapping(path = "/get-userByEmail/{email}")
	public ResponseEntity getUser(@PathVariable String email) throws Exception{
		try {
			UserDto userDto = userService.getUser(email);
			return new ResponseEntity(UserTransformer.UserDto_To_UserResponse(userDto), HttpStatus.CREATED);
		}
		catch (UserNotFoundException e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/get-userByUserId/{userId}")
	public ResponseEntity getUserByUserId(@PathVariable String userId) throws Exception {
		try {
			UserDto userDto = userService.getUserByUserId(userId);
			return new ResponseEntity(UserTransformer.UserDto_To_UserResponse(userDto), HttpStatus.CREATED);
		}
		catch (UserNotFoundException e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}


	@PutMapping(path = "/update-user/{userId}")
	public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserTransformer.UserDetailsRequestModel_To_UserDto(userDetails);
		try{
			UserDto userDto1 = userService.updateUser(userId,userDto);
			return new ResponseEntity(UserTransformer.UserDto_To_UserResponse(userDto1), HttpStatus.CREATED);
		}
		catch (UserNotFoundException e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/delete-user/{userId}")
	public ResponseEntity deleteUser(@PathVariable String userId) throws Exception {

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete User");
		try {
			userService.deleteUser(userId);
			operationStatusModel.setOperationResult("SUCCESS");
			return new ResponseEntity(operationStatusModel, HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
			operationStatusModel.setOperationResult("FAILED");
			return new ResponseEntity(operationStatusModel, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get-allUsers")
	public List<UserResponse> getUsers(){
        List<UserDto> userDtoList = userService.getUsers();
		List<UserResponse> userResponseList = new ArrayList<>();
		for(UserDto userDto : userDtoList){
			userResponseList.add(UserTransformer.UserDto_To_UserResponse(userDto));
		}
		return userResponseList;
	}
	
}
