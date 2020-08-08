import java.io.*;
import java.util.Scanner;
public class Test {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Paste the link of the server here: ");
        String link = scan.nextLine();
        PlayerLog.webPageDownload(link);

        File serverFile = new File("download.html");
        Scanner reader = new Scanner(serverFile);
        String serverList = "";

        while(reader.hasNextLine()) {
            String temp = reader.nextLine();
            temp = serverList + temp;
            serverList = temp;
        }

        System.out.println(serverList);
        reader.close();
        System.out.println();

        if(serverList.contains("Xcentric")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        if(serverList.contains("gorg")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
