package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Result;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  //UserController(UserService userService) {
//    this.userService = userService;
//  }

    //register
    @PostMapping("/register")
    public Object register(@RequestBody User user){
        userService.createUser(user);
        return user;
    }

    //login
    @PostMapping("/login")
    public Result login(String username, String password){
        User login = userService.login(username, password);
        login.setPassword(null);
        return Result.success(login);
    }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserGetDTO> getAllUsers() {
    // fetch all users in the internal representation
    List<User> users = userService.getUsers();
    List<UserGetDTO> userGetDTOs = new ArrayList<>();

    // convert each user to the API representation
    for (User user : users) {
      userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
    }
    return userGetDTOs;
  }

//  @PostMapping("/users")
//  @ResponseStatus(HttpStatus.CREATED)
//  @ResponseBody
//  public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
//    // convert API user to internal representation
//    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
//
//    // create user
//    User createdUser = userService.createUser(userInput);
//    // convert internal representation of user back to API
//    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
//  }
}
