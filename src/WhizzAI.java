
//this is the intelligent AI
//it is competitive.
// it will first evaluate its hand, make a decision to remove the useless cards and not random


import java.util.LinkedList;

public class WhizzAI extends Hand{

    //constructor
    public WhizzAI(){}

    @Override
    public LinkedList<Cardable> discard() {
        LinkedList<Cardable> discarded = new LinkedList<>();
        Cardable[] myarray = new Cardable[5];
        String rank;
        int [] arr;
        //cpu evaluates its cards.
       if(findFlush()!= null || findStraight()!= null){ // the hand is good for the cpu will not want to loose it
           return discarded;
       }else{

           arr = getAnyPair(getList().toArray(myarray));
           if(arr[3]!=0){//there is a full house , the cpu has a big chance to win
               return discarded;
           }else{
               for(int i =0; i < HAND_SIZE; i++){
                   if(arr[1]!= ((Card)getList().get(i)).getVal()){
                       discarded.add(getCard(i));
                       getList().set(i, null);
                   }
               }
           }
       }
     return discarded;
    }
}
