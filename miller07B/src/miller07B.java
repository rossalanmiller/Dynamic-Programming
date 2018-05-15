import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class miller07B 
{
	public static void main(String args[]) throws FileNotFoundException
	{
		System.out.print("Please enter a filename: ");
		Scanner keyboard = new Scanner(System.in);
		String 	filename = keyboard.nextLine();
		Scanner file = new Scanner(new File(filename));
		
		int permLength = Integer.parseInt(file.nextLine());
		while(file.hasNextLine())
		{
			int[] array = lineToArray(file, permLength);
			System.out.println(toCycleNotation(array));
		}
	}
	
	/* Reads a single line of numbers in a file
	 * into an integer array
	 */
	public static int[] lineToArray(Scanner file, int length)
	{
		int[] nums = new int[length+1];
		for(int i = 1; i <= length; i++)
		{
			nums[i] = file.nextInt();
		}
		return nums;
	}
	
	
	/* Produces the cycle notation for a permuatation
	 * given as an array of numbers
	 */
	public static String toCycleNotation(int[] perm)
	{
		String cycle = "";
		int[] check = new int[perm.length];
		
		for(int checkNum = 1; checkNum < check.length; checkNum++)
		{
			//if i is not been included in a sequence yet
			if(check[checkNum] == 0)
			{
				//Then construct the cyle starting at checkNum
				cycle += "( ";
				// for each number we encounter add it to the cycle
				// and mark it in the check array, following the
				// permutation as we go along
				for(int permNum = checkNum; check[permNum] != 1; permNum = perm[permNum])
				{
					cycle += permNum + " ";
					check[permNum] = 1;
				}
				cycle += ")";
			}
		}	
		return cycle;
	}
}
