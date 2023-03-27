package org.bolyuk.bkcmmnlib;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleTimer {
    Timer task;

    public SimpleTimer(Tasker tasker, Long time){
        task = new Timer();
        task.schedule(new TimerTask() {
            @Override
            public void run() {
                tasker.onAction();
            }
        }, time);
    }

    public void cancel(){
        task.cancel();
    }

        public interface Tasker{
        void onAction();
        }
}
