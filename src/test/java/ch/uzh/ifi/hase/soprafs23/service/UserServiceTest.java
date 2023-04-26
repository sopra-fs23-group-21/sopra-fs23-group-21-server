package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {
    @Autowired
    private UserService userService;

    private User user;

    public static final AtomicInteger add= new AtomicInteger();


    @BeforeEach
    public void setup() {
        if(Objects.nonNull(user)){
            return;
        }
        int i = add.get();
        user = new User();
        user.setName("Firstname Lastname"+i);
        user.setUsername("firstname@lastname"+i);
        user.setPassword("firstname@123"+i);
        user.setStatus(UserStatus.OFFLINE);
        try {
            user =  userService.createUser(user);
        }catch (Exception e){
            log.info(e.getMessage());
        }

        assertNotNull(user.getToken());
    }

    @Test
    @DirtiesContext
    public void testLogin() {

        User found = userService.login(user.getUsername(),user.getPassword());
        assertNotNull(found.getId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
    }

    @Test
    @DirtiesContext
    public void testGetUserByToken() {

        User found = userService.getUserByToken(user.getToken());
        assertNotNull(found.getId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
    }


    @Test
    @DirtiesContext
    public void testCreateUser(){
        user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setPassword("firstname@123");
        user.setStatus(UserStatus.OFFLINE);
        try {
            user =  userService.createUser(user);
        }catch (Exception e){}
        user.setUsername("firstname@lastname1");
        try {
            user =  userService.createUser(user);
        }catch (Exception e){}
        user.setUsername("firstname@lastname");
        user.setName("Firstname Lastname2");
        try {
            user =  userService.createUser(user);
        }catch (Exception e){}    }

}