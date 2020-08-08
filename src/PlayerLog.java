import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.URL;
//import java.net.MalformedURLException;
public class PlayerLog {
    public static void main(String[] args) throws IOException {
        //variables
        Scanner scan = new Scanner(System.in);
        int tabsNum = 0;
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        String serverName = "";
        boolean notifications = true;

        //collects the name of the server that the user is playing on
        System.out.print("Paste the link of the server here: ");
        String serverLink = scan.nextLine();
        webPageDownload(serverLink);

        //collects the number of players
        System.out.println("How many people do you want to keep tabs on?");
        tabsNum = scan.nextInt();
        scan.nextLine();

        //collects the name of the players
        for(int i=0; i < tabsNum; i++) {
            //spelling warning only on first player
            if(i==0) {
                System.out.println("List the name of the " + (i + 1) + "st player (Make sure to include correct capitalization, spaces, etc.)");
            }
            else {
                System.out.println("List the name of the " + (i + 1) + "th player");
            }
            playerNames.add(scan.nextLine());
        }

        //makes an ArrayList filled with Player variables given the names from earlier
        for(int i=0; i < playerNames.size(); i++) {
            Player temp = new Player(playerNames.get(i));
            players.add(temp);
        }

        //prints a list of commands that the user can use
        System.out.println("Notifications: true");
        System.out.println("Commands:\n1. <playerName>.isOnline(); (returns true if player is online)\n2. <playerName>.timeOnline(); (returns time in minutes a player has been online, returns 0 if they are offline)" +
                "\n3. <playerName>.timeOffline(); (returns time in minutes a player has been offline, returns 0 if they are online)\n4. notifications(toggle); (turns notifications on/off)\n5. quit(); (ends the session)");

        //checks for commands until the user quits
        while(true) {
            String command = scan.nextLine(); //collects user's command
            Player cmdPlayer = new Player(""); //player possibly used in command

            //checks to make sure that a player possibly in the command is an actual available player
            for (Player player : players) { //
                if (command.contains(player.getName())) {
                    cmdPlayer = new Player(player.getName());
                }
            }

            //prints <player>.isOnline();
            if(command.contains(".isOnline();")) { //working
                if(!cmdPlayer.getName().equals("")) {
                    System.out.println(cmdPlayer.isOnline());
                } else {
                    System.out.println("Invalid name");
                }
            }

            //prints <player>.timeOnline();
            else if(command.contains(".timeOnline();")) { //not working
                if(!cmdPlayer.getName().equals("")) {
                    System.out.println(cmdPlayer.timeOnline());
                } else {
                    System.out.println("Invalid name");
                }
            }

            //prints <player>.timeOffline();
            else if(command.contains(".timeOffline();")) { //not working
                if(!cmdPlayer.getName().equals("")) {
                    System.out.println(cmdPlayer.timeOffline());
                } else {
                    System.out.println("Invalid name");
                }
            }

            //turns on/off notifications
            else if(command.equals("notifications(toggle);")) { //toggles, notifications not set up
                notifications = !notifications;
                System.out.println("Notifications: " + notifications);
            }

            //breaks loop
            else if(command.contains("quit();")) { //works
                break;
            }

            //prints "Unknown command" if the command the user entered isn't recognized
            else {
                System.out.println("Unknown command");
            }
        }

        //-=-=-=-=-=-=-=-=-=-=-=-   TESTING AREA    -=-=-=-=-=-=-=-=-=-=-=-
    }

    public static void webPageDownload(String link) throws IOException {
        URL url = new URL(link);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter writer = new BufferedWriter(new FileWriter("Download.html"));
        String line = "";
        while((line = reader.readLine()) != null) {
            writer.write(line);
        }

        reader.close();
        writer.close();
        System.out.println("Downloaded"); //delete
    }
}
