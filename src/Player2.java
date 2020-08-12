import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Player2 {
    //Variables
    private String name;
    private String link;

    //constructor
    public Player2(String name, String link) {
        setName(name);
        setLink(link);
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    //returns true if player is online, returns false if they aren't
    public boolean isOnline() throws IOException {
        PlayerLog2.webPageDownload(getLink());
        File serverFile = new File("download" + (PlayerLog2.getFileCount()-1) + ".html");
        Scanner reader = new Scanner(serverFile);
        String serverList = "";

        while(reader.hasNextLine()) {
            String temp = reader.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }
        boolean x = serverList.contains("now");
        reader.close();
        return x;
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
