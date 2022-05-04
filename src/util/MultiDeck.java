package util;

public class MultiDeck extends Deck {
    private final int numDecks;
    public MultiDeck(int numDecks) {
        super();
        this.numDecks = numDecks;
        for (int i = 0; i < numDecks; i++) {
            super.addNewDeck(new Deck());
        }
    }
    public int getNumDecks() {
        return numDecks;
    }
}
