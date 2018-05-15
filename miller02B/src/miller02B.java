//**********************************************************
// Program name: miller02B.java
// Course: CSC   509
// Author: Ross Miller
// Due date: 5/9/17
// About:    Generates a number of random points to
//			 approximate the total volume of a
//			 peruvian holy bead.
//**********************************************************
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

public class miller02B {
	public static void main(String args[])
	{
		System.out.print("How many points would yo like to generate?: ");
		int total = (new Scanner(System.in)).nextInt();	// aren't using a variable to store hold the scanner since were only using it once

		Random rand = new Random();
		int count = 0;
		for(int i = 1; i <= total; i++)
		{	// generate three squares of random points between -4 to 4
			double x2 = Math.pow(rand.nextDouble()*8-4, 2);		//Avoided using loop to save three lines
			double y2 = Math.pow(rand.nextDouble()*8-4, 2);		//While making things still readable with
			double z2 = Math.pow(rand.nextDouble()*8-4, 2);		//x, y, and z
			count = ( x2 + y2 + z2 <= 16 && x2 + y2  > 1) ? count + 1 : count;	// <- don't use these too often
		}
		double volume = ((double)count/(double)total)*512;
		System.out.println("The approximate volume of the object is " +
				volume + " <units_placeholder> cubed.");
	}
}