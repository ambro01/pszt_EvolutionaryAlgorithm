package model;

public class MainRunnable {
	MyRunnable myRunnable;
	private int threadNumber;
	
	public MainRunnable(int dimension, double sigma0, double sigmaMin, int it, int m, double c1, double c2, int threadNumber){
		myRunnable = new MyRunnable(dimension, sigma0, sigmaMin, it, m, c1, c2);
		this.threadNumber = threadNumber;
	}
	
	public void run(){		
		Thread [] threads = new Thread[threadNumber];
		
		for(int i = 0; i < threads.length; ++i){
			threads[i] = new Thread(myRunnable);
			threads[i].start();
		}
		
		for(int i = 0; i < threads.length; ++i){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public MyRunnable getMyRunnable(){
		return myRunnable;
	}
}
