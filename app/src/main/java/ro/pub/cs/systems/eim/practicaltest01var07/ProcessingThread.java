package ro.pub.cs.systems.eim.practicaltest01var07;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context = null;

    private boolean isRunning = true;

    private Random random = new Random();

    public ProcessingThread(Context context) {
        this.context = context;
    }

    public void run() {
        Log.d(Constants.SERVICE_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());

        while (isRunning) {
            sleep();
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        Integer number1 = random.nextInt(10);
        Integer number2 = random.nextInt(10);
        Integer number3 = random.nextInt(10);
        Integer number4 = random.nextInt(10);
        intent.putExtra(Constants.MESSAGE_EXTRA, new Date(System.currentTimeMillis()) +
                " " + number1 + " " + number2 + " " + number3 + " " + number4);
        intent.putExtra(Constants.NUMBER_1_EXTRA, number1);
        intent.putExtra(Constants.NUMBER_2_EXTRA, number2);
        intent.putExtra(Constants.NUMBER_3_EXTRA, number3);
        intent.putExtra(Constants.NUMBER_4_EXTRA, number4);

        intent.setAction(Constants.ACTION);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
