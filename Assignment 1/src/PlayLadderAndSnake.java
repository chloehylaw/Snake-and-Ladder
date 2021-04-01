/**
 * Name and ID: Chloe Hei Yu law - 40173275
 * COMP 249
 * Assignment: 1
 * Due Date: Feb 8, 2020
 *
 * The purpose of this program is to create a snake and ladder game where the user can select the number of players to play with.
 */

import java.util.Scanner;

/**
 * Example program that runs the game snake and ladder
 */
public class PlayLadderAndSnake {

    /**
     * A constructor
     */
    public PlayLadderAndSnake(){

    }

    /**
     * Starts the game snake and ladder
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PlayLadderAndSnake run = new PlayLadderAndSnake();
        run.start();
    }

    /**
     * A method that asks the user's input on the number of players to play against which will prompt the program to start the game.
     */
    private void start(){
        Scanner kb = new Scanner(System.in);
        String numPlayers;
        int attempts = 4;
        int counter = 0;

        System.out.println("My name is Chloe and please enjoy my game. \n" +
                "\n " +
                "Snake and Ladder is a game played between two to four players. \n" +
                "Please enter the number of players for your game. \n" +
                "It has to be a number between 2 and 4 inclusively");

        //This loop makes sures that the user input is not a letter or an integer other than ones between 2-4
        while (counter < attempts) {
            numPlayers = kb.next();
            if (!checkNumber(numPlayers)) {
                counter++;
                System.out.println(counter + " wrong attempt, please enter a correct number of players.");
            } else {
                int players = Integer.parseInt(numPlayers);

                if (players < 2 || players > 4) {
                    if (counter < 2) {
                        counter++;
                        System.out.println(counter + " wrong attempt, please enter a correct number of players.");
                    } else if (counter == 2) {
                        counter++;
                        System.out.println("This is your last attempt, please enter a correct number of players");
                    } else {
                        counter++;
                        System.out.println("You have no more attempts left. The game will not start.");
                    }
                } else {
                    System.out.println("You have selected to play a game between " + numPlayers + " players.");
                    LadderAndSnake game = new LadderAndSnake(players);
                    game.play();
                }
            }
        }

    }


    /**
     * Method to check if the user input is an integer or not
     * Return false if the input is not a number
     * Return true if the input is a number
     * @param numPlayers the user input value for the number of players
     * @return if the user input was a number or not
     */
    static boolean checkNumber(String numPlayers){
        try {
            Integer.parseInt(numPlayers);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}

