import java.util.Scanner;
import java.util.Arrays;


public class Miller18B 
{
	
	//table holds the results of the different sub problems
	public static int table[][];
	
	
	public static int getInput(String message)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print(message + ": ");
		int input = scan.nextInt();
		return input;
	}
	
	
	public static void initArray(int eggs, int floors)
	{
		table = new int[eggs+1][floors+1];
		for(int r = 0; r < table.length; r++)
		{
			for(int c = 0; c < table[0].length; c++)
			{
				table[r][c] = -1;
			}
		}
	}
	
	public static int solveEggFloor(int eggs, int floors)
	{
		if(table[eggs][floors] != -1)
		{
			return table[eggs][floors];
		}
		if(eggs == 1)
		{
			table[eggs][floors] = floors;
			return floors;
		}
		if(floors == 1)
		{
			table[eggs][floors] = 1;
			return 1;
		}
		if(floors == 0 || eggs == 0)
		{
			table[eggs][floors] = 0;
			return 0;
		}
		
		// one analysis for each floor where the result of the
		// analysis is the larger of the two conditions for
		// dropping an egg at that floor, either the egg broke
		// or it didn't
		int[] analysis = new int[floors];
		
		for(int i = 1; i <= floors; i++)
		{
			// when we drop an egg it will either be broken in which case we can eleminate all floors
			// above the floor at which the egg broke, or if it is not broken we can eliminate all floors
			// below the floor at which we dropped the egg and still have the original number of eggs
			analysis[i-1] = 1 + Math.max(solveEggFloor(eggs-1, i-1), solveEggFloor(eggs, floors-i ));
		}

		// the analysis array holds a list of results which indicates the least number of egg drops required
		// when dropping from each floor, so naturally we want the floor the resulted in the smallest number of
		// drops, for this program it's not important that we know what floor we need to drop from, just that we
		// know the minimum number of drops required, although it would not be difficult to find the index of the
		// smallest value in the analysis array which would indicate what floor we ultimately chose.
		Arrays.sort(analysis);
		
		int result = analysis[0];
		
		table[eggs][floors] = result;
		
		return result;
	}
	
	public static void main(String[] args) 
	{
		int eggs =  getInput("Please enter the number of eggs");
		int floors = getInput("Please enter the number of floors");
		
		//int eggs = 2;
		//int floors = 100;
		
		initArray(eggs, floors);
		
		int result = solveEggFloor(eggs,floors);
		System.out.println(result);
	}
	
	public static void print2DArray()
	{
		for(int i = 1; i < table.length; i++)
		{
			for(int j = 1; j < table[0].length; j++)
			{
				System.out.print(table[i][j] + ", ");
			}
			System.out.println();
		}
	}

}
