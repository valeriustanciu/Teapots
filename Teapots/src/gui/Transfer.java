package gui;

import java.util.List;

import javax.swing.SwingWorker;

public class Transfer extends SwingWorker<Integer, Integer> {
	  private static final int DELAY = 1000;

	  public Transfer() {
	  }

	  @Override
	  protected Integer doInBackground() throws Exception {
		// TODO 3.2
		  
		  setProgress(0);
		  int count = 10;
		  int i     = 0;
		  try {
		      while (i < count) {
		          i++;
		  	Thread.sleep(DELAY);
		  	setProgress(i);
		  	publish(i);
		      }
		  } catch (InterruptedException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		  }
		  
		  System.out.println("doInBackground: " + Thread.currentThread());
		  return 0;
	  }
	   
	  protected void process(List<Integer> chunks) {
	   // TODO 3.3 - print values received
			  System.out.println(chunks);
			  System.out.println("process: " + Thread.currentThread());
	  }

	  @Override
	  protected void done() {
	    if (isCancelled())
	      System.out.println("Cancelled !");
	    else
	      System.out.println("Done !");
	  System.out.println("done: " + Thread.currentThread());
	  setProgress(0);
	  }
	}