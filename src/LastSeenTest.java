import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Scanner;

public class LastSeenTest {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the name of the player: ");
        String name = scan.nextLine();
        System.out.print("Enter the Battlemetrics link of the player: ");
        String link = scan.nextLine();

        URL url = new URL(link);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt"));
        String line = "";
        while((line = reader.readLine()) != null) {
            writer.write(line);
        }
        reader.close();
        writer.close();

        File serverFile = new File(name + ".txt");
        Scanner readr = new Scanner(serverFile);
        String serverList = "";

        while(readr.hasNextLine()) {
            String temp = readr.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }

        if(serverList.contains("Last Seen</dt><dd><time dateTime=")) {
            //gets current time that battlemetrics is using
            java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT");
            java.util.Calendar c = java.util.Calendar.getInstance(tz);

            //gets time that the player logged off
            String sneakyBullshit = serverList.substring(serverList.indexOf("Last Seen</dt><dd><time dateTime="), serverList.indexOf("title", serverList.indexOf("Last Seen</dt><dd><time dateTime=")));
            System.out.println(sneakyBullshit);

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

            System.out.println();
            System.out.println("Log time:");
            System.out.println("month : day : hour : minute : second");
            System.out.println(logMonth + " : " + logDay + " : " + logHour + " : " + logMinute + " : " + logSecond);

            System.out.println();
            System.out.println("Current time:");
            System.out.println("month : day : hour : minute : second");
            System.out.println(currentMonth + " : " + currentDay + " : " + currentHour + " : " + currentMinute + " : " + currentSecond);

            System.out.println();

            if(logMonth==currentMonth) {
                int logSec = logDay * 86400 + logHour * 3600 + logMinute * 60 + logSecond;
                int currentSec = currentDay * 86400 + currentHour * 3600 + currentMinute * 60 + currentSecond;
                int difference = currentSec - logSec;
                if(difference > 86400) {
                    System.out.println(name + " logged out " + (difference/86400) + " days " + ((difference%86400)/3600) + " hours " + (((difference%86400)%3600)/60) + " minutes and " + (((difference%86400)%3600)%60) +
                            " seconds ago");
                } else if (86400 > difference && difference > 3600) {
                    System.out.println(name + " logged out " + (difference/3600) + " hours " + ((difference%3600)/60) + " minutes and " + ((difference%3600)%60) + " seconds ago");
                } else if (3600 > difference && difference > 60) {
                    System.out.println(name + " logged out "  + (difference/60) + " minutes and" + (difference%60) + " seconds ago");
                } else {
                    System.out.println(name + " logged out "  + difference + " seconds ago");
                }

            } else {
                System.out.println("Player has not logged on this month");
            }
        }

        else if (serverList.contains("Last Seen</dt><dd>now")) {
            System.out.println("The user is currently online");

        } else {
            System.out.println("an error occurred");
        }
        readr.close();
    }
}
