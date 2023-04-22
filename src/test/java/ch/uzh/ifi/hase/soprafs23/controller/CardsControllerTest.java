package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.config.WebSocketConfigOne;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.core.GameContext;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;









@WebMvcTest(CardsController.class)
class CardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomSync mockRoomSync;

    private Gson gson = new Gson();


    User user;
    @BeforeEach
    public void initUser(){
        user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setPassword("firstname@123");
        user.setToken("1");
        user.setId(1);
        user.setStatus(UserStatus.OFFLINE);
        WebSocketConfigOne.executor = new ThreadPoolExecutor(4,40,60l, TimeUnit.SECONDS, new LinkedBlockingQueue<>(8));
        GameContext gameContext = new GameContext();
        gameContext.prepare(user);
        CardsController.GAME_ROOM.put(0,gameContext);

    }
    @Test
    void testCreateGame1() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/cards") .content(gson.toJson(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(mockRoomSync).push();
    }





}
