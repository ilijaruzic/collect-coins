package rs.ilijaruzic.coins.util;

import java.awt.*;

public class Timer implements Runnable {
    private final Thread thread = new Thread(this);
    private final Label timeLabel;
    private int minutes, seconds;
    private boolean ticking;

    public Timer(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!ticking) {
                    wait();
                }
            }
            while (!thread.isInterrupted()) {
                timeLabel.setText(toString());
                timeLabel.revalidate();
                Thread.sleep(1000);
                ++seconds;
                if (seconds % 60 == 0) {
                    ++minutes;
                    seconds = 0;
                }
            }
        } catch (InterruptedException e) {
        }
    }

    public synchronized void startTimer() {
        ticking = true;
        notify();
    }

    public synchronized void pauseTimer() {
        ticking = false;
    }

    public synchronized void resetTimer() {
        minutes = seconds = 0;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", minutes, seconds);
    }
}
