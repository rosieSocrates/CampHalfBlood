
/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    March 2000
 * 
 * Modified by: April Schermann
 * Date:        October 2018
 * 
 * Modified by: Amy Feeney
 * Date:        November 2018
 * 
 *  This class is the main class of the "Zork" application. Zork is a very
 *  simple, text based adventure game.  Users can walk around some scenery.
 *  That's all. It should really be extended to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  routine.
 * 
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates the
 *  commands that the parser returns.
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room startingGate;
    private Room campHalfBlood;
    private Room thaliasTree;
    private Room theGraySistersTaxi;
    private Room argoTwo;
    private Room calypsosIsland;
    private Room festus;
    private Room campJupiter;
    
        

    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room startingGate, campHalfBlood, thaliasTree, theGraySistersTaxi; 
        Room argoTwo, calypsosIsland, festus, campJupiter ;
      
        // create the rooms
        startingGate = new Room("The Main Gate at Camp Half Blood");
        campHalfBlood = new Room("Camp Half Blood in Long Island");
        thaliasTree = new Room("Thalia's Tree");
        theGraySistersTaxi = new Room("Board Taxi");
        argoTwo = new Room("Board Argo 2");
        calypsosIsland = new Room("Calypo's Island");
        festus = new Room("Festus");
        campJupiter = new Room("Camp Jupiter");
        
        // initialise room exits
        startingGate.setExits(null, campHalfBlood, null, null);
        campHalfBlood.setExits(null, null, thaliasTree, null);
        thaliasTree.setExits(null, null, null, theGraySistersTaxi);
        theGraySistersTaxi.setExits(argoTwo, null, null, null);
        argoTwo.setExits(null, calypsosIsland, null, null);
        calypsosIsland.setExits(null, null, festus, null);
        festus.setExits(null, null, null, campJupiter);

        currentRoom = startingGate;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Goodbye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Camp Half Blood!");
        System.out.println("In this game, you will assist the demigods to get the fleece to the other camp.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.longDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        if(command.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
        {
            if(command.hasSecondWord())
                System.out.println("Quit what?");
            else
                return true;  // signal that we want to quit
        }
        return false;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around Normal Community High School.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.nextRoom(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else 
        {
            currentRoom = nextRoom;
            System.out.println(currentRoom.longDescription());
        }
    }
}