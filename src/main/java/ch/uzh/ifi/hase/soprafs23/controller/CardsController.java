package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.controller.base.BaseController;
import ch.uzh.ifi.hase.soprafs23.core.GameContext;
import ch.uzh.ifi.hase.soprafs23.core.PokerCombination;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@RequestMapping("/cards")
@RestController
public class CardsController {
    public static final Map<Integer,GameContext> GAME_ROOM= new ConcurrentHashMap<>();

    public static final AtomicInteger add= new AtomicInteger();
    public static final ThreadLocal<User> SESSION_USER = new ThreadLocal<>();
    public User getUser(){
        return SESSION_USER.get();
    }

    //出牌
    //play the cards
    @PostMapping("/pay/{roomCode}")
    public Result pay(@PathVariable Integer roomCode, @RequestBody PokerCombination pokerCombination){
        GameContext gameContext = GAME_ROOM.get(roomCode);
        gameContext.pay(pokerCombination, getUser().getId());
        return Result.success();
    }
    //跳过
    // Execute pass operation
    @PostMapping("/pass/{roomCode}")
    public Result pay(@PathVariable Integer roomCode){
        GameContext gameContext = GAME_ROOM.get(roomCode);
        gameContext.pass(getUser().getId());
        return Result.success();
    }
}
