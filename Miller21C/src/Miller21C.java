//////////////////////////////////////////////////////////////////////////
// Author:			Ross Miller
//
// Date: 			Jan 22 2018
//
// Program Name:	Miller21C.java
//
// Purpose:			Solves the three snow plow problem using a simulation
//					approach as explained by Dr.Lewis in his paper on the
//					subject
//////////////////////////////////////////////////////////////////////////


import java.util.Queue;
import java.util.LinkedList;

public class Miller21C
{

	static int timer = 0;
	
	/** This is basically the java equivalent of a struct and is used to keep track of the
	 * state changes of the plows, mainly the position and how many slices each plow has consumed
	 */
	public static class Plow
	{
		public int seconds = 0;		//how long the plow has been running
		public int slices  = 0;		//how many time slices the plow has consumed
		public double pos  = 0;		//the current position of the plow
		
		public void reset() { seconds = 0; slices = 0; pos = 0;};
	}
	//end class Plow\
	
	public static void main(String[] args)
	{
		//each of our plows starts at position 0.
		Plow p1 = new Plow();
		Plow p2 = new Plow();
		Plow p3 = new Plow();
		
		double removalRate  = .5;								//square_ft/second
		double snowFallRate = 1;								//ft/hour
		
		double lateTG = 0, earlyTG = 12;						//initial interval is 0-12 hours before noon
		double S = .5*(earlyTG + lateTG);
		
		boolean twoCone    = false;
		boolean threeCtwo  = false;								//booleans for when the plows catch each other
		
		Queue<Double> Q1 = new LinkedList<Double>();
		Queue<Double> Q2 = new LinkedList<Double>();
		
		// we don't want to stop the simulation until plow2 catches plow1, 
		// and plow3 catches plow2 in the same second.
		while(!twoCone || !threeCtwo)
		{
			if(twoCone)
				earlyTG = S;
			if(threeCtwo)
				lateTG = S;
			S = .5*(earlyTG + lateTG);
			
			twoCone   = false;
			threeCtwo = false;
			
			Q1.clear();
			Q2.clear();
			
			p1.reset();
			p2.reset();
			p3.reset();
			timer = 0;
			
			// the first snowplow starts off at 12
			double depth = getDepth(S, snowFallRate);
			for(int s = 1; s <= 3600; s++)
			{
				timer++;
				depth  += snowFallRate/3600;
				p1.pos += removalRate/depth;
				Q1.add(p1.pos);
			}
			
			
			// after the first hour the second snow plow sets off at 1:00
			depth = getDepth(S+1.0, snowFallRate);
			for(int s = 1; s <= 3600; s++)
			{
				timer++;
				depth += snowFallRate/3600;
				p1.pos += removalRate/depth;
				Q1.add(p1.pos);
				
				advancePlow(p2, Q1, snowFallRate, removalRate);
				Q2.add(p2.pos);
				
				if(Q1.isEmpty())
				{
					break;
				}
			}
			
			//the second plow has caught the first before the third plow even started,
			//so restart the simulation.
			if(Q1.isEmpty())
			{
				twoCone = true;
				continue;
			}
			
			depth = getDepth(S+2.0, snowFallRate);
			while(true)											//finally the third plow sets off at 2:00 and we run the simulation
			{													//till one of the plows catch up.
				timer++;
				depth += snowFallRate/3600;
				p1.pos += removalRate/depth;
				Q1.add(p1.pos);
				
				
				advancePlow(p2, Q1, snowFallRate, removalRate);
				Q2.add(p2.pos);
				advancePlow(p3, Q2, snowFallRate, removalRate);
				
				if(Q1.isEmpty() || Q2.isEmpty())
				{
					break;
				}
			}
			
			if(Q1.isEmpty())
				twoCone = true;
			if(Q2.isEmpty())
				threeCtwo = true;
		}
		
		System.out.println("The three plows met at " + getTime(timer) + " PM");
		System.out.println("and it started snowing around " + getTime((int)((12-S)*3600)) + " AM");
	}
	
	
	/** Given the current positon of a plow p, the queue it's currently working
	 *  it's way through, and the current second of it's journey, this function
	 *	will return it's updated position.
	 *
	 * Values that this function may change are p.slices, p.pos, and p.seconds
	 */
	public static void advancePlow(Plow p, Queue<Double> Q, double snowfallRate, double removalRate)
	{
		p.seconds++;
		double timeLeft = 1.0;
		
		while(timeLeft > 0)
		{
			double width = Q.peek() - p.pos;
			double height = getHeight(p, snowfallRate);
			double area = width*height;
			double timeRequired = (1.0/removalRate)*area;
			
			//if the time Required to clear a rectangle is greater then the amount of time left
			if(timeRequired > timeLeft)
			{
				double area_removed = timeLeft*removalRate;
				double distance_moved = area_removed/height;
				p.pos += distance_moved;
				timeLeft = -1;
			}
			//Otherwise we advance the plow to the next rectange and decrement th
			else
			{
				p.pos = Q.remove();
				p.slices++;
				timeLeft -= timeRequired;
			}
			if(Q.isEmpty())
			{
				break;
			}
		}
	}//end method advancePlow;
	
	
	/** Returns the depth of the snow after a given amount
	 * of hours and given a snowfall rate
	 */
	public static double getDepth(double hours, double ftPerHour)
	{
		return hours*ftPerHour;
	}
	
	
	/** Returns the height of the snow currently being plowed based on how
	 * many slices a plow has consumed and how many seconds it's been plowing for, 
	 * the slices keep track of how far behind one plow is from another while the 
	 * seconds indicate how long the plow has been running, since while a plow is 
	 * plowing, more snow will of course be accumulating.
	 * 
	 */
	public static double getHeight(Plow p, double snowFallRate)
	{
		double height_from_distance = (((double)(3600 - p.slices))/3600.0)*snowFallRate;
		double height_from_time		= (((double)p.seconds)/3600.0)*snowFallRate;
		return height_from_distance + height_from_time;
	}

	
	/** Given the number of seconds past 12, this function will print the time.*/
	public static String getTime(int seconds)
	{
		int hours = seconds/3600;
		seconds = seconds%3600;
		int minutes = seconds/60;
		seconds = seconds%60;
		return String.format("%d:%02d.%02d", hours, minutes, seconds);
	}
}
