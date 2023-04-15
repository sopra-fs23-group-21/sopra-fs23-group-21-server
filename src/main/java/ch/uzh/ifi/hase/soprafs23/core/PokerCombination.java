package ch.uzh.ifi.hase.soprafs23.core;

import ch.uzh.ifi.hase.soprafs23.constant.CombinationType;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 牌组对象
 */
@Data
public class PokerCombination {


    /**
     * 牌型
     */
    private CombinationType combinationType;

    private List<Poker> card;

    /**
     * 出牌用户
     */
    private Integer userId;




    /**
     * 三带一找到三张的牌值
     * @param pokerCombination
     * @return
     */
    private Integer getIdentical(PokerCombination pokerCombination){
        List<Poker> num = pokerCombination.getCard();
        Map<Integer, List<Poker>> collect = num.stream()
                .collect(Collectors.groupingBy(Poker::getValue));

        Collection<List<Poker>> values = collect.values();
        for (List<Poker> value : values) {
            if(value.size()==3){
                return value.get(0).getValue();
            }
        }
        throw new RuntimeException("不是三带一");
    }


    /**
     * 飞机找到三张最大的牌值
     * @param pokerCombination
     * @return
     */
    private Integer getDoubleThreeMaxValue(PokerCombination pokerCombination){
        List<Poker> num = pokerCombination.getCard();
        Map<Integer, List<Poker>> collect = num.stream().collect(Collectors.groupingBy(Poker::getValue));
        List<Integer> head = Lists.newArrayList();
        for (Map.Entry<Integer, List<Poker>> integerListEntry : collect.entrySet()) {
            if (integerListEntry.getValue().size()==3) {
                head.add(integerListEntry.getKey());
            }
        }
        return Collections.max(head);
    }

    /**
     * 连队的最大值
     * @param pokerCombination
     */
    public Integer isDoubleContinuationMaxValue(PokerCombination pokerCombination){
        List<Poker> num = pokerCombination.getCard();
        Map<Integer, List<Poker>> collect = num.stream().collect(Collectors.groupingBy(Poker::getValue));
        return Collections.max(Lists.newArrayList(collect.keySet().iterator()));
    }
    /**
     * wather the value of 'four and two' is biggest
     * @param pokerCombination
     */


    /**
     * 判断类型
     */
    public void  initType(){
        if(card.size()==1){
            this.combinationType = CombinationType.ONE;
        }else if(isTwo(card)){
            this.combinationType = CombinationType.TWO;
        }else  if(isFour(card)){
            this.combinationType = CombinationType.FOUR;
        }else if(isThreeAndOne(card)){
            this.combinationType = CombinationType.THREEANDONE;
        }else if(isThree(card)){
            this.combinationType = CombinationType.THREE;
        }else if(isDoubleKing(card)){
            this.combinationType = CombinationType.DOUBLEKING;
        }else if(isContinuation(card)){
            this.combinationType = CombinationType.CONTINUATION;
        }else if(isDoubleContinuation(card)){
            this.combinationType = CombinationType.DOUBLECONTINUATION;
        }else if(isDoubleThree(card)){
            this.combinationType = CombinationType.DOUBLETHREE;
        }else  if(isFourAndThree(card)){
            this.combinationType = CombinationType.FOURANDTHREE;
        }else{
            throw new RuntimeException("未知牌型");
        }
    }

    /**
     * 是否是 牌型对
     */
    private boolean  isTwo( List<Poker> card){
        if(card.size()!=2){
            return false;
        }
        return  card.get(0).getValue() == card.get(1).getValue() && card.get(0).getValue()!=14 && card.get(1).getValue()!=15;
    }


    /**
     * 是否三代一
     */
    private boolean  isThreeAndOne( List<Poker> card){
        if(card.size()>5){
            return false;
        }
        Map<Integer, List<Poker>> collect = card.stream()
                .collect(Collectors.groupingBy(Poker::getValue));
        if(collect.size()!=2){
            return false;
        }
        for (List<Poker> value : collect.values()) {
            if(value.size()==3){
                return true;
            }
        }
        return  false;
    }


    /**
     * 是否三不带
     */
    private boolean  isThree( List<Poker> card){
        if(card.size()!=3){
            return false;
        }
        Integer lastNum = null;
        for (Poker next : card) {
            if(Objects.isNull(lastNum)){
                lastNum = next.getValue();
            }else {
                if(lastNum != next.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否炸弹 determine weather it is bomb
     */
    private boolean  isFour( List<Poker> card){
        if(card.size()!=4){
            return false;
        }
        Integer lastNum = null;
        for (Poker next : card) {
            if(Objects.isNull(lastNum)){
                lastNum = next.getValue();
            }else {
                if(lastNum != next.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否是王炸
     */
    private boolean  isDoubleKing( List<Poker> card){
        if(card.size()!=2){
            return false;
        }
        List<Integer> integers = Lists.newArrayList(14, 15);
        return integers.contains(card.get(0).getValue()) && integers.contains(card.get(1).getValue());
    }

    /**
     * 是否是顺子
     */
    private boolean  isContinuation(List<Poker> card){
        if(card.size()<5){
            return false;
        }
        Collections.sort(card);
        Integer tempNumber = null;
        for (Poker next : card) {
            if(Objects.nonNull(tempNumber)){
                if(tempNumber+1 != next.getValue()){
                    return false;
                }
            }
            tempNumber = next.getValue();
        }
        //13 为牌2
        return tempNumber <13;
    }
    //
//    DOUBLECONTINUATION(1),
    /**
     * 是否是连队
     */
    private boolean  isDoubleContinuation(List<Poker> card){
        if(card.size()<6 || card.size()%2==1){
            return false;
        }
        Map<Integer, List<Poker>> cardMap = card.stream().collect(Collectors.groupingBy(Poker::getValue));
        //判断是否都是一对
        for (Map.Entry<Integer, List<Poker>> c : cardMap.entrySet()) {
            if(c.getValue().size()!=2){
                return false;
            }
        }
        //是否是顺序 判断是否都为顺
        List<Integer> key = cardMap.keySet().stream().collect(Collectors.toList());
        Collections.sort(key);
        Integer tempNumber = null;
        for (Integer next : key) {
            if(Objects.nonNull(tempNumber)){
                if(tempNumber+1 != next){
                    return false;
                }
            }
            tempNumber = next;
        }
        //13 为牌2
        return tempNumber <13;
    }

    /***
     * 是否是飞机
     * @param card
     * @return
     */
    private boolean  isDoubleThree(List<Poker> card){
        if(card.size()<8){
            return false;
        }
        //通过数值分组
        Map<Integer, List<Poker>> cardMap = card.stream().collect(Collectors.groupingBy(Poker::getValue));

        //带牌
        List<Integer> tail = Lists.newArrayList();
        //三张的牌
        List<Integer> head = Lists.newArrayList();
        for (Map.Entry<Integer, List<Poker>> c : cardMap.entrySet()) {
            if(c.getValue().size()==3){
                head.add(c.getKey());
            }else {
                tail.add(c.getKey());
            }
        }
        if (head.size()<2) {
            return false;
        }
        //判断情况一 三带一            判断情况二 是否是三代二
        if((card.size()-head.size()*3) != head.size() && tail.size() != head.size()){

            return false;
        }

        //判断是否有序
        Collections.sort(head);
        Integer tempNumber = null;
        for (Integer next : head) {
            if(Objects.nonNull(tempNumber)){
                if(tempNumber+1 != next){
                    return false;
                }
            }
            tempNumber = next;
        }
        //13 为牌2
        return tempNumber <13;



        /**
         * whether it is 'four and two'是否是四带二
         * @param card
         * @return
         */
    
    }



}
