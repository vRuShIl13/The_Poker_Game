/*
This class implements the Cardable interface.
This holds an individual card. with a value and a suit.
 */

public class Card implements Cardable{
    //value of the card,suit of the card, is the card facing up, is the card selected?
    private int val;
    private Suit cardSuit;
    private boolean selected;
    private boolean faceUp;

    //constructor
    public Card(int value,Suit s){
        val = value;
        cardSuit = s;
        faceUp = false;
        selected = false;
    }
    public Card(){

    }

    //returns the selected state of the card
    @Override
    public boolean getSelected() {
        return selected;
    }

    //returns the boolean value of the state face up
    @Override
    public boolean getFaceUp() {
        return faceUp;
    }

    //returns the suit type of the card
    @Override
    public Suit getSuit() {
        return cardSuit;
    }

    //returns the value of the card
    public int getVal() {
        return val;
    }

    //changes the selected state to the other state
    //true -> false  or false -> true
    @Override
    public void switchSelectedState() {
        selected = !selected;
    }


    //Unselects the card
    @Override
    public void resetSelected() {
        selected = false;
    }

    //sets the faceUp according to what is given in the parameter.
    @Override
    public void setFaceUp(boolean faceUp) {
            this.faceUp = faceUp;
    }

    //These methods returns the value and the suit of the card
    //the information is used in the drawing of the card.
    public String toString() {
        String suit = "";
        String value = "";
        if (this.cardSuit == Suit.HEART) {
            suit = "\u2665";
        } else if (this.cardSuit == Suit.DIAMOND) {
            suit = "\u2666";
        } else if (this.cardSuit == Suit.SPADE) {
            suit = "\u2660";
        } else {
            suit = "\u2663";
        }
        if (val > 10) {
            value = getChar(val);
        }else {
            value = String.valueOf(val);
        }
        return value+suit;
    }


    public static String getChar(int i){
        String value = "";
        if (i == 11) {
            value = "J";
        }else if(i == 12){
            value = "Q";
        }else if(i == 13){
            value = "K";
        }else if(i == 14){
            value = "A";
        }
        return value;
    }
}
