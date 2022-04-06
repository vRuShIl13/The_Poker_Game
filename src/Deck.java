/*
    This class implements the interface called Deckable, So the deck will be
    a bundle of 52 cardable cards of 4 different suits 13 in each
 */


import java.util.LinkedList;
import java.util.*;

public class Deck implements Deckable{
    private LinkedList<Cardable> list;

    //constructor
    public Deck(){
        list = new LinkedList<Cardable>();
    }

    //this method is for creating the deck.
    //a deck of 52 cardable cards
    //each card is unique with a different value and a suit
    public void createDeck(){
        //total cards per suit
        int cardsPerSuit = Deckable.NUM_CARDS/4;

        for(Cardable.Suit s : Cardable.Suit.values()) {
            for (int i = 2; i < cardsPerSuit+2; i++) {
                Card newCard = new Card(i,s);
                list.add(newCard);
            }
        }
    }

    //this method is used to shuffle the elements in the list
    //changes the position of the cards in the deck to other random positions.
    @Override
    public void shuffle() {

        Collections.shuffle(list);

    }

    //adding a list to the end of the main list
    //this method will be called when a player discards sme cards from their hand.
    //the discarded cards to back to the deck.
    //good to add the discarded cards to the end of the deck.
    @Override
    public void returnToDeck(LinkedList<Cardable> discarded) {
        if(list.size()+discarded.size() <= Deckable.NUM_CARDS){
            list.addAll(discarded);
        }
    }

    //get the first card from the top of the deck and return the card to the method calling it
    //changes the faceUp state depending on what is given in the parameter.
    //since the card is to be drawn from the deck it needs to be removed from the deck
    @Override
    public Cardable drawACard(boolean faceUp) {
        Cardable c = list.getFirst();
        c.setFaceUp(faceUp);
        list.removeFirst();
        return  c;
    }


    //testing purposes
    public void print(){
        System.out.println(list);
    }


}
