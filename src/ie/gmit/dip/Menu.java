package ie.gmit.dip;

import java.util.*;
import java.lang.Integer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * The Menu class provides a console menu for the User.
 * When the program is run as a console program, the Menu object should be instantiated in the Runner class 
 * Then the start() method should be invoke. It starts the main menu.
 * 
 * The availabe options for the User are diplayed to the console.
 * It consists of multi-level menus to ensure the high control of the program by the User.
 * All higer level menus have option to go back to the previous menu.
 * The main menu has the option to quit the program.
 * 
 * The method nextLine() from Scanner class is used to take the input from the User. This method returns the whole line, excluding the line separator.
 * 
 * @author  Pawel Zamorski
 * @version 1.0, 30 August 2018
 */
public class Menu {
    // Instance Variables (Non-Static Fields):
    // Declare a reference variable 'sc' of type Scanner (package java.util.Scanner)
    private Scanner sc; // default null value
    // Declare a reference variable 'cipher' of type Cipher
    private Cipher cipher; // default null value
    // Declare a boolean variable and assing the value true to it
    /**
     * It is used to control the while loop in start() method. It keeps running the loop util the variable is false.
     * The value of the keepRunning variable is changed to false by invoking quit() method.
     */
    private boolean keepRunning = true;
    /**
     * It is used to check if the keyQ2 keyword is set up in the upper-right quadrants of the matrix variable in Cipher class.
     * {@code true} if is set up, {@code false} if it is not set up.
     */
    private boolean correctKeyQ2 = false;
    /**
     * It is used to check if the keyQ3 keyword is set up in the lower-left quadrants of the matrix variable in Cipher class.
     * {@code true} if is set up, {@code false} if it is not set up.
     */
    private boolean correctKeyQ3 = false;
    /**
     * It is used to store encrypted text for further processing (displaying to the console or saving to the file)
     */
    private String[] encryptedText; // default null value
    /**
     * It is used to store decrypted text for further processing (displaying to the console or saving to the file)
     */
    private String[] decryptedText; // default null value
    
    /**
     * Constructs Menu object with Scanner(System.in) and Cipher() objects.
     */
    public Menu(){
        sc = new Scanner(System.in); // Create an instance/object of the Scanne class and assign it to the sc variable. Pass System.in (returning InputStream) as an argumetn to the Scanner constructor
        cipher = new Cipher();       // The new operator returns a reference to the object it created. This reference is usually assigned to a variable of the appropriate type.
    }

/* 
 * REMEMBER
 * The system class can not be instantiated. It has only static fields and methods
 * System.in is a "standard" input stream and typicly correspods to keybord input stream
 */
    

/* 
 * FIRST LEVEL MENU - MAIN MENU
 */

    /**
     * Runs the first level menu.
     * <p>
     * It displays the options of the menu to the console: invokes second level menu or quits the program.
     * The menu is run in a while loop until the User chooses the 'Quit' option.
     * <p>
     * Choosing the option by the User works as described below:
     * The option variable stores the value of the chosen option by the User as an integer.
     * The selectOption method validates an input inserted to the console by the User.
     * The valid input is parsed to the integer and return. It is assigned to the option variable.
     * Then the option value is used in switch statement to choose the appropriate case.
     * <p>
     * The first tree options displays the second level menu.
     * The four option quits the programm.
     * The default case displays an information for the User, that there is no option for the inserted input.
     * It may happen when the arguments in the selectOption(min, max) method were set up incorrectly.
     * <p>
     * The 0 option invokes a simple text method for testing the correctnes of setting up keywords, encrypting and decrypting methods.
     */
    public void start(){
        System.out.println("-----------------------------------");
        System.out.println("--- A Four-Square Cipher v. 1.0 ---");
        System.out.println("-----------------------------------");
        
        // Use while loop to run it at least once.
        // Keep running the loop until variable 'keepRunning' is true. Change the value by invoking quit() method.
        while(keepRunning){
            // Dispaly menu to the console for the User
            showMainMenu();
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = selectOption(0, 4);
            // Use 'switch' statement to run the apropriate method for the choosen option
            switch(option){
                case 0: // Invoke test method
                    test();
                    break;
                case 1:
                    keyMenu();
                    break;
                case 2:
                    // Invoke 'encryptMenu' method only if both keys are set up. Use 'keysSetUp()' to check if the keys have been set up
                    if(checkKeysSetUp()){
                        encryptMenu();
                    }
                    break;
                case 3:
                    // Invoke 'decryptMenu' method only if both keys are set up. Use 'keysSetUp()' to check if the keys have been set up
                    if(checkKeysSetUp()){
                        decryptMenu();
                    }
                    break;
                case 4:
                    // 'quit' method changes the 'keepRunning' varialbe to false. The loop ends and the program quits.
                    quit();
                    break;
                default:
                    // Invoke the below method when there is no such option for selected one.
                    // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                    displayIncorrectOption(option);
                    quit();
                    break;
            }
        }
    }
    
    /**
     * Displays the main menu text with the options to the console
     */
    private void showMainMenu(){
        System.out.println("###################################");
        System.out.println("------------ MAIN MENU ------------");
        System.out.println("-----------------------------------");
        System.out.println("(1) Keys Menu");
        System.out.println("(2) Encrypt Menu");
        System.out.println("(3) Decrypt Menu");
        System.out.println("(4) Quit");
    }
    
    /**
     * Quits the program by assigning false value to the keepRunning global variable
     */
    private void quit(){
        System.out.println("Thank you for using 'A Four-Square Cipher'. Bye bye!");
        keepRunning = false;
    }

/* 
 * SECOND LEVE MENUS
 */

    /**
     * Opens the second level menu with the options for:
     * - setting up the keys keyQ2 and keyQ3 in in the upper-right and lower-left quadrants respectively (matrix variable in Cipher class)
     * - saving or displaying the keys, once the keys are set up.
     * <p>
     * Once the key is set up, the value of the apropriate global variable correctKeyQ2 or correctKeyQ3 is change to true
     * <p>
     * The menu is run in a while loop until the User chooses the 'Go to Main Menu' option.
     * <p>
     * The keys may be inserted to the console or set up randomly.
     * The option 'Insert the keys to the console' opens Third Level Menu, with the options for inserting two separate keywords keyQ2 and keyQ3 or one keyword for both keyQ2 & keyQ3.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of switch statement.
     */
    private void keyMenu(){
        // Variable to control the while loop. Run until it is false.
        boolean run = true;
        while(run){
            // Display the menu to the console
            System.out.println("###################################");
            System.out.println("------------- KEYS MENU ------------");
            System.out.println("-----------------------------------");
            System.out.println("(1) Insert the keys to the console");
            System.out.println("(2) Insert the keys from the file");
            System.out.println("(3) Insert the random keys");
            System.out.println("(4) Save or display the inserted keys");
            System.out.println("(5) Go to the MAIN MENU");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = selectOption(1, 5);
            // Use if-then-else statement to run the apropriate block of code for the choosen option
            if(option == 1){
                // this method runs third level menu
                insertKeyMenu();
            }else if(option == 2){
                // Assign the value false to both variables. Indicate, that the keys are not set up
                System.out.println("Any old keys have been removed.");
                correctKeyQ2 = false;
                correctKeyQ3 = false;
                // Take the file name from console
                System.out.println("Insert the path of the file containing the keywords.");
                System.out.println("The keywords must be in two first lines of the file. In first line keyQ2, in second line keyQ3.");
                String filePath = sc.nextLine();
                try{
                    Parser parser = new Parser();
                    // Reads the keywords from the file and assing to keywords String[]
                    String[] keywords = parser.parseKeywords(filePath); // May return String[] with null, empty or invalid key values

                    // Set up keyQ2
                    // Validate the keyQ2. 'validateKey' method may return null.
                    String[] keyQ2 = validateKey(keywords[0]);
                    // Set up keyQ2 if it was validated sucesfully. Check if the validated key is not null
                    if(keyQ2 != null){
                        cipher.setKeyQ2(keyQ2);
                        // Change the global variable to true. It indicates that the keyQ2 is set up.
                        correctKeyQ2 = true;
                        System.out.println("The keyQ2 has been set up sucessfuly :)");
                        // Display the keyQ2
                        displayKeyQ2();
                        pressEnter();
                    }
                    else{
                        System.out.println("The keyQ2 was not set up.");
                        pressEnter();
                    }
    
                    // Set up keyQ3
                    // Validate the keyQ3. 'validateKey' method may return null.
                    String[] keyQ3 = validateKey(keywords[1]);
                    // Set up keyQ3 if it was validated sucesfully. Check if the validated key is not null
                    if(keyQ3 != null){
                        cipher.setKeyQ3(keyQ3);
                        // Change the global variable to true. It indicates that the keyQ3 is set up.
                        correctKeyQ3 = true;
                        System.out.println("The keyQ3 has been set up sucessfuly : )");
                        // Display the keyQ3
                        displayKeyQ3();
                        pressEnter();
                    }
                    else{
                        System.out.println("The keyQ3 was not set up.");
                        pressEnter();
                    }
                }catch(FileNotFoundException fnfEx){
                    System.out.println(fnfEx.getMessage());
                    fnfEx.printStackTrace();
                    System.out.println("Please, make sure the inserted path of the file is valid");
                    pressEnter();
                }catch(IOException ioEx){
                    System.out.println(ioEx.getMessage());
                    ioEx.printStackTrace();
                    System.out.println("I/O Exception");
                    pressEnter();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Uppsss... something went wrong :(\n Please, try again");
                    pressEnter();
                }
            }else if(option == 3){
                // This block of code sets up the random keywords (keyQ2 and keyQ3).
                // Set up keyQ2. Create random key using 'kyeRandom' method and than 'setKeyQ2' method to set up key
                String[] randomKeyQ2 = cipher.keyRandom();
                cipher.setKeyQ2(randomKeyQ2);
                correctKeyQ2 = true; // Indicate that keyQ2 is ready
                
                // Set up keyQ3. Create random key using 'kyeRandom' method and than 'setKeyQ3' method to set up key
                String[] randomKeyQ3 = cipher.keyRandom();
                cipher.setKeyQ3(randomKeyQ3);
                correctKeyQ3 = true; // Indicate that keyQ3 is ready
                
                // Display the keys to console
                System.out.println("The keyQ2 & keyQ3 have been set up sucessfuly :)");
                displayKeyQ2();
                displayKeyQ3();
                pressEnter();
            }else if(option == 4){
                if(checkKeysSetUp()){ // Use 'checkKeysSetUp' method to check if both keys (keyQ2 & keyQ3) have been set up.
                    saveDisplayKeyMenu(); // Open the menu for displaying the key to the console or saving it to the file
                }
            }
            else if(option == 5){
                run = false;
            }else{
                // Invoke the below method when there is no such option for selected one.
                // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                displayIncorrectOption(option);
                run = false;
            }
        }
    }
    
    /**
     * Opens the second level menu with the options for:
     * - encrypting the file or URL
     * - saving or displaying the encrypted text, once it nas been encrypted.
     * <p>
     * The encrypted text is stored in a global variable 'encryptedText' for the later processing.
     * <p>
     * The menu is run in a while loop until the User chooses the 'Go to Main Menu' option.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of switch statement.
     */
    private void encryptMenu(){
        // Variable to control the while loop. Run until it is false.
        boolean run = true;
        while(run){            
            System.out.println("###################################");
            System.out.println("----------- ENCRYPT MENU ----------");
            System.out.println("-----------------------------------");
            System.out.println("(1) Encrypt file");
            System.out.println("(2) Encrypt URL");
            System.out.println("(3) Save or display to the console");
            System.out.println("(4) Go to the MAIN MENU");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method.
            int option = selectOption(1, 4);
            // Use if-then-else statement to run the apropriate block of code for the choosen option
            if(option == 1){
                // Clear 'encryptedText' variable from any previously encrypted text
                encryptedText = null;
                System.out.println("Any previously encrypted text has been removed.");
                // This variable is used in 'parse' method to indicate that the file will be encrypted
                boolean isURL = false;
                System.out.println("Insert the path of the file to be encrypted >");
                String filePath = sc.nextLine();
                
                try{
                    // Create an object of Parser class to use its methods
                    Parser parser = new Parser();
                    // String array 'bigramArray' stores a plain text as a bigram. The plain text is parsed to the bigram using 'parse' method
                    String[] bigramArray = parser.parse(filePath, isURL); // parse method may return zero-length String[]
                    // Encrypt text. Use encrypt method form Cipher class
                    if(bigramArray.length > 0){ // Check if not zero-length String[]
                        // Run the test that checks the speed of encryption
                        long startTime = System.currentTimeMillis();
                        encryptedText = cipher.encrypt(bigramArray);
                        long finishTime = System.currentTimeMillis();
                        System.out.println("Encryption was successful. It took: " + (finishTime - startTime) + " ms");
                        pressEnter();
                    }else{
                        // Inform the User that there was no letters in the file
                        System.out.println("There are no letters in the file to be encrypted");
                        pressEnter();
                    }
                }catch(FileNotFoundException fnfEx){
                    System.out.println(fnfEx.getMessage());
                    fnfEx.printStackTrace();
                    System.out.println("Please, make sure the inserted path of the file is valid");
                    pressEnter();
                }catch(IOException ioEx){
                    System.out.println(ioEx.getMessage());
                    ioEx.printStackTrace();
                    System.out.println("I/O Exception");
                    pressEnter();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Uppsss... something went wrong :(\n Please, try again");
                    pressEnter();
                }
            }else if(option == 2){
                // Clear 'encryptedText' variable from any previously encrypted text
                encryptedText = null;
                System.out.println("Any previously encrypted text has been removed.");
                // this variable is used in 'parse' method to indicate that the URL will be encrypted
                boolean isURL = true;
                System.out.println("Insert the URL to be encrypted >");
                String URLPath = sc.nextLine();
               
                try{
                    // Create an object of Parser class to use its methods
                    Parser parser = new Parser();
                    // String array 'bigramArray' stores a plain text as a bigram. The plain text is parsed to the bigram using 'parse' method
                    String[] bigramArray = parser.parse(URLPath, isURL);
                    // Encrypt text. Use 'encrypt' method form 'Cipher' class
                    // Check if there are any letters in the 'bigramArray'  to be encrypted (it may happen that parsed URL contains no letters). If so, encrypt it.
                    if(bigramArray.length > 0){
                        // Run the test that checks the speed of encryption
                        long startTime = System.currentTimeMillis();
                        encryptedText = cipher.encrypt(bigramArray);
                        long finishTime = System.currentTimeMillis();
                        System.out.println("Encryption was successful. It took: " + (finishTime - startTime) + " ms");
                        pressEnter();
                    }else{
                        // Inform the User that there was no letters in the URL
                        System.out.println("There are no letters to be encrypted");
                        pressEnter();
                    }
                }catch(MalformedURLException mURLEx){
                    System.out.println(mURLEx.getMessage());
                    System.out.println("Please, insert the valid URL.");
                    pressEnter();
                }catch(IOException ioEx){
                    System.out.println(ioEx.getMessage());
                    ioEx.printStackTrace();
                    System.out.println("I/O Exception. Uppsss... something went wrong :(\n Please, try again");
                    pressEnter();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Uppsss... something went wrong :(\n Please, try again");
                    pressEnter();
                }
            }else if(option == 3){
                // Check if there is any encrypted text to be saved or diplayed ('encryptedText' may be null)
                if(encryptedText != null && encryptedText.length > 0){
                    saveDisplayTextMenu(encryptedText); // Open the menu for displaying the encrypted text to the console or saving it to the file
                }else{
                    System.out.println("There is no encrypted text. Encrypt any text at first.");
                    pressEnter();
                }
            }else if(option == 4){
                run = false;
            }else{
                // Invoke the below method when there is no such option for selected one.
                // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                displayIncorrectOption(option);
                run = false;
            }
        }                
    }

    /**
     * Opens the second level menu with the options for:
     * - decrypting the file
     * - saving or displaying the decrypted text, once it has been decrypted.
     * <p>
     * The decrypted text is stored in the global variable decryptedText for the later processing.
     * <p>
     * The menu is run in a while loop until the User chooses the 'Go to Main Menu' option.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of switch statement.
     */
    private void decryptMenu(){
        // Variable to control the while loop. Run until it is false.
        boolean run = true;
        while(run){            
            System.out.println("###################################");
            System.out.println("----------- DECRYPT MENU ----------");
            System.out.println("-----------------------------------");
            System.out.println("(1) Decrypt file");
            System.out.println("(2) Save or display to the console");
            System.out.println("(3) Go to the MAIN MENU");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = selectOption(1, 3);
            // Use if-then-else statement to run the apropriate block of code for the choosen option
            if(option == 1){
                // Clear 'decryptedText' variable from any previously decrypted text
                decryptedText = null;
                System.out.println("Any previously decrypted text has been removed.");
                System.out.println("Insert the path of the file to be decrypted >");
                String filePath = sc.nextLine();
                try{
                    // Create an object of Parser class to use its methods
                    Parser parser = new Parser();
                    // String array 'bigramArray' stores a decrypted text as a bigram. The text is parsed to the bigram using 'parse' method. The second argument is false. It means that the text is read from the file, not from the URL
                    String[] bigramArray = parser.parse(filePath, false);
                    // Decrypt text. Use 'decrypt' method form 'Cipher' class
                    // Check if there are any letters in the 'bigramArray'  to be decrypted (it may happen that parsed file contains no letters). If so, decrypt it.

                    if(bigramArray.length > 0){
                        // Run the test that checks the speed of decryption
                        long startTime = System.currentTimeMillis();

                        decryptedText = cipher.decrypt(bigramArray);
                        long finishTime = System.currentTimeMillis();
                        System.out.println("Decryption was successful. It took: " + (finishTime - startTime) + " ms");                        
                        pressEnter();
                    }else{
                        // Inform the User that there was no letters in the file
                        System.out.println("There are no letters in the file to be decrypted");
                        pressEnter();
                    }
                }catch(FileNotFoundException fnfEx){
                    System.out.println(fnfEx.getMessage());
                    fnfEx.printStackTrace();
                    System.out.println("Please, make sure the inserted path of the file is valid");
                    pressEnter();
                }catch(IOException ioEx){
                    System.out.println(ioEx.getMessage());
                    ioEx.printStackTrace();
                    System.out.println("I/O Exception");
                    pressEnter();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Uppsss... something went wrong :(\n Please, try again");
                    pressEnter();
                }
            }else if(option == 2){
                // Check if there is any decrypted text to be saved or diplayed ('decryptedText' may be null)
                if(decryptedText != null && decryptedText.length > 0){
                    saveDisplayTextMenu(decryptedText);
                }else{
                    System.out.println("There is no decrypted text. Decrypt any text at first.");
                    pressEnter();
                }
            }else if(option == 3){
                run = false;
            }else{
                // Invoke the below method when there is no such option for selected one.
                // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                displayIncorrectOption(option);
                run = false;
            }
        }        
    }

/* 
 * THIRD LEVEL MENU
 */
    
    /**
     * Opens the third level menu with the options for:
     * - saving the encrypted/decrypted text to the file
     * - displaying the encrypted/decrypted
     * <p>
     * This method takes the text String array as an argument: encrypted or decrypted text (encryptedText or decryptedText global variable).
     * <p>
     * The menu is run in a while loop until the User chooses the 'Go back' option.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of the switch statement.
     * 
     * @param text the String array
     */
    private void saveDisplayTextMenu(String[] text){
        // Variable to control the while loop.
        boolean run = true;
        while(run){
            System.out.println("---------------------------------------");
            System.out.println("------ SAVE OR DISPLAY TEXT MENU ------");
            System.out.println("---------------------------------------");
            System.out.println("(1) Save text to the file");
            System.out.println("(2) Display text to the console");
            System.out.println("(3) Go back");
            System.out.println("Select the option [1 - 3] >");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = selectOption(1, 3);
            if(option == 1){
                System.out.println("Insert the path of the file to write into >");
                String path = sc.nextLine();
                try{
                    Saver save = new Saver();
                    save.saveFile(path, text);
                    System.out.println("Text has been sucesfuly saved to the '" + path + "' file.");
                    pressEnter();
                }catch(NullPointerException npEx){
                    System.out.println(npEx.getMessage());
                    npEx.printStackTrace();
                    System.out.print("Saving process failed. ");
                    pressEnter();
                }catch(FileNotFoundException fnfEx){
                    System.out.println(fnfEx.getMessage());
                    fnfEx.printStackTrace();
                    System.out.print("Saving process failed. ");
                    System.out.println("Please, make sure the inserted path of the file is valid");
                    pressEnter();
                }catch(IOException ioEx){
                    System.out.println(ioEx.getMessage());
                    ioEx.printStackTrace();
                    System.out.println("I/O Exception");
                    pressEnter();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Uppsss... something went wrong :(\n Please, try again");
                    pressEnter();
                }
            }else if(option == 2){
                // Loop through the 'text' array and display text to the console
                for(int i = 0; i < text.length; i++){
                    System.out.print(text[i]);
                }
                System.out.println("\n");
                pressEnter();
            }else if(option == 3){
                run = false;
            }else{
                // Invoke the below method when there is no such option for selected one.
                // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                displayIncorrectOption(option);
                run = false;
            }
        }
    }

    /**
     * Opens the third level menu with the options for inserting two different keywords or one keyword for both keys keyQ2 and keyQ3.
     * Then the keyword is validated. The valid key must include 25 upper case letters [A-Z], excluding 'J'.
     * Validation is processed by invoking validateKey method.
     * If one keyword is inserterted, at first the keyword is split to two keywords, even or uneven.
     * <p>
     * The menu is run in a while loop until the User chooses the 'Go back' option.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of switch statement.
     */
    private void insertKeyMenu(){
        // Variable to control the while loop. Run loop until 'run' is true
        boolean run = true;
        while(run){
            System.out.println("-----------------------------");
            System.out.println("------ INSERT KEY MENU ------");
            System.out.println("-----------------------------");
            System.out.println("(1) Insert the first key (keyQ2)");
            System.out.println("(2) Insert the second key (keyQ3)");
            System.out.println("(3) Insert one keyword for both keys (keyQ2 & keyQ3)");
            System.out.println("(4) Go back to -> KEY MENU");
            System.out.println("Select the option [1 - 4] >");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = selectOption(1, 4);
            // Use if-then-else statement to run the apropriate block of code for the choosen option
            if(option == 1){
                // Change the global variable to false. It indicates that the keyQ2 is not set up, even if there is an old key in the ' matrix' variable (Cipher class)
                correctKeyQ2 = false;
                System.out.println("Any old keyQ2 has been removed.");
                // Set up the keyQ2
                System.out.println("Insert keyQ2. Use letters [A - Z] excluding 'J'. Letters will be change to upper case >");
                String line = sc.nextLine();
                // Validate the inserted key. Use 'validateKey' method to return the correct key as a String array. 'validateKey' method may return null.
                String[] keyQ2 = validateKey(line);
                // Set up keyQ2 if it was validated sucesfully. Check if the validated key is not null
                if(keyQ2 != null){
                    cipher.setKeyQ2(keyQ2);
                    // Change the global variable to true. It indicates that the keyQ2 is set up.
                    correctKeyQ2 = true;
                    System.out.println("The keyQ2 has been set up sucessfuly :)");
                    // Display the keyQ2
                    displayKeyQ2();
                    pressEnter();
                }
                else{
                    System.out.println("The keyQ2 was not set up.");
                    pressEnter();
                }
            }else if(option == 2){
                // Change the global variable to false. It indicates that the keyQ3 is not set up, even if there is an old key in the ' matrix' variable (Cipher class)
                correctKeyQ3 = false;
                System.out.println("Any old keyQ3 has been removed.");
                // Set up the keyQ3
                System.out.println("Insert keyQ3. Use letters [A - Z] excluding 'J'. Letters will be change to upper case >");
                String line = sc.nextLine();
                // Validate the inserted key. Use 'validateKey' method to return the correct key as a String array.  'validateKey' method may return null.
                String[] keyQ3 = validateKey(line);
                // Set up keyQ2 if it was validated sucesfully. Check if the validated key is not null
                if(keyQ3 != null){
                    cipher.setKeyQ3(keyQ3);
                    // Change the global variable to true. It indicates that the keyQ3 is set up.
                    correctKeyQ3 = true;
                    System.out.println("The keyQ3 has been set up sucessfuly :)");
                    // Display the keyQ3
                    displayKeyQ3();
                    pressEnter();
                }
                else{
                    System.out.println("The keyQ3 was not set up.");
                    pressEnter();
                }
            }else if(option == 3){
                // Change the global variables to false. It indicates that the keyQ2 and the keyQ3 are not set up, even if there are old keys in the ' matrix' variable (Cipher class)
                correctKeyQ2 = false;
                correctKeyQ3 = false;
                System.out.println("Any old keys have been removed.");
                System.out.println("Insert key. Use letters [A - Z] excluding 'J'. Letters will be change to upper case >");
                String line = sc.nextLine();
                // Split the inserted keyword to two keywords.
                // If the inserted keyword is even split it to two even keywords. Otherwise the keywords will be uneven
                int length = line.length();
                String lineKeyQ2 = null;
                String lineKeyQ3 = null;
                lineKeyQ2 = line.substring(0, length/2);
                lineKeyQ3 = line.substring(length/2, length);
                System.out.println("Initial keyQ2: " + lineKeyQ2);
                System.out.println("Initial keyQ3: " + lineKeyQ3);

                // Set up keyQ2
                // Validate the inserted key. Use 'validateKey' method to return the correct key as a String array.  'validateKey' method may return null.
                String[] keyQ2 = validateKey(lineKeyQ2);
                // Set up keyQ2 if it was validated sucesfully. Check if the validated key is not null
                if(keyQ2 != null){
                    cipher.setKeyQ2(keyQ2);
                    // Change the global variable to true. It indicates that the keyQ2 is set up.
                    correctKeyQ2 = true;
                    System.out.println("The keyQ2 has been set up sucessfuly :)");
                    // Display the keyQ2
                    displayKeyQ2();
                    pressEnter();
                }
                else{
                    System.out.println("The keyQ2 was not set up.");
                    pressEnter();
                }

                // Set up keyQ3
                // Validate the inserted key. Use 'validateKey' method to return the correct key as a String array.  'validateKey' method may return null.
                String[] keyQ3 = validateKey(lineKeyQ3);
                // Set up keyQ3 if it was validated sucesfully. Check if the validated key is not null
                if(keyQ3 != null){
                    cipher.setKeyQ3(keyQ3);
                    // Change the global variable to true. It indicates that the keyQ3 is set up.
                    correctKeyQ3 = true;
                    System.out.println("The keyQ3 has been set up sucessfuly :)");
                    // Display the keyQ3
                    displayKeyQ3();
                    pressEnter();
                }
                else{
                    System.out.println("The keyQ3 was not set up.");
                    pressEnter();
                }                
            }else if(option == 4){
                run = false;
            }else{
                // Invoke the below method when there is no such option for selected one.
                // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                displayIncorrectOption(option);
                run = false;
            }
        }
    }

    /**
     * Opens the third level menu with the options for:
     * - saving the inserted keys keyQ2 and keyQ3 to the file
     * - displaying the keys to the console
     * - displaying the matrix to the console
     * <p>
     * The menu is run in a while loop until the User chooses the 'Go back' option.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of switch statement.
     */
    private void saveDisplayKeyMenu(){
        // Variable to control the while loop. Run loop until 'run' is true
        boolean run = true;
        while(run){
            System.out.println("--------------------------------------");
            System.out.println("------ SAVE OR DISPLAY KEY MENU ------");
            System.out.println("--------------------------------------");
            System.out.println("(1) Save keys to the file");
            System.out.println("(2) Display keys to the console");
            System.out.println("(3) Display matrix to the console");
            System.out.println("(4) Go back");
            System.out.println("Select the option [1 - 4] >");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = selectOption(1, 4);
            // Use if-then-else statement to run the apropriate block of code for the choosen option
            if(option == 1){
                saveKeys();
            }else if(option == 2){
                displayKeyQ2();
                displayKeyQ3();
                pressEnter();
            }else if(option == 3){
                displayMatrix();
                pressEnter();
            }else if(option == 4){
                run = false;
            }else{
                // Invoke the below method when there is no such option for selected one.
                // It may occure when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                displayIncorrectOption(option);
                run = false;
            }
        }
    }

/*
 * OTHER METHODS
 */

    /**
     * Displays the keyQ2 from the matrix (Cipher object) to the console.
     * <p>
     * It gets the keyQ2 from the matrix as an String array (getKeyQ2 method),
     * changes it to the String (arrayToSring methods) and displays the result to the console.
     * <p>
     * The result of getKeyQ2 may be a String array containing null values. As a result it will produce the String with repeated word 'null'.
     * Before invoking this method it is recommended to check if the keyQ2 is set up.
     */
    private void displayKeyQ2(){
        // Display keyQ2
        System.out.print("KeyQ2: ");
        String keyQ2 = cipher.arrayToString(cipher.getKeyQ2());
        System.out.print(keyQ2);
        // Insert new line at the end.
        System.out.print("\n");
    }

    /**
     * Displays the keyQ3 from the matrix (Cipher object) to the console.
     * This method gets the keyQ3 from the matrix as an String array (getKeyQ2 method),
     * changes it to the String (arrayToSring methods) and displays the result to the console.
     * <p>
     * The result of getKeyQ3 may be a String array containing null values. As a result it will produce the String with repeated word 'null'.
     * Before invoking this method it is recommended to check if the keyQ3 is set up.
     */
    private void displayKeyQ3(){
        // Display keyQ3
        System.out.print("KeyQ3: ");
        String keyQ3 = cipher.arrayToString(cipher.getKeyQ3());
        System.out.print(keyQ3);
        // Insert new line at the end
        System.out.print("\n");
    }

    /**
     * Displays the values from the matrix (Cipher object) to the console.
     * <p>
     * Before invoking this method it is recommended to check if the keyQ2 and keyQ3 are set up.
     */
    private void displayMatrix(){
        System.out.println("The matrix of the four-square cipher:");
        String[][] matrix = cipher.getMatrix();
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                if(col == 9){
                    System.out.print(matrix[row][col] + "\n");
                }else{
                    System.out.print(matrix[row][col] + ", ");
                }   
            }
        }
        // Insert new line at the end
        System.out.print("\n");
    }

   /**
     * Asks the User for a file name and tries to save the the keyQ2 and keyQ3 to the file.
     * It displayes the appropriate info to the console (failed or successful).
     * <p>
     * The result of getKeyQ2 or getKeyQ3 method may be a String array containing null values. As a result it will produce the String with repeated word 'null'.
     * Before invoking this method it is recommended to check if both keys are set up.
     */
    private void saveKeys(){
        System.out.println("Insert file name to save the keys. Any text that exist in the file will be erased >");
        String fileName = sc.nextLine();
        // Get the keyQ2 and process to String
        String keyQ2 = cipher.arrayToString(cipher.getKeyQ2());
        // Get the keyQ3 and process to String
        String keyQ3 = cipher.arrayToString(cipher.getKeyQ3());
        // Save keyQ2 and keyQ3 to the file. Use 'saveKey' method from Saver class
        Saver saver = new Saver();
        try{
            saver.saveKey(fileName, keyQ2, keyQ3);
            System.out.println("Text has been sucesfuly saved to the '" + fileName + "' file.");
            pressEnter();
        }catch(NullPointerException npEx){
            System.out.println(npEx.getMessage());
            npEx.printStackTrace();
            System.out.print("Saving process failed. ");
            pressEnter();
        }catch(FileNotFoundException fnfEx){
            System.out.println(fnfEx.getMessage());
            fnfEx.printStackTrace();
            System.out.print("Saving process failed. ");
            System.out.println("Please, make sure the inserted path of the file is valid");
            pressEnter();
        }catch(IOException ioEx){
            System.out.println(ioEx.getMessage());
            ioEx.printStackTrace();
            System.out.print("Saving process failed. ");
            System.out.println("I/O Exception");
            pressEnter();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.print("Saving process failed. ");
            System.out.println("Uppsss... something went wrong :(\n Please, try again");
            pressEnter();
        }
    }

    /**
     * Returns the valid chosen option from the given range of options.
     * <p>
     * It validates an input inserted to the console by the User.
     * At first it displays the range of options to the console.
     * The options are from the given range. The range is set up by arguments: min and max.
     * It uses the while loop and runs it until the User inserts the valid input.
     * The valid input is an integer from a given range.
     * Finally, the valid input is parsed to the integer and returned.
     * <p>
     * This method takes two arguments: min and max. Max must be greater than or equal to min.
     * Otherwise it displays the info to the console about the wrong setting and returns the value -1.
     * 
     * @param min integer, the minimum value from the range of options
     * @param max integer, the maximum value from the range of options
     * @return option integer, the valid option from the given range or -1 if the min is greater then max
     */    
    private int selectOption(int min, int max){
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
                input = sc.nextLine();
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
     * Checks if the keyQ2 and keyQ3 are set up. Returns {@code true} if keywords are set up, {@code false} otherwise.
     * <p>
     * They are set up if the value of the global variables correctKeyQ2 and correctKeyQ3 is true. 
     * If it is not, it displays the appropriate information to the console.
     * 
     * @return setUp {@code true} if keywords are set up, {@code false} otherwise
     */        
    private boolean checkKeysSetUp(){
        // The value of this variable will be returned
        boolean setUp = false;
        if(correctKeyQ2 && correctKeyQ3){
            setUp = true;
        }else{
            if(!correctKeyQ2){
                System.out.println("KeyQ2 is not set up.");
            }
            if(!correctKeyQ3){
                System.out.println("KeyQ3 is not set up.");
            }
            System.out.println("Please, set up the key before processing.");
            pressEnter();
        }
        return setUp;
    }
    
    /**
     * Opens extra level menu for the keyword (keyQ2 or keyQ3) validating process.
     * It takes a String as an argument and returns a valid key as an String array or null value if the process was unsuccessful.
     * Both lower case and upper case lettes are accepted as a valid letters in a String argument.
     * <p>
     * The valid key must include 25 upper case letters [A-Z], excluding 'J'.
     * <p>
     * The validation process goes throught few steps as follows. It checks if:
     * - (step 1) line argument is null
     * - (step 2) anything was inserted,
     * - (step 3) any letter was inserted,
     * - (step 4) the letters are from [A - Z] excluding 'J'
     * - (step 5) there are no duplicate letters
     * - (step 6) there is 25 letters.
     * <p>
     * The validation process runs in a loop.
     * If the validation fails in any of the first two steps, the appropriate information is displaied to the console, the loop breaks and the null value is returned.
     * If the validation fails in the next steps, the User has choice: 
     * 1) to make the valid key automaticly by:
     *      - removing other letters than [A-Z], exluding 'J'
     *      - removing duplicate letters,
     *      - adding the missing letters automaticly. The letters are inserted in alphabetical order.
     * 2) for going back to the previous menu. If User chooses this option, the null value is returned.
     * <p>
     * Choosing the option by the User is described in details in start method.
     * However, here the if-then-else statement is used instead of switch statement.
     * 
     * @param line the String containing the keyword to be validated
     * @return key - a valid key as an String array if a validation process was successful
     *              or null value if the validation process was unsuccessful
     */
    private String[] validateKey(String line){
        // The value of this variable is returned
        String[] key = null;
        // The variable that stores the input from the User after displaying the options for the User
        int option = 0;
        // This variable controls the while loop.
        boolean run = false; // This variable controls do-while loop
        do{ // Run this loop only once. Use break when there is no letters or when the User wish so
            // Step 1: test if line is null
            if(line == null){
                System.out.println("No data for the key (null value). Please, insert a valid key");
                pressEnter();
                break;
            }
            // Step 2: test if the 'line' is empty.
            if(line.equals("")){ // REMEMBER: equals method throws NullPointerException if the String = null
                System.out.println("No key was inserted. Please, insert a valid key");
                pressEnter();
                break;
            }
            // Step 3: test if the 'line' contains any letters
            if(!line.matches(".*[a-zA-Z]+.*")){
                System.out.println("No letter was inserted. Please, insert a valid key");
                pressEnter();
                break;                
            }
            
            // Change the letters to upper case
            line = line.toUpperCase();
            // Use the split method and regex to return the String array.
            // The regex works as follows: lookbehind, start from the end of the previous match, match any character, exacly one
            key = line.split("(?<=\\G.{1})");
            // Step 4: check if the letters are from [A - Z] excluding 'J'
            if(!cipher.checkValidLetters(key)){
                System.out.println("The inserted key consist other characters than [A - Z] excludin 'J'. Would you like to remove them?");
                System.out.println("Enter (1) for YES; (2) for going back to INSERT KEY MENU");
                // Get the input from the User, validate and parse it to integer using 'selectOption' method
                option = selectOption(1, 2);
                if(option == 1){
                    key = cipher.removeInvalidLetters(key);
                }else if(option == 2){
                    key = null;
                    break;
                }else{
                    // Display information that there is no such option for the inserted one
                    displayIncorrectOption(option);
                    key = null;
                    break;
                }
            }
            
            // Step 5: check if there is no duplicate letters
            if(!cipher.checkNoDuplicateLetters(key)){
                System.out.println("There are duplicate letters. Would you like to remove them?");
                System.out.println("Enter (1) for YES; (2) for going back to INSERT KEY MENU");
                // Get the input from the User, validate and parse it to integer using 'selectOption' method
                option = selectOption(1, 2);
                // Use 'if' statement to run the apropriate method for the chosen option
                if(option == 1){
                    key = cipher.removeDuplicate(key);
                }else if(option == 2){
                    key = null;
                    break;
                }else{
                    // Display information that there is no such option for the inserted one
                    displayIncorrectOption(option);
                    key = null;
                    break;
                }
            }
            // Step 6: check if there are 25 letters. There can not be more than 25 as all duplicated and wrong letter have been removed
            if(key.length < 25){
                System.out.println("There is " + key.length + " letters in a key. 25 letters are required.");
                System.out.println("Would you like to insert the missing letters automaticly? They will be inserted in alphabetical order ");
                System.out.println("Enter (1) for YES; (2) for going back to INSERT KEY MENU");
                // Get the input from the User, validate and parse it to integer using 'selectOption' method
                option = selectOption(1, 2);
                if(option == 1){
                    key = cipher.fillUpKey(key);
                }else if(option == 2){
                    key = null;
                    break;
                }else{
                    // Display information that there is no such option for the inserted one
                    displayIncorrectOption(option);
                    key = null;
                    break;
                }
            }
        }while(run);

        return key;
    }
    
    /**
     * Displays an information for the User, that there is no option for the inserted input.
     * It may happen when the range of options ('min' and 'max' argument) in selectOption method was set up incorrectly
     */
    private void displayIncorrectOption(int i){
        System.out.println("Selected option is " + i + ". Error!!! There is no such option!");
        pressEnter();
    }

    /**
     * Pause the program. It enables the User to read the info from the console before continuing.
     */
    private void pressEnter(){
        System.out.println("Press enter' to continue...");
        sc.nextLine();
    }
    
    /**
     * Tests the setting and getting the keywords, and encryp and decrypt methods
     */
    private void test(){
        String[] keyQ2 = {"Z", "G", "P", "T", "F", "O", "I", "H", "M", "U", "W", "D", "R", "C", "N", "Y", "K", "E", "Q", "A", "X", "V", "S", "B", "L"};
        String[] keyQ3 = {"M", "F", "N", "B", "D", "C", "R", "H", "S", "A", "X", "Y", "O", "G", "V", "I", "T", "U", "E", "W", "L", "Q", "Z", "K", "P"};
        String plaintext = "THECURFEWTOLLSTHEKNELLOFPARTINGDAY";
        String ciphertext = "ESPDKWUMBTWGRIESFANNWXWSWDQTHGMFTL";
        String[] plaintextBigram = {"TH", "EC", "UR", "FE", "WT", "OL", "LS", "TH", "EK", "NE", "LL", "OF", "PA", "RT", "IN", "GD", "AY"};
        String[] ciphertextBigram = {"ES", "PD", "KW", "UM", "BT", "WG", "RI", "ES", "FA", "NN", "WX", "WS", "WD", "QT", "HG", "MF", "TL"};
        
        // Set the keys
        cipher.setKeyQ2(keyQ2);            
        cipher.setKeyQ3(keyQ3);            

        // Test keys
        String keyQ2Original = cipher.arrayToString(keyQ2);
        String keyQ3Original = cipher.arrayToString(keyQ3);
        String keyQ2Matrix = cipher.arrayToString(cipher.getKeyQ2());
        String keyQ3Matrix = cipher.arrayToString(cipher.getKeyQ3());

        System.out.println("keyQ2 original: " + keyQ2Original);
        System.out.println("keyQ2 matrix: " + keyQ2Matrix);
        System.out.println("keyQ3 original: " + keyQ3Original);
        System.out.println("keyQ3 matrix: " + keyQ3Matrix);
        
        if(keyQ2Original.equals(keyQ2Matrix)){
            System.out.println("Setting up and getting the keyQ2 works perfectly.\n");
        }else{
            System.out.println("Sth wrong with setting or getting the keyQ2");
        }
        
        if(keyQ3Original.equals(keyQ3Matrix)){
            System.out.println("Setting up and getting the keyQ3 works perfectly.\n");
        }else{
            System.out.println("Sth wrong with setting or getting the keyQ2");
        }

        // Test encryption
        System.out.println("Test encryption.");
        System.out.println("Original plaintext: " + plaintext);
        String decryptedCiphertext = cipher.arrayToString(cipher.decrypt(ciphertextBigram));            
        System.out.println("The original ciphertext after decryption (to the plaintext):" + decryptedCiphertext);
        if(plaintext.equals(decryptedCiphertext)){
            System.out.println("Encryption works perfectly.\n");
        }else{
            System.out.println("Sth wrong with encryption");
        }

        // Test decryption
        System.out.println("Test decryption.");
        System.out.println("Original ciphertext: " + ciphertext);
        String encryptedPlaintext = cipher.arrayToString(cipher.encrypt(plaintextBigram));
        System.out.println("The plaintext after encrypton (to the ciphertext):" + encryptedPlaintext);
        if(ciphertext.equals(encryptedPlaintext)){
            System.out.println("Decryption works perfectly.\n");
        }

        pressEnter();
    }
}