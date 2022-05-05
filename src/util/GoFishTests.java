package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import controller.GoFishController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.GoFishModel;


public class GoFishTests {
	
	@Test
	void modelTest1() {
		GoFishModel gameModel = new GoFishModel(2, true, true, 2, 7);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		assertEquals(true, (game.getPlayers()[1] instanceof GoFishAi));
	}
	
	@Test
	void modelTest2() {
		GoFishModel gameModel = new GoFishModel(4, false, false, 1, 3);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		assertEquals(40 , game.getDeck().size());
		assertEquals(true, (game.getPlayers()[1] instanceof GoFishPlayer));
	}
	
	@Test
	void turnTest() {
		GoFishModel gameModel = new GoFishModel(4, false, false, 1, 3);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.changeTurn();
		assertEquals(1, game.getWhosTurn());
		game.changeTurn();
		assertEquals(2, game.getWhosTurn());
	}
	
	@Test
	void guessTest() { // works most of the time
		GoFishModel gameModel = new GoFishModel(2, true, true, 4, 1);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		boolean check = false;
		check = game.makeGuess("king", 1);
		assertEquals(false, check);
	}
	
	@Test
	void booksTest() {
		GoFishModel gameModel = new GoFishModel(2, true, true, 4, 104);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.makeGuess("king", 1);
		game.makeGuess("4", 1);
		game.makeGuess("3", 1);
		game.makeGuess("2", 1);
		game.makeGuess("Ace", 1);
		game.makeGuess("Queen", 1);
		assert(game.getPlayers()[0].getNumberOfBooks() > 0);
	}
	
	@Test
	void gameOverTest1() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 26);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.makeGuess("king", 1);
		game.makeGuess("Queen", 1);
		game.makeGuess("Jack", 1);
		game.makeGuess("10", 1);
		game.makeGuess("9", 1);
		game.makeGuess("8", 1);
		game.makeGuess("7", 1);
		game.makeGuess("6", 1);
		game.makeGuess("5", 1);
		game.makeGuess("4", 1);
		game.makeGuess("3", 1);
		game.makeGuess("2", 1);
		game.makeGuess("Ace", 1);
		game.makeGuess("king", 0);
		game.makeGuess("Queen", 0);
		game.makeGuess("Jack", 0);
		game.makeGuess("10", 0);
		game.makeGuess("9", 0);
		game.makeGuess("8", 0);
		game.makeGuess("7", 0);
		game.makeGuess("6", 0);
		game.makeGuess("5", 0);
		game.makeGuess("4", 0);
		game.makeGuess("3", 0);
		game.makeGuess("2", 0);
		game.makeGuess("Ace", 0);
		game.playerGoFish("1");
		assertEquals(game.isGameOver(), true);
	}
	
	@Test
	void saveLoadTest() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.saveGame();
		game.loadGame();
	}
	
	@Test
	void printHandTest() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		System.out.println(game.getOurCurrentHand());
	}
	
	@Test
	void printPlayerDeckCountTest() {
		GoFishModel gameModel = new GoFishModel(4, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		System.out.println(game.getPlayerDeckCount("left"));
		System.out.println(game.getPlayerDeckCount("right"));
		System.out.println(game.getPlayerDeckCount("top"));
		System.out.println(game.getPlayerDeckCount("ours"));
	}
	
	@Test
	void printPlayerNameTest() {
		GoFishModel gameModel = new GoFishModel(4, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		System.out.println(game.getPlayerName("left"));
		System.out.println(game.getPlayerName("right"));
		System.out.println(game.getPlayerName("top"));
		System.out.println(game.getPlayerName("ours"));
	}
	
	@Test
	void printPlayerBookCountTest() {
		GoFishModel gameModel = new GoFishModel(4, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		System.out.println(game.getPlayerBookCount("left"));
		System.out.println(game.getPlayerBookCount("right"));
		System.out.println(game.getPlayerBookCount("top"));
		System.out.println(game.getPlayerBookCount("ours"));
	}
	
	@Test
	void playerGoFishTest() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.playerGoFish("1");
	}
	
	@Test
	void TurnOverTest1() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.setTurnOver(true);
		assertEquals(true, game.isTurnOver());
	}
	
	@Test
	void TurnOverTest2() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.setTurnOver(false);
		assertEquals(false, game.isTurnOver());
	}

	@Test
	void getNumberOfPlayersTest() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.setTurnOver(false);
		assertEquals(2, game.getNumberOfPlayers());
	}
	
/*	@Test
	void getDeckImagesTest() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1,26);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		//ImageView[] i = game.getCardsLeft();
	} */
	
	@Test
	void getCardsLeftTest() {
		GoFishModel gameModel = new GoFishModel(2, false, false, 1, 5);
		GoFishController game = new GoFishController(gameModel);
		game.createDecks();
		game.getCardsLeft();
	}
	
	@Test
	void altPlayerTest() {
		GoFishPlayer p = new GoFishPlayer(1, "ryan");
	}
	
	@Test
	void GoFishpPlayerNameTest() {
		GoFishPlayer p = new GoFishPlayer(1, "ryan");
		assertEquals("ryan", p.getName());
	} 
	
	@Test
	void GoFishpPlayerNumberTest() {
		GoFishPlayer p = new GoFishPlayer(1, "ryan");
		assertEquals(1, p.getPlayerNumber());
	} 
	
	@Test
	void GoFishpPlayerHandSizeTest() {
		GoFishPlayer p = new GoFishPlayer(1, "ryan");
		assertEquals(0, p.handSize());
	} 
	
	@Test
	void PlayerDrawTest() {
		GoFishPlayer p = new GoFishPlayer(1, "ryan");
		Card card = new Card("Spades", "2");
		p.addCard(card);
		Card c = p.draw();
		assertEquals(c, card);
	}
	
	@Test
	void CardToStringTest() {
		Card card = new Card("Spades", "2");
		assertEquals("2 of Spades", card.toString());
	}
	
	@Test
	void CardEqualsOnRankTest() {
		Card card = new Card("Spades", "2");
		Card card2 = new Card("Diamonds", "2");
		assertEquals(true, card.equalsOnRank(card2));
	} 
	
	@Test
	void MultiDeckTest() {
		MultiDeck m = new MultiDeck(2);
		assertEquals(2, m.getNumDecks());
	}
	
	@Test
	void addOpposingMoveAiTest() {
		GoFishAi a = new GoFishAi(0);
		a.addOpposingMove("Spades", 1);
	}
	
	@Test
	void removeOpposingMoveAiTest() {
		GoFishAi a = new GoFishAi(0);
		a.addOpposingMove("Spades", 1);
		a.removeOpposingMove();
	}
	
	@Test
	void addOpposingMoveAiTest() {
		GoFishAi a = new GoFishAi(0);
		a.addOpposingMove("Spades", 1);
	}
	
	@Test
	void addOpposingMoveAiTest() {
		GoFishAi a = new GoFishAi(0);
		a.addOpposingMove("Spades", 1);
	}
}