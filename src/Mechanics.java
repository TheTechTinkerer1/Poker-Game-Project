import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Mechanics {
    public int pot;
    public int currentBet;
    public int smallBlind;
    public int bigBlind;
    public int playerTurn;
    public ArrayList<Player> players;
    public int round;
    public Player dealer;
    public int dealerIndex;
    public boolean isRoundOver;
    public boolean isBettingRoundOver;
    public String isCheckorCall;
    public static boolean isGameOver;


    public Mechanics(int smallBlind, int bigBlind) {
        this.dealerIndex = -1;
        this.players = new ArrayList<>(10);
        this.pot = 0;
        this.currentBet = 0;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.playerTurn = 0;
        this.round = 0;
        this.dealer = this.players.get(0);
        this.isRoundOver = false;
        this.isBettingRoundOver = false;
        this.isGameOver = false;
        this.isCheckorCall = "check";
    }

    public Mechanics() {
        this.dealerIndex = -1;
        this.players = new ArrayList<>();
        this.pot = 0;
        this.currentBet = 0;
        this.smallBlind = 0;
        this.bigBlind = 0;
        this.playerTurn = 0;
        this.round = 0;
        this.isRoundOver = false;
        this.isBettingRoundOver = false;
        Mechanics.isGameOver = false;
        this.isCheckorCall = "check";
    }
    

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public void startRound() {
        this.round++;
        System.out.println("Round " + this.round + " has started");
        this.rotateDealer();
        this.assignBlinds();
        Deck deck = new Deck();
        deck.shuffleDeck();
        deck.dealCards(this.players);

    }

    public void assignBlinds() {
        int smallBlindIndex = (dealerIndex + 1) % this.players.size();
        int bigBlindIndex = (dealerIndex + 2) % this.players.size();

        for(Player player : this.players){
            player.isSmallBlind = false;
            player.isBigBlind = false;
        }

        this.players.get(smallBlindIndex).isSmallBlind = true;
        this.players.get(bigBlindIndex).isBigBlind = true;

        for(Player player : this.players){
            if(player.isSmallBlind){
                player.money -= this.smallBlind;
                this.pot += this.smallBlind;
                System.out.println(player.name + " is the small blind and bets " + this.smallBlind);
            }
            else if(player.isBigBlind){
                player.money -= this.bigBlind;
                this.pot += this.bigBlind;
                System.out.println(player.name + " is the big blind and bets " + this.bigBlind);
            }
        }
    }

    public void rotateDealer() {
        this.dealerIndex = (this.dealerIndex + 1) % this.players.size();
        this.dealer = this.players.get(this.dealerIndex);
        System.out.println(this.players.get(this.dealerIndex).getName() + " is the dealer"); 
    
    }

    public boolean allPlayersCheckedorFolded() {
        for(Player player : this.players){
            if(player.isChecked == false && player.isFolded == false){
                return false;
            }
        }
        return true;
    }

    public boolean checkIfAllButOneFolded() {
        int playersFolded = 0;
        for(Player player : this.players){
            if(player.isFolded == true){
                playersFolded++;
            }
        }
        return playersFolded == this.players.size() - 1;
    }

    public void resetRound(Deck deck) {
        for(Player player : this.players){
            player.isFolded = false;
            player.isAllIn = false;
            player.isChecked = false;
            player.playerCurrentBet = 0;
            player.yourMaxRaises = 3;
            for(Card card : player.hand){
                deck.cards.add(card);
                player.hand.remove(card);
            }
        }
        for(Card card : deck.communityCards){
            deck.cards.add(card);
            deck.communityCards.remove(card);
        }
        for(Card card : deck.discardPile){
            deck.cards.add(card);
            deck.discardPile.remove(card);
        }
        this.isRoundOver = false;
        this.isBettingRoundOver = false;
        this.currentBet = 0;
        this.pot = 0;

    }

    public void determineWinner(ArrayList<Card> communityCards, ArrayList<Player> players) {
        Player winner = null;
        if(checkIfAllButOneFolded() == true)
        {
            for(Player player : players){
                if(player.isFolded == false){
                    winner = player;
                    winner.money += this.pot;
                    isRoundOver = true;
                }
            }
        }
        else {
            ArrayList<HandEvaluation> playersEligibleForComparison = new ArrayList<>();
            
            for(Player player : players){
                if(player.isFolded == false){
                    playersEligibleForComparison.add(new HandEvaluation(player, player.evalPlayerHand(communityCards, this.players)));
                }
            }
            Collections.sort(playersEligibleForComparison);
            HandEvaluation bestHand = playersEligibleForComparison.get(0); //hand with the highest rating
            playersEligibleForComparison.remove(0);
            ArrayList<Player> playersWithSameRating = new ArrayList<>();
            for(HandEvaluation he : playersEligibleForComparison){
                 if(he.handRating > bestHand.handRating){
                     bestHand = he;
                 }
                 else if(he.handRating == bestHand.handRating){
                     playersWithSameRating.add(he.player);
                 }   
            }
            if(playersWithSameRating.size() == 0){
                winner = bestHand.player;
            }
            else {
                Player[] playerHighCardRankings = new Player[playersWithSameRating.size()];
                ArrayList<Player> playersWithSameHighCardRanking = new ArrayList<>();
                for(Card card : communityCards){
                    for(Player player : playersWithSameRating){
                        player.hand.add(card);
                    }  
                }
                for(Player player : playersWithSameRating){
                    player.sortHandByRank();
                    Collections.sort(player.hand, Collections.reverseOrder());
                }
                Player bestPlayer = playersWithSameRating.get(0);
                for(Player player : playersWithSameRating){
                    if(player.hand.get(0).getRank() > bestPlayer.hand.get(0).getRank()){
                        bestPlayer = player;
                        playersWithSameHighCardRanking.clear();
                        playersWithSameHighCardRanking.add(player);
                    }
                    else if(player.hand.get(0).getRank() == playerHighCardRankings[0].hand.get(0).getRank()){
                        playersWithSameHighCardRanking.add(player);
                    }
                }

                if(playersWithSameHighCardRanking.size() == 1){
                    winner = bestPlayer;
                    winner.money += this.pot;
                    isRoundOver = true;
                }
                else {
                   int moneyPerWinner = this.pot / playersWithSameHighCardRanking.size();
                   for(Player player : playersWithSameHighCardRanking){
                       player.money += moneyPerWinner;
                   }
                   isRoundOver = true;

                }

            }
            
        }
    }

    public void promptPlayerTurn() {
        if(this.players.get(this.playerTurn).isSmallBlind == true) {
            this.players.get(this.playerTurn).money -= this.smallBlind;
            this.pot += this.smallBlind;
            System.out.println(this.players.get(this.playerTurn).name + " is the small blind and bets " + this.smallBlind);
            this.playerTurn++;
        }
        else if(this.players.get(this.playerTurn).isBigBlind == true) {
            this.players.get(this.playerTurn).money -= this.bigBlind;
            this.pot += this.bigBlind;
            System.out.println(this.players.get(this.playerTurn).name + " is the big blind and bets " + this.bigBlind);
            this.currentBet = this.bigBlind;
            this.playerTurn++;
        }
       
        while(!this.isBettingRoundOver) {
            if(allPlayersCheckedorFolded() || checkIfAllButOneFolded()) {
                this.isBettingRoundOver = true;
                break;
            }
        
            if(this.playerTurn >= this.players.size()) {
                this.playerTurn = 0;
            }
            if(this.players.get(this.playerTurn).isFolded == false && this.players.get(this.playerTurn).isAllIn == false) {
                System.out.println(this.players.get(this.playerTurn).name + " it is your turn");
                if(this.players.get(this.playerTurn).playerCurrentBet == currentBet){
                    System.out.println("Would you like to 1. fold, 2. check, 3. raise, or go 4. ALL-IN?");
                    this.isCheckorCall = "check";
                }
                else {
                    System.out.println("Would you like to 1. fold, 2. call, 3.raise, or go 4. ALL-IN?");
                    this.isCheckorCall = "call";
                }   
                System.out.println("Type the number of your answer");
                boolean isValidChoice = false;
                while(isValidChoice == false) {
                    try {
                        Scanner scanner = new Scanner(System.in);
                        int choice = scanner.nextInt();
                        switch(choice){
                            case 1:
                                this.players.get(this.playerTurn).isFolded = true;
                                this.playerTurn = this.playerTurn + 1;
                                isValidChoice = true;
                                break;
                            case 2:
                                if(this.isCheckorCall.equals("check")) {
                                    this.players.get(this.playerTurn).isChecked = true;
                                    this.playerTurn = this.playerTurn + 1;
                                    isValidChoice = true;
                                    
                                }
                                else {
                                    if(this.players.get(this.playerTurn).money < this.currentBet - this.players.get(this.playerTurn).playerCurrentBet) {
                                        System.out.println("You do not have enough money to call");
                                        System.out.println("You can only go ALL-IN, or fold");
                                        isValidChoice = false;
                                        
                                    }
                                    else {
                                        this.players.get(this.playerTurn).playerCurrentBet += this.currentBet - this.players.get(this.playerTurn).playerCurrentBet;
                                        this.players.get(this.playerTurn).money -= this.players.get(this.playerTurn).playerCurrentBet;
                                        this.pot += this.currentBet - this.players.get(this.playerTurn).playerCurrentBet;
                                        this.playerTurn = this.playerTurn + 1;
                                        isValidChoice = true;
                                        
                                    }
                                }
                                break;
                            case 3:
                                if(players.get(this.playerTurn).yourMaxRaises > 0) {
                                    if(this.players.get(this.playerTurn).money < this.currentBet - this.players.get(this.playerTurn).playerCurrentBet) {
                                        System.out.println("You cannot raise because you don't have enough money");
                                        isValidChoice = false;
                                        
                                    }
                                    else {
                                        System.out.println("How much would you like to raise?");
                                        int raiseAmount = scanner.nextInt();
                                        System.out.println(this.playerTurn);
                                        if(raiseAmount > this.players.get(this.playerTurn).money) {
                                            System.out.println("You do not have enough money to raise that amount");
                                            isValidChoice = false;
                                           
                                        }
                                        else {
                                            this.players.get(this.playerTurn).playerCurrentBet = raiseAmount + this.currentBet;
                                            this.currentBet += raiseAmount;
                                            this.players.get(this.playerTurn).yourMaxRaises--;
                                            this.isBettingRoundOver = false;
                                            this.playerTurn = this.playerTurn + 1;
                                            isValidChoice = true;
                                           
                                        }
                                        
                                    }
                                    
                                    
                                }
                                break;
                            case 4: 
                                this.players.get(this.playerTurn).isAllIn = true;
                                this.players.get(this.playerTurn).playerCurrentBet += this.players.get(this.playerTurn).money;
                                this.pot += this.players.get(this.playerTurn).money;
                                this.players.get(this.playerTurn).money = 0;
                                this.playerTurn = this.playerTurn + 1;      
                                isValidChoice = true;
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number");
                        isValidChoice = false;
                    }
                }   
            }
            else {
                this.playerTurn++;
            }
            
        }
        
    }

    
}
