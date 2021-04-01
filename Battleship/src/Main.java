/*
Assignment 4
Written by: Chloe Hei Yu Law 40173275
For COMP 248 Section R - Fall 2020
The purpose of this program is to create a battleship simulation game with a two-dimensional array of objects and multiple classes.
*/



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Main {

    private Game game;
    private Scanner kb = new Scanner(System.in);
    private String[][] playBoard = new String[8][8];

    //Enable a new game
    public Main() {
        game = new Game();
        this.initPlayBoard();
    }

    public static void main(String[] args) {
        Main run = new Main();
        run.start();
    }

    //Initialize the battlefield
    private void initPlayBoard() {
        for (String[] ss : this.playBoard) {
            Arrays.fill(ss, "_");
        }
    }

    //Start the game
    private void start() {
        System.out.println();
        System.out.println();
        System.out.println("Hi, let’s play Battleship!");
        System.out.println();
        System.out.println("You will have to sink all of my ships to win so be careful!");
        System.out.println("I will let you set your ships first.");
        System.out.println();
        this.placeUserPieces();// initiate user's pieces
        System.out.println("Don't forget where you have placed your ships and grenades!"); //Brief instructions of the game
        System.out.println("    1. If you hit your own ship, it will sink!");
        System.out.println("    2. If you hit any grenade, you lose a turn!");
        System.out.println();
        System.out.println("Give me a few seconds to place my pieces.");
        System.out.println();
        for(int i = 1; i < 4; i++) {
            try {
                int secondsToSleep = 1;
                Thread.sleep(secondsToSleep * 1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            System.out.println("    .");
            System.out.println();
        }
        game.getReady();// initiate computer's pieces and other necessary information.
        System.out.println();
        System.out.println("Let the battle begin!");

        ArrayList<String> calledCoord = new ArrayList<>();

        while (!game.isDone()) {// before the game is done, keep calling position
            while (game.availableSteps() != 0) {// if the current player can play more than once.
                System.out.println();
                System.out.println((game.getCurrentPlayer().equals("computer") ? "My" : "Your" ) + " turn.");
                System.out.print("Rocket coordinates: ");
                int[] position = { 0, 0 };

                //Computer's turn to play
                if (game.getCurrentPlayer().equals("computer")) {// generate call position randomly
                    Random r = new Random();
                    position[0] = r.nextInt(8);
                    char firstLetter = (char) (position[0] + (int) ('A'));
                    position[1] = r.nextInt(8);
                    System.out.println(firstLetter + "" + (position[1] + 1));
                    String coordinate = charToString(firstLetter, position[1]+1);
                    calledCoord.add((coordinate.toUpperCase())); //Add the used coordinates to the array list

                    //User's turn to play
                } else {
                    String positionStr = kb.nextLine();
                    char[] chars = positionStr.toUpperCase().toCharArray();
                    if (positionStr.length() != 2 ){
                        System.out.println("Sorry, the coordinates are outside the grid. Try again.");
                        continue;
                    }else if(this.isOutOfBounds(chars)){
                        System.out.println("Sorry, the coordinates are outside the grid. Try again.");
                        continue;
                    }
                    position = charToInt(positionStr.toUpperCase().toCharArray());
                    calledCoord.add(positionStr.toUpperCase()); //Add the user's coordinates to the array list
                }

                //Checks where the rocket has landed
                Coordinates originalCoordinates = game.shoot(position[0], position[1]);

                //If a rocket has not landed on this coordinate yet
                if (!originalCoordinates.isCalled()) {
                    switch (originalCoordinates.getType()) {

                        //If it lands on a ship
                        case "ship":
                            this.playBoard[position[0]][position[1]] = originalCoordinates.getOwner().equals("computer") ? "S" : "s";
                            System.out.println((game.getCurrentPlayer().equals("computer") ? "I" : "You" ) + " hit " + (originalCoordinates.getOwner().equals("computer") ? "my" : "your") + " ship.");
                            //System.out.println("Hit a ship!");
                            System.out.println();
                            break;

                        //If it lands on a grenade
                        case "grenade":
                            this.playBoard[position[0]][position[1]] = originalCoordinates.getOwner().equals("computer") ? "G" : "g";
                            System.out.println((game.getCurrentPlayer().equals("computer") ? "I" : "You" ) + " hit " + (originalCoordinates.getOwner().equals("computer") ? "my" : "your") + " grenade.");
                            //System.out.println("Boom! A grenade was hit!");
                            System.out.println();
                            break;

                        //If it lands on nothing
                        case "nothing":
                            this.playBoard[position[0]][position[1]] = "*";
                            System.out.println((game.getCurrentPlayer().equals("computer") ? "I" : "You" ) + " missed.");
                            //System.out.println("Missed!");
                            System.out.println();
                            break;
                    }
                } else {
                    System.out.println("This position was already called!");
                    System.out.println();
                }
                if (!game.isDone()) {
                    System.out.println("The called positions are: " + Arrays.toString(calledCoord.toArray())); //Called positions
                    System.out.println();
                    System.out.println("******** BATTLEFIELD ********");
                    this.printCurrentStates();
                    System.out.println();
                }
            }
            game.switchPlayer();

        }
        System.out.println("******** BATTLEFIELD ********");
        this.printCurrentStates();
        System.out.println();

        for(int i = 1; i < 4; i++) {
            try {
                int secondsToSleep = 1;
                Thread.sleep(secondsToSleep * 1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            System.out.println("    .");
            System.out.println();
        }

        System.out.println((game.getWinner().equals("computer") ? "I" : "You") + " win!"); //Declare winner
        System.out.println((game.getWinner().equals("computer") ? "Better luck beating a computer next time!" : "I will be smarter next time!")); //Ending message
        this.kb.close();
    }

    private void placeUserPieces() {
        this.placeShips();
        this.placeGrenades();
    }

    //Ask for user's ship coordinates
    private void placeShips() {
        int generated = 0;
        ArrayList<String> ships = new ArrayList<>();
        System.out.println("Please enter the coordinates of your six ships please!");
        while (generated < Game.numShips) {
            System.out.print("Ship number " + (++generated) + ": ");
            String positionStr = kb.nextLine();
            char[] chars = positionStr.toUpperCase().toCharArray();
            if (positionStr.length()!= 2) { //Verify two characters coordinates
                System.out.println("Sorry, the coordinates are outside the grid. Try again.");
                --generated;
            }else if (this.isOutOfBounds(chars)){ //Verify coordinates in range
                System.out.println("Sorry, the coordinates are outside the grid. Try again.");
                --generated;
            } else if (this.isOccupied(chars)) { //Verify coordinates not used previously
                System.out.println("Sorry, coordinates already used. Try again.");
                --generated;
            } else {
                int[] position = charToInt(chars);
                game.setCell(new Coordinates(position[0], position[1], "ship", "user")); //Place the ship on the field accordingly
                ships.add(positionStr.toUpperCase()); //Add the ship coordinates to the array list
            }
        }
        System.out.println("The position of your ships are: " + Arrays.toString(ships.toArray())); //Player's ship position
        System.out.println();
    }

    //Ask for user's grenade coordinates
    private void placeGrenades() {
        int generated = 0;
        ArrayList<String> grenades = new ArrayList<>();
        System.out.println("Please enter the coordinates of your four grenades please!");
        while (generated < Game.numGrenades) {
            System.out.print("Grenade number " + (++generated) + ": ");
            String positionStr = kb.nextLine();
            char[] chars = positionStr.toUpperCase().toCharArray();
            if (positionStr.length()!= 2) { //Verify two characters coordinates
                System.out.println("Sorry, the coordinates are outside the grid. Try again.");
                --generated;
                continue;
            }else if (this.isOutOfBounds(chars)){ //Verify coordinates in range
                System.out.println("Sorry, the coordinates are outside the grid. Try again.");
                --generated;
                continue;
            } else if (this.isOccupied(chars)) { //Verify coordinates not used previously
                System.out.println("Sorry, coordinates already used. Try again.");
                --generated;
                continue;
            } else {
                int[] position = charToInt(chars);
                game.setCell(new Coordinates(position[0], position[1], "grenade", "user")); //Place the grenades on the field accordingly
                grenades.add(positionStr.toUpperCase()); //Add the grenade coordinates to the array list
            }
        }
        System.out.println("The position of your grenades are: " + Arrays.toString(grenades.toArray())); //Player's ship position
        System.out.println();
    }
    //Check whether the input is out of range.
    private boolean isOutOfBounds(char[] chars) {
        if ('A' <= chars[0] && chars[0] <= 'H' && 1 <= Character.getNumericValue(chars[1]) && Character.getNumericValue(chars[1]) <= 8) {
            return false;
        } else {
            return true;
        }
    }

    //Check whether the target position is occupied.
    private boolean isOccupied(char[] chars) {
        int[] position = charToInt(chars);
        return game.isOccupied(position[0], position[1]);
    }

    //Convert char to string and return a string of the coordinate
    private String charToString(char x, int y){
        String coordX = Character.toString(x);
        String coordY = Integer.toString(y);
        return coordX + coordY;
    }

    //Convert input char to integer
    private int[] charToInt(char[] chars) {
        int x = (int) (chars[0]) - (int) ('A');
        int y = Character.getNumericValue(chars[1]) - 1;
        return new int[] { x, y };
    }

    //Print out the current grid.
    private void printCurrentStates() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(playBoard[i][j] + "\t");
            }
            System.out.println();
        }
    }
}