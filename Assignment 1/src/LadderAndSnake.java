import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class is responsible for containing the game players and to enable
 * the game to start when proper inputs were given
 */
public class LadderAndSnake {
    /**
     * The number of players for the game
     */
    private final int Players;

    /**
     * This connects the main class (PlayLadderAndSnake) with this class (LadderAndSnake)
     * @param p the number of players for the game
     */
    public LadderAndSnake(int p) {
        Players = p;
    }

    /**
     * This method generates a random dice number between 1-6 inclusively
     * @return the dice rolled number
     */
    private int flipDice() {
        int dice;
        dice = (int) (Math.random() * 6) + 1;
        return dice;
    }

    /**
     * This initiates the game by sorting the players in their respective order
     * Once the players are in order, they will roll a dice to determine their next position on the board
     */
    public void play() {
        System.out.println();
        System.out.println("Let's decide the playing order with a roll of a dice!");

        int[] diceRoll = new int[Players];
        ArrayList<Integer> list = new ArrayList<>();
        int dice = 6;
        for(int i = 1; i < dice +1; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        //System.out.println(list);
        for(int i = 0; i < Players; i++){
            diceRoll[i] = list.get(i);
        }

        for(int i = 0; i < Players; i++){
            System.out.println("Player " + (i+1) + " rolled a " + diceRoll[i] + "." );
        }
        System.out.println("Players rolled" + Arrays.toString(diceRoll));
        System.out.println();

        System.out.println("Let me organize the players in order.");
        timeLag();

        int[] playingOrder = this.order(diceRoll);
        System.out.println("Playing order" + Arrays.toString(playingOrder));

        timeLag();
        System.out.println("""
                
                We are finally ready to play the game!\s
                Ladders help you move forward faster.\s
                Snakes will bring you down.\s
                Everyone will start outside the board.\s
                If you move past square 100, you will have to go back. \s
                Good luck!
                """);

        timeLag();

        int[] position = new int[Players];
        Arrays.fill(position, 0); //sets every player's position to zero = outside of the board

        int[] snakesAndLadder = new int[17]; //sets the initial position of the head of the snake and the bottom of the ladder
        //9 ladders
        snakesAndLadder[0] = 1;
        snakesAndLadder[2] = 4;
        snakesAndLadder[3] = 9;
        snakesAndLadder[4] = 21;
        snakesAndLadder[5] = 28;
        snakesAndLadder[5] = 36;
        snakesAndLadder[6] = 51;
        snakesAndLadder[7] = 71;
        snakesAndLadder[8] = 80;

        //8 snakes
        snakesAndLadder[9] = 98;
        snakesAndLadder[10] = 97;
        snakesAndLadder[11] = 95;
        snakesAndLadder[12] = 93;
        snakesAndLadder[13] = 79;
        snakesAndLadder[14] = 64;
        snakesAndLadder[15] = 48;
        snakesAndLadder[16] = 16;

        /*This loop checks if the game is over or not
        If the game is not over, the next player will roll the dice
        If the player lands on a snake or ladder, they will move on a different position than their initial rolled number
        If the player lands on a position above 100, they will move backwards
        */
        boolean gameOver = false;
        while(!gameOver) {
            System.out.println("----------------------");
            System.out.println();
            for (int i = 0; i < Players; i++) {
                int roll = flipDice();
                position[i] += roll;
                int newPos = checkSnakesLadder(position[i], snakesAndLadder);

                if (position[i] == 100) {
                    System.out.println();
                    System.out.println("GAME OVER");
                    timeLag();
                    System.out.println();
                    System.out.println("Player " + (playingOrder[i]) + " has landed on square " + position[i] + " and won the game! \n" +
                            "Thank you for trying this game, let's play another time!");
                    gameOver = true;
                    break;
                }
                if (position[i] < 100) {
                    if (position[i] == newPos) {
                        System.out.println("Player " + (playingOrder[i]) + " has rolled " + roll + " → now in square " + position[i]);
                    } else {
                        System.out.println("Player " + (playingOrder[i]) + " has rolled " + roll + " → now in square " + position[i] + " → move to square " + newPos);
                        position[i] = newPos;
                    }
                } else {
                    int returnPos = position[i] - 100;
                    System.out.println("Player " + (playingOrder[i]) + " has exceeded the board and has to go backwards. \n" +
                            "   Currently in square " + returnPos);
                    position[i] = returnPos;
                }
                gameOver = false;
            }
            if (!gameOver) {
                System.out.println("Game not over yet.");
                System.out.println();
            }
        }
    }

    /**
     * This method manages the movements of the player when they land on a ladder or a snake
     * @param currentPos takes the position of the player
     * @param snakesAndLadder takes the position of the snake or ladder
     * @return the new position of the player
     */
    public int checkSnakesLadder(int currentPos, int[] snakesAndLadder){
        if(currentPos == snakesAndLadder[0]){
            currentPos = 38;
        }else if(currentPos == snakesAndLadder[1]){
            currentPos = 14;
        }else if(currentPos == snakesAndLadder[2]) {
            currentPos = 31;
        }else if(currentPos == snakesAndLadder[3]) {
            currentPos = 42;
        }else if(currentPos == snakesAndLadder[4]) {
            currentPos = 84;
        }else if(currentPos == snakesAndLadder[5]) {
            currentPos = 44;
        }else if(currentPos == snakesAndLadder[6]) {
            currentPos = 67;
        }else if(currentPos == snakesAndLadder[7]) {
            currentPos = 91;
        }else if(currentPos == snakesAndLadder[8]) {
            currentPos = 100;
        }else if(currentPos == snakesAndLadder[9]) {
            currentPos = 78;
        }else if(currentPos == snakesAndLadder[10]) {
            currentPos = 76;
        }else if(currentPos == snakesAndLadder[11]) {
            currentPos = 24;
        }else if(currentPos == snakesAndLadder[12]) {
            currentPos = 68;
        }else if(currentPos == snakesAndLadder[13]) {
            currentPos = 19;
        }else if(currentPos == snakesAndLadder[14]) {
            currentPos = 60;
        }else if(currentPos == snakesAndLadder[15]) {
            currentPos = 30;
        }else if(currentPos == snakesAndLadder[16]) {
            currentPos = 6;
        }
        return currentPos;
    }

    /**
     * This method orders the rolls made by players initially to determine their playing order
     * @param diceRoll the array of dice roll that each player rolled, the index is the player's name
     * @return a new array which gives the order that they player will play the game
     */
    private int[] order(int[] diceRoll){
        int[] finalPlayingOrder = new int[Players];

        int[] copyDiceRoll = Arrays.copyOf(diceRoll,Players);
        Arrays.sort(diceRoll);

        for(int i = 0; i < Players; i++){
            finalPlayingOrder[i] = findIndex(copyDiceRoll, reverse(diceRoll)[i])+1;
        }

        for(int i = 0; i < Players; i++){
            System.out.println("Dice roll number " + (i+1) + " goes to player " + finalPlayingOrder[i]  + ".");
        }
        return finalPlayingOrder;
    }

    /**
     * This method finds the index of the dice roll number according to which player rolled it
     * @param diceRoll the array of all the diceRoll results of the players
     * @param rollOrder the roll number we are trying to find the index of
     * @return the index of the dice roll for the player
     */
    private int findIndex(int[] diceRoll, int rollOrder){
        int i = 0;
        while(i < Players){
            if(diceRoll[i] == rollOrder){
                return i;
            }else{
                i = i + 1;
            }
        }
        return -1;
    }

    /**
     * This method reverses the array
     * @param sortedArray takes this array that needs to be reversed
     * @return a new array that is reversed from the sortedArray
     */
    private int[] reverse(int[] sortedArray){
        int[] reversedArray = new int[Players];
        int j = Players;
        for(int i = 0; i < Players; i++){
            reversedArray[j - 1] = sortedArray[i];
            j = j - 1;
        }
        return reversedArray;
    }

    /**
     * A method that simply slows down the game play to let the user to visualize the game
     */
    static void timeLag(){
        for(int i = 1; i < 4; i++) {
            try {
                int secondsToSleep = 1;
                Thread.sleep(secondsToSleep * 700);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
