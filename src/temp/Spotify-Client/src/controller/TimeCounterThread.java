package controller;

import view.MainView;

public class TimeCounterThread extends Thread{
	private int count;
	private boolean running;
	private MainView controller;
	
	public TimeCounterThread(MainView controller){
		this.controller = controller;	
		count = 0;
	}
	
	public void run(){
		running = true;
		while(running){
			count++;
			try {
				sleep(10);
			} catch (InterruptedException e1) {}
			
			if(count == 5){
				count = 0;
				try{
					controller.update();
				}catch(Exception e){}
				
			}
		}
	}
}
