import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> cards;
    public ArrayList<Card> communityCards;
    public ArrayList<Card> discardPile;
    private static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};


    public Deck() {
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suit, j + 2));
            }
        }
        this.communityCards = new ArrayList<>(); 
        this.discardPile = new ArrayList<>();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
        System.out.println("Deck has been shuffled");
    }

    public void dealCards(ArrayList<Player> players) {
        for (Player player : players) {
            player.hand.add(cards.remove(0)); 
            player.hand.add(cards.remove(0));
        }
        System.out.println("Cards have been dealt");
    }

    public void resetDiscardPile() {
        for(Card card : discardPile) {
            cards.add(card);
            discardPile.remove(card);
        }
    }

    public void resetCommunityCards() {
        for(Card card : communityCards) {
            cards.add(card);
            communityCards.remove(card);
        }
    }

    public void drawFlop() {
        this.discardPile.add(cards.remove(0));
        System.out.println("Burn card has been drawn and added to the discard pile.");
        this.communityCards.add(cards.remove(0));
        this.communityCards.add(cards.remove(0));
        this.communityCards.add(cards.remove(0));
        System.out.println("Flop has been drawn");  
        System.out.println("The flop is: " + communityCards.get(0).getName() + " of " + communityCards.get(0).getSuit() + ", " + communityCards.get(1).getName() + " of " + communityCards.get(1).getSuit() + ", " + communityCards.get(2).getName() + " of " + communityCards.get(2).getSuit());
        System.out.println(this.communityCards.size());
    }

    public void drawTurn() {
        this.discardPile.add(cards.remove(0));
        System.out.println("Burn card has been drawn and added to the discard pile.");
        this.communityCards.add(cards.remove(0));
        System.out.println(communityCards.size());
        System.out.println("Turn has been drawn");
        System.out.println("The turn is: " + communityCards.get(3).getName() + " of " + communityCards.get(3).getSuit());
    }

    public void drawRiver() {
        this.discardPile.add(cards.remove(0));
        System.out.println("Burn card has been drawn and added to the discard pile.");
        this.communityCards.add(cards.remove(0));
        System.out.println("River has been drawn");
        System.out.println("The river is: " + communityCards.get(4).getName() + " of " + communityCards.get(4).getSuit());
    }
}
