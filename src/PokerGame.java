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
    }
}