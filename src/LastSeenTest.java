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

            //converts time into number of minutes
            int currentTimeInMin = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(java.util.Calendar.MINUTE); //Battlemetrics time
            int logTimeInMin = Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.indexOf(":")-2, sneakyBullshit.indexOf(":"))) * 60 + Integer.parseInt(sneakyBullshit.substring(sneakyBullshit.indexOf(":")+1,
                    sneakyBullshit.indexOf(":")+3));
            System.out.println("Current: " + currentTimeInMin);
            System.out.println("Log: " + logTimeInMin);

            //subtracts logTimeInMin from currentTimeInMin to find out how long the player was last online
            int timeOffline = currentTimeInMin - logTimeInMin;
            System.out.println(name + " got offline " + timeOffline/60 + " hours and " + timeOffline%60 + " minutes ago");
        }

        else if (serverList.contains("Last Seen</dt><dd>now")) {
            System.out.println("The user is currently online");

        } else {
            System.out.println("an error occurred");
        }
        readr.close();
    }
}
