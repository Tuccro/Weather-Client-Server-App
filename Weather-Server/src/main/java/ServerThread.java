import utils.Utils;
import weather.OpenWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;

public class ServerThread extends Thread {

    Socket client = null;
    PrintWriter output;
    BufferedReader input;
    OpenWeather weather;

    private boolean run = true;
    private long startTime;


    public ServerThread(Socket client, OpenWeather weather) {
        this.client = client;
        this.weather = weather;
    }


    @Override
    public void run() {

        startTime = System.currentTimeMillis();
        try {

            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String command;

            while (run) {

                if (input.ready()) {
                    if ((command = input.readLine()) != null) {
                        executeCommand(command);
                    }
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
                sendMessage(getTime() + weather.getWeather());
                break;
            case 2:
                sendMessage(checkServer());
                break;
            case 3:
                stopThread();
                break;
        }
    }

    private String getTime() {
        Time time = new Time(System.currentTimeMillis());
        return "\nTime on server: " + time.toString() + "\n";
    }

    public void stopThread() {
        try {
            System.out.println("Thread stopped");
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            run = false;
        }
    }

    private String checkServer() {
        long upTime = System.currentTimeMillis() - startTime;
        return "\nServer running: " + (int) upTime / 60000 + " minutes and is ready for commands.\n" +
                "Access to weather API server: " + Utils.checkInternetConnection();
    }
}
