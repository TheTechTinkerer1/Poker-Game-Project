public class Mechanics {
    public int pot;
    public int currentBet;
    public int smallBlind;
    public int bigBlind;
    public int playerTurn;
    public Player[] players;
    public int round;
    public String dealer;

    public Mechanics(Player[] players, int pot, int currentBet, int smallBlind, int bigBlind, int playerTurn, int round) {
        this.players = players;
        this.pot = pot;
        this.currentBet = currentBet;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.playerTurn = playerTurn;
        this.round = round;
    }

    
}
