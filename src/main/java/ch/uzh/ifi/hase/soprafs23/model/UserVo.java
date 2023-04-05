package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.websocket.Session;
import java.util.List;

@Data
public class UserVo extends User {

    /**
     * 手牌
     */
    private List<Poker> handCard;


    /***
     * 是否抢地主
     */
    private Boolean contend;

    /**
     * 是否继续
     */
    private boolean isContinue;

    /**
     * session
     */
    private Session session;


    public UserVo(User user){
        BeanUtils.copyProperties(user,this);
        this.handCard = Lists.newArrayList();
    }
}
