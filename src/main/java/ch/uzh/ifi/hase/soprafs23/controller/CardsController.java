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
public class CardsController extends BaseController {
    public static final Map<Integer,GameContext> GAME_ROOM= new ConcurrentHashMap<>();

    public static final AtomicInteger add= new AtomicInteger();


    //退出房间
    @DeleteMapping("/{roomCode}")
    public Result createGame(@PathVariable Integer roomCode){
        GameContext gameContext = GAME_ROOM.get(roomCode);
        if (!gameContext.quitGame(getUser().getId())) {
            GAME_ROOM.remove(roomCode);
        }
        return Result.success();
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

    //准备,这里是跳过以后继续游戏
    // continue the game
    @PostMapping("/{roomCode}")
    public Result continueGame(@PathVariable Integer roomCode){
        GameContext gameContext = GAME_ROOM.get(roomCode);
        gameContext.continueGame(getUser().getId());
        return Result.success();
    }
    //抢地主
    @PostMapping("/contend")
    public Result contend(Integer roomCode,boolean isContend){
        GameContext gameContext = GAME_ROOM.get(roomCode);
        gameContext.contend(getUser().getId(), isContend);
        return Result.success();
    }
}
