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
    public Player lastRaiser;


    public Mechanics(int smallBlind, int bigBlind) {
        this.dealerIndex = 0;
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
        this.lastRaiser = null;
    }

    public Mechanics() {
        this.dealerIndex = 0;
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

    public void assignBlinds() {
        int smallBlindIndex = (this.dealerIndex + 1) % this.players.size();
        int bigBlindIndex = (this.dealerIndex + 2) % this.players.size();

        for(Player player : this.players){
            player.isSmallBlind = false;
            player.isBigBlind = false;
        }

        this.players.get(smallBlindIndex).isSmallBlind = true;
        this.players.get(bigBlindIndex).isBigBlind = true;

        
    }

    public void enforceBlinds(){
    
            this.players.get(this.dealerIndex + 1).money -= this.smallBlind;
            this.pot += this.smallBlind;
            System.out.println(this.players.get(this.dealerIndex + 1).name + " is the small blind and bets " + this.smallBlind);
            this.players.get((this.dealerIndex + 1) % this.players.size()).playerCurrentBet = this.smallBlind;
            
        
            this.players.get(this.dealerIndex + 2).money -= this.bigBlind;
            this.pot += this.bigBlind;
            System.out.println(this.players.get(this.dealerIndex + 2).name + " is the big blind and bets " + this.bigBlind);
            this.players.get(this.dealerIndex + 2).isTheRaiser = true;
            this.lastRaiser = this.players.get(this.dealerIndex + 2);
            this.currentBet = this.bigBlind;
            this.players.get((this.dealerIndex + 2) % this.players.size()).playerCurrentBet = this.bigBlind;

            this.playerTurn += 3;
        
    }

    public void rotateDealer() {
        this.dealerIndex = (this.dealerIndex + 1) % this.players.size();
        this.dealer = this.players.get(this.dealerIndex);
        System.out.println(this.players.get(this.dealerIndex).getName() + " is the dealer"); 
    
    }

    public boolean allPlayersCheckedorFoldedorAllIn() {
        for(Player player : this.players){
            if(!player.isChecked && !player.isFolded && !player.isAllIn){
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
            player.isTheRaiser = false;
            this.lastRaiser = null;
            if(isRoundOver == true){
                for(int i = 0; i < player.hand.size(); i++){
                    deck.cards.add(player.hand.get(i));
                    player.hand.remove(player.hand.get(i));
                    i--;
                }
            }
            
        }
        if(isRoundOver == true){
            for(int i = 0; i < deck.communityCards.size(); i++){
                deck.cards.add(deck.communityCards.get(i));
                deck.communityCards.remove(deck.communityCards.get(i));
                i--;
            }
            for(int i = 0; i < deck.discardPile.size(); i++){
                deck.cards.add(deck.discardPile.get(i));
                deck.discardPile.remove(deck.discardPile.get(i));
                i--;
            }
            this.isRoundOver = false;
            this.pot = 0;
            rotateDealer();
            
        }
        this.isBettingRoundOver = false;
        this.currentBet = 0;
        

    }

    public Player getPlayerWithGreatestBet() {
        Player playerRaiser = null;
        for(Player player : this.players){
            if(player.isTheRaiser == true){
                playerRaiser = player;
            }
        }
        return playerRaiser;
    }

    public void determineWinner(ArrayList<Card> communityCards, ArrayList<Player> players) {
        Player winner = null;
        HandEvaluation handEval = new HandEvaluation();
        if(checkIfAllButOneFolded() == true)
        {
            for(Player player : players){
                if(player.isFolded == false){
                    winner = player;
                    winner.money += this.pot;
                    isRoundOver = true;
                    System.out.println(winner.name + " has won the round with " + handEval.getHandName(player.evalPlayerHand(communityCards, players)) + "and recieves " + this.pot);
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
            if(playersWithSameRating.isEmpty()){
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
                playerHighCardRankings[0] = bestPlayer; 
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
                    System.out.println(winner.name + " has won the round with " + handEval.getHandName(winner.evalPlayerHand(communityCards, players)) + "and recieves " + this.pot);
                    isRoundOver = true;
                    
                }
                else {
                   int moneyPerWinner = this.pot / playersWithSameHighCardRanking.size();
                   for(Player player : playersWithSameHighCardRanking){
                       player.money += moneyPerWinner;
                       System.out.println(player.name + " is a winner  " + handEval.getHandName(player.evalPlayerHand(communityCards, players)) + "and recieves a split of " + this.pot + "from the pot");
                   }
                   isRoundOver = true;

                }

            }
            
        }
    }

    public void promptPlayerTurn(Deck deck) {
        while(this.isBettingRoundOver == false) {
            if(allPlayersCheckedorFoldedorAllIn() || checkIfAllButOneFolded()) {
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
                    System.out.println("You can also 5. look at your cards, 6. look at community cards, or 7. look at pot");
                    this.isCheckorCall = "check";
                }
                else {
                    System.out.println("Would you like to 1. fold, 2. call, 3. raise, or go 4. ALL-IN?");
                    System.out.println("You can also 5. look at your cards, 6. look at community cards, or 7. look at pot");
                    this.isCheckorCall = "call";
                }   
                System.out.println("Type the number of your answer");
                boolean isValidChoice = false;
                boolean not567 = false;
                Scanner scanner = new Scanner(System.in);
                while(isValidChoice == false && not567 == false) {
                    try {
                        int choice = scanner.nextInt();
                        switch(choice){
                            case 1 -> {
                                this.players.get(this.playerTurn).isFolded = true;
                                this.playerTurn = this.playerTurn + 1;
                                isValidChoice = true;
                                not567 = true;
                                System.out.println(this.players.get(this.playerTurn).name + " has folded.");
                            }
                            case 2 -> {
                                if(this.isCheckorCall.equals("check")) {
                                    this.players.get(this.playerTurn).isChecked = true;
                                    System.out.println(this.players.get(this.playerTurn).name + " has checked.");
                                    this.playerTurn = this.playerTurn + 1;
                                    isValidChoice = true;
                                    not567 = true;
                                    
                                    
                                }
                                else {
                                    int callAmount = this.currentBet - this.players.get(this.playerTurn).playerCurrentBet;
                                    if(this.players.get(this.playerTurn).money < this.currentBet - this.players.get(this.playerTurn).playerCurrentBet) {
                                        System.out.println("You do not have enough money to call");
                                        System.out.println("You can only go ALL-IN, or fold");
                                        isValidChoice = false;
                                        not567 = true;
                                        
                                    }
                                    else {
                                        Player playerWithGreatestBet = getPlayerWithGreatestBet();
                                        System.out.println(this.players.get(this.playerTurn).name + " has matched " + playerWithGreatestBet.name + "'s bet and added " + callAmount + " to the pot.");
                                        this.players.get(this.playerTurn).playerCurrentBet += callAmount;
                                        this.players.get(this.playerTurn).money -= callAmount;
                                        System.out.println(this.players.get(this.playerTurn).playerCurrentBet);
                                        this.pot += callAmount;
                                        this.playerTurn = this.playerTurn + 1;
                                        isValidChoice = true;
                                        not567 = true;
                                        
                                    }
                                }
                            }
                            case 3 -> {
                                if(players.get(this.playerTurn).yourMaxRaises > 0) {
                                    if(this.players.get(this.playerTurn).money < this.currentBet - this.players.get(this.playerTurn).playerCurrentBet) {
                                        System.out.println("You cannot raise because you don't have enough money");
                                        isValidChoice = false;
                                        not567 = true;
                                        
                                    }
                                    else {
                                        System.out.println("How much would you like to raise?");
                                        int raiseAmount = scanner.nextInt();
                                        System.out.println(this.playerTurn);
                                        if(raiseAmount > this.players.get(this.playerTurn).money) {
                                            System.out.println("You do not have enough money to raise that amount");
                                            isValidChoice = false;
                                            not567 = true;
                                           
                                        }
                                        else {
                                            if(this.lastRaiser != null){
                                                this.lastRaiser.isTheRaiser = false;
                                            }
                                            this.players.get(this.playerTurn).isTheRaiser = true;
                                            this.lastRaiser = this.players.get(this.playerTurn);
                                            this.players.get(this.playerTurn).money -= raiseAmount;
                                            this.players.get(this.playerTurn).playerCurrentBet = raiseAmount + this.currentBet;
                                            this.currentBet += raiseAmount;
                                            this.players.get(this.playerTurn).yourMaxRaises--;
                                            System.out.println(this.players.get(this.playerTurn).name + " has raised the bet by " + raiseAmount + " and added " + this.players.get(this.playerTurn).playerCurrentBet + " to the pot.");
                                            this.isBettingRoundOver = false;
                                            this.playerTurn = this.playerTurn + 1;
                                            isValidChoice = true;
                                            not567 = true;
                                           
                                        }
                                        
                                    }
                                    
                                    
                                }
                                else {
                                    System.out.println("You have reached the maximum number of raises");
                                    isValidChoice = false;
                                    not567 = true;
                                }
                            }
                            case 4 -> { 
                                this.players.get(this.playerTurn).isAllIn = true;
                                this.players.get(this.playerTurn).playerCurrentBet += this.players.get(this.playerTurn).money;
                                this.pot += this.players.get(this.playerTurn).money;
                                System.out.println(this.players.get(this.playerTurn).name + " has gone ALL-IN and added " + this.players.get(this.playerTurn).money + " to the pot.");
                                this.players.get(this.playerTurn).money = 0;
                                this.playerTurn = this.playerTurn + 1;      
                                isValidChoice = true;
                                not567 = true;
                            }
                            case 5 -> {
                                this.players.get(this.playerTurn).handCheck();
                                isValidChoice = true;
                                not567 = false;
                            }
                            case 6 -> {
                                System.out.println("Community Cards: ");
                                for(Card card : deck.communityCards){
                                    System.out.println(card.getName() + " of " + card.getSuit());
                                }
                                isValidChoice = true;
                                not567 = false;
                            }
                            case 7 -> {
                                System.out.println("Pot: " + this.pot);
                                isValidChoice = true;
                                not567 = false;
                            }
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
