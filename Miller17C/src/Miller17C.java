
public class Miller17C 
{
	
	public static double getSnowHeight(double T, double snowrate)
	{
		return T*snowrate;
	}
	
	public static void main(String[] args) 
	{
		double snowrate = 1;			//snowrate is in terms of ft/hour
		double T = 0;					//T = time in hours before 12:00 is started snowing
		double Q = 0;					//Q the removal of snow is in terms of square ft per second
		
		double earlyTG = 10, lateTG = 0;
		double snowheight = 0;
		double distanceTraveled = 0;
		
		double tolerence = 1e-3;
		
		T = .5*(earlyTG + lateTG);
		
		while(Math.abs(distanceTraveled - 2640) > tolerence)
		{
			double lowQ = 0, highQ = 10;
			Q = .5*(lowQ + highQ);
			
			
			while(Math.abs(distanceTraveled - 5280) > tolerence)
			{
				snowheight = getSnowHeight(T, snowrate);
				distanceTraveled = 0;
				for(int i = 1; i <= 3600; i++)
				{
					snowheight += snowrate/3600.0;
					distanceTraveled += Q/snowheight;
				}
				
				//if the distance traveled is greater then 5280
				//then the removal rate is to high and needs
				//to be lowered
				if(distanceTraveled > 5280)
				{
					highQ = Q;
				}
				else
				{
					lowQ = Q;
				}
				Q = .5*(lowQ + highQ);
				//System.out.println(Q);
			}
			
			//Now that we have a good guess for Q, were going to run
			//the simulation for another hour.
			snowheight = getSnowHeight(T+1, snowrate);
			distanceTraveled = 0;
			for(int i = 1; i <= 3600; i++)
			{
				snowheight += snowrate/3600.0;
				distanceTraveled += Q/snowheight;
			}
			
			//if we go to far then our guess for when
			//it started snowing is to late so ajust
			//for an earlier guess of the time
			if(distanceTraveled > 2640)
			{
				earlyTG = T;
			}
			else
			{
				lateTG = T;
			}
			T = .5*(earlyTG + lateTG);
		}
		
		
		int min_before_12 = (int) (60.0*(T));
		
		int hours = min_before_12/60;
		int min = min_before_12 - hours*60;
		
		
		System.out.println("Snow began to fall around " + (11-hours) + ":" + (60 - min) + "am");
	}
}
