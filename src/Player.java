import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player3 {
    private String name;
    private String link;
    private boolean notificationsToggle;

    public Player3(String name, String link, boolean notificationsToggle) {
        setName(name);
        setLink(link);
        setNotificationsToggle(notificationsToggle);
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
    public boolean isNotificationsToggle() {
        return notificationsToggle;
    }
    public void setNotificationsToggle(boolean notificationsToggle) {
        this.notificationsToggle = notificationsToggle;
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

    public String lastSeen() throws IOException { //only works within the day which is super cringe
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

            //converts time into number of minutes
            return serverList.substring(serverList.indexOf("Last Seen</dt><dd><time dateTime="), serverList.indexOf("title", serverList.indexOf("Last Seen</dt><dd><time dateTime=")));
        } return "";
    }

    public void notifications() throws IOException {
        boolean originalStatus = isOnline();
        Runnable notifRunnable = new Runnable() {
            public void run() {
                try {
                    if(originalStatus != isOnline()) {
                        if(originalStatus) {
                            System.out.println(name + " has gone offline!");
                        } else {
                            System.out.println(name + " has gone online!");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(notifRunnable, 0, 1, TimeUnit.MINUTES);
    }
}
