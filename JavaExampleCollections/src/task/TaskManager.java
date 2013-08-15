/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import LeoLib.tools.Log;
import java.io.*;
/**
 *
 * @author leo
 */
 
//Main start
public class TaskManager implements java.lang.Runnable {
    private final String TAG = TaskManager.class.getName();
    
    private java.util.List<TaskRunner> taskList;
 
    // 載入要排程的程式,並設定間隔時間1000 = 1秒
    public TaskManager() {
        taskList = new java.util.ArrayList<TaskRunner>();
        taskList.add(new TaskRunner( 2 * 1000));
 
    }
 
    // 把所有thread class執行
    @Override
    public void run() {
        for (TaskRunner runner : taskList) {
            Log.i(TAG, "Start Running");
            runner.run();
        }
 
    }
 
    public static void main(String args[]) {
        TaskManager manager = new TaskManager();
        manager.run();
    }
 
}
