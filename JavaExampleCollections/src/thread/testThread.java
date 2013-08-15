/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

/**
 *
 * @author leo
 */
public class testThread extends java.lang.Thread {

    public long waittime;
    public String name;

    public testThread(long waittime, String value) {
        this.waittime = waittime;
        this.name = value;
    }

    public void run()// 啟動Thread時會執行run
    {
        try {
            while (true) {// 永遠讓Thread執行下去,只有在強制中斷時才會失效
                // 停幾秒後執行System內容
                Thread.sleep(waittime);
                System.out.println(this.name);
                System.out.println("Thread Name in System "+Thread.currentThread().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        // 1000毫秒 = 1秒
        // 用start 來啟動thread
        testThread tT1 = (new testThread(3 * 1000, "Thread-1"));
        testThread tT2 = (new testThread(1 * 1000, "Thread-2"));
        
        tT1.start();
        tT2.start();

    }
}
