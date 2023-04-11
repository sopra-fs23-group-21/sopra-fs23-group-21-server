package ch.uzh.ifi.hase.soprafs23.core;

import ch.uzh.ifi.hase.soprafs23.model.Poker;
import ch.uzh.ifi.hase.soprafs23.model.Result;
import ch.uzh.ifi.hase.soprafs23.model.UserVo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
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

    /**
     * 获胜者id
     */
    private Integer winner;

    public UserVo getUser(Integer id) {
        for (UserVo userVo : this.userList) {
            if (userVo.getId().equals(id)) {
                return userVo;
            }
        }
        throw new RuntimeException("unknown user");
    }

    //同步
    public void sync() {

        Gson gson = new Gson();
        Result<GameContext> success = Result.success(this);
        WebSocketConfigOne.executor.execute(() -> {
            //同步数据
            this.userList.forEach(userVo -> {
                if (Objects.isNull(userVo.getSession())) {
                    return;
                }
                try {
                    synchronized (userVo) {
                        userVo.getSession().getBasicRemote().sendText(gson.toJson(success));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    //同步
    public void sync(Integer id) {

        Gson gson = new Gson();
        Result<GameContext> success = Result.success(this);

        UserVo user = getUser(id);
        WebSocketConfigOne.executor.execute(() ->
                user.getSession().getAsyncRemote().sendText(gson.toJson(success))
        );
    }

    /**
     * 发牌
     */
    public void sendCard() {

        this.start = (int) (Math.random() * 3);

        initPokers();
        //发牌
        int i = this.start;

        while (pokers.size() != 3) {

            UserVo userVo = userList.get(i);
            //随机抽取一张底牌
            int num = (int) (Math.random() * pokers.size());

            Poker poker = pokers.get(num);

            pokers.remove(poker);
            //加入手牌
            userVo.getHandCard().add(poker);

            if (++i == 3) {
                i = 0;
            }
        }

        gameStatus = 2;

        this.now = start;
        //同步牌
        sync();

    }

    /**
     * 初始化牌组
     */
    public void initPokers() {
        pokers = Lists.newArrayList(Poker.builder().value(14).build(),
                Poker.builder().value(15).build());
        for (int i = 1; i < 14; i++) {
            for (int j = 1; j < 5; j++) {
                pokers.add(Poker.builder()
                        .value(i)
                        .type(j)
                        .build());
            }
        }
    }

    public void continueGame(Integer userId) {
        UserVo user = getUser(userId);
        user.setContinue(true);
        List<UserVo> collect = this.userList.stream().filter(UserVo::isContinue).collect(Collectors.toList());
        if (collect.size() == 3) {
            sendCard();
        }
    }

    /***
     * 出牌 to 未知牌型 出牌错误
     * @param pokerCombination
     */
    public boolean pay(PokerCombination pokerCombination, Integer userId) {
        pokerCombination.initType();
        if (!this.userList.get(this.now).getId().equals(userId)) {
            throw new RuntimeException("不是你的回合");
        }
        //比较牌型
        if (Objects.nonNull(this.last) && !this.last.compareTo(pokerCombination)) {
            throw new RuntimeException("牌型不符合");
        }
        // 扣减手牌
        this.last = pokerCombination;
        UserVo userVo = getUser(pokerCombination.getUserId());
        userVo.getHandCard().removeAll(pokerCombination.getCard());
        // 判断是否还有手牌
        if (userVo.getHandCard().size() == 0) {
            this.winner = this.now;
            gameOver();
        }
        else {
            // 更换出牌对象
            next();
        }
        sync();
        return true;
    }


    /**
     * 跳过
     */
    public void pass(Integer id) {
        if (!this.userList.get(this.now).getId().equals(id)) {
            throw new RuntimeException("不是你的回合");
        }
        if (Objects.isNull(last)) {
            throw new RuntimeException("先手，不能跳过");
        }
        //下一位
        //如果最后一手牌和下一位是同一个用户 则重置上一首牌
        if (now == this.userList.indexOf(getUser(this.last.getUserId()))) {
            this.last = null;
        }

    }


}
