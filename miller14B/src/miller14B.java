/////////////////////////////////////////////////////////
// Author:		Ross Miller
// Name:		miller14B.java
// Due:			10/31/17
// Purpose:		To use a monte carlo approach to solve
//				the toads on a lily pad problem
//              
/////////////////////////////////////////////////////////

import java.util.Random;
import java.util.Scanner;

public class miller14B 
{
	public static void main(String[] args) 
	{
		
		
		int tours = getNumTours();
		int actualTours = tours;
		int count = 0;
		for(int i = 1; i <= tours; i++)
		{
			try 
			{
				if(notEaten(1))
					count++;
			}
			catch(StackOverflowError e)
			{
				actualTours--;			//throw away the result if it overflows and decrease the
			}							//count of the actual tours ran by one

		}
		
		double percentage = (double)count/(double)actualTours;
		System.out.println(percentage);
		percentage        = 100*percentage;
		System.out.println("The frog escaped approximately " + percentage + "% of the time.");
	}
	
	public static int getNumTours()
	{
		System.out.print("How many tours would you like to run?: ");
		
		Scanner scan = new Scanner(System.in);
		int tours = scan.nextInt();
		scan.close();
		return tours;
		
		
	}
	public static boolean notEaten(int pad)
	{
		if(pad == 10  || pad == 0)
			return pad == 10;
		
		Random rand = new Random();
		int randNum = rand.nextInt(10) +1;//generates a # between 1 and 10 inclusive
		int nextPad;
		if(randNum > pad)
			nextPad = pad+1;
		else
			nextPad = pad-1;
		return notEaten(nextPad);   //I realize this has the potential for a stack overflow but
									//I'm choosing to be lazy in this case
	}
}


