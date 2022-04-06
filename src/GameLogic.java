//This class implements Gamelogicable interface
//This keeps track of everything going on in the game.
//players, deck, hand of both players,game number


public class GameLogic implements GameLogicable{

    private final Deck d;
    private Hand humanHand;
    private Hand cpuHand;
    private static int gameCount;

    private static int currentState;
    private  static int humanWins;
    private static int cpuWins;

    public GameLogic(){
        //creating the deck
        d = new Deck();
        d.createDeck();
        d.shuffle();
        d.print();
        //where the program starts the gameCount and the current state always start at 1
        currentState = 1;
        gameCount = 1;

        humanHand = new Hand();
        humanHand.createHand(d);
        humanHand.showAllCards();

        //cpuHand = new DumbAI();
        cpuHand = new WhizzAI();
        cpuHand.createHand(d);
    }


    @Override
    public Handable getCPUHand() {
        //here we will have the cpu when it is dump and when it is smart
        //2 different instances of 2 different classes.
        return cpuHand;
    }

    @Override
    public Handable getHumanHand() {
        return humanHand;
    }

    //this controls the states, which state should be active
    //how to add the cards back to the deck and draw.
    //the messages to be displayed at every stage
    //keeping track of the games played, human wins, cpu wins.
    @Override
    public boolean nextState(String[] messages) {
        String humanPlayer = "BlurredBloke";
        String cpuPlayer;
        String humanEvaluate;
        String cpuEvaluate;

        if(cpuHand instanceof DumbAI){
            cpuPlayer = "NumbSkull";
        }else{
            cpuPlayer = "Whizzz";
        }

        //check we are within the 6 states of the game
        if(currentState == 1){ // first stage, human will be asked to select the cards to be discarded.
            humanHand.showAllCards();
            humanHand.unselectCards();
            messages[0] = "Beginning of game "+ gameCount;
            messages[1] = humanPlayer + " choose which cards to discard and click on the proceed button.";
            currentState++;  //moving to the next stage
        } else if(currentState == 2){//2nd stage; cards selected by the human are now discarded and added back to the deck
                                    //the cpu is now thinking which cards to discard
            messages[0] = humanPlayer +" has discarded cards. ";
            messages[1] = cpuPlayer + " is thinking... ";
            d.returnToDeck(humanHand.discard());
            currentState++;
        }else if(currentState==3){//3. the selected cards by the cpu are discarded and added to the deck.
                                    //both the players will now be dealt with the number of discarded cards
            messages[0] = cpuPlayer +" has discarded cards. ";
            messages[1] =  "Each player will be dealt with the same number of cards they discarded. ";
            if(cpuHand instanceof DumbAI) {
                d.returnToDeck(((DumbAI) cpuHand).discard());
            }else{
                d.returnToDeck(cpuHand.discard());
            }

            currentState++;
        }else if(currentState==4){//4. both the cpu and the human receive the new cards from the deck
                                        //after this it is showtime!!
            messages[0] = "Each player has been dealt new cards. ";
            messages[1] =  "Click on proceed to see the winner. ";

            drawingCards(humanHand);
            humanHand.showAllCards();
            drawingCards(cpuHand);

            currentState++;
        }else if(currentState==5){//5. show down both hands are evaluated , then compared and the winner is announced.
            humanEvaluate = humanHand.evaluateHand();
            cpuEvaluate = cpuHand.evaluateHand();
            messages[0] = cpuPlayer+ " has: "+ cpuEvaluate ;
            messages[1] = humanPlayer+  " has: " + humanEvaluate;

            cpuHand.showAllCards();  //the cpu shows its cards for the comparison
            cpuHand.unselectCards();

            if(humanHand.compareTo(cpuHand)>0){
                humanWins++;
                messages[2] = humanPlayer + "  wins!";
            }else if (humanHand.compareTo(cpuHand)<0){
                messages[2] = cpuPlayer + "  wins!";
                cpuWins++;
            }else{
                messages[2] = "This is a draw!!";
            }
            messages[3] = humanPlayer + " has won "+ humanWins+ " games. "+ cpuPlayer + " has won "+ cpuWins + " games.";
            currentState++;
            gameCount++;

        }else if(currentState == 6){//all the cards from the human and the cpu hand are added back to the deck,
                                    //deck is shuffled. new hands are drawn but not shown. and can skip to stage 1 when proceed is clicked.
            messages[0]= "Click on proceed to play a new Game";
            d.returnToDeck( humanHand.returnCards());
            d.returnToDeck( cpuHand.returnCards());

            d.shuffle();

            humanHand.draw(d,false);
            cpuHand.draw(d,false);

            currentState = 1;
        }
        return true;
    }


    public void discardCards(Handable h){
         h.discard();
    }
    public void drawingCards(Handable h){
        h.draw(d,false);
    }


}
