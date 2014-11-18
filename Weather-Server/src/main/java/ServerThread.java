import weather.OpenWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket client = null;
    PrintWriter output;
    BufferedReader input;
    OpenWeather weather;

    public ServerThread(Socket client, OpenWeather weather) {
        this.client = client;
        this.weather = weather;
    }


    @Override
    public void run() {

        try {

            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String command;

            while (true) {

                command = null;
                if ((command = input.readLine()) != null) {
                    executeCommand(command);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMessage(String message) {

        output.println(message + "\n \n" + "END_OF_MESSAGE");
    }

    private void executeCommand(String command) {
        int inputInt = Integer.parseInt(command);

        switch (inputInt) {
            case 1:
                sendMessage(weather.getWeather());
                break;
        }

    }
}
