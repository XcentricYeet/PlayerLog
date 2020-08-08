import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player2 {
    //Variables
    private String name;
    private String link;
    private int fileNum;

    //constructor
    public Player2(String name, String link, int linkNum) {
        setName(name);
        setLink(link);
        setFileNum(linkNum);
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
    public int getFileNum() {
        return fileNum;
    }
    public void setFileNum(int linkNum) {
        this.fileNum = linkNum;
    }

    //returns true if player is online, returns false if they aren't
    public boolean isOnline() throws FileNotFoundException {
        File serverFile = new File("download" + getFileNum() + ".html");
        Scanner reader = new Scanner(serverFile);
        String serverList = "";

        while(reader.hasNextLine()) {
            String temp = reader.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }
        return serverList.contains("now");
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
