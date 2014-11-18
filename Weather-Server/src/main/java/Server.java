import weather.OpenWeather;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    public static void main(String[] args) {
        AtomicInteger numThreads = new AtomicInteger(0);
        final List<ServerThread> list = new ArrayList<ServerThread>();
        final OpenWeather openWeather = new OpenWeather();

        Thread informer = new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();


                while (true) {
                    if (System.currentTimeMillis() - startTime > 60000) {

                        if (!list.isEmpty()) {
                            String weather = openWeather.getWeather();
                            for (ServerThread thread : list) {
                                thread.sendMessage(weather);
                            }
                        }

                        startTime = System.currentTimeMillis();
                    }
                }
            }
        });

        informer.start();

        try {
            // listen for incoming connections on port 15432
            ServerSocket socket = new ServerSocket(15432);
            System.out.println("Server listening on port 15432");

            while (true) {
                Socket client = socket.accept();

                ServerThread thread = new ServerThread(client, openWeather);
                list.add(thread);
                thread.start();
                numThreads.incrementAndGet();

                System.out.println("Thread " + numThreads.get() + " started.");

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}