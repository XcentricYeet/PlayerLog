import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
public class PlayerLog3 {
    public static  void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.print("How many players would you like to add to your watchlist? ");
        int playerNum = scan.nextInt();

        ArrayList<Player3> playerList = new ArrayList<>();
        for(int i=0; i < playerNum; i++) {
            System.out.print("List the name of the player " + (i+1) + ": ");
            String playerName = scan.next();
            System.out.print("List the Battlemetrics link of " + playerName + ": ");
            String playerLink = scan.next();
            Player3 newPlayer = new Player3(playerName, playerLink);
            playerList.add(newPlayer);
        }

        //commands list
        System.out.println("Commands:\n    1. commandList();\n    2. <playerName>.isOnline();\n    3. <playerName>.lastSeen();\n    4. quit();");

        while(true) {
            String command = scan.nextLine(); //collects user's command
            Player3 cmdPlayer = new Player3("", ""); //player possibly used in command

            //checks to make sure that a player possibly in the command is an actual available player
            for (Player3 player : playerList) {
                if (command.contains(player.getName())) {
                    cmdPlayer = new Player3(player.getName(), player.getLink());
                }
            }

            //prints the command list
            if(command.equals("commandList();")) {
                System.out.println("Commands:\n    1. commandList();\n    2. <playerName>.isOnline();\n    3. <playerName>.lastSeen();\n    4. quit();");
            }

            //prints <player>.isOnline();
            else if (command.contains(".isOnline();")) { //working
                if (!cmdPlayer.getName().equals("")) {
                    System.out.println(cmdPlayer.isOnline());
                } else {
                    System.out.println("An error occurred. Make sure you spelled the name correctly");
                }
            }

            //prints <player>.lastSeen();
            else if (command.contains(".lastSeen();")) {
                if (!cmdPlayer.getName().equals("")) {
                    //gets current time that battlemetrics is using
                    java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT");
                    java.util.Calendar c = java.util.Calendar.getInstance(tz);

                    //converts to minutes
                    int currentTimeInMin = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(java.util.Calendar.MINUTE); //Battlemetrics time
                    int logTimeInMin = cmdPlayer.lastSeen();

                    //subtracts logTimeInMin from currentTimeInMin to find out how long the player was last online if the time offline is > 0
                    if (logTimeInMin > 0) {
                        int timeOffline = currentTimeInMin - logTimeInMin;
                        System.out.println(cmdPlayer.getName() + " got offline " + timeOffline / 60 + " hours and " + timeOffline % 60 + " minutes ago");
                    //if time offline <= 0, player is online
                    } else {
                        System.out.println("The player is currently online");
                    }
                } else {
                    System.out.println("An error occurred. Make sure you spelled the name correctly");
                }
            }

            //removes all player files and quits the program
            else if (command.contains("quit();")) { //works
                for(int i=0; i < playerList.size(); i++) {
                    fileDeleter(playerList.get(i).getName() + ".txt");
                }
                break;
            }

            //prints "Unknown command" if the command the user entered isn't recognized
            else {
                System.out.println("Unknown command");
            }
        }
    }

    //downloads webpage code into a .txt file
    public static void webPageDownloader(String name, String link) throws IOException {
        URL url = new URL(link);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt"));
        String line = "";
        while((line = reader.readLine()) != null) {
            writer.write(line);
        }
        reader.close();
        writer.close();
    }

    //deletes file
    public static void fileDeleter(String fileName) throws IOException {
        Files.deleteIfExists(Path.of(fileName));
    }
}
