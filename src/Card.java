public class Card {
    private final  String name;
    private final String suit;
    private final int rank;

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
