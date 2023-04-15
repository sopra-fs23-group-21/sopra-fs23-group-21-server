package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Poker implements Comparable<Poker>{

    // 1->3 2->4 3->5 4->6 5->7 6->8 7->9 8->10 9->j 10->Q 11->K 12->A 13->2 14->small joker 15->big joker

    /**
     * value of card
     */
    private Integer value;

//    suit: 1 heart 2 diamond 3 clubs 4 spades

    private Integer type;

    @Override
    public int compareTo(Poker o) {
        return this.value-o.getValue();
    }

}
