package ch.uzh.ifi.hase.soprafs23.core;

import ch.uzh.ifi.hase.soprafs23.constant.CombinationType;
import ch.uzh.ifi.hase.soprafs23.model.Poker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PokerCombinationTest {

    private PokerCombination pokerCombinationUnderTest;

    @BeforeEach
    void setUp() {
        pokerCombinationUnderTest = new PokerCombination();
        pokerCombinationUnderTest.setCard(List.of(Poker.builder().type(1).value(1).build()));

    }
}
