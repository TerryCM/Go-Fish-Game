package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.*;


@SuppressWarnings("deprecation")
public class GoFishModel extends Observable {
	/**
	 * keep track of which players turn it is, start at 0.
	 */
	private int whosTurn;
	/**
	 * keep track of if the game is over or not, start as false
	 */
	private boolean gameOver;
	/**
	 * an array of all players in the game
	 */
	private GoFishPlayer[] players;
	/**
	 * how many players are present
	 */
	private int numberOfPlayers;
	/**
	 * the size of the starting hand
	 */
	private int startingHandSize;
	/**
	 * deck of cards being used for the game
	 */
	private Deck deck;

	private boolean turnOver;

	/**
	 * constructor for the model
	 */
	public GoFishModel(int numberOfPlayers, boolean ais, boolean multiDeck,
					   int numberOfDecks, int startingHandSize) {

		this.whosTurn = 0;
		this.gameOver = false;
		this.turnOver = false;
		this.numberOfPlayers = numberOfPlayers;
		this.startingHandSize = startingHandSize;
		this.players = new GoFishPlayer[this.numberOfPlayers];
		this.deck = multiDeck ? new MultiDeck(numberOfDecks) : new Deck();




		//creating the players
		if (ais) {
			players[0] = new GoFishPlayer(0);
			for (int i = 1; i < numberOfPlayers; i++) {
				players[i] = new GoFishAi(i);
			}
		} else {
			for (int i = 0; i < numberOfPlayers; i++) {
				players[i] = new GoFishPlayer(i);
			}
		}
	}

	public void startGame() {
		// assuming that number of players is >= 3
		// drawn cards for each player
		for (int i = 0; i < getPlayers().length; i++) {
			for (int j = 0; j < startingHandSize; j++) {
				getPlayers()[i].addCard(getDeck().draw());
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * change who's turn it is
	 */
	public void changeTurn() {
		//if we are at the last player, change to first player
		this.whosTurn = (this.whosTurn + 1) % this.numberOfPlayers;
		setChanged();
		notifyObservers();
	}

	/**
	 * returns which players turn it is, 0 being first player.
	 *
	 * @return int 0 - 3 depending on how many players there are present
	 */
	public int getWhosTurn() {
		return this.whosTurn;
	}


	/**
	 * method that controls a player asking for a card from another player
	 * <p>
	 * if the other player has a card with that rank, the card is taken from their hand
	 * and given to another player.
	 * <p>
	 * returns true if the player had the card, and takes it.
	 * false if the player did not have the card.
	 */
	public boolean playerAskForCard(int playerToAsk, String rankOfCard) {
		// does the player asked have any of that rank of card?
		if (players[playerToAsk].hasCard(rankOfCard)) {
			//removing all of the card asked
			ArrayList<Card> stolenCards = players[playerToAsk].removeCards(rankOfCard);
			for (Card c : stolenCards) {
				//adding them to the hand of the player who asked
				players[whosTurn].addCard(c);
				
				setChanged();
				notifyObservers();
			}
			
			if (players[whosTurn].getBooks().get(rankOfCard) == 4) {
				players[whosTurn].removeCards(rankOfCard);
				for (GoFishPlayer p: players) {
					if (p instanceof GoFishAi) {
						GoFishAi tempai = (GoFishAi) p;
						tempai.removeRankTracking(rankOfCard);
					}
				}
			}
			//cards were taken
			return true;
		} else {
			//cards not taken, need to make the current player go fish.
			return false;

		}
	}


	/**
	 * return true if the game is over, false otherwise
	 *
	 * @return boolean if game is over
	 */
	public boolean isGameOver() {
		// the game is over if all books have been declared. 
		boolean retval = false;
		int count = 0;
		for (int i=0; i < this.players.length; i++) {
			count += this.players[i].getNumberOfBooks();
		}
		if (count == 13)
			retval = true;
		return retval;
	}


	/**
	 * method to save a game in its current state
	 */
	public void saveGame() {
        Map<String, String> saveGameFile = new HashMap<>();
        saveGameFile.put("whosTurn", Integer.toString(this.whosTurn));
        saveGameFile.put("gameOver", String.valueOf(this.gameOver));
        saveGameFile.put("numberOfPlayers", Integer.toString(this.numberOfPlayers));
        //card ranks delimited by spaces
        saveGameFile.put("deck", this.deck.toStringDeck());
        //card ranks delimited by spaces, each player is their own line in the string.
        //player 1 = line 1 of string, player 2 = line 2 of string, etc.
        saveGameFile.put("hands", "");
        //loop through all current players
        String test = "";
        for (int i = 0; i < this.numberOfPlayers; i++) {
            //save the player's hand by concatenating the cards ranks followed by a new line
            test += this.players[i].toStringHand() + ", ";
        }
        saveGameFile.put("hands", test);
        // new file object
        File file = new File("saveGameFile.txt");

        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> entry :
                 saveGameFile.entrySet()) {
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bf.close();
            }
            catch (Exception e) {
            }
        }
    }

	/**
	 * method to load a game given a save game file.
	 */
	public void loadGame() {
		Map<String, String> gameFile = HashMapFromTextFile();
		
		this.whosTurn = Integer.parseInt(gameFile.get("whosTurn"));
        this.gameOver = Boolean.parseBoolean(gameFile.get("gameOver"));
        this.numberOfPlayers = Integer.parseInt(gameFile.get("numberOfPlayers"));
        this.players = new GoFishPlayer[this.numberOfPlayers];
		
		ArrayList<String> hands = new ArrayList<String>(Arrays.asList(gameFile.get("hands").split(",")));
		
		int count = 0;
		for (String hand:hands) {
			ArrayList<String> playersCards = new ArrayList<String>(Arrays.asList(hand.split(" ")));
			playersCards.remove("");
			int i = 0;
			int j = 1;
			while (j < playersCards.size()) {
				if (players[count] == null) {
					players[count] = new GoFishPlayer(count);
				}
				players[count].addCard(new Card(playersCards.get(j),playersCards.get(i)));
				i += 2;
				j += 2;
			}
			count += 1;
		}
		
		this.deck = new Deck();
		
		ArrayList<String> deckList = new ArrayList<String>(Arrays.asList(gameFile.get("deck").split(",")));
		ArrayList<Card> newCards = new ArrayList<Card>();
		for (String card:deckList) {
			ArrayList<String> cards = new ArrayList<String>(Arrays.asList(card.split(" ")));
			cards.remove("");
			newCards.add(new Card(cards.get(1), cards.get(0)));
		}
		this.deck.replaceDeck(newCards);
		setChanged();
		notifyObservers();
		return;
	}
	
	public static Map<String, String> HashMapFromTextFile()
    {
  
        Map<String, String> map
            = new HashMap<String, String>();
        BufferedReader br = null;
  
        try {
  
            // create file object
            File file = new File("saveGameFile.txt");
  
            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));
  
            String line = null;
  
            // read file line by line
            while ((line = br.readLine()) != null) {
  
                // split the line by :
                String[] parts = line.split(":");
  
                // first part is name, second is number
                String name = parts[0].trim();
                String number = parts[1].trim();
  
                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !number.equals(""))
                    map.put(name, number);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
  
            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }
  
        return map;
    }

	/**
	 *
	 * @return players array
	 */
	public GoFishPlayer[] getPlayers() {
		return players;
	}

	public Deck getDeck() {
		return deck;
	}

	public String handToString(ArrayList<Card> toStr) {
		String retval = "Deck: ";
		for (Card card:toStr) {
			retval += card.getRank() +" ";
		}
		return retval;
	}

	public String getOurCurrentHand() {
		// TODO Auto-generated method stub
		return handToString(this.players[this.whosTurn].getHand());
	}

	public String getPlayerDeckCount(String location) {
		int player = 0;
		if (location.equals("right")) {
			player = (this.whosTurn + 3) % this.numberOfPlayers;
		} else if (location.equals("top")) {
			player = (this.whosTurn + 2) % this.numberOfPlayers;
		} else if (location.equals("left")) {
			player = (this.whosTurn + 1) % this.numberOfPlayers;
		}
		int size = this.players[player].getHand().size();
		return "Deck: " +size;
	}
	
	public String getPlayerName(String location) {
		
		int player = 0;
		if (location.equals("right")) {
			player = (this.whosTurn + 3) % this.numberOfPlayers;
		} else if (location.equals("top")) {
			player = (this.whosTurn + 2) % this.numberOfPlayers;
		} else if (location.equals("left")) {
			player = (this.whosTurn + 1) % this.numberOfPlayers;
		} else {
			player = this.whosTurn;
		}
		return "Player " +Integer.toString(player + 1);
	}
	
	public String getPlayerBookCount(String location) {
		int bookCount = 0;
		if (location.equals("right")) {
			bookCount = this.players[(this.whosTurn + 3) % this.numberOfPlayers].getNumberOfBooks();
		} else if (location.equals("top")) {
			bookCount = this.players[(this.whosTurn + 2) % this.numberOfPlayers].getNumberOfBooks();
		} else if (location.equals("left")) {
			bookCount = this.players[(this.whosTurn + 1) % this.numberOfPlayers].getNumberOfBooks();
		} else if (location.equals("ours")) {
			bookCount = this.players[this.whosTurn].getNumberOfBooks();
		}
		
		return "Stack: " + Integer.toString(bookCount);
	}

	public String getCardsLeft() {
		return "Cards Left: " + Integer.toString(this.deck.size());
	}

	public ImageView[] getDeckImages() {
		ImageView[] retval = new ImageView[players[whosTurn].getHand().size()];

		int leftOffset = 0;
		int topOffset = 0;
		int count = 0;
		for (Card c: players[whosTurn].getHand()) {
			if (c.getRank().equals("Ace")) {
				leftOffset = 0;
			} else if (c.getRank().equals("Jack")) {
				leftOffset = 85*10;
			} else if (c.getRank().equals("Queen")) {
				leftOffset = 85*11;
			} else if (c.getRank().equals("King")) {
				leftOffset = 85*12;
			} else {
				leftOffset = 85*(Integer.parseInt(c.getRank())-1);
			}

			if (c.getSuit().equals("Hearts")) {
				topOffset = 0;
			} else if (c.getSuit().equals("Diamonds")) {
				topOffset = 119*2;

			} else if (c.getSuit().equals("Clubs")) {
				topOffset = 119*3;
			} else if (c.getSuit().equals("Spades")) {
				topOffset = 119*1;
			}
			Image image = new Image("util/isolatedcards.png");
			ImageView iv3 = new ImageView(image);
			Rectangle2D viewportRect = new Rectangle2D(5+leftOffset, 5+topOffset, 80, 119);
			iv3.setViewport(viewportRect);
			retval[count] = iv3;
			count++;
		}

		return retval;
	}


	public boolean playerGoFish(String rankAsked) {
		//draw random card from the shuffled deck
		if (getDeck().size() == 0) {
			return false;
		};
		Card fishedCard = getDeck().draw();
		//add to players hand
		getPlayers()[getWhosTurn()].addCard(fishedCard);
		setChanged();
		notifyObservers();
		return fishedCard.getRank().equals(rankAsked);
	}


	public void setTurnOver(boolean b) {
		// TODO Auto-generated method stub
		this.turnOver = b;
	}

	public boolean isTurnOver() {
		// TODO Auto-generated method stub
		return this.turnOver;
	}

	public int getNumPlayers() {
		// TODO Auto-generated method stub
		return this.numberOfPlayers;
	}

}

