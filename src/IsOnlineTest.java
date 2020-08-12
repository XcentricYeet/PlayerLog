import java.io.*;
import java.net.URL;
import java.util.Scanner;
public class IsOnlineTest {
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

        boolean testFinal = !serverList.contains("Not online");
        System.out.println(testFinal);
    }
}
