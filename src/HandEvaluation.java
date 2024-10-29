import java.util.ArrayList;
import java.util.Collections;

public class HandEvaluation {
     
    public float evalHands(ArrayList<Card> communityCards, Player[] players) {
        float rating = 0.0f;
        for(Player player : players) {
            for(Card card : communityCards) {
                player.hand.add(card);
            }
        }
        if(isRoyalFlush(communityCards, "Hearts") || isRoyalFlush(communityCards, "Diamonds") || isRoyalFlush(communityCards, "Clubs") || isRoyalFlush(communityCards, "Spades")) {
                rating = 1.0f;
            
        }
        else if(isStraightFlush(communityCards, "Hearts") || isStraightFlush(communityCards, "Diamonds") || isStraightFlush(communityCards, "Clubs") || isStraightFlush(communityCards, "Spades")) {
                rating = 0.9f;
            
        }
        else if(isFourOfAKind(communityCards)){
                rating = 0.8f;
            
        }
        else if(isFullHouse(communityCards)){
            rating = 0.7f;
        }
        else if(isFlush(communityCards, "Hearts") || isFlush(communityCards, "Diamonds") || isFlush(communityCards, "Clubs") || isFlush(communityCards, "Spades")){
            rating = 0.6f;
        }
        else if(isStraight(communityCards)){
            rating = 0.5f;
        }
        

        return 0.2f;
    }

    public boolean isRoyalFlush(ArrayList<Card> hand, String suit) {
        final ArrayList<String> cardsInRoyalFlush = new ArrayList<>();
        cardsInRoyalFlush.add("Ace");
        cardsInRoyalFlush.add("King");
        cardsInRoyalFlush.add("Queen");
        cardsInRoyalFlush.add("Jack");
        cardsInRoyalFlush.add("10");
        ArrayList<String> cardsOfSuit= new ArrayList<>();
        int correctCardsCounter = 0;
        for(Card card : hand) {
            if(card.getSuit().equals(suit)) {
                cardsOfSuit.add(card.getName());
            }
        }
        if(cardsOfSuit.size() < 5) {
            return false;
        }
        else if(cardsOfSuit.size() >= 5){
            for(String card : cardsOfSuit){
                if(cardsInRoyalFlush.contains(card)){
                    correctCardsCounter++;
                    cardsInRoyalFlush.remove(card);
                }
            }     
        }
        return correctCardsCounter == 5;
       
    } 
    
    public boolean isStraightFlush(ArrayList<Card> hand, String suit) {
        ArrayList<Card> cardsOfSuit= new ArrayList<>();
        ArrayList<Integer> cardsInStraightFlush = new ArrayList<>();
        for(Card card : hand) {
            if(card.getSuit().equals(suit)) {
                cardsOfSuit.add(card);
            }
        }
        for(Card card : cardsOfSuit){
            cardsInStraightFlush.add(card.getRank());
        }
        Collections.sort(cardsInStraightFlush);
        if(cardsOfSuit.size() < 5) {
            return false;
        }
        else if(cardsOfSuit.size() >= 5){
            return cardsInStraightFlush.get(4) - cardsInStraightFlush.get(0) == 4;
        }
        else {
            return false;
        }
    }
    public boolean isFourOfAKind(ArrayList<Card> hand) {
        for(Card card : hand) {
            int counter = 0;
            for(Card card2 : hand) {
                if(card.getRank() == card2.getRank()) {
                    counter++;
                }
            }
            if(counter == 4) {
                return true;
            }
        }
        return false;
    
    }

    public boolean isFullHouse(ArrayList<Card> hand) {
        boolean isThreeOfAKind = false;
        boolean isPair = false;
        int[] rankCounter = new int[13];

        for(Card card : hand) {
            rankCounter[card.getRank() - 2]++;
        }
        
        for(int count : rankCounter) {
            if(count == 3) {
                isThreeOfAKind = true;
            }
            else if(count == 2) {
                isPair = true;
            }
        }
        return isThreeOfAKind && isPair;
    }

    public boolean isFlush(ArrayList<Card> hand, String suit) {
        ArrayList<Card> cardsOfSuit= new ArrayList<>();
        for(Card card : hand) {
            if(card.getSuit().equals(suit)) {
                cardsOfSuit.add(card);
            }
        }
        if(cardsOfSuit.size() < 5) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isStraight(ArrayList<Card> hand) {
        ArrayList<Integer> cardsInStraight = new ArrayList<>();
        for(Card card : hand){
            cardsInStraight.add(card.getRank());
        }
        Collections.sort(cardsInStraight);
        if(cardsInStraight.size() < 5) {
            return false;
        }
        else if(cardsInStraight.size() >= 5){
            return cardsInStraight.get(4) - cardsInStraight.get(0) == 4;
        }
        else {
            return false;
        }
    }
}

