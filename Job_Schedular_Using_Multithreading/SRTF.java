/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

class Job {
    
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int id;
    
    public Job() {
        
    }
    
    public Job(int arrivalTime, int burstTime, int priority, int id) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.id = id;
    }
    
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getArrivalTime() {
        return this.arrivalTime;
    }
    
    public int getBurstTime() {
        return this.burstTime;
    }
    
    public int getPriority() {
        return this.priority;
    }
}

class TaskManager extends Thread {
    
    public static ArrayList<Job> jobsAvailableAtParticularTime = new ArrayList<>();
    
    @Override
    public void run() throws Exception{
        List<Process> jobs = null;
        
        for(int i=0;i<jobsAvailableAtParticularTime.getSize();i++) {
            if(jobsAvailableAtParticularTime.get(i).getBurstTime() == 0)
                jobsAvailableAtParticularTime.remove(i);
        }
        
        if(!ThreadScheduler.getHasAllJobsArrived()) {
            synchronized(ThreadScheduler.jobsToRun) {
                jobs = ThreadScheduler.jobsToRun.get(ThreadScheduler.time);
                if(jobs != null) {
                    for(int i=0;i<jobs.size();i++) {
                        jobsAvailableAtParticularTime.add(jobs.get(i));
                    }
                }
                Collections.sort(jobsAvailableAtParticularTime, new Comparator<Job> {
                        @Override
                        public int compare(Job p1, Job p2) {
                            if(p1.getBurstTime() != p2.getBurstTime())
                                return p1.getBurstTime()-p2.getBurstTime();
                            else    
                                return p1.getPriority()-p2.getPriority();
                        }
                    });
                notify();
            }
        }
    }
}

class WorkingThread extends Thread {
    
    @Override
    public void run() {
        
        Job p = ThreadScheduler.getJobToExecute();
        System.out.println("Process "+ p.getId() +  " is executed by Thread " + Thread.getName());
        if(ThreadScheduler.hasAllJobsArrived) {
            System.out.println("Process "+ p.getId() +  " is executed by Thread " + Thread.getName() + " to completion");
            p.setBurstTime(0);
        }
        else {
            p.setBurstTime(p.getBurstTime() - 1);   
        }
    }
}


class ThreadScheduler
{
    public static Map<Integer, List<Job>> jobsToRun = new HashMap<>();
    private Queue<Integer> threadPool;
    public static int time = 0;
    private static int jobCount;
    private boolean hasAllJobsArrived;
    private static final int threadPoolSize;
    
    public static boolean getHasAllJobsArrived() {
        return this.hasAllJobsArrived;
    }
    
    public void addJobs(List<Job> jobs) {
        
        for(Job p : jobs) {
            if(this.jobsToRun.get(p.getArrivalTime()) == null) {
                this.jobsToRun.put(this.jobsToRun.getArrivalTime(), new ArrayList<>().add(p));
            }
            else {
                this.jobsToRun.get(p.getArrivalTime()).add(p);
            }
        }
    }
    
    public void createThreadPool(int maxNumberOfThreads) {
        this.threadPoolSize = maxNumberOfThreads;
        this.threadPool =  = new LinkedList<>();
        for(int i=0;i<maxNumberOfThreads;i++) {
            threadPool.add(new WorkingThread());
        }
    }
    
    public static synchronized Job getJobToExecute() {
        if(jobCount < TaskManager.jobsAvailableAtParticularTime.size())
            return TaskManager.jobsAvailableAtParticularTime.get(jobCount++);
    }
    
    public void executeJobs() {
        TaskManager taskManager = new TaskManager();
        
        //Till all the jobs are not available
        while(!map.isEmpty()) {
            synchronized(ThreadScheduler.jobsToRun) {
                taskManager.start();
                wait();
                map.remove(time);
                this.time += 1;
            }
            for(int i=0;i<TaskAdder.jobsAvailableAtParticularTime.size() && i<this.threadPoolSize;i++) {
                WorkingThread thread = threadPool.remove();
                thread.start();
                threadPool.add(thread);
            }
        }
        this.hasAllJobsArrived = true;
        
        //When all the jobs have arrived
        while(!TaskManager.jobsAvailableAtParticularTime.isEmpty()) {
            WorkingThread thread = threadPool.remove();
            thread.start();
            threadPool.add(thread);
        }
    }
    
    //Driver Code
	public static void main (String[] args) throws java.lang.Exception
	{
		ThreadScheduler threadScheduler = new ThreadScheduler();
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(0, 4, 5, 1));
        jobs.add(new Job(0, 3, 4, 2));
        jobs.add(new Job(1, 10, 5, 3));
        jobs.add(new Job(2, 2, 1, 4));
        jobs.add(new Job(2, 5, 2, 5));
        jobs.add(new Job(2, 5, 1, 6));
        threadScheduler.addJobs(jobs);
		threadScheduler.createThreadPool(3);
		threadScheduler.executeJobs();
	}
}
