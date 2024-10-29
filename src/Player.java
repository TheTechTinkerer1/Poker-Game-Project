
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

    public float evalPlayerHand(ArrayList<Card> communityCards, Player[] players) {
        HandEvaluation handEval = new HandEvaluation();
        return handEval.evalHands(communityCards, players, this.hand, this);
        
    }

    public raise
}
