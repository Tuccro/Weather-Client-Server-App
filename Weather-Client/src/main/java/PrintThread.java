import java.io.BufferedReader;
import java.io.IOException;

public class PrintThread extends Thread {

    BufferedReader input;
    boolean run = true;

    public PrintThread(BufferedReader input) {
        this.input = input;
    }


    @Override
    public void run() {
        String outputString;
        while (run) {
            try {
                if (input.ready()) {
                    while (((outputString = input.readLine()) != null) && (!outputString.equals("END_OF_MESSAGE"))) {
                        System.out.println(outputString);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        run = false;
    }
}
