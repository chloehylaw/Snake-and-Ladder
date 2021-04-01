public class Coordinates {

    private final int x;
    private final int y;
    private boolean called;

    private String type;
    private String owner;

    //Coordinate position
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = "nothing";
        this.owner = "none";
        this.called = false;
    }

    //Type and owner of the piece on the coordinate position
    public Coordinates(int x, int y, String type, String owner) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.owner = owner;
        this.called = false;
    }

    //Copy the initialized position
    public Coordinates(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
        this.type = coordinates.type;
        this.owner = coordinates.owner;
        this.called = coordinates.called;
    }

    //Get type of piece (ship, grenade, nothing)
    public String getType() {
        return type;
    }

    //Set the type of piece (ship, grenade, nothing)
    public void setType(String type) {
        this.type = type;
    }

    //Get the owner of the position (player, computer, none)
    public String getOwner() {
        return owner;
    }

    //Set the owner of the piece (player, computer, none)
    public void setOwner(String owner) {
        this.owner = owner;
    }

    //Determine if position was previously called or not
    public boolean isCalled() {
        return called;
    }

    //Set if its called or not
    public void setCalled(boolean called) {
        this.called = called;
    }

    //Get the position in x
    public int getX() {
        return x;
    }

    //Get the position in y
    public int getY() {
        return y;
    }
}

