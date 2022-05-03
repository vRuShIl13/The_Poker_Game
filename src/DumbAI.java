// this is the ai that will be stupid when it comes to select the cards to discard.
// it will randomly discard cards hoping to win miraculously.

import java.util.LinkedList;
import java.util.Random;

public class DumbAI extends Hand {
    //it will hold an instance of hand that contains 5 poker cards

    //constructor
    public DumbAI(){
    }
    //discard cards randomly from the hand.
    //SELECT random cards.
    public LinkedList<Cardable> discard(){
        LinkedList<Cardable> discarded = new LinkedList<>();
        Random rand = new Random();
        int upperB = 5;
        int howMany = rand.nextInt(upperB);
        int whichToPick;

        for (int i = 0; i <= howMany; i++){
            whichToPick = rand.nextInt(5);
            if(getCard(whichToPick) != null) {
                getCard(whichToPick).switchSelectedState();
                discarded.add(getCard(whichToPick));
                getList().set(whichToPick, null);
            }
        }
        return discarded;
    }

}
