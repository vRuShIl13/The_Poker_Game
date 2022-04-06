
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Hand implements TestableHand{
    //holds the 5 cardable cards
    private LinkedList<Cardable> list;


    //constructor, initialize the list of 5 cards held by the player
    public Hand(){
        list = new LinkedList<Cardable>();
    }

    //give 5 cards from the deck to the player
    public void createHand(Deck deck){
        for(int i = 0; i< HAND_SIZE; i++){
            list.add(deck.drawACard(false));
        }
    }

    //get the card at position i of the hand.
    @Override
    public Cardable getCard(int i) {
        return list.get(i);
    }

    //this method is called after the player discards from of the cards from his hand.
    //this method checks which positions in the hand list is null/ doesnt have a card
    //it then adds the card from the deck to the empty position.
    @Override
    public void draw(Deckable d, boolean faceUp) {
        for(int i = 0 ; i<HAND_SIZE; i++){
            if(list.get(i)==null) {
                list.set(i,d.drawACard(faceUp));
            }
        }
    }

    //sets the faceUp state of all the cards in the hand to true.
    //useful for showDown.
    @Override
    public void showAllCards() {

        for (int i = 0; i < HAND_SIZE; i++){
            list.get(i).setFaceUp(true);
        }
    }

    //this method is called to unselect all the cards in the hand
    //to avoid the previous selected positions from affecting the new game.
    public void unselectCards(){
        for (int i = 0; i < HAND_SIZE; i++){
            list.get(i).resetSelected();
        }
    }

    //this method represents the choice made by the player to get rid of some cards that are useless for winning the game.
    //the player selected this cards and the cards are then returned to the deck .
    @Override
    public LinkedList<Cardable> discard() {
        LinkedList<Cardable> discarded = new LinkedList<Cardable>();

        for (int i = 0; i < HAND_SIZE; i++){
             if(list.get(i).getSelected()){
                 discarded.add(list.get(i));
                 list.set(i,null);
            }
        }

        return discarded;
    }

    //this method is called at then end of the round. All the cards are returned to the deck irrespective of the selected state.
    //take all cards from the hand. Marks the end of the round.
    @Override
    public LinkedList<Cardable> returnCards() {
        LinkedList<Cardable> discarded = new LinkedList<Cardable>();
        for (int i = 0; i < HAND_SIZE; i++){
            discarded.add(list.get(i));
            list.set(i,null);
        }
        return discarded;
    }

//====================================================================================================================
    //this method evaluates the hand in the sense that i returns the best poker hand that could be made by the cards
    @Override
    public String evaluateHand() {
        Cardable[] myarray = new Cardable[5];
        String description = "";
        int [] bestHand;
        String ch ;
        String h ;
        //the method returns any type of pair in the hand of the player.
        //this can be 4 of a kind, 3 of a kind, 2 pairs , 1 pair or nothing

        bestHand = getAnyPair(list.toArray(myarray));
        //the besthand contains the type of pair and the value that makes the pair
        //example: [3,6,1,2] => this is a full house : 3 of a kind (6) a pair (2)

        //if the array returned is null that means there is either a straight flush,straight or a flush.
        if(bestHand[0]== 0){

            String flush = findFlush();
            String straight = findStraight();

            if(flush != null && straight != null){
                ch = straight.split(" ")[1];
                description = "Straight Flush " + ch + " High";
            }else if(flush != null){
                description = flush;
            }else if(straight != null){
                description = straight;
            }else{
                 ch = getSymbol(getHighest());
                 description = "Nothing " + ch + " High";

            }

        }else{
            //breakdown of the pair types.
            if(bestHand[0]==4){
                ch = getSymbol(bestHand[1]);
                description = "Four of a kind " + ch;

            }else if(bestHand[0] == 3){
                if(bestHand[2]==1&& bestHand[1]!=bestHand[3]){
                    ch = getSymbol(bestHand[1]);
                    h = getSymbol(bestHand[3]);
                    description = "Full House "+"T "+ ch + " P " + h;
                }else {
                    ch = getSymbol(bestHand[1]);
                    description = "Three of a kind " + ch;
                }
            }else if(bestHand[0] == 1){
                if(bestHand[2]==3 && bestHand[1]!=bestHand[3]){
                    ch = getSymbol(bestHand[1]);
                    h = getSymbol(bestHand[3]);
                    description = "Full House "+"P "+ ch + " T " + h;
                }else if (bestHand[2]==1) {
                    ch = getSymbol(bestHand[1]);
                    h = getSymbol(bestHand[3]);
                    description = "Two pairs " + ch +" "+ h ; //confirm the win if there is a tie.
                }else {
                    ch = getSymbol(bestHand[1]);
                    description = "One Pair " + ch;
                }
            }
        }
        return description;
    }

//====================================================================================================================
    public String getSymbol(int i){
        String h ;
        if(i>10){
            h = Card.getChar(i);
        }else{
            h = String.valueOf(i);
        }
        return h;
    }

//====================================================================================================================
    //used to find the highest value card among the 5 cards.
    //casting the cardable to card ,get its value and compare with the next
    public int getHighest(){
        Card e = new Card();

        int highCard = 0;
        for(int i =0; i< HAND_SIZE; i++) {
            if(list.get(i) instanceof Card) {
                e = (Card) list.get(i);
            }
            if (highCard < e.getVal()) {
                highCard = e.getVal();
            }
        }
           return  highCard;
    }

//====================================================================================================================
    //this method checks if the cards are all of the same suit.
    public String findFlush(){
        String s ;

        String ch;

        for(int i =0; i< HAND_SIZE-1; i++) {
            //if the suit is not the same, flush does not exist
            if (list.get(i).getSuit() != list.get(i + 1).getSuit()) {
                return null;
            }
        }
        ch = getSymbol(getHighest());
        s = "Flush " + ch + " High";

        return s;
    }


//====================================================================================================================
    //This method sorts the cards from the smallest to the largest and checks if the cards are in a sequence.
    public String  findStraight(){
        int[] bestHand = new int[5];
        int []cl ;
        //for downcasting the cardable to card
        Card e = new Card();
        String s ;
        String ch;

        //get the value of each card in the hand.
        for(int i =0; i< HAND_SIZE; i++){
            if(list.get(i) instanceof Card) {
                e = (Card) list.get(i);
            }
            bestHand[i] = e.getVal();
        }
        //sorting from the smallest value to the largest value in the array.
        Arrays.sort(bestHand);
        if(bestHand[bestHand.length-1]==14){

            boolean lowSeq = true;
            cl = bestHand.clone();

            //changing the ace to a lower value.
            cl[cl.length-1]= 1;
            Arrays.sort(cl);

            for(int i = 0;i<cl.length-1; i++){
                if(cl[i]+1!=cl[i+1]){
                    lowSeq = false;
                    break;
                }
            }
            if(lowSeq) {
                ch =String.valueOf(5);
                s =  "Straight " + ch + " High";
                return s;
            }
        }
        //check if the cards are in an array
        for(int i = 0;i<bestHand.length-1; i++){
            if(bestHand[i]+1!=bestHand[i+1]){
                return null;
            }
        }

        ch = getSymbol(bestHand[bestHand.length - 1]);
        s =  "Straight " + ch + " High";

        return s;
    }

//====================================================================================================================
    //this method will look for all the kind of pairs.
    //this includes the 4 of a kind, 3 of a kind, 2 pairs and 1 pair
    //edge case there can be a 3 of akind and a pair.
    //the 2 pairs can also be a four of a kind
    public int [] getAnyPair(Cardable[] arr) {
        int possiblePairs = 3;
        int count;
        Card e = new Card();
        Card next = new Card();
        int filled=0;

        //this is a special array that will store the pair type in the even spot and the rank in the odd spot
        //for example 3 of a kind and 1 pair  { 3 , A , 1 ,6 } there are 3 A's and 1 pair of 6

        int[] intArr = new int[possiblePairs*2];
        for(int i = 0; i< arr.length;i++){
            if(list.get(i) instanceof Card) {
                e = (Card) list.get(i);
            }

            count = 0;
            for(int j = i+1 ; j<arr.length;j++){
                //the next card to be compared.
                if(list.get(j) instanceof Card) {
                    next = (Card) list.get(j);
                }
                //check if the 2 cards have the same rank
                if(e.getVal() == next.getVal()){
                    count++;
                }
            }
            if(filled<=possiblePairs ) {
                if (count == 3) { // four of a kind
                    intArr[filled] = 4;
                    filled++;
                    intArr[filled] = (e.getVal());
                    return intArr;
                } else if (count == 2) { //three of a kind //we do not return because there might be a pair.
                    intArr[filled] = 3;
                    filled++;
                    intArr[filled] = (e.getVal());
                    filled++;
                } else if (count == 1) {
                    intArr[filled] = 1;
                    filled++;
                    intArr[filled] = (e.getVal());
                    filled++;
                }
            }
        }
        return intArr;
    }

//====================================================================================================================
    //this method is used to compare the hand of the humar player vs the cpu player
    //this decides who has the stronger hand and is eventually decalared the winner of the round.

    @Override
    public int compareTo(Handable o) {
        int [] human;
        int [] cpu = new int[0];

        //human and cpu store the strength of the hand i e [9,5] -> 9 = Straight Flush 5 is the highest casd.
        //the strength reduces from 9(straight flush) all the way to 1 (nothing)
        //CHECK the public LEVEL enum below for more understanding.

        human = getLevel(this );
        if(o instanceof Hand) {
            cpu = getLevel((Hand) o);
        }

        //1.fIRST COMPARE the players level of cards. one might have a stronger, lower or equal hand.
        if(human[0]>cpu[0]){
            return 1;
        }else if(human[0]<cpu[0]){
            return -1;
        }else{

            int tieBreakerHuman;
            int tieBrkCpu;
            //2. Compare the highest card
                //scenario where both players have the same level of the cards. ie both have Four of a Kind
                //break them down to fullHouse,Two Pairs and the rest
           if(human[3]==0) { // this means we are checking for all the levels except full house and 2 pairs
               //comparing the highest card.
               if(human[1] > cpu[1]) {
                   return 1;
               }else if(human[1] < cpu[1]) {
                   return -1;
               }else{
                   if(human[2] > cpu[2]){
                       return 1;
                   }else if(human[2] < cpu[2]) {
                       return -1;
                   }else{
                       //get the tieBreakers.
                       tieBreakerHuman = getTieBreaker(this);
                       tieBrkCpu = getTieBreaker(((Hand) o));
                       return Integer.compare(tieBreakerHuman, tieBrkCpu);
                   }
               }
           }else{ //compare the full house, first compare the 3 of a kind , if there is a tie , compare the pair.
               if(human[1] > cpu[1]) {
                   return 1;
               }else if (human[1] < cpu[1]) {
                   return -1;
               } else {
                   return Integer.compare(human[2], cpu[2]);
               }
           }
        }
    }


    @Override
    public void addCards(Cardable[] cards) {
        list.addAll(Arrays.asList(cards));
    }


    //Just of testing purposes.
    @Override
    public String toString() {
        return "Hand{" +
                "list=" + list +
                '}';
    }
//=================================================================================================================================
    public enum Level {
    StraightFlush(9),FourOfKind(8),FullHouse(7),Flush(6),Straight(5),ThreeOfKind(4),TwoPairs(3),OnePair(2),Nothing(1);

    private final int value;
    Level(int i) {
        value = i;
    }

    public int getValue(){
        return value;
    }
}


    //this method will be called by the compareTo method to decide who the winner is.
    //receives a hand, the method will evaluate the hand and return the level of the hand according to the levels
    //4 of a kind 10 high will be [9 10] 9 represents the highest level of a poker hand and the highest card in the hand.
    public int[] getLevel(Hand h){
        int maxSize = 5;
        int [] levels = new int[maxSize];
        String s;
        int highCard;
        int highCard2;
        s = h.evaluateHand();

        String [] splitIt =  s.split(" ");
        String strength = splitIt[0];

        if ("Straight".equals(strength)) {
            if (s.split(" ").length == 4) {
                levels[0] = Level.StraightFlush.getValue();
                highCard = getValue(splitIt[2]);
            } else {
                levels[0] = Level.Straight.getValue();
                highCard = getValue(splitIt[1]);
            }
            levels[1] = highCard;
        } else if ("Flush".equals(strength)) {
            levels[0] = Level.Flush.getValue();
            highCard = getValue(splitIt[1]);
            levels[1] = highCard;
        } else if ("Four".equals(strength)) {
            levels[0] = Level.FourOfKind.getValue();
            highCard = getValue(splitIt[4]);
            levels[1] = highCard;
        } else if ("Three".equals(strength)) {
            levels[0] = Level.ThreeOfKind.getValue();
            highCard = getValue(splitIt[4]);
            levels[1] = highCard;
        } else if ("Full".equals(strength)) {//EDGE CASE
            levels[0] = Level.FullHouse.getValue();  //the level number
            if (Objects.equals(splitIt[2], "T")) {
                levels[1] = Level.ThreeOfKind.getValue(); //int for 3 of a kind
                highCard = getValue(splitIt[3]);
                levels[2] = highCard;                    // 3 of a kind high value
                if (Objects.equals(splitIt[4], "P")) {
                    levels[3] = Level.OnePair.getValue(); //pair level int
                    highCard = getValue(splitIt[5]);
                    levels[4] = highCard;                  // pair high value
                }
            } else if (Objects.equals(splitIt[2], "P")) {
                levels[1] = Level.ThreeOfKind.getValue(); //int for 3 of a kind
                highCard = getValue(splitIt[5]);
                levels[2] = highCard;                    // 3 of a kind high value
                if (Objects.equals(splitIt[4], "T")) {
                    levels[3] = Level.OnePair.getValue(); //pair level int

                    highCard = getValue(splitIt[3]);
                    levels[4] = highCard;                  // pair high value
                }
            }
        } else if ("Two".equals(strength)) {
            levels[0] = Level.TwoPairs.getValue();
            highCard = getValue(splitIt[2]);
            highCard2 = getValue(splitIt[3]);
            if (highCard > highCard2) {
                levels[1] = highCard;
                levels[2] = highCard2;
            } else {
                levels[1] = highCard2;
                levels[2] = highCard;
            }
        } else if ("One".equals(strength)) {
            levels[0] = Level.OnePair.getValue();
            highCard = getValue(splitIt[2]);
            levels[1] = highCard;
        } else {
            levels[0] = Level.Nothing.getValue();
            highCard = getValue(splitIt[1]);
            levels[1] = highCard;
        }


        return levels;
    }

    //this method returns the value of the card.
    public int getValue(String s){
        switch (s) {
            case "A":
                return 14;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            default:
                return Integer.parseInt(s);
        }
    }

    //this method will look for the lone card and return its value
    // this method is used when there is a tie in 1 pair /2 pair
    public int getTieBreaker(Hand o){
        int indexVal=0;
        int c = 0;
        for(int i=0; i<HAND_SIZE;i++){
            for(int j=0; j<HAND_SIZE;j++){
                if(((Card)o.getCard(i)).getVal() ==((Card)o.getCard(j)).getVal() && i!=j){
                    c++;
                }
            }
            if(c == 0 && i!=0){
                if(indexVal< ((Card)o.getCard(i)).getVal()) {
                    indexVal = ((Card)o.getCard(i)).getVal();
                }
            }
            c = 0;
        }
        return indexVal;
    }
    //for the sub classes.
    public LinkedList<Cardable> getList() {
        return list;
    }
}
