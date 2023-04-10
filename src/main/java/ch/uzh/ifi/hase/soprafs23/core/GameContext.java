package ch.uzh.ifi.hase.soprafs23.core;

import ch.uzh.ifi.hase.soprafs23.model.Poker;
import ch.uzh.ifi.hase.soprafs23.model.UserVo;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 游戏牌局上下文：
 * 初始化牌组，加入房间等等
 */

@Data
public class GameContext {

    /**
     * 牌局用户 用户id，用户对象
     */
    private List<UserVo> userList = Lists.newArrayList();

    private List<Poker> pokers;

    /**
     * 是否结束
     */
    private volatile boolean isGameOver;

    /**
     * 当前操作用户
     */
    private Integer now;

    /**
     * 庄
     */
    private Integer start;


    /**
     * 上一副牌
     */
    private PokerCombination last;


    /**
     * 当前出牌对象
     */
    private UserVo user;

    public UserVo getUser(Integer id){
        for (UserVo userVo : this.userList) {
            if( userVo.getId().equals(id)){
                return userVo;
            }
        }
        throw new RuntimeException("unknown user");
    }

    public void continueGame(Integer userId){
        UserVo user = getUser(userId);
        user.setContinue(true);
        List<UserVo> collect = this.userList.stream().filter(UserVo::isContinue).collect(Collectors.toList());
        if(collect.size()==3){
            sendCard();
        }
    }


    /**
     * 跳过
     */
    public void pass(Integer id){
        if(!this.userList.get(this.now).getId().equals(id)){
            throw new RuntimeException("不是你的回合");
        }
        if(Objects.isNull(last)){
            throw new RuntimeException("先手，不能跳过");
        }
        //下一位
        //如果最后一手牌和下一位是同一个用户 则重置上一首牌
        if(now == this.userList.indexOf( getUser(this.last.getUserId()))){
            this.last = null;
        }

    }


}
