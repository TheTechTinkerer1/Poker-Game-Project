import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> cards;
    private static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    public Deck() {
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suit, j + 2));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
}
