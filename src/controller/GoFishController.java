package controller;


import javafx.scene.image.ImageView;
import model.GoFishModel;
import util.Deck;
import util.GoFishPlayer;

public class GoFishController {

    private final GoFishModel model;

    /**
     * Constructor for GoFishController
     *
     * @param model
     */

    // consider making the controller a singleton
    public GoFishController(GoFishModel model) {
        this.model = model;
    }

    /**
     * @param rankAsked
     * @param playerToAsk
     * @return true if the player has the card asked for and false otherwise
     */
    public boolean makeGuess(String rankAsked, int playerToAsk) {
        if (!model.playerAskForCard(playerToAsk, rankAsked)) {
			return playerGoFish(rankAsked);
        }
        return true;
    }

    /**
     * method to make the current player "go fish" from the deck.
     * <p>
     * this occurs if a player asks for a card and the player being asked
     * does not have any of that cards rank.
     * <p>
     * player pulls a random card from the deck and brings it to their hand
     */
    public boolean playerGoFish(String rankAsked) {
        return model.playerGoFish(rankAsked);
    }

    /**
     * Create Decks for the game
     */
    public void createDecks() {
        model.startGame();
    }

    /**
     * Return the players
     *
     * @return GoFishPlayer objects
     */
    public GoFishPlayer[] getPlayers() {
        return model.getPlayers();
    }

    /**
     * Return whose turn it is
     *
     * @return int representing the player's turn
     */
    public int getWhosTurn() {
        return model.getWhosTurn();
    }

    /**
     * Return the deck
     *
     * @return Deck object
     */
    public Deck getDeck() {
        return model.getDeck();
    }

    /**
     * Return the game over boolean
     *
     * @return boolean representing if the game is over
     */
    public boolean isGameOver() {
        return model.isGameOver();
    }

    /**
     * Return the current player's hand
     *
     * @return A String reperesenting the current player's hand
     */
    public String getOurCurrentHand() {
        // TODO Auto-generated method stub
        return model.getOurCurrentHand();
    }

    /**
     * Return the current player deck count
     *
     * @param i the player's index
     * @return int representing the current player's deck count
     */
    public String getPlayerDeckCount(String i) {
        return model.getPlayerDeckCount(i);
    }

    /**
     * @return a String, representing the cards on the left hand
     */
    public String getCardsLeft() {
        // TODO Auto-generated method stub
        return model.getCardsLeft();
    }

    /**
     * Saves the game.
     */
    public void saveGame() {
        // TODO Auto-generated method stub
        model.saveGame();
    }

    /**
     * Get Deck Images
     *
     * @return a ImageView array
     */
    public ImageView[] getDeckImages() {
        return model.getDeckImages();
    }

    /**
     * @return true if the turn is over, false otherwise
     */
    public boolean isTurnOver() {
        return model.isTurnOver();
    }

    /**
     * Set the turns over
     *
     * @param b boolean representing if the game is over
     */
    public void setTurnOver(boolean b) {
        // TODO Auto-generated method stub
        model.setTurnOver(b);
    }

    /**
     * @return the number of pla
     */
    public int getNumberOfPlayers() {
        // TODO Auto-generated method stub
        return model.getNumPlayers();
    }

    /**
     * Changes the turn
     */
    public void changeTurn() {
        model.changeTurn();
    }

    /**
     * Get players book count
     *
     * @param string, representing the player's index
     * @return the player's book count
     */
    public String getPlayerBookCount(String string) {
        // TODO Auto-generated method stub
        return model.getPlayerBookCount(string);
    }

    /**
     * Return player name.
     *
     * @param string, representing the player's index
     * @return the player's name
     */
    public String getPlayerName(String string) {
        // TODO Auto-generated method stub
        return model.getPlayerName(string);
    }

    /**
     * Loads the game.
     */
    public void loadGame() {
        // TODO Auto-generated method stub
        model.loadGame();
    }

    /**
     * @return a String representing the winner
     */
    public String getWinner() {
        // TODO Auto-generated method stub
        return model.getWinner();
    }

}