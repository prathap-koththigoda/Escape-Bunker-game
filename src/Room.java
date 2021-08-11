
/*
 * Class Room - a room in an adventure game.
 *
 *
 * Author:  
 * Version: 
 * Date:    
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */

import java.util.HashMap;

public class Room {
	
	private String description;
	private HashMap<String,Room> exits; // stores exits of this room.

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */
	public Room(String description) {
		this.description = description;
		this.exits = new HashMap<String,Room>();
	}

	/**
	 * Define the exits of this room.  Every direction either leads to
	 * another room or is null (no exit there).
	 */
	public void setExits(Room north, Room east, Room south, Room west) {
		if (north != null) {
			exits.put("vorwärts", north);
		}
		if (east != null) {
			exits.put("rechts", east);
		}
		if (south != null) {
			exits.put("rückwärts", south);
		}
		if (west != null) {
			exits.put("links", west);
		}
	}

	/**
	 * Return the description of the room (the one that was defined in the
	 * constructor).
	 */
	public String shortDescription() {
		return description;
	}

	/**
	 * Return a long description of this room, on the form:
	 *     You are in the kitchen.
	 *     Exits: north west
	 */
	public String longDescription() {
		return " Text:" + description + ".\n" + exitString();
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west ".
	 */
	private String exitString() {
		String returnString = "Richtungen:";
		for (String key : exits.keySet()) {
			returnString += " " + key;
		}
		return returnString;
	}

	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 */
	public Room nextRoom(String direction) {
		return (Room) exits.get(direction);
	}
}