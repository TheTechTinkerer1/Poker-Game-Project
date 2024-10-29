import java.util.ArrayList;

public class PokerGame {
    public static void main(String[] args) {
        Deck deck = new Deck();
        for(int i = 0; i < 52; i++) {
            System.out.println(deck.cards.get(i).name + " of " + deck.cards.get(i).suit);
        }
        deck.shuffleDeck();
        for(int i = 0; i < 52; i++) {
            System.out.println(deck.cards.get(i).name + " of " + deck.cards.get(i).suit);
        }

        Player player1 = new Player(new ArrayList<>(), 1000, "Player 1");
        Player player2 = new Player(new ArrayList<>(), 1000, "Player 2");

        Mechanics mechanics = new Mechanics(new Player[2], 0, 0, 10, 20, 0, 0);
        mechanics.players[0] = player1;
        mechanics.players[1] = player2;
        
        deck.dealCards(mechanics.players);

        System.out.println("1" + player1.handCheck());
        System.out.println("2" + player2.handCheck());
    }
}