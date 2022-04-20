package util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {
	
	/**
	 * represents the message as a String
	 */
	private String message;

	
	/**
	 * BattleMessage given just a message
	 * @param m the message
	 */
	public Message(String m) {
		this.message = m;
	}
	
	/**
	 * Method to return the message as a string
	 * @return String the message
	 */
	public String toString() {
		return message;
	}
	
}
