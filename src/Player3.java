import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class Player3 {
    private String name;
    private String link;

    public Player3(String name, String link) {
        setName(name);
        setLink(link);
    }

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

    public boolean isOnline() throws IOException {
        PlayerLog3.webPageDownloader(name, link);
        File serverFile = new File(name + ".txt");
        Scanner readr = new Scanner(serverFile);
        String serverList = "";

        while(readr.hasNextLine()) {
            String temp = readr.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }
        readr.close();
        return !serverList.contains("Not online");
    }

    public int lastSeen() throws IOException {
        PlayerLog3.webPageDownloader(name, link);
        File serverFile = new File(name + ".txt");
        Scanner readr = new Scanner(serverFile);
        String serverList = "";

        while(readr.hasNextLine()) {
            String temp = readr.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }
        readr.close();

        if(serverList.contains("Last Seen</dt><dd><time dateTime=")) {
            //gets time that the player logged off
            String sneakyBullshit = serverList.substring(serverList.indexOf("Last Seen</dt><dd><time dateTime="), serverList.indexOf("title", serverList.indexOf("Last Seen</dt><dd><time dateTime=")));

            //converts time into number of minutes
            return Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.indexOf(":") - 2, sneakyBullshit.indexOf(":"))) * 60 + Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.indexOf(":") + 1,
                    sneakyBullshit.indexOf(":") + 3));
        } return 0;
    }
}
