import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    public static void main(String[] args) {
        AtomicInteger numThreads = new AtomicInteger(0);
        // the list of threads is kept in a linked list
        ArrayList<Thread> list = new ArrayList<Thread>();

        try {
            // listen for incoming connections on port 15432
            ServerSocket socket = new ServerSocket(15432);
            System.out.println("Server listening on port 15432");

            while(true) {
                Socket client = socket.accept();

                Thread thread = new Thread(new ServerThread(client));
                list.add(thread);
                thread.start();
                numThreads.incrementAndGet();
                System.out.println("Thread " + numThreads.get() + " started.");

            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}