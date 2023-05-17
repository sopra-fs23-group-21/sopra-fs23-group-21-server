package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data

public class Poker implements Comparable<Poker>{

    /**
     * 牌值
     */
    private Integer value;
    /**
     * 花色 1红桃 2方块 3草花 4黑桃
     */
    private Integer type;

    @Override
    public int compareTo(Poker o){
        return this.value - o.getValue();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
