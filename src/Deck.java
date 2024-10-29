import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> cards;
    public ArrayList<Card> communityCards;
    private static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};


    public Deck(ArrayList<Card> comCards) {
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suit, j + 2));
            }
        }
        this.communityCards = comCards;
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public void dealCards(Player[] players) {
        for (Player player : players) {
            player.hand.add(cards.remove(0)); 
            player.hand.add(cards.remove(0));
        }
    }
}
