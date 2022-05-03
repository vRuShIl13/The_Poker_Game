
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitTests {
	//Testing the compareTo method.
    @Test
	@DisplayName("Confirming the player with the highest rank  ")
    public void testLevel()
	{
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    }

	//Testing the compareTo method when there is a tie in the rank
	@Test
	@DisplayName("player with highest value wins when there is a tie in  rank")
	public void testTie4Kind()
	{
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);
		Cardable[] cards2 = {new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(3, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "4" + "" + "" + " of a Kind tie but Highest Card wins.");
	}
	//Testing the compareTo method when there is a tie in the rank
	@Test
	@DisplayName("2 pairs, the higher tie breaker wins")
	public void testTieBreak2Pair()
	{
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(3, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "EC,the higher value tie breaker wins.");
	}

	//Testing the compareTo method when there is a tie in the rank
	@Test
	@DisplayName("2 straight flushes, hand with higher card wins")
	public void testSFtie()
	{
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(12, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "Straight flush tie broken by the highest card.");
	}

	//Testing the compareTo method when there is a tie in the rank
	@Test
	@DisplayName("2 3 of kind,hand with higher card wins")
	public void test3Kindtie()
	{
		Cardable[] cards1 = {new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "3 kind tie broken by the highest card.");
	}



	//=========================================================================================================================================
	//checking  if the ith card is got.
	@Test
	@DisplayName("Getting the ith card from the hand")
	public void getCard(){
		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		assertTrue(th2.getCard(0).getSuit() == Cardable.Suit.HEART
				&& ((Card)th2.getCard(0)).getVal()== 3);
	}

//=========================================================================================================================================
	//tests for the evaluate hand method.
	//this test method is for the EDGE CASE where the A = 1 forms the low sequence 1,2,3,4,5.
	@Test
	@DisplayName("checking for the edge case where A=1")
	public void testEvaluateMethod(){
		Cardable[] cards2 = {new Card(1, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		String s = th2.evaluateHand();
		assertEquals("Straight 5 High", s);

	}

//=========================================================================================================================================

	//tests for the evaluate hand method to give straight
	@Test
	@DisplayName("checking for the straight rule")
	public void testGetStraight(){
		Cardable[] cards2 = {new Card(6, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		String s = th2.evaluateHand();
		assertEquals("Straight 6 High", s);
	}
	//tests for the evaluate hand method to give flush
	@Test
	@DisplayName("checking for the flush rule")
	public void testGetFlush(){
		Cardable[] cards2 = {new Card(6, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		String s = th2.evaluateHand();
		assertEquals("Flush J High", s);
	}
	//tests for the evaluate hand method to give straight flush
	@Test
	@DisplayName("checking for the straight flush rule")
	public void testGetStraightFlush(){
		Cardable[] cards2 = {new Card(10, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		String s = th2.evaluateHand();
		assertEquals("Straight Flush J High", s);
	}
//=========================================================================================================================================

	//tests for the evaluate hand method to give 4 of kind
	@Test
	@DisplayName("checking for the 4 of a kind")
	public void testGet4Kind(){
		Cardable[] cards2 = {new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		String s = th2.evaluateHand();
		assertEquals("Four of a kind 10", s);

	}
	//tests for the evaluate hand method to give 3 of kind
	@Test
	@DisplayName("checking for the 3 of a kind")
	public void testGet3Kind(){
		Cardable[] cards2 = {new Card(1, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		String s = th2.evaluateHand();
		assertEquals("Three of a kind 10", s);

	}
	//tests for the evaluate hand method to give full house
	@Test
	@DisplayName("checking for the full house")
	public void testGetFullhouse(){
		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		String s = th2.evaluateHand();
		assertEquals("Full House P 2 T 10", s);
	}

	//tests for the evaluate hand method to give 2 pairs
	@Test
	@DisplayName("checking for the 2 pairs")
	public void testGet2pairs(){
		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		String s = th2.evaluateHand();
		assertEquals("Two pairs 2 10", s);
	}

	//tests for the evaluate hand method to give pair
	@Test
	@DisplayName("checking for the pair")
	public void testGetpair(){
		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.CLUB)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		String s = th2.evaluateHand();
		assertEquals("One Pair 2", s);
	}

//=========================================================================================================================================







}
