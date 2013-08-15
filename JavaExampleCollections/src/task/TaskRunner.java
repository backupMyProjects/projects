/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import LeoLib.tools.Log;
/**
 * Required : 
 * LeoLib.jar
 */
/**
 *
 * @author leo
 */
//定時thread,定時執行class
public class TaskRunner implements java.lang.Runnable {
    private final String TAG = TaskRunner.class.getName();

    private boolean doRun;
    private long SleepTime;
    
    public void stopTask(boolean input){
        doRun = !input;
    }

    // 要執行的class,及休眠時間
    public TaskRunner(long SleepTime) {
        doRun = true;
        this.SleepTime = SleepTime;
    }

    @Override
    public void run() {
        while (doRun) {
            if (doRun) {
                try {
                    Thread.sleep(SleepTime);
                    Log.i(TAG,"Hello");
                } catch (InterruptedException exception) {
                    exception.getMessage();
                    // Who is the idiot who stops me?
                }
            }

        }
        
        Log.i(TAG,"Task STOP.");

    }
}
