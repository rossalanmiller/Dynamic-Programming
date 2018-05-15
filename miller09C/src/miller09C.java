/////////////////////////////////////////////////////////
// Author:		Ross Miller
// Name:		miller09C.java
// Due:			10/14/2017
// Purpose:		To use a monte carlo approach to solve
//				the birds on a wire problem.
/////////////////////////////////////////////////////////



import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class miller09C
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the lenght of the wire/ number of birds: ");
		
		int numBirds = scan.nextInt();
		
		System.out.print("Enter the number of trials you would like: ");
		int numTrials = scan.nextInt();
		double average = 0;
		for(int trial = 1; trial <= numTrials; trial++)
		{
			double[] points = new double[numBirds];
			//System.out.println(points.length);
			Random rand = new Random();
			for(int i = 0; i < points.length; i++)
			{
				points[i] = (rand.nextDouble()*numBirds);
			}
				
			Arrays.sort(points);
			
			boolean connRight = false;
			double total = 0;
			double left, right;
			for(int i = 0; i < points.length; i++)
			{
				if( i == 0)
				{
					right = points[1] - points[0];
					total += right;
					connRight = true;
				}
				else if( i == (points.length - 1))
				{
					if(connRight == false)
					{
						left = points[i] - points[i-1];
						total += left;
						connRight = false;
					}
				}
				else if(connRight == true)
				{
					left = points[i] - points[i-1];
					right = points[i+1] - points[i];
					if(right < left)
					{
						total += right;
						connRight = true;
					}
					else
					{
						connRight = false;
					}
				}
				else if(connRight == false)
				{
					left = points[i] - points[i-1];
					right = points[i+1] - points[i];
					if(right < left)
					{
						total += right;
						connRight = true;
					}
					else
					{
						total += left;
						connRight = false;
					}
				}
			}
			// end for loop
			average += total/(double)points.length;
			
		}//end for loop
		double result = average/(double)numTrials;
		
		System.out.println(result);
		scan.close();
		
		
	}
	
}
