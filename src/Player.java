import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player {
    //Variables
    private String name;

    //constructor
    public Player(String name) {
        setName(name);
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //returns true if player is online, returns false if they aren't
    public boolean isOnline() throws FileNotFoundException {
        File serverFile = new File("download.html");
        Scanner reader = new Scanner(serverFile);
        String serverList = "";

        while(reader.hasNextLine()) {
            String temp = reader.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }
        return serverList.contains(getName());
    }

    //returns how long a player has been online in minutes, returns 0 if they are offline
    public int timeOnline() {
        return 0;
    }

    //returns how long a player has been offline in minutes, returns 0 if they are online
    public int timeOffline() {
        return 0;
    }
}
