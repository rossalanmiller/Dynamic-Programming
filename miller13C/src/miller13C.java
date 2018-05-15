import java.util.Scanner;

/////////////////////////////////////////////////////////
//Author:		Ross Miller
//Name:			miller13C.java
//Due:			10/31/17
//Purpose:		To use a dynamic programming approach
//				to find the largest palindromes in a
//				string and partitition it accordingly
/////////////////////////////////////////////////////////
public class miller13C 
{
	// a three dimensional array is used to 
	// store both the table that will tell
	// us the final answer as well as the
	// table that will tell us where to
	// place the cuts, essentially the
	// two tables are stacked upon one
	// another and the two constants
	// below are used to indicate which
	// table I would like to access
	final static int CUTS = 0;
	final static int POS = 1;
	public static void main(String[] args)
	{
		String input = getInput();
		int[][][] tables = generateTables(input);
		int cutsIndex = tables[CUTS][0].length-1;
		
		System.out.println("The number of cuts made was: ");
		System.out.println(tables[CUTS][0][cutsIndex]);
		System.out.println(toPartitionedString(tables[POS], input));
	}
	
	
	/** Repeatedly prompts the user for input
	 *  until they enter a string between 5
	 *  and 100 characters inclusive
	 * @return
	 */
	public static
	String getInput()
	{
		String input = "";
		Scanner keyboard = new Scanner(System.in);
		boolean validInput = false;
		
		while(!validInput)
		{
			System.out.println("Please enter a string between 5 and 100 characters: ");
			input = keyboard.nextLine();
			
			if(input.length() < 5 || input.length() > 100)
			{
				System.out.println("Ivalid Input, string must be between 5 and 100 charactes");
				input = "";
			}
			else
				validInput = true;
		}
		keyboard.close();
		return input.toUpperCase();
	}
	
	/** 
	 * Returns the values in the upper right corner of our cuts table 
	 * which for this project will hold the total number of cuts used
	 * to partition our input string
	 */
	public static
	int getNumCuts(int[][] cutsTable)
	{
		return cutsTable[0][cutsTable.length - 1];
	}


	/** Generates the optimal cutsTable given
	 *  the input string s
	 */
	public static
	int[][][] generateTables(String s)
	{
		int[][][] tables = new int[2][s.length()][s.length()];
		//for each diagonal
		for(int diag = 0; diag < s.length(); diag++)
		{
			//travel along the diagonal
			for(int L = 0, R = diag; R < s.length(); L++, R++)
			{
				if(isPalindrome(s.substring(L, R+1)))
				{
					tables[CUTS][L][R] = 0;
					tables[POS][L][R] = -1;
				}
				else
				{
					tables[CUTS][L][R] = getMinCutsAndRecord(tables, L, R);
				}
			}
		}
		return tables;
	}
	
	//Method created by Dr.Lewis for our use
	public static boolean isPalindrome(String S)
   {
      int i, N = S.length();

      for (i = 0; i < N/2; i++)
         if (S.charAt(i) != S.charAt(N-i-1))
            return false;
         // end if
      // end for

      return true;
   }
	
	
	/** Prints out a 2d integer array
	 */
	static void print2DArray(int[][] array)
	{
		// Since at most will be dealing with a string
		// of consisting of 100 characters and at most
		// 99 cuts we can print out the array with 
		// formatted spacing for just two characters
		String sep = String.format("%0" + (array.length*4 + 2) + "d", 0).replace("0","-");
		// Stack overflow credit for the clever solution above 
		// https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
		for(int[] row : array)
		{
			System.out.println(sep);
			System.out.print("[");
			for(int i : row)
			{
				System.out.printf(" %02d ", i);
			}
			System.out.println("]");
			
		}
		System.out.println(sep);
		System.out.println();
	}
	
	
	/** Gets the minimum # of cuts for table 
	 *  at table[CUTS][L][R] and records where
	 *  that minimum was found in tables[POS][L][R]
	 */
	static int getMinCutsAndRecord(int[][][] tables, int L, int R)
	{
		int Min1 = 0, Min2 = 0;
		int cuts = 1000000;
		//travels along each row and column corresponding
		//and gets the minimum sum from adding one element
		//from the row to one element from the column
		int finalRow = 0, finalCol = 0;
		for(int col = L, row = L+1; col < R; col++, row++)
		{
			Min1 = tables[CUTS][L][col];
			Min2 = tables[CUTS][row][R];
			if(cuts > Min1 + Min2 + 1)
			{
				cuts = Min1 + Min2 + 1;
				finalRow = row; 
				finalCol = col;
			}
		}
		tables[POS][L][R] = finalCol;
		return cuts;
	}

	/** Just a helper method to make the call in the main a bit cleaner
	 * 
	 */
	static String toPartitionedString(int[][] posTable, String input)
	{
		return toPartitionedString(posTable, input, 0, posTable.length-1);
	}
	
	/** Returns the partitioned string from the results matrix
	 *
	 */
	static String toPartitionedString(int[][] posTable, String input, int row, int col)
	{
		if(posTable[row][col] == -1)
		{
			return input.substring(row, col+1);
		}
		else
		{
			int bounds = posTable[row][col];
			// based on where we made our cut, (which we get from our position table)
			// get the left part of the string and the right part of the string
			// and concatenate them with a cut in between.
			String leftString = toPartitionedString(posTable, input, row, bounds);
			String rightString = toPartitionedString(posTable, input, bounds+1, col);
			return leftString + "|" +rightString;
		}
	}
}
