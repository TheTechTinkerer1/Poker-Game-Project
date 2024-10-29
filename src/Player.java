
import java.util.ArrayList;
import java.util.Comparator;

public class Player {
    public ArrayList<Card> hand;
    public int money;
    public int playerCurrentBet;
    public String name;
    public int yourMaxRaises;
    public boolean isSmallBlind;
    public boolean isBigBlind;
    public boolean isTurn;
    public boolean isFolded;
    public boolean isAllIn;
    public boolean isWinner;
    public boolean isChecked;

    

    public Player(ArrayList<Card> cards,int money, String name) {
        this.hand = cards;
        this.money = money;
        this.name = name;
        this.yourMaxRaises = 3;
        this.playerCurrentBet = 0;
        this.isSmallBlind = false;
        this.isBigBlind = false;
        this.isTurn = false;
        this.isFolded = false;
        this.isAllIn = false;
        this.isWinner = false;
        this.isChecked = false;
    
    }

    public String handCheck() {
        String cardsInHand = "";
        for(Card card : hand) {
            cardsInHand += card.getName() + " of " + card.getSuit() + " ";
        }
        return cardsInHand;
    }

    public int evalPlayerHand(ArrayList<Card> communityCards, ArrayList<Player> players) {
        HandEvaluation handEval = new HandEvaluation();
        return handEval.evalHands(communityCards, players, this.hand, this);
        
    }

    public String getName() {
        return name;
    }

    public void sortHandByRank() {
        hand.sort(Comparator.comparing(Card::getRank));
    }

    
}
