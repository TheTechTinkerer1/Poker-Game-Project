import java.util.ArrayList;
import java.util.Collections;

public class HandEvaluation implements Comparable<HandEvaluation> {

    Player player;
    int handRating;

    public HandEvaluation(Player player, int handRating) {
        this.player = player;
        this.handRating = handRating;
    }

    public HandEvaluation() {
    }

    public int evalHands(ArrayList<Card> communityCards, ArrayList<Player> players, ArrayList<Card> hand, Player person) {
        int rating = 0;
        
        for(Card card : communityCards) {
            person.hand.add(card);
        }
        
        if(isRoyalFlush(hand, "Hearts") || isRoyalFlush(hand, "Diamonds") || isRoyalFlush(hand, "Clubs") || isRoyalFlush(hand, "Spades")) {
                rating = 10;
            
        }
        else if(isStraightFlush(hand, "Hearts") || isStraightFlush(hand, "Diamonds") || isStraightFlush(hand, "Clubs") || isStraightFlush(hand, "Spades")) {
                rating = 9;
            
        }
        else if(isFourOfAKind(hand)){
                rating = 8;
            
        }
        else if(isFullHouse(hand)){
            rating = 7;
        }
        else if(isFlush(hand, "Hearts") || isFlush(hand, "Diamonds") || isFlush(hand, "Clubs") || isFlush(hand, "Spades")){
            rating = 6;
        }
        else if(isStraight(hand)){
            rating = 5;
        }
        else if(isThreeOfAKind(hand)){
            rating = 4;
        }
        else if(isTwoPair(hand)){
            rating = 3;
        }
        else if(isPair(hand)){
            rating = 2;
        }
        else {
            rating = 1;
        }
        return rating;
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
            return (cardsInStraightFlush.get(4) - cardsInStraightFlush.get(0) == 4) || (cardsInStraightFlush.get(5) - cardsInStraightFlush.get(1) == 4);
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
        return cardsOfSuit.size() >= 5;
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
            return (cardsInStraight.get(4) - cardsInStraight.get(0) == 4) || (cardsInStraight.get(5) - cardsInStraight.get(1) == 4) || (cardsInStraight.get(6) - cardsInStraight.get(2) == 4);
        }
        else {
            return false;
        }
    }

    public boolean isThreeOfAKind(ArrayList<Card> hand) {
        int[] rankCounter = new int[13];
        for(Card card : hand) {
            rankCounter[card.getRank() - 2]++;
        }
        for(int count : rankCounter) {
            if(count == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean isTwoPair(ArrayList<Card> hand) {
        int[] rankCounter = new int[13];
        int pairCounter = 0;
        for(Card card : hand) {
            rankCounter[card.getRank() - 2]++;
        }
        for(int count : rankCounter) {
            if(count == 2) {
                pairCounter++;
            }
        }
        return pairCounter >= 2;
    }

    public boolean isPair(ArrayList<Card> hand) {
        int[] rankCounter = new int[13];
        for(Card card : hand) {
            rankCounter[card.getRank() - 2]++;
        }
        for(int count : rankCounter) {
            if(count == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(HandEvaluation other) {
        return Integer.compare(other.handRating, this.handRating);
    }

}

