import java.util.ArrayList;
import java.util.List;

class ThreadsPoints extends Thread {
	   private Thread t;
	   private String threadName;
	   private int threadPoints;
	   private PtsInCircle pic;
	   
	   ThreadsPoints(String name, int points, PtsInCircle pc) {
	     threadPoints= points;
	     threadName= name;
	     pic=pc;
	     System.out.println("Creating " +  threadName );
	  }
	   
	   public void run() {
	      	double x, y; 

	      	int numInside= 0; //numero de pontos dentro do circulo
			
			
			for (int i=0; i<threadPoints; i++) {
				x= Math.random();
				y= Math.random();
				if ( ( Math.pow(x, 2)+ Math.pow(y, 2) )<=1) {
						numInside++;
						synchronized (pic) {
							pic.inCircle++;
						}
				}
				
			}
			System.out.println(threadName + ": Total number os points: "+  threadPoints);
			System.out.println(threadName + ": Points within circle : " + numInside);
			System.out.println(threadName + ": Pi estimation: " + 4*(double)numInside/(double)threadPoints);
			synchronized (pic) {
				pic.threadCounter--;
			}
	   }
	   
	   public void start () {
		  System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this);
	         t.start ();
	      }
	   }

	}

	class PtsInCircle{
		public static int inCircle= 0;
		public static int threadCounter= 0;
		
	}

	public class testePiPar {

	   public static void main(String args[]) {
		   
		  int nPoints= Integer.parseInt(args[0]);
		  int nThreads= Integer.parseInt(args[1]);
		  int pointsPerThread= nPoints/nThreads;
		  
		  PtsInCircle  pts= new PtsInCircle();
		  pts.threadCounter= nThreads;
		  
		  List <ThreadsPoints> tp= new ArrayList <ThreadsPoints>();
		   
		  for (int i=0; i<nThreads; i++) {
			  tp.add(i, new ThreadsPoints("thread"+i, pointsPerThread, pts));
		  }
		  for (int i=0; i<nThreads; i++) {
			  tp.get(i).start();
		  }
		  
		 do {
		 } while (pts.threadCounter>0);
		 
		 System.out.println("Global: Total number os points: "+  nPoints);
		 System.out.println("Global: Points within circle : " + pts.inCircle);
		 System.out.println("Global: Pi estimation: " + 4*(double)pts.inCircle/(double)nPoints);
		  
	   }   
	}
