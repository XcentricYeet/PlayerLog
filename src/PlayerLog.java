/*
current goals:
1. fix unknown command bug: done, but my solution is quite stupid
2. command to add players: done
3. notifications: done-ish
    - create boolean variable and have commands that turn on/off notifications for both specific players and every player: done
    - have it check to see if a specific player is online or not every minute, and to say "<playerName> got online!" or "<playerName> got offline!" when they get on/offline: done-ish
4. ui/app thing and actual notifications on computer
5. bug: last seen only works within the same day, whoops done
6. bug: once the notifications start, they repeat every minute, but they change if the player goes offline
7. bug: cannot quit once notifications are turned on
other possible things to add
1. heli/cargo log would be cool, might only be possible on stevious tho, sadly. look into it
 */
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
public class PlayerLog {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.print("How many players would you like to add to your watchlist? ");
        int playerNum = scan.nextInt();

        ArrayList<Player> playerList = new ArrayList<>();
        for(int i=0; i < playerNum; i++) {
            System.out.print("List the name of player " + (i+1) + ": ");
            String playerName = scan.next();
            System.out.print("List the Battlemetrics link of " + playerName + ": ");
            String playerLink = scan.next();
            Player newPlayer = new Player(playerName, playerLink, false);
            playerList.add(newPlayer);
        }

        //commands list
        getCommandList();
        while(true) {
            String command = scan.nextLine(); //collects user's command
            Player cmdPlayer = new Player("", "", false); //player possibly used in command

            //checks to make sure that a player possibly in the command is an actual available player
            for (Player player : playerList) {
                if (command.contains(player.getName())) {
                    cmdPlayer = new Player(player.getName(), player.getLink(), player.isNotificationsToggle());
                }
            }

            //prints the command list
            if (command.equals("commandList();")) {
                getCommandList();
            }

            //allows the user to add more players to the watchlist without restarting the program
            else if (command.equals("addPlayers();")) {
                System.out.print("How many players would you like to add to your watchlist? ");
                playerNum = scan.nextInt();
                for(int i=0; i < playerNum; i++) {
                    System.out.print("List the name of player " + (i+1) + ": ");
                    String playerName = scan.next();
                    System.out.print("List the Battlemetrics link of " + playerName + ": ");
                    String playerLink = scan.next();
                    Player newPlayer = new Player(playerName, playerLink, false);
                    playerList.add(newPlayer);
                }
                getCommandList();
            }

            //turns on/off notifications for a specific player
            else if (command.contains(".notifications();")) {
                for (Player player : playerList) {
                    if (player.getName().equals(cmdPlayer.getName())) {
                        player.setNotificationsToggle(!player.isNotificationsToggle());
                        System.out.println("Notificaitons: " + player.isNotificationsToggle());
                    }
                }
            }

            //turns on notifications for all players, or off notifications if all notifications are already on
            else if (command.equals("allNotifications();")) {
                int falseCounter=0;
                for (Player player : playerList) {
                    if (!player.isNotificationsToggle()) {
                        falseCounter++;
                    }
                }
                if(falseCounter>0) {
                    for (Player player : playerList) {
                        if (!player.isNotificationsToggle()) {
                            player.setNotificationsToggle(true);
                        }
                    }
                } else {
                    for (Player player : playerList) {
                        player.setNotificationsToggle(false);
                    }
                }
            }

            //prints <player>.isOnline();
            else if (command.contains(".isOnline();")) {
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

                    String sneakyBullshit = cmdPlayer.lastSeen();
                    if(!sneakyBullshit.equals("")) {
                        int logMonth = Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.indexOf("-") + 1, sneakyBullshit.indexOf("-") + 3));
                        int logDay = Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.lastIndexOf("-") + 1, sneakyBullshit.lastIndexOf("-") + 3));
                        int logHour = Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.lastIndexOf("T") + 1, sneakyBullshit.indexOf(":")));
                        int logMinute = Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.indexOf(":") + 1, sneakyBullshit.lastIndexOf(":")));
                        int logSecond = Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.lastIndexOf(":") + 1, sneakyBullshit.lastIndexOf(":") + 3));

                        int currentMonth = c.get(Calendar.MONTH) + 1;
                        int currentDay = c.get(Calendar.DAY_OF_MONTH);
                        int currentHour = c.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = c.get(Calendar.MINUTE);
                        int currentSecond = c.get(Calendar.SECOND);

                        if (logMonth == currentMonth) {
                            int logSec = logDay * 86400 + logHour * 3600 + logMinute * 60 + logSecond;
                            int currentSec = currentDay * 86400 + currentHour * 3600 + currentMinute * 60 + currentSecond;
                            int difference = currentSec - logSec;
                            if (difference > 86400) {
                                System.out.println(cmdPlayer.getName() + " logged out " + (difference / 86400) + " days " + ((difference % 86400) / 3600) + " hours " + (((difference % 86400) % 3600) / 60) + " minutes and " + (((difference % 86400) % 3600) % 60) +
                                        " seconds ago");
                            } else if (86400 > difference && difference > 3600) {
                                System.out.println(cmdPlayer.getName() + " logged out " + (difference / 3600) + " hours " + ((difference % 3600) / 60) + " minutes and " + ((difference % 3600) % 60) + " seconds ago");
                            } else if (3600 > difference && difference > 60) {
                                System.out.println(cmdPlayer.getName() + " logged out " + (difference / 60) + " minutes and" + (difference % 60) + " seconds ago");
                            } else {
                                System.out.println(cmdPlayer.getName() + " logged out " + difference + " seconds ago");
                            }
                        }
                    } else {
                        System.out.println("Player has not logged on this month");
                    }
                } else {
                    System.out.println("An error occurred. Make sure you spelled the name correctly");
                }
            }

            //removes all player files and quits the program
            else if (command.contains("quit();")) { //works
                for (Player player : playerList) {
                    fileDeleter(player.getName() + ".txt");
                }
                break;
            }

            //shits retarded and would go ahead and print "Unknown command" before any commands are written but this shit fixes it
            else if (command.isEmpty()) {
                System.out.print("");
            }

            //prints "Unknown command" if the command the user entered isn't recognized
            else {
                System.out.println("Unknown command");
            }

            for (Player player : playerList) {
                if (player.isNotificationsToggle()) {
                    player.notifications();
                }
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

    //just so I dont have to update 5 things everytime i add a command
    public static void getCommandList() {
        System.out.println("Commands:\n    1. commandList();\n    2. addPlayers();\n    3. <playerName>.notifications();\n    4. allNotifications();\n    5. <playerName>.isOnline();\n" +
                "    6. <playerName>.lastSeen();\n    7. quit();");
    }
}
