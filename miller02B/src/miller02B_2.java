//**********************************************************
// Program name: miller02B_2.java
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
public class miller02B_2 
{
	public static void main(String args[])
	{
		// get the user input
		Scanner scan = new Scanner(System.in);
		System.out.print("How many points would you like to generate?: ");
		int total = scan.nextInt();
		
		// setup the random number generator
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);
		
		// initialize our objects
		double cylinderHeight = 8.0;
		Cylinder cylinder = new Cylinder(cylinderHeight+10, 1.0); // the +10 is to ensure the cylinder
		Sphere sphere 	  = new Sphere(cylinderHeight/2.0);	      // beyond the bounds of the sphere
		
		// generate points and see where they land
		int count 	      = 0;
		for(int i = 1; i <= total; i++)
		{
			Point p = new Point( rand.nextDouble()*cylinderHeight-4, 
								 rand.nextDouble()*cylinderHeight-4,
								 rand.nextDouble()*cylinderHeight-4);
			
			if(sphere.containsPoint(p) && !cylinder.containsPoint(p))
				count += 1;
		}
		
		double volume = (double)count/(double)total;
		volume = volume*512;
		
		System.out.println("The approximate volume of the object is " +
							volume + " <units_placeholder> cubed.");
		
	}
	
	public static class Point
	{
		private double x, y, z;
		public Point(double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public String toString()
		{
			return  "( " + x + ", " + y + ", " + z + " )";
		}
	}
	
	public static class Sphere
	{
		private double radius, radius_squared;
		private double volume;
		
		public Sphere(double radius) 
		{
			this.radius         = radius;
			this.radius_squared = Math.pow(radius, 2);
			
			// The area of a Sphere is (4/3)*pi*r^3
			this.volume           = (4/3.0)*Math.PI*Math.pow(radius, 3);
		}
		
		/* Checks weather a given point is contained
		 * in the sphere
		 */
		public boolean containsPoint(Point p)
		{
			double sum;
			sum = Math.pow(p.x, 2) +
				  Math.pow(p.y, 2) +
				  Math.pow(p.z, 2);
			
			// the point is contained within the sphere if
			// the sum of the squares of the points is less
			// than or equal to the radius squared
			return (sum <= radius_squared);
		}
	}
	
	
	public static class Cylinder
	{
		private double height, radius, volume;
		
		public Cylinder(double height, double radius)
		{
			this.height = height;
			this.radius = radius;
			this.volume = (Math.PI * Math.pow(radius, 2)) * height;
		}
		
		public boolean containsPoint(Point p)
		{
			double sum        = Math.pow(p.x, 2) + Math.pow(p.y, 2);
			double ceil_floor = height/2.0;		// divide the height by zero because all
												// polygons are implicitly assumed to be
												// around the origin, it's maximum z coordinate
												// is height/2.0 and it's minimum z coordinate
												// is -(height/2.0)
			
			boolean isWithinCircle       = sum <= Math.pow(radius, 2);
			boolean isWithinCeilingFloor = Math.abs(p.z) <= ceil_floor;
			
			// a point is within a cylinder if the x and y components are
			// within the circular portion of cylinder and the z component
			// is not beyond the ceiling or floor of the cylinder
			return (isWithinCircle && isWithinCeilingFloor);
		}
	}
}
