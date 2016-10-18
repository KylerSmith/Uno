import java.io.*;
public class Numbers_input {
	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
		System.out.print("How many numbers would you like to input? ");
		int num_of_numbers = Integer.parseInt(input.readLine());
		double[] number_array = new double[num_of_numbers];		
		for (int i = 0; i < num_of_numbers; i++){					// Get all the numbers to calculate
			System.out.print("Number " + (i+1) + " : ");
			number_array[i] = Double.parseDouble(input.readLine());			
		}
		double sum_all = set_sum_all(number_array, num_of_numbers); // set the sum of all the numbers
		double mean = calc_mean(sum_all, num_of_numbers);			// Calculate the mean
		double standard_deviation = calc_stdev(number_array, mean);	// Calculate the standard deviation
		print_all(number_array, mean, standard_deviation);			// Print all the numbers and results
		} // End of main--------------------------------------------------------
	public static double set_sum_all(double[] number_array, int num_of_numbers){
		double sum_numbers = 0;
		for (int i = 0; i < num_of_numbers; i++){
			sum_numbers += number_array[i];		// Sum all numbers	
		}		
		return sum_numbers;
	}	// End of set_sum_all--------------------------------------------------
	public static double calc_mean(double sum_all, int num_of_numbers){
		double mean = (sum_all/num_of_numbers);	// Calculate the mean
		return mean;
	}	// End of calc_mean-----------------------------------------------------
	public static double calc_stdev(double[] number_array, double mean){
		double sum_numbers = 0;						// Sum of all the doubles
		for (int i = 0; i < number_array.length; i++){
			sum_numbers += ((number_array[i] - mean)*(number_array[i] - mean)) ; // (number - mean)squared
		}
		double stdev = Math.sqrt((sum_numbers)/(number_array.length - 1));	// Calculate the standard deviation
		return stdev;
	}	// End of calc_stdev---------------------------------------------------
	public static void print_all(double[] number_array, double mean, double stdev){
		System.out.println("The numbers to calculate were: ");
		for (int i = 0; i < number_array.length; i++){
			System.out.println(number_array[i]);
		}
		System.out.println("The average (mean) of the number set is: " + mean);
		System.out.println("The standard deviation of the number set is: " + stdev);
	}	// End of print_all----------------------------------------------------
}	// End of Class----------------------------------------------------

