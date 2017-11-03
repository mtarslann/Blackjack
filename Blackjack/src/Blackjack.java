import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
	private static Card[] playerDeck = new Card[2];
	private static Card[] dealerDeck = new Card[2];
	private static Card[] deck;

	static Scanner scan = new Scanner(System.in);

	static {//Put cards in the deck
		deck = new Card[52];

		//HEARTS
		deck[0] = new Card("A","Hearts",1);
		for(int i=1; i<10; i++){
			deck[i] = new Card(i+1+"", "Hearts",i+1);
		}
		deck[10] = new Card("J","Hearts",10);
		deck[11] = new Card("Q","Hearts",10);
		deck[12] = new Card("K","Hearts",10);
		//DIAMONDS
		deck[13] = new Card("A","Diamonds",1);
		for(int i=1; i<10; i++){
			deck[i+13] = new Card(i+1+"", "Diamonds",i+1);
		}
		deck[23] = new Card("J","Diamonds",10);
		deck[24] = new Card("Q","Diamonds",10);
		deck[25] = new Card("K","Diamonds",10);
		//CLUBS
		deck[26] = new Card("A","Clubs",1);
		for(int i=1; i<10; i++){
			deck[i+26] = new Card(i+1+"", "Clubs",i+1);
		}
		deck[36] = new Card("J","Clubs",10);
		deck[37] = new Card("Q","Clubs",10);
		deck[38] = new Card("K","Clubs",10);
		//SPADES
		deck[39] = new Card("A","Spades",1);
		for(int i=1; i<10; i++){
			deck[i+39] = new Card(i+1+"", "Spades",i+1);
		}
		deck[49] = new Card("J","Spades",10);
		deck[50] = new Card("Q","Spades",10);
		deck[51] = new Card("K","Spades",10);
	}

	public static Card[] getDeck(){
		return deck;
	}

	public static void start(){
		System.out.println();
		//Put deck in a LinkedList to use remove method to avoid picking same card again
		LinkedList<Card> deckList = new LinkedList<Card>(Arrays.asList(deck));
		//First 2 Card of Player
		int rndm = randomCardNumber(deckList); //1
		playerDeck[0] = ((Card) deckList.get(rndm));
		deckList.remove(rndm);
		rndm = randomCardNumber(deckList); //2
		playerDeck[1] = ((Card) deckList.get(rndm));
		deckList.remove(rndm);
		//First 2 Card of Dealer
		rndm = randomCardNumber(deckList); //1
		dealerDeck[0] = ((Card) deckList.get(rndm));
		deckList.remove(rndm);
		rndm = randomCardNumber(deckList); //2
		dealerDeck[1] = ((Card) deckList.get(rndm));
		deckList.remove(rndm);

		LinkedList<Card> playerDeckList = new LinkedList<Card>(Arrays.asList(playerDeck));
		System.out.println("You get " + ((Card) playerDeckList.get(0)).toString() + " and " + ((Card)playerDeckList.get(1)).toString() + "."
				+ "\nYour total is "+ valueManager(playerDeckList,2) + ".");

		LinkedList<Card> dealerDeckList = new LinkedList<Card>(Arrays.asList(dealerDeck));
		System.out.println("\nThe dealer has " + ((Card) dealerDeckList.get(0)).toString() + " showing, and a hidden card."
				+ "\nHis total is hidden,too");
		System.out.println();
		game(deckList,playerDeckList,dealerDeckList);
	}

	public static int valueManager(LinkedList<Card> list, int cardnum){
		int Anum = 0,value = 0;
		for(int i = 0;i<cardnum;i++){
			if(((Card) list.get(i)).getName().equalsIgnoreCase("A")){
				Anum++;	// A's value may be 1 or 11 depends on the situation, So count number of A
			}
			value = value + ((Card) list.get(i)).getValue();
		}
		boolean over21 = false;
		int m = 0;
		while(m<Anum && !over21){
			if(value+10 <= 21)
				value = value + 10;
			m++;
		}
		return value;
	}

	public static int randomCardNumber(LinkedList<Card> list){
		Random rand = new Random();
		return rand.nextInt(list.size());
	}

	public static void game(LinkedList<Card> deckList, LinkedList<Card> playerDeckList, LinkedList<Card> dealerDeckList){
		int rndm, pcardnum = 2, dcardnum = 2;
		int dtotal = valueManager(dealerDeckList,2);
		int ptotal = valueManager(playerDeckList,2);
		System.out.print("Would you like to \"hit\" or \"stay\"? ");
		String choice = scan.nextLine();
		while(!choice.equalsIgnoreCase("hit") && !choice.equalsIgnoreCase("stay")){
			System.out.println("There is no option such as " + choice);
			System.out.print("Would you like to \"hit\" or \"stay\"? ");
			choice = scan.nextLine();
		}

		while(choice.equalsIgnoreCase("hit")){
			rndm = randomCardNumber(deckList);
			playerDeckList.add(deckList.get(rndm));
			deckList.remove(rndm);
			pcardnum++;
			ptotal = valueManager(playerDeckList,pcardnum);
			System.out.println("You drew " + ((Card) playerDeckList.get(playerDeckList.size()-1)).toString()
					+ "\nYour total is " + ptotal);
			if(ptotal > 21){
				System.out.println("\nYou bust.");
				Driver.gameOver("Dealer");
			}
			System.out.print("\nWould you like to \"hit\" or \"stay\"? ");
			choice = scan.nextLine();
			System.out.println();
		}

		System.out.println("\nOkay, dealer's turn.");
		System.out.println("His hidden car was " + ((Card) dealerDeckList.get(1)).toString() + "."
				+ "\nHis total was " + dtotal + "\n");

		while(dtotal <= 17 && dtotal < ptotal){
			System.out.println("\nDealer chooses to hit.");
			rndm = randomCardNumber(deckList);
			dealerDeckList.add(deckList.get(rndm));
			dcardnum++;
			dtotal = valueManager(dealerDeckList,dcardnum);
			System.out.println("He draws " + ((Card) dealerDeckList.get(dealerDeckList.size()-1)).toString() + "."
					+ "\nHis total is " + dtotal);
			if(dtotal > 21){
				System.out.println("\nDealer busts.");
				Driver.gameOver("Player");
			}
		}

		System.out.println("\nDealer stays.");
		
		System.out.println("\nDealer total is " + dtotal
							+ ".\nYour total is " + ptotal + ".");
		System.out.println();
		if(ptotal > dtotal) Driver.gameOver("Player");
		else if(dtotal > ptotal) Driver.gameOver("Dealer");
		else Driver.gameOver("Draw");
	}
}
