import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("User did not enter a host name. Client program exiting.");
            System.exit(1);
        }

        String hostName = args[0];

        mainMenu(hostName);
    }

    public static void connect(String hostName) {
        try {
            Socket socket = new Socket(hostName, 15432);
            boolean run = true;

            System.out.println("Establishing connection.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintThread pt = new PrintThread(input);
            pt.start();

            while (run) {

                int menuSelection;
                menuSelection = clientMenu();
                out.print(menuSelection);
                out.println();

                if (menuSelection == 3) {
                    run = false;
                    pt.stopThread();
                    input.close();
                    out.close();
                    socket.close();
                    System.out.println("Disconnected from server");
                    mainMenu(hostName);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostName);
            System.exit(1);
        } catch (ConnectException e) {
            System.err.println("Connection refused by host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int clientMenu() {
        int menuSelection = 0;

        while ((menuSelection <= 0) || (menuSelection > 3)) {
            System.out.println("1. Host current time and weather \n2. Server statistics\n3. Quit ");
            System.out.print("Please provide number corresponding to the action you want to be performed: \n");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextInt()) menuSelection = sc.nextInt();
        }
        return menuSelection;
    }

    public static void mainMenu(String hostName) {

        int menuSelection = 0;

        while ((menuSelection <= 0) || (menuSelection > 2)) {
            System.out.println("1. Connect to server \n2. Quit ");
            System.out.print("Please provide number corresponding to the action you want to be performed: \n");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextInt()) menuSelection = sc.nextInt();
        }

        switch (menuSelection) {
            case 1:
                connect(hostName);
                break;
            case 2:
                System.exit(1);
                break;
        }
    }

}
