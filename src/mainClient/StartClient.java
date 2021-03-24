package mainClient;

import java.util.Scanner;

public class StartClient {

    public static void main(String[] args) {

        String version = "V.0.4.0";

        System.out.println("Starte Ver. " + version);

        Scanner inputscan = new Scanner(System.in);
        System.out.println("Input Hostname");
        String hostname = inputscan.nextLine();
        System.out.println("Input Username");
        String username = inputscan.nextLine();

        GameClient spiel = new GameClient(hostname, username);
        spiel.start();

        inputscan.close();
    }
}
