import java.util.ArrayList;

public class PokerGame {
    public static void main(String[] args) {
        Mechanics mechanics = new Mechanics(new Player[2], 0, 0, 10, 20, 0, 0);
        Deck deck = new Deck(new ArrayList<>());
        deck.shuffleDeck();
        Player player1 = new Player(new ArrayList<Card>(), 100, "Player 1");
        Player player2 = new Player(new ArrayList<Card>(), 100, "Player 2");
        Player[] players = {player1, player2};
        deck.dealCards(players);
        System.out.println(player1.handCheck());
        System.out.println(player2.handCheck());
        HandEvaluation handEval = new HandEvaluation();
        deck.communityCards.add(deck.cards.remove(0));
        deck.communityCards.add(deck.cards.remove(0));
        deck.communityCards.add(deck.cards.remove(0));
        deck.communityCards.add(deck.cards.remove(0));
        deck.communityCards.add(deck.cards.remove(0));
        for(Card card : deck.communityCards) {
            System.out.println(card.getName() + " of " + card.getSuit());
        }
        System.out.println(player1.evalPlayerHand(deck.communityCards, players));
        System.out.println(player2.evalPlayerHand(deck.communityCards, players));
    }
}