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

        try {

            Socket socket = new Socket(hostName, 15432);

            System.out.println("Establishing connection.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String outputString;
                    while (true) {
                        try {
                            while (((outputString = input.readLine()) != null) && (!outputString.equals("END_OF_MESSAGE"))) {
                                System.out.println(outputString);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            printThread.start();

            while (true) {

                int menuSelection;
                menuSelection = mainMenu();
                out.print(menuSelection);
                out.println();

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
        // finally, close the socket and decrement runningThreads
        finally {
/*
            try {
                socket.close();
                System.out.flush();
            }
            catch (IOException e ) {
                System.out.println("Couldn't close socket");
            }
            */
        }

    }

    public static int mainMenu() {
        int menuSelection = 0;
        // loop (and prompt again) until the user's input is an integer between 1 and 8
        while ((menuSelection <= 0) || (menuSelection > 8)) {
            System.out.println("The menu provides the following choices to the user: ");
            System.out.println("1. Host current Date and Weather \n2. Quit ");
            System.out.print("Please provide number corresponding to the action you want to be performed: \n");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextInt()) menuSelection = sc.nextInt();
        }
        return menuSelection;
    }


}
