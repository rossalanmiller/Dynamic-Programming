/**************************************************************************
*                                                                         *
*     Program Filename:  HorseAndArmy.java                                *
*     Author          :  John B. Student                                  *
*     Date Written    :  October 30, 2017                                 *
*     Purpose         :  Demonstrate predictor-corrector solution         *
*     Input from      :  None                                             *
*     Output to       :  Screen                                           *
*                                                                         *
**************************************************************************/

public class miller15B
{
   public static void main(String[] args) 
   {
      double  Horse, Front, Rear, HorseSpeed, ArmySpeed, A, B, Distance;
      double  TimeSlice=0.00001, Tolerance=0.0001;
   
      A = 1.0;
      B = 30.0;

      for (;;)
      {
         Front = 40.0;
         Rear = Horse = Distance = 0.0; 
         HorseSpeed = (A+B)/2.0;
         ArmySpeed = 10.0;

         while (Horse < Front)
         {
            Front = Front + TimeSlice*ArmySpeed;
            Horse = Horse + TimeSlice*HorseSpeed;
            Distance = Distance + TimeSlice*HorseSpeed;
         }
         // end while

         Rear = Front - 40.0;

         while (Horse > Rear)
         {
            Rear = Rear + TimeSlice*ArmySpeed;
            Horse = Horse - TimeSlice*HorseSpeed;
            Distance = Distance + TimeSlice*HorseSpeed;
         }
         // end while

         if (Rear - 40.0 > Tolerance)
            A = HorseSpeed;
         else if (40.0 - Rear > Tolerance)
            B = HorseSpeed;
         else
            break;
         // end if
      }
      // end for

      System.out.println();
      System.out.println(ArmySpeed + " " + HorseSpeed + " " + ArmySpeed*(1+Math.sqrt(2)) + " " + Distance);
   }
   // end main method
}
// end class HoresAndArmy
