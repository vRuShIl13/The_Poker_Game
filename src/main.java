//just for testing stuff right now

import java.util.LinkedList;
import java.util.List;

public class main {


    public static void main(String[] args)
    {
        Deck d = new Deck();
        d.createDeck();

        Cardable c = d.drawACard(true);


        LinkedList<Cardable> l = new LinkedList<>();
        l.add(c);
        c = d.drawACard(true);
        l.add(c);
        c = d.drawACard(true);
        l.add(c);
        c = d.drawACard(true);
        l.add(c);
        c = d.drawACard(true);
        l.add(c);
        System.out.println(l);
        d.print();
        d.returnToDeck(l);
        d.print();
        System.out.println(c);


        Hand h = new Hand();
        Cardable [] cards = {new Card(11, Cardable.Suit.CLUB),
                new Card(11, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.CLUB),
                new Card(11, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB)};

        cards[1].switchSelectedState();
        cards[4].switchSelectedState();
        h.addCards(cards);
        System.out.println(h.evaluateHand());

        System.out.println(h);

        //this will not work because the deck is already full of cards , no space
         d.returnToDeck(h.discard());
         d.print();

        System.out.println(h);

        h.draw(d,true);

        System.out.println(h);

        d.print();



    }
}
