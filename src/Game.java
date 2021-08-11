/**
 * Author:  
 * Version: 
 * Date:    
 *
 */

public class Game {
	
	private Parser parser;
	private Room currentRoom;
	private Room ruheraum, aufenthaltsraum, chemieraum, lagerraum, kontrollraum, schluesselraum, hauptfloor, escaperoom;
	private boolean[] items; // Referenzvariable wird erstellt


	/**
	 * Spiel wird kreiert und initialisiert.
	 */
	public Game() {
		
		parser = new Parser();

		items = new boolean[3]; //Array mit 3 Items wird erstellt
		for(int i=0; i < items.length;i++) {
			items[i]=false;
		}



		// Create all the rooms and link their exits together.
		ruheraum = new Room("Du bist im Ruheraum");
		aufenthaltsraum = new Room("Du hast den Aufenthaltsraum betreten");
		chemieraum = new Room("Chemieraum wurde betreten");
		lagerraum = new Room("Du befindest dich jetzt im Lager");
		kontrollraum = new Room("Du siehst viele kaputte Geräte, du befindest dich wohl im Kontrollraum");

		schluesselraum = new Room("Du siehst viele Schlüssel im Schlüsselraum");
		hauptfloor = new Room("Der Hauptfloor wurde betreten");
		escaperoom = new Room("Du bist entkommen, !!! Spiel erfolgreich abgeschlossen !!!");

		// initialise room exits    vorwärts, rechts, rückwärts, links
		ruheraum.setExits(aufenthaltsraum, null, null, null);
		aufenthaltsraum.setExits(hauptfloor, kontrollraum, ruheraum, chemieraum);
		chemieraum.setExits(null, aufenthaltsraum, lagerraum, null);
		lagerraum.setExits(chemieraum, null, null, null);
		kontrollraum.setExits(null, null, schluesselraum, aufenthaltsraum);

		schluesselraum.setExits(kontrollraum, null, null, null);
		hauptfloor.setExits(escaperoom, null, aufenthaltsraum, null);

		currentRoom = ruheraum; // start ist ruheraum
		
	}


	/**
	 *  Main play routine.  Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop.  Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Danke fürs Spielen. Bis bald !");
	}

	/**
	 * Texte die gegeben werden wenn der Spieler den Raum betritt
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Willkommen zu unserem Spiel Escape Bunker!");
		System.out.println("Deine Aufgabe wird sein aus diesem Bunker zu flüchten.");
		System.out.println("Da allerdings der Flur mit giftigem Kohlenmonoxid gefüllt ist, werden drei Komponenten benötigt.");
		System.out.println("Eine Maske, ein Schlüssel und einen O2 Sauerstofftank.");
		System.out.println("Bewege dich mit 'go' (go vorwärts, zurück, links, rechts).");
		System.out.println("Beachte dass dein Charakter immer nach Norden schaut.");
		System.out.println("Falls du Hilfe benötigst tippe 'help' im Spiel ein.");
		System.out.println("Und nun viel Spass beim spielen.");
		System.out.println();
		System.out.println(currentRoom.longDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * If this command ends the game, true is returned, otherwise false is
	 * returned.
	 */
	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("Ich weiss nicht was du meinst");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Quit, was denn ?");
			} else {
				return true; // signal that we want to quit
			}
		}
		return false;
	}

	/*
	 * implementations of user commands:
	 */ 

	/**
	* Helfer Menu
	 */
	private void printHelp() {
		System.out.println("Du brauchst 3 Komponenten um zu entkommen.");
		System.out.println("Eine Maske, ein Schlüssel und einen O2 Sauerstofftank.");
		System.out.println();
		System.out.println("Deine Kommandowörter sind: 'go' - vorwärts, zurück, link. rechts.");
		System.out.println(parser.showCommands());
	}

	/** 
	 * Try to go to one direction. If there is an exit, enter the new
	 * room, otherwise print an error message.
	 */
	private void goRoom(Command command) {
		// if there is no second word, we don't know where to go...
		if (!command.hasSecondWord()) {
			System.out.println("Go, wohin gehen ?");
		} else {
			
			String direction = command.getSecondWord();
	
			// Try to leave current room.
			Room nextRoom = currentRoom.nextRoom(direction);

			if (nextRoom == null)
				System.out.println("Du kannst nicht in diese Richtung gehen !");
			else {
				currentRoom = nextRoom;
				System.out.println(currentRoom.longDescription());
				}
			}


			switch (currentRoom.shortDescription()) {
				case "Chemieraum wurde betreten": items[0] = true;
					System.out.println("Du hast eine Maske erhalten");
				break;
				case "Du befindest dich jetzt im Lager": items[1] = true;
					System.out.println("Du hast einen O2 Tank erhalten");
					break;
				case "Du siehst viele Schlüssel im Schlüsselraum": items[2] = true;
					System.out.println("Du hast einen Schlüssel erhalten");
					break;
				case "Der Hauptfloor wurde betreten": 	if ((items[0] == true) && (items[1] == true) && (items[2] == true) ){

				}
				else  {
					System.out.println("Sorry du bist gestorben, nicht Alle Items vorhanden.");
				}


			}
		}
	}
