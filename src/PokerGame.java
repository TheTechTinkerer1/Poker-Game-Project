import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Mechanics mc = new Mechanics();
        System.out.println("Welcome to Poker!");
        
        boolean isValid = false;
        System.out.println("How many players are playing?");
        int PlayerCount = scanner.nextInt();
        while(!isValid){
            if(PlayerCount < 3 || PlayerCount > 8){
                System.out.println("Invalid number of players. Please enter a number between 3 and 8");
                PlayerCount = scanner.nextInt();
            }
            else{
                isValid = true;
            }
        }
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
            mc.resetRound(deck);
            mc.enforceBlinds();
            mc.promptPlayerTurn();
            deck.drawFlop();
            mc.resetRound(deck);
            mc.promptPlayerTurn();
            deck.drawTurn();
            mc.resetRound(deck);
            mc.promptPlayerTurn();
            deck.drawRiver();
            mc.resetRound(deck);
            mc.promptPlayerTurn();
            mc.determineWinner(deck.communityCards, mc.players);
            mc.isRoundOver = true;
            mc.resetRound(deck);
            
        }

        
    }
}