import java.util.Scanner;

public class Driver {

	private static Scanner scan;

	public static void main(String[] args) {
		System.out.println("Welcome to BlackJack! by github/mtarslann");
		Blackjack.start();
	}
	
	public static void gameOver(String who){
		if(who.equalsIgnoreCase("Player"))
			System.out.println("YOU WÝN!");
		else if(who.equalsIgnoreCase("Dealer"))
			System.out.println("DEALER WÝNS!");
		else
			System.out.println("THERE IS A DRAW!");
		System.out.println();
		scan = new Scanner(System.in);
		System.out.print("Do you want to play one more game? \"yes\" or \"no\"? ");
		String choice = scan.nextLine();
		while(!choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("no")){
			System.out.println("There is no option such as " + choice);
			System.out.print("Do you want to play one more game? \"yes\" or \"no\"? ");
			choice = scan.nextLine();
		}
		if(choice.equalsIgnoreCase("yes")) Blackjack.start();
		else{
			System.out.println("Good Bye!");
			System.exit(0);
		}
	}
}
