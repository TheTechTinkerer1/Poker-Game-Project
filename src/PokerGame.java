import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Mechanics mc = new Mechanics();
        System.out.println("Welcome to Poker!");
        System.out.println("How many players are playing?");
        int PlayerCount = scanner.nextInt();
        
        for(int i = 0; i < PlayerCount; i++){
            System.out.println("Enter player " + (i + 1) + "'s name: ");
            String name = scanner.next();
            mc.players.add(new Player(new ArrayList<Card>(), 1000, name));
        }
        System.out.println("Specify Small Blind and Big Blind");
        System.out.println("Example small blind: 5, big blind: 10");
        System.out.println("Small Blind: ");
        int smallBlind = scanner.nextInt();
        mc.smallBlind = smallBlind;
        System.out.println("Big Blind: ");
        int bigBlind = scanner.nextInt();
        mc.bigBlind = bigBlind;
        mc.dealer = mc.players.get(0);


        System.out.println("Lets play Poker!");
            
        while(Mechanics.isGameOver == false){ 
            mc.round++;
            System.out.println("Round " + mc.round + " has started");
            mc.assignBlinds();
            Deck deck = new Deck();
            deck.shuffleDeck();
            deck.dealCards(mc.players);
            System.out.println(mc.playerTurn);
            mc.promptPlayerTurn();
            deck.drawFlop();
            mc.promptPlayerTurn();
            deck.drawTurn();
            mc.promptPlayerTurn();
            deck.drawRiver();
            mc.promptPlayerTurn();
            mc.determineWinner(deck.communityCards, mc.players);
            
        }

        
    }
}