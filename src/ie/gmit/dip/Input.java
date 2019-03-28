package ie.gmit.dip;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The Input class is used for reading and processing input inserted to the console.
 * 
 */
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Input {
	private Scanner scanner;
	
	/**
	 * Creates a new Input object that is ready for taking and processing an input inserted to the console.
	 */
	public Input() {
		super();
		scanner  = new Scanner(System.in);
	}
	
	/**
	 * Returns an integer as an chosen option by the User.
	 * <p>
	 * It displays a range of options and asks User to select one from the range.
	 * It accepts only integers.
	 * The range of options is specified by the min and max parameters.
	 * 
	 * @param min a minimum value for option
	 * @param max a maximum value for option
	 * @return a selected option
	 */
    public int selectOption(int min, int max){
        // The value of this variable is to be returned. The default is -1.
        int option = -1;
        if(min <= max){
            // Display the range of options to the console
            System.out.println("Please select the option [" + min + " - " + max + "] >");
            // Variable that stores the input
            String input = null;
            // Variable that controls the while loop
            boolean run = true;
            // Run while loop until the input is correct: type of integer and from the given range
            while(run){
                // Use method 'nextLine()' to take the whole line, including the new line '\n'
                input = scanner.nextLine();
                // Use try-catch block to parse String to Integer. It throws NumberFormatException
                try{
                    option = Integer.parseInt(input);
                }catch(NumberFormatException nfEx){
                    System.out.println("Invalid input ('" + input  + "' is not a number - integer).");
                    System.out.println("Please select the valid option [" + min + " - " + max + "] >");
                    continue;
                }
                // Check if the 'option' is from the range
                if(option >= min && option <= max){
                    run = false; // Input is valid. Do not run loop again.
                }else{
                    System.out.println("No such option for a given input: " + option  + ". Choose the correct option.");
                    System.out.println("Please select the valid option [" + min + " - " + max + "] >");                
                }
            }
        }else{
            System.out.println("Error: the arguments for min and max parameters in selectOption method are not set up correctly. Max must be greater than or equal to min.");
        }
        return option;
    }

    /**
     * Returns the whole input as a string
     * 
     * @return a whole input as a string
     */
    public String inputString(){
    	return scanner.nextLine();
    }
    
    /**
     * Displays a message for the User and waits and reads the whole line
     * 
     * @param message  an info to be displayed for the User
     */
    public void pressEnter(String message) {
    	System.out.println(message);
        System.out.println("Press 'enter' to continue...");
        scanner.nextLine();
    }
    
    /**
     * Returns set of Strings.
     * <p>
     * Takes words as an arguments and parses to the set of Strings.
     * It uses space key as a delimiter.
     * 
     * @return sets of words. Size may be 0
     * 
     */
    public Set<String> inputWords() {
    	String words = scanner.nextLine();
    	Set<String> wordsSet = new TreeSet<String>(); // Use set to remove duplications
    	words = words.toLowerCase().replaceAll("[^A-Za-z0-9 ]", ""); // Remove non-word characters, do not remove space
    	String[] wordsArr = words.split(" ");
    	wordsSet.addAll(Arrays.asList(wordsArr));
    	return wordsSet;
    }
    
    /**
     * Returns integer that has been inserted to the console.
     * 
     * @return integer that has been inserted
     */
    public int inputInt() {
        int number = 0;
        String input = null;
        
        boolean run = true;
        while(run){
            // Use method 'nextLine()' to take the whole line, including the new line '\n'
            input = scanner.nextLine();
            // Use try-catch block to parse String to Integer. It throws NumberFormatException
            try{
                number = Integer.parseInt(input);
                run = false;
            }catch(NumberFormatException nfEx){
                System.out.println("Invalid input ('" + input  + "' is not a number - integer).");
                System.out.println("Please select the valid number >");
                continue;
            }
        }
        return number;
    }
}
