package com.threads;
// An incorrect implementation of a producer and consumer.
class Q {
	int n;
	boolean valueSet = false;
	synchronized int get()  {
		while(!valueSet){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //mr. consumer plz wait for the producer to produce a value;
		}
		System.out.println("Got: " + n);
		valueSet = false;
		notify(); //notify producer that i (consumer) have consumed the value
		return n;
	}
	synchronized void put(int n) {
		
		while(valueSet){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	//mr. producer plz wait for consumer to consume the previously produced value
		}
		this.n = n;
		System.out.println("Put: " + n);
		valueSet = true;
		notify();
	}
}
class Producer implements Runnable {
	Q q;
	Producer(Q q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}
	public void run() {
		int i = 0;
		while(true) {
			q.put(i++);
			
			

		}
	}
}
class Consumer implements Runnable {
	Q q;
	Consumer(Q q) {
		this.q = q;
		new Thread(this, "Consumer").start();
	}
	public void run() {
		while(true) {
			q.get();
			
		}
	}
}
class PC {
	public static void main(String args[]) {
		Thread t = Thread.currentThread();
		t.setName("MainThread");
		Q q = new Q();
		new Producer(q);
		new Consumer(q);
		System.out.println("Press Control-C to stop.");
	}
}