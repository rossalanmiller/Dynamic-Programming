/**************************************************************************
*                                                                         *
*     John B. Student                              CSCI 509               *
*     27 November 2017                             Special Topics         *
*                                                                         *
*     The Cow and the Silo                                                *
*                                                                         *
*     A cow is tied to the side of a circular silo of radius 10 feet      *
*     with a rope of length 20 feet.  What is the area of the region      *
*     in which the cow can graze?                                         *
*                                                                         *
*     Input:  none                                                        *
*     Output: screen                                                      *
*                                                                         *
**************************************************************************/
import java.util.Random;

public class Miller20B
{
   public static void main(String[] args)
   {
      int i, Counter=0;
      double p, q, Area;
      Random RNG = new Random();

      for (i = 1; i <= 10000000; i++)
      {
         p = 20.0*RNG.nextDouble() - 10.0;
         q = 20.0*RNG.nextDouble();

         if (p*p + q*q > 100.0 && CowCanReach(p,q))
            Counter++;
         // end if
      }
      // end for

      Area = 400*Counter/10000000.0;
      System.out.println(2*Area + 200*3.14159265);
   }
   // end function main

/**************************************************************************
*                                                                         *
*     This is a user-defined function named CowCanReach. This function    *
*     will return true if the cow can reach the point (p,q) and return    *
*     false otherwise.                                                    *
*                                                                         *
**************************************************************************/
   public static boolean CowCanReach(double p, double q)
   {
      double x, y, distance;

      x = FindTangentPoint(p,q);
      y = Math.sqrt(100.0 - x*x);

      distance = 10*Math.acos(x/10) + Math.sqrt((q-y)*(q-y) + (p-x)*(p-x));

      if (distance <= 20.0)
         return true;
      else
         return false;
      // end if
   }
   // end method CowCanReach

/**************************************************************************
*                                                                         *
*     This is a user-defined function named FindTangentPoint which will   *
*     determine the x-coordinate of the point of tangency on the circle   *
*     x*x + y*y = 100  which connects a tangent line to the point (p,q).  *
*                                                                         *
**************************************************************************/
   public static double FindTangentPoint(double p, double q)
   {
	   double upper = Math.PI, lower = 0;
	   
	   upper = getAngle(p,q);
	   double theta = .5*(upper + lower);
	   
	   while( Math.abs(getInterceptAngle(theta, p, q) - (Math.PI/2)) > 1e-3)
	   {
		   if(getInterceptAngle(theta, p, q) > Math.PI/2)
		   {
			   upper = theta;
		   }
		   else
		   {
			   lower = theta;
		   }
		   
		   theta = .5*(upper + lower);
	   }
	   return 10*Math.cos(theta);
   }
   // end method FindTangentPoint
   
   
   /** Given a pair of coordinates, returns
    * the angle of those coordinates relative
    * to the origin (0,0)
    */
   public static double getAngle(double p, double q)
   {
	  double theta = Math.atan2(q, p);
	 //atan works in the range of -pi to pi, so if we
	 //get a negetive angle we add 2*pi so that were working
	 //in the range of 0 to 2pi
	 if(theta < 0)
		 theta += 2*Math.PI;
	 return theta;
   }
   
   public static String stringAngle(double theta)
   {
	   double ang = theta/Math.PI;
	   return ang + " pi";
   }
   
   /** Returns the interior angle of the line from (p,q) to our current
    * guess and the line from the orgin to our current guess
    */
   public static double getInterceptAngle(double theta, double p, double q)
   {
	   double x = 10.0*Math.cos(theta);
	   double y = 10.0*Math.sin(theta);
	   
	   // gamma is the angle formed by treating x and y as
	   // the origin point and p and q as a pair of coordinates
	   double gamma = getAngle(p-x, q-y);
	   return (Math.PI - gamma) + theta;
	   
   }
}
// end class CowAndSilo
        
