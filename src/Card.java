public class Card {
    private  String name;
    private String suit;
    private int rank;

    public Card(String name, String suit, int rank) {
        this.name = name;
        this.suit = suit;
        this.rank = rank;
    }
    
    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }



}
