
import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand;
    public int money;
    public String name;


    public Player(ArrayList<Card> cards,int money, String name) {
        this.hand = cards;
        this.money = money;
        this.name = name;
    }

    public String handCheck() {
        String cardsInHand = "";
        for(Card card : hand) {
            cardsInHand += card.getName() + " of " + card.getSuit() + " ";
        }
        return cardsInHand;
    }

    public float evalHands(ArrayList<Card> communityCards, Player[] players) {
        for(Player player : players) {
            for(Card card : communityCards) {
                player.hand.add(card);
            }
        }

        
        
        return 0.2f;
    }
}
