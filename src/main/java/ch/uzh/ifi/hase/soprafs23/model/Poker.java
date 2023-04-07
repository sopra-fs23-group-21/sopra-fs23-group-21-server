package ch.uzh.ifi.hase.soprafs23.model;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Poker implements Comparable<Poker>{

    /**
     * 值
     */
    private Integer value;
    /**
     * 花色类型 1 红桃，2方块，3樱花，4黑桃
     */
    private Integer type;

    @Override
    public int compareTo(Poker o) {
        return this.value-o.getValue();
    }

}
