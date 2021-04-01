import java.util.Random;

public class Game {

    public static final int numShips = 6;
    public static final int numGrenades = 4;

    private Coordinates[][] coordinates = new Coordinates[8][8];

    private int numCompShips;
    private int numUserShips;

    private String currentPlayer;
    private int currentNumPlays;
    private int nextNumPlays;

    //Initiate game
    public Game() {
        this.initializeArray();
    }

    //Identify player playing
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    //Number of turns for the player to play
    public int availableSteps() {
        return currentNumPlays;
    }

    //Prepare game
    public void getReady() {
        this.placeComputerPieces();
        this.numCompShips = numShips;
        this.numUserShips = numShips;
        this.currentPlayer = "user";
        this.currentNumPlays = 1;
        this.nextNumPlays = 1;
    }

    //Calls the coordinate where the rocket lands
    public Coordinates shoot(int x, int y) {
        Coordinates coordinates = this.getCell(x, y);
        Coordinates originalCoordinates = new Coordinates(coordinates);
        if (coordinates.isCalled()) { //Falls on a called coordinate = nothing happens
            --this.currentNumPlays;
        } else {
            coordinates.setCalled(true);
            if (coordinates.getType().equals("grenade")) { //Falls on a grenade = let next player play two times
                this.currentNumPlays = 0;
                this.nextNumPlays = 2;
                coordinates.setType("nothing");
                coordinates.setOwner("none");
            } else if (coordinates.getType().equals("ship")) { //Falls on a ship = ship sinks
                if (coordinates.getOwner().equals("computer")) {
                    --this.numCompShips; //Removes a ship for the computer
                } else {
                    --this.numUserShips; //Removes a ship for the user
                }
                coordinates.setType("nothing");
                coordinates.setOwner("none");
                --this.currentNumPlays;
            } else { //Falls on an empty coordinate = nothing
                --this.currentNumPlays;
            }
        }
        return originalCoordinates;

    }
    //Switch player after each turn
    public void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == "user" ? "computer" : "user");
        this.currentNumPlays = this.nextNumPlays;
        this.nextNumPlays = 1;
    }

    //Verify number of ships left
    public boolean isDone() {
        return this.numCompShips == 0 || this.numUserShips == 0;
    }

    //Find winner of the game
    public String getWinner() {
        if (this.isDone()) {
            return this.numCompShips == 0 ? "user" : "computer";
        } else {
            return "none";
        }
    }

    //Get the coordinate position
    public Coordinates getCell(int x, int y) {
        return this.coordinates[x][y];
    }

    //Check if coordinate is occupied
    public boolean isOccupied(int x, int y) {
        return !(this.coordinates[x][y].getOwner().equals("none"));
    }

    //Place coordinate into the array (battlefield)
    public void setCell(Coordinates c) {
        this.coordinates[c.getX()][c.getY()] = c;
    }

    //Initialize the array
    private void initializeArray() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.coordinates[i][j] = new Coordinates(i, j);
            }
        }
    }

    //Place computer's pieces
    private void placeComputerPieces() {
        placeCompShips();
        placeCompGrenades();
    }

    //Place computer's ships randomly
    private void placeCompShips() {
        int shipHasBeenPlaced = 0;
        while (shipHasBeenPlaced < Game.numShips) {
            int[] position = this.randomPosition();
            if (this.isOccupied(position[0], position[1])) {
            } else {
                this.setCell(new Coordinates(position[0], position[1], "ship", "computer"));
                ++shipHasBeenPlaced;
            }
        }

    }

    //Place computer's grenades randomly
    private void placeCompGrenades() {
        int grenadeHasBeenPlaced = 0;
        while (grenadeHasBeenPlaced < Game.numGrenades) {
            int[] position = this.randomPosition();
            if (this.isOccupied(position[0], position[1])) {
            } else {
                this.setCell(new Coordinates(position[0], position[1], "grenade", "computer"));
                ++grenadeHasBeenPlaced;
            }
        }
    }

    //Random number generator
    private int[] randomPosition() {
        Random r = new Random();
        int[] position = { r.nextInt(8), r.nextInt(8) };
        return position;
    }
}