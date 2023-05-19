package ch.uzh.ifi.hase.soprafs23.core;

import ch.uzh.ifi.hase.soprafs23.constant.CombinationType;
import ch.uzh.ifi.hase.soprafs23.model.Poker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PokerCombinationTest {

    private PokerCombination pokerCombinationUnderTest;

    @BeforeEach
    void setUp() {
        pokerCombinationUnderTest = new PokerCombination();
        pokerCombinationUnderTest.setCard(List.of(Poker.builder().type(1).value(1).build()));

    }

    @Test
    void testCompareTo() {
        // Setup
        PokerCombination o = new PokerCombination();
        o.setCard(List.of(Poker.builder()
                .value(2)
                .build()));
        // Setup 一张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build()));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));
        // Verify the results

        // Setup 两张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(1).build()));
        o.setCard(Lists.newArrayList(Poker.builder().type(1).value(2).build(),Poker.builder().type(1).value(2).build()));
        // Run the test

        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));


        // Setup 三张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build(),
                Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(1).build()));
        // Run the test
        o.setCard(Lists.newArrayList(Poker.builder().type(1).value(2).build(),
                Poker.builder().type(1).value(2).build(),Poker.builder().type(1).value(2).build()));
        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));

        // Setup 四张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(1,1)));
        // Run the test
        o.setCard(Lists.newArrayList(new Poker(2,1),new Poker(2,1),new Poker(2,1),new Poker(2,1)));

        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));


        // Setup 双王
        o.setCard(Lists.newArrayList(new Poker(14,1),new Poker(15,1)));
        // Run the test
        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));

        // Setup 四张带两张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(2,1),new Poker(3,1)));
        // Run the test
        o.setCard(Lists.newArrayList(new Poker(2,1),new Poker(2,1),new Poker(2,1),new Poker(2,1),new Poker(3,1),new Poker(4,1)));

        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));


        // Setup 三张带一张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(2).build(),
                Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(1).build()));
        // Run the test
        o.setCard(Lists.newArrayList(Poker.builder().type(1).value(2).build(),Poker.builder().type(1).value(2).build(),
                Poker.builder().type(1).value(2).build(),Poker.builder().type(1).value(3).build()));
        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));

        //顺子
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(1,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(4,1),
                new Poker(5,1)));
        // Run the test
        o.setCard(Lists.newArrayList(
                new Poker(6,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(4,1),
                new Poker(5,1)));

        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));


        //连队
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(1,1),
                new Poker(1,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(3,1)));
        // Run the test
        o.setCard(Lists.newArrayList(
                new Poker(4,1),
                new Poker(4,1),
                new Poker(5,1),
                new Poker(5,1),
                new Poker(6,1),
                new Poker(6,1)));
        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));
        assertTrue(pokerCombinationUnderTest.compareTo(o));


        //飞机
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(1,1),
                new Poker(1,1),
                new Poker(1,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(4,1)));
        // Run the test
        o.setCard(Lists.newArrayList(
                new Poker(3,1),
                new Poker(3,1),
                new Poker(3,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(5,1),
                new Poker(4,1)));
        assertNotNull(pokerCombinationUnderTest.compareTo(o));
        assertNotNull(pokerCombinationUnderTest.compareTo(pokerCombinationUnderTest));
    }

    @Test
    void testIsDoubleContinuationMaxValue() {
        // Setup
        final PokerCombination pokerCombination = new PokerCombination();
        pokerCombination.setCombinationType(CombinationType.ONE);
        pokerCombination.setCard(List.of(new Poker(2,1), new Poker(2,1), new Poker(3,1), new Poker(3,1), new Poker(4,1), new Poker(4,1)));
        pokerCombination.setUserId(0);

        // Run the test
        final Integer result = pokerCombinationUnderTest.isDoubleContinuationMaxValue(pokerCombination);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testIsFourAndTwoMaxValue() {
        // Setup
        final PokerCombination pokerCombination = new PokerCombination();
        pokerCombination.setCombinationType(CombinationType.ONE);
        pokerCombination.setCard(List.of(new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(2,1),new Poker(3,1)));
        pokerCombination.setUserId(0);

        // Run the test
        final Integer result = pokerCombinationUnderTest.isFourAndTwoMaxValue(pokerCombination);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testInitType() {
        // Setup 一张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build()));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());
        // Verify the results

        // Setup 两张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(1).build()));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());


        // Setup 三张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build(),
                Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(1).build()));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());

        // Setup 四张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(1,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());


        // Setup 双王
        pokerCombinationUnderTest.setCard(Lists.newArrayList(new Poker(14,1),new Poker(15,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());

        // Setup 四张带两张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(1,1),new Poker(2,1),new Poker(3,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());


        // Setup 三张带一张
        pokerCombinationUnderTest.setCard(Lists.newArrayList(Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(2).build(),
                Poker.builder().type(1).value(1).build(),Poker.builder().type(1).value(1).build()));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());

        //顺子
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(1,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(4,1),
                new Poker(5,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());


        //连队
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(1,1),
                new Poker(1,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(3,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());


        //飞机
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(1,1),
                new Poker(1,1),
                new Poker(1,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(2,1),
                new Poker(3,1),
                new Poker(4,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());

        //Test DoubleCombination
        pokerCombinationUnderTest.setCard(Lists.newArrayList(
                new Poker(3,1),
                new Poker(3,1),
                new Poker(4,1),
                new Poker(4,1),
                new Poker(5,1),
                new Poker(5,1)));
        // Run the test
        pokerCombinationUnderTest.initType();

        assertNotNull(pokerCombinationUnderTest.getCombinationType());


    }
}
