/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

class Jobs implements Runnable {
    private String[] jobName = {"J1","J2","J3","J4","J5"};
    private int[] duration = {10,20,15,30,10};
    private int[] deadline = {10,10,20,30,90};
    private static int count;
    private static int clock;
    private static final int SIZE = 5;
    
    private String getJobName() {
        return jobName[count++];
    }
    
    private int getDuration() {
        return duration[count];
    }
    
    private int getDeadline() {
        return deadline[count];
    }
    
    public void run() {
        try {
            while(count < SIZE) {
                int dur = getDuration();
                int dead = getDeadline();
                String name = getJobName();
                
                if(clock+dur <= dead) {
                    System.out.println(Thread.currentThread().getName() + " " + "has started " + name + " job.");
                    Thread.sleep(dur);
                    if(clock > dur)
                        clock += clock - dur;
                    else    
                        clock += dur-clock;
                }
                else {
                    System.out.println(name + " has crossed it's deadline");
                }
            }
        }
        catch(InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " is interrupted");
        }
    }
}

class Codechef
{
    
	public static void main (String[] args) throws java.lang.Exception
	{
	    Jobs job = new Jobs();
	    Thread t1 = new Thread(job);
	    Thread t2 = new Thread(job);
	    Thread t3 = new Thread(job);
	    t1.start();
	    t2.start();
	    t3.start();
	}
}
