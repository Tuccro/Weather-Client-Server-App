import weather.OpenWeather;

import java.util.List;

public class AutoInformer extends Thread {

    public static final long ONE_MINUTE = 60000;

    List<ServerThread> list;
    OpenWeather weather;
    boolean run = true;


    public AutoInformer(List<ServerThread> list, OpenWeather weather) {
        this.list = list;
        this.weather = weather;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();


        while (run) {
            if (System.currentTimeMillis() - startTime > ONE_MINUTE) {

                if (!list.isEmpty()) {

                    String message = weather.getWeather();
                    for (ServerThread thread : list) {
                        if (thread.isAlive()) thread.sendMessage(message);
                    }
                }

                startTime = System.currentTimeMillis();
            }
        }
    }

    public void stopThread() {
        this.run = false;
    }
}
