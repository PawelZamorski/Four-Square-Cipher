package ie.gmit.dip;

/**
 * The Cipher class is used for encrypting and decrypting a text.
 * <p>
 * It uses a four-square cipher {@code matrix}: four 5x5 matrices asrranged in a square to encrypt/decrypt pairs of characters.
 * Each of 5x5 matrices contains 25 letters (the relatively rare letter "J" is merged with the letter "I").
 * The upper-left and lower-right quadrants are "plaintext square" containing a standard alphabet.
 * The upper-right and lower-left quadrants are the "ciphertext squares" contain a mixed random alphabetic sequence.
 * <p>
 * The four-square cipher is a polygraphic substitution cipher that uses the same keyword to encrypt plain-text and decrypt cipher-text.
 * <p>
 * It uses set of the bigrams to encrypt or decrypt text. The bigram is a set of exacly two characters, ie: "AB", "CD", ect.
 * The valid bigram may contain only upper case letter from the range [A-Z], excluding "J".
 * <p>
 * The encryption process runs as follows:
 * - locates each of the two characters in the bigram in the top left and the bootom right 'matrix' square respectively.
 * - in the top-right 'matrix' square, finds the charecter at the intersection of the row of the plaintext character
 *   in the top left square with the column of the plaintext character in the bottom right square
 * - in the bottom-left 'matrix' square, find the character at the intersection of the column of the plaintext character
 *   in the top left square with the row of the plaintext character in the bottom right square
 * <p>
 * The decryption process runs similarly to encryption process. It runs as follows:
 * - locates each of the two characters in the bigram in the top right and bottom left 'matrix' square respectively.
 * - in the top-left 'matrix' square, finds the charecter at the intersection of the row of the ciphertext character
 *   in the top right square with the column of the ciphertext character in the bottom left square
 * - in the bottom-right 'matrix' square, find the character at the intersection of the column of the ciphertext character
 *   in the top right square with the row of the ciphertext character in the bottom left square
 * 
 * @author  Pawel Zamorski
 * @version 1.0, 30 August 2018
 */
public class Cipher {
    /**
     * Four 5x5 matrices asrranged in a square to encrypt/decrypt pairs of characters.
     * 
     * A {@code matrix} ready to encrypting/decryptin process must contain 25 letters [A-Z], excluding 'J' in every four 5x5 matrices.
     * The upper-left and lower-right quadrants are the "plaintext square" containing a standard alphabet.
     * The upper-right and lower-left quadrants are the "ciphertext squares". The initial values in those quadrants are null.
     * The keys in "ciphertext squares" must be set up with a mixed random alphabetic sequence before encrypting or decrypting process.
     */
    private String[][] matrix = {
        {"A", "B", "C", "D", "E", null, null, null, null, null},
        {"F", "G", "H", "I", "K", null, null, null, null, null},
        {"L", "M", "N", "O", "P", null, null, null, null, null},
        {"Q", "R", "S", "T", "U", null, null, null, null, null},
        {"V", "W", "X", "Y", "Z", null, null, null, null, null},
        {null, null, null, null, null, "A", "B", "C", "D", "E"},
        {null, null, null, null, null, "F", "G", "H", "I", "K"},
        {null, null, null, null, null, "L", "M", "N", "O", "P"},
        {null, null, null, null, null, "Q", "R", "S", "T", "U"},
        {null, null, null, null, null, "V", "W", "X", "Y", "Z"}        
        };
    
    /**
     * Sets up the key in the upper-right quadrant of the {@code matrix}. This key is called further keyQ2.
     * <p>
     * It is recommended to validate the {@code key} String array before passing it as an argument.
     * The valid key String array is as follows:
     * - the lengts is 25
     * - it only consist of the single upper case letters [A-Z], excludin "J"
     * - the letters can not be doubled
     * 
     * @param key   the String array
     * @throws ArrayIndexOutOfBoundsException   if the key String array is less than 25
     */
    public void setKeyQ2(String[] key) {
        int index = 0; // Index of the key String array
        // Sets up the values in the upper-right quadrant of the matrix variable
        for (int row = 0; row < 5; row++) {
            for (int col = 5; col < 10; col++) {
                matrix[row][col] = key[index];
                index++;
            }
        }
    }

    /**
     * Sets up the key in the lower-left quadrant of the {@code matrix}. This key is called further keyQ3.
     * <p>
     * It is recommended to validate the {@code key} String array before passing it as an argument.
     * The valid key String array is as follows:
     * - the lengts is 25
     * - it consist only single upper case letters [A-Z], excludin "J"
     * - the letters can not be doubled
     * 
     * @param key   the String array
     * @throws ArrayIndexOutOfBoundsException when the key String[] is less than 25
     */
    public void setKeyQ3(String[] key) {
        int index = 0; // Index of the key String array
        // Set up the values in the lower-left quadrant of the matrix variable
        for (int row = 5; row < 10; row++) {
            for (int col = 0; col < 5; col++) {
                matrix[row][col] = key[index];
                index++;
            }
        }
    }

    /**
     * Returns the String array containing the keyQ2, that is the values from the upper-right quadrant of the matrix variable.
     * 
     * @return keyQ2 the String array
     */
    public String[] getKeyQ2(){
        String[] keyQ2 = new String[25];
        int index = 0; // Index of the keyQ2 String array
        for(int row = 0; row < 5; row++){
            for(int col = 5; col < 10; col++){
                keyQ2[index] = matrix[row][col];
                index++;
            }
        }
        return keyQ2;
    }

    /**
     * Returns the String array containing the keyQ3, that is the values from the lower-left quadrant of the matrix variable.
     * 
     * @return keyQ3 the String array 
     */
    public String[] getKeyQ3(){
        String[] keyQ3 = new String[25];
        int index = 0; // Index of the 'keyQ3' String array
        for(int row = 5; row < 10; row++){
            for(int col = 0; col < 5; col++){
                keyQ3[index] = matrix[row][col];
                index++;
            }
        }
        return keyQ3;
    }
    
    /**
     * Returns the two-dimensional String array containing the matrix, that is all values from the matrix variable.
     * 
     * @return matrix
     */
    public String[][] getMatrix(){
        return this.matrix;
    }

    /**
     * Tells whether the key String array contains duplicate letters or not.
     * It is used to validate a key that will be set up in "ciphertext squares" (upper-right or lower-left quadrants of the {@code matrix}).
     * <p>
     * The checking process runs in for loops as follows:
     * In the first loop it gets the 'letter' from the key String array. In the second loop this letter is compared with the rest of the letters from the key String array.
     * Both loops brakes when there is the first occurance of duplicate letter and the false is returned.
     * Otherwise the true is returned meaning, there is no duplicate letters in the key String array.
     * <p>
     * The key String array can not contain any null value.
     * <p>
     * This method differenciate between upper and lower case.
     * 
     * @param key the String array
     * @return {@code true} if there in no duplicate letters in the key String array, {@code false} if there is duplicate letter
     * @throw NullPointerException if the key String array contains null value or if the value of key String array is null
     */
    public boolean checkNoDuplicateLetters(String[] key) {
        // The value of this variable is returned.
        boolean noDuplicate = true;
        // This variable controls the breaking of the first for loop
        boolean run = true; 
        for(int letter = 0; letter < key.length; letter++){
            // Start this loop from 'letter + 1'. It will not compare the letter to itself and will not repeat the comparing process
            for(int i = (letter + 1); i < key.length; i++){
                // Compare the letters from first loop to the letter from second loop
                if(key[letter].equals(key[i])){
                    noDuplicate = false;
                    run = false; // Change to false to break the first loop
                    break;
                }
            }
            if(!run){
                break; // Break the first loop
            }
        }
        return noDuplicate;
    }

    /**
     * Returns String array containing no duplicate letters.
     * <p>
     * This method is used in the validation process of the key that will be set up in "ciphertext squares" (upper-right or lower-left quadrants of the {@code matrix}).
     * It removes the duplicate letters.
     * <p>
     * It takes the String array as an argument, removes the duplicate letters and returns the String array containing no duplicate letters.
     * <p>
     * It runs in two steps.
     * Step 1: The value in the key String array of the second and more occurance of the letter is change to null.
     * This process runs in for loops as follows:
     * In the first loop it gets the 'letter' from the key String array. In the second loop this letter is compared with the rest of the letters from the key String array.
     * If the letters are equal, the value of the equla letter is changed to null.
     * Step 2: The temp String array is created. It has the size of key String array minus the amount of null values.
     * It is populated with the values from key String array, ommiting the null values. Then it is returned.
     * <p>
     * This method differenciate between upper and lower case.
     * 
     * @param key the String array
     * @return temp the String array containing no duplicate letters
     * @throw NullPointerException if the value of the key String array is null
     * 
     */
    public String[] removeDuplicate(String[] key){
        // The value of temp variable is to be returned
        String[] temp = null;
        // Variable for counting the repeated letters. It is used to set up the correct size of the returned String array
        int countRepeated = 0;

        // Step 1: change the value in the 'key' of the second and more occurance of the letter to null
        for(int letter = 0; letter < key.length; letter++){
            // Start this loop from 'letter + 1'. It will not compare the letter to itself and will not repeat the comparing process
            for(int i = (letter + 1); i < key.length; i++){
                // Compare the String only if value is not null
                if(key[letter] != null && key[i] != null){ // Using 'equals()' method for String with the null value causes NullPointerException
                    if(key[letter].equals(key[i])){
                        // Change the value of repeated 'letter' to null
                        key[i] = null;
                        countRepeated++;
                    }
                }
            }
        }
        
        // Step 2: Populate the new String array. Ommit null values
        temp = new String[key.length - countRepeated];
        // Index for temp array
        int index = 0;
        for(int i = 0; i < key.length; i++){
            if(key[i] != null){
                temp[index] = key[i];
                index++;
            }
        }
        return temp;
    }
    
    /**
     * Tells whether the key String array contains only the valid values.
     * The valid value is a single upper-case letter from the range [A-Z], excluding 'J' or not.
     * It is used to validate a key that will be set up in "ciphertext squares" (upper-right or lower-left quadrants of the {@code matrix}).
     * <p>
     * The checking process runs in a loop. The loop breaks when there is the first occurance of incorrect letter and the false value is returned.
     * Otherwise the true value is returned meaning that all letters are correct in the key String array.
     * <p>
     * The key String array can not contain null any value.
     * <p>
     * This method differenciate between upper and lower case.
     * 
     * @param key the String array
     * @return {@code true} if there all letters are valid in the key String array, {@code false} if there is any invalid letter
     * @throw NullPointerException if the key String array contains null value or if the value of key String array is null
     */
    public boolean checkValidLetters(String[] key){
        boolean correct = true;
        for(int index = 0; index < key.length; index++){
            if(!key[index].matches("[A-IK-Z]{1}")){
                correct = false;
                break;
            }
        }
        return correct;
    }

    /**
     * Returns String array containing only the valid values for the keyword.
     * The valid value is a single upper-case letter from the range [A-Z], excluding 'J'.
     * <p>
     * This method is used in the validation process of the key that will be set up in "ciphertext squares" (upper-right or lower-left quadrants of the {@code matrix}).
     * It removes all the invalid letters: takes the String array as an argument, removes all the invalid letters and returns the String array containing only the valid letters.
     * <p>
     * The key String array can not contain any null value.
     * <p>
     * It runs in two steps.
     * Step 1: The value of the invalid letter in the key String array is changed to null.
     * Step 2: The temp String array is created. It has the size of key String array minus the amount of null values.
     * It is populated with the values from key String array, ommiting the null values. Then it is returned.
     * 
     * @param key the String array
     * @return temp the String array containing only the valid letters
     * @throw NullPointerException if the key String array contains null value or if the value of key String array is null
     */
    public String[] removeInvalidLetters(String[] key){
        // Variable for counting the invalid letters. It is used to set up the correct size of the returned String array
        int counter = 0;

        // Step 1: change the value in the 'key' of the invalid letter to null
        for(int index = 0; index < key.length; index++){
            if(!key[index].matches("[A-IK-Z]{1}")){
                key[index] = null;
                counter++;
            }
        }

        // Step 2: Populate the new String array. Ommit null values
        String[] temp = new String[key.length - counter];
        // Index for the temp array
        int index = 0;
        for(int i = 0; i < key.length; i++){
            if(key[i] != null){
                temp[index] = key[i];
                index++;
            }
        }
        return temp;
    }

    /**
     * Returns the String array containing 25 valid values for the keyword.
     * The valid value is a single upper-case letter from the range [A-Z], excluding 'J'.
     * <p>
     * This method is used for filling up a keyword with the missing letters, if there is less then 25 letters in it.
     * The keyword is filled with the remainin letters from the alphabet with alphabetical order.
     * <p>
     * The key String array can not contain any duplicate, invalid or null value.
     * <p>
     * It runs in two steps.
     * Step 1: The temp String array having size of 25 is populated with the letters from the key String array.
     * While running this process it checks which letter was used.
     * The value of the String in the alphabet String array is changed to null, if the letter occured in the key String array.
     * Step 2: The temp String array array is populated with the values from the alphabet String array, ommiting the null values.
     * Then it is returned.
     * 
     * @param key the String array
     * @return temp the String array containing 25 valid letters
     * @throw NullPointerException if the key String array contains null value 
     *                              or if the value of key String array is null
     * @throw ArrayIndexOutOfBoundsException if the key String array contains duplicate or invalid values
     */
    public String[] fillUpKey(String[] key) {
        int keyLength = key.length; // The length of the 'key' String array
        // Create temporary String array having the size of 25
        String[] tempKey = new String[25];
        // The String array of 25 alphabet letters (excluding J)
        String[] alphabet =
                          {"A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", 
                          "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        // Step 1: fill up the tempKey with the letters from the initial key using loop.
        for (int i = 0; i < keyLength; i++) {
            tempKey[i] = key[i];
            // Loop through the 'alphabet' array and check, which letter appear in the initial key
            for (int j = 0; j < 25; j++) {
                // Ommit when the value in the 'alphabet' String array is null
                if(alphabet[j] != null){ // Copmaring Strings using 'equals()' method, when String value is null, causes NullPointerException
                    // If the letter appear in the initial key, change the value in appropriate index of 'alphabet' to null
                    if (tempKey[i].equals(alphabet[j])) {
                        alphabet[j] = null;
                    }
                }
            }
        }

        // Step 2: fill up tempKey with the letters from the 'alphabet' array. Ommit the one with the null value
        for (int i = 0; i < 25; i++) {
            if (alphabet[i] != null) {
                tempKey[keyLength] = alphabet[i];
                keyLength++;
            }
        }
        
        return tempKey;
    }
    
    /**
     * Returns the String array containing 25 randomly shuffled valid values for the keyword.
     * The valid value is a single upper-case letter from the range [A-Z], excluding 'J'.
     * <p>
     * It uses Fisher-Yates shuffle algorithm.
     * 
     * @return keyRandom the String array containing 25 randomly shuffled valid values for the keyword
     */
    public String[] keyRandom(){
        // The String array of 25 alphabet letters (excluding J)
        String[] alphabet =
                          {"A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", 
                          "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        // Create String array having size of 25
        String[] keyRandom = new String[25];
        // Variable 'random' for storing the random number
        int random;
        // Variable 'index' for storing current index of 'keyRandom' array
        int index = 0;
        // Use for loop to deploy 'keyRandom' array with a randomly choosed String from 'alphabet' array
        for(int alphabetLength = 25; alphabetLength > 0; alphabetLength--){
            // Select the random number from 0 (including) to 'alphabetLength' (exluding)
            random = (int) Math.floor(Math.random() * alphabetLength);
            // Populate 'keyRandom' with the value from 'alphabet' having 'random' index.
            keyRandom[index] = alphabet[random];
            // Increment 'index'
            index++;
            // Change the 'alphabet' array to the new one that does not consist the chosen character
            // Create 'tempAlphabet' String array
            String[] tempAlphabet = new String[alphabetLength - 1];
            // Create variable 'j' for storing the index of 'tempAlphabet' array
            int j = 0;
            for(int i = 0; i < alphabetLength; i++){
                // Ommit the value from 'alphabet' having 'random' index. Use the rest of the letters and insert to the tempAlphabet array
                if(i != random){
                    tempAlphabet[j] = alphabet[i];
                    // Increment 'j' - the index of 'tempAlphabet' array
                    j++;
                }
            }
            // Swap 'alphabet' with 'tempAlphabet' array
            alphabet = tempAlphabet;
        }        
        return keyRandom;
    }

    /**
     * Returns a string representation of the contents of the stringArray String array.
     * 
     * @param stringArray the String array
     * @return s the String representation of the contents of the stringArray String array
     * @throw NullPointerException if the stringArray String array reference is null
     */
    public String arrayToString(String[] stringArray){
        StringBuffer sb = new StringBuffer();
        for(int i =0; i < stringArray.length; i++){
            sb = sb.append(stringArray[i]);
        }
        String s = sb.toString();
        return s;
    }

    /**
     * Returns the String array containing encrypted content of of the plainText String array
     * or null value if:
     * - the plainText String array contains null value
     * - a bigram of the plainText String array contains invalid letters.
     * <p>
     * The valid value of the plainText String array (called further the plaintext bigram) must consist of exacly 2 letters from the range [A-Z], excluding "J", ie. "AB".
     * The null value is returned and the appropriate information is displaied to the console if there are invalid letter in the plaintext bigram.
     * The NullPointerException is thrown if there is less than 2 letters in the plaintext bigram.
     * <p>
     * This method uses global matrix variable to process the encryption.
     * Before running this method the "ciphertext squares" (upper-right and lower-left quadrants of the 'matrix' variable)
     * must be populated with the correct keywords.
     * <p>
     * The encryption process runs as follows:
     * - locates each of the two letters in the bigram (the value of the plainText String array) in the top left and the bootom right 'matrix' square respectively.
     * - gets the indices for both found letters and assing the value to row and col
     * - locates the two ciphered letters in the top right and the bootom left 'matrix' square respectively by using appropriate values of row and col from the plaintext bigram letters
     * - concatenates the ciphered letters to single String
     * - adds the ciphered String to String array, that is returned at the end of process.
     * <p>
     * The process runs in the for loops. Once the index of the plaintext bigram letter is found in the 'matrix' both loop are broken.
     * 
     * @param plainText the String array containing valid bigrams
     * @return encryptedText the String array containing encrypted bigrams
     * @throw NullPointerException if the value of the plainText String array is null
     * @throw StringIndexOutOfBoundsException if the plainText String array contains less than 2 letters in a bigram
     */
    public String[] encrypt(String[] plainText) {
        // The value of this variable will be returned
        String[] encryptedText = new String[plainText.length];

/*
 * EXTRA COMMENTS FOR MYSELF :)
 * In Java, class and instance variables assume a default value (null, 0, false) if they are not initialized manually.
 * However, local variables don't have a default value.
 * Unless a local variable has been assigned a value, the compiler will refuse to compile the code that reads it.
 */

        // StringBuilder for processing the plaintext bigram
        StringBuilder sbPlain = new StringBuilder();
        // StringBuilder for processing the encrypted letters
        StringBuilder sbEncrypt = new StringBuilder();

/* 
 * EXTRA COMMENTS FOR MYSELF :)
 * Instanciate the StringBuilder before the loop and reuse it
 * It should performed faster than instanciating the StringBuilder inside the loop in each circle of a loop
 * 
 * String vs StringBuffer vs StringBuilder:
 * 1) String is immutable whereas StringBuffer and StringBuider are mutable classes.
 * 2) StringBuffer is thread safe and synchronized whereas StringBuilder is not, thats why StringBuilder is more faster than StringBuffer.
 * 3) String concat + operator internally uses StringBuffer or StringBuilder class.
 * 4) For String manipulations in non-multi threaded environment, we should use StringBuilder else use StringBuffer class.
 */
        
        for(int i = 0; i < plainText.length; i++){
            // Varialbes for storing indices of encrypted letter values in the 'matrix'.
            // Assign them the value '-1'. It is used for cheking if the letter from plaintext bigram was found in 'matrix'
            int rowQ2 = -1; // Row of the letterQ2
            int colQ2 = -1; // Col of the letterQ2
            int rowQ3 = -1; // Row of the letterQ3
            int colQ3 = -1; // Col of the letterQ3
            
            // Step 1: Find the indices of the plaintext bigram letters (letterQ1 and letterQ4)
            sbPlain.append(plainText[i]);
            // Find the indices of the first letter from the bigram (letterQ1) in the square Q1 of the 'matrix'
            String letterQ1 = sbPlain.substring(0, 1); // Get the letterQ1
            for(int rowQ1 = 0; rowQ1 < 5; rowQ1++){ // Run this loop until the letterQ1 is found in the 'matrix'
                boolean run = true; // This variable controls the breaking of the current loop
                for(int colQ1 = 0; colQ1 < 5; colQ1++){
                    if(matrix[rowQ1][colQ1].equals(letterQ1)){
                        rowQ2 = rowQ1;
                        colQ3 = colQ1;
                        run = false; // Change this variable to false to break the previous loop
                        break;
                    }
                }
                if(!run){ // Check if loop should be broken
                    break;
                }
            }

            // Find the indices of the second letter from the bigram (letterQ4) in the square Q4 of the 'matrix'
            String letterQ4 = sbPlain.substring(1, 2); // Get the letterQ4
            for(int rowQ4 = 5; rowQ4 < 10; rowQ4++){ // Run this loop until the letterQ4 is found in the 'matrix'
                boolean run = true; // This variable controls the breaking of the current loop
                for(int colQ4 = 5; colQ4 < 10; colQ4++){
                    if(matrix[rowQ4][colQ4].equals(letterQ4)){
                        rowQ3 = rowQ4;
                        colQ2 = colQ4;
                        run = false; // Change this variable to false to break the previous loop
                        break;
                    }
                }
                if(!run){ // Check if loop should be broken
                    break;
                }
            }
            sbPlain.delete(0, sbPlain.length()); // Clear the StringBuilder to reuse it in the next circle of the loop

            // Step 2: Find the appriopriate letters in ciphertext squares: Q2 and Q3 and add into encryptedText Sting array
            if(rowQ2 != -1 & colQ2 != -1 & rowQ3 != -1 & colQ3 != -1){ // Make sure that both letterQ1 nad letterQ4 were found in the 'matrix'
                String letterQ2 = matrix[rowQ2][colQ2];
                String letterQ3 = matrix[rowQ3][colQ3];
                String encryptedBigram = sbEncrypt.append(letterQ2).append(letterQ3).toString();
                encryptedText[i] = encryptedBigram; // Add 'encryptedBigram' to the 'encryptedText' String array
                sbEncrypt.delete(0, sbEncrypt.length()); // Clear the StringBuilder to reuse it in the next circle of the loop
            }else{
                System.out.println("The plain text has invalid character. The character was not found in the matrix. It can not be ciphered.");
                encryptedText = null;
                break;
            }
        }
        return encryptedText;
    }
    
    /**
     * Returns the String array containing decrypted content of of the encryptedText String array
     * or null value if:
     * - the encryptedText String array contains null value
     * - a bigram of the encryptedText String array contains invalid letters.
     * <p>
     * The valid value of the encryptedText String array (called further the plaintext bigram) must consist of exacly 2 letters from the range [A-Z], excluding "J", ie. "AB".
     * The null value is returned and the appropriate information is displayed to the console if there are invalid letter in the encryptedtext bigram.
     * The NullPointerException is thrown if there is less than 2 letters in the encryptedtext bigram.
     * <p>
     * This method uses global matrix variable to process the decryption.
     * Before running this method the "ciphertext squares" (upper-right and lower-left quadrants of the 'matrix' variable)
     * must be populated with the correct keywords.
     * <p>
     * The decryption process runs as follows:
     * - locates each of the two letters in the encrypted text bigram in the top right and the bootom left 'matrix' square respectively.
     * - gets the indices for both found letters and assing the value to row and col
     * - locates the two plain letters in the top left and the bootom right 'matrix' square respectively by using appropriate values of row and col from the encrypted text bigram letters
     * - concatenates both plain letters to single String
     * - adds the plain String to String array, that is returned at the end of process.
     * <p>
     * The process runs in the for loops. Once the indices of the encrypted text bigram letter is found in the 'matrix' both loop are broken.
     * 
     * @param encryptedText the String array containing valid bigrams
     * @return decryptedText the String array containing decrypted bigrams
     * @throw NullPointerException if the value of the encryptedText String array is null
     * @throw StringIndexOutOfBoundsException if the encryptedText String array contains less than 2 letters in a bigram
     */
    public String[] decrypt(String[] encryptedText) {
        // The value of this variable will be returned
        String[] decryptedText = new String[encryptedText.length];
        // StringBuilder for processing the encrypted bigram letters
        StringBuilder sbEncrypt = new StringBuilder(2);
        // StringBuilder for processing the decrypted bigram letters
        StringBuilder sbDecrypt = new StringBuilder(2);
        for(int i = 0; i < encryptedText.length; i++){
            // Varialbes for storing indices of decrypted letter values in the 'matrix'.
            // Assign them the value '-1'. It is used for cheking if the letter from ecnrypted bigram was found in 'matrix'
            int rowQ1 = -1; // Row of the letterQ1
            int colQ1 = -1; // Col of the letterQ1
            int rowQ4 = -1; // Row of the letterQ4
            int colQ4 = -1; // Col of the letterQ4

            // Step 1: Find the indices of the encrypted bigram letters (letterQ2 and letterQ3)
            sbEncrypt.append(encryptedText[i]);
            // Find the indices of the first letter from the bigram (letterQ2) in the square Q2 of the 'matrix'
            String letterQ2 = sbEncrypt.substring(0, 1); // Get the letterQ2
            for(int rowQ2 = 0; rowQ2 < 5; rowQ2++){ // Run this loop until the letterQ2 is found in the 'matrix'
                boolean run = true; // This variable controls the breaking of the current loop
                for(int colQ2 = 5; colQ2 < 10; colQ2++){
                    if(matrix[rowQ2][colQ2].equals(letterQ2)){
                        rowQ1 = rowQ2;
                        colQ4 = colQ2;
                        run = false; // Change to false to break the previous loop
                        break;
                    }
                }
                if(!run){ // Check if loop should be broken
                    break;
                }
            }

            // Find the indices of the second letter from the bigram (letterQ3) in the square Q3 of the 'matrix'
            String letterQ3 = sbEncrypt.substring(1, 2); // Get the letterQ3
            for(int rowQ3 = 5; rowQ3 < 10; rowQ3++){ // Run this loop until the letterQ3 is found in the 'matrix'
                boolean run = true;  // This variable controls the breaking of the current loop
                for(int colQ3 = 0; colQ3 < 5; colQ3++){
                    if(matrix[rowQ3][colQ3].equals(letterQ3)){
                        rowQ4 = rowQ3;
                        colQ1 = colQ3;
                        run = false; // Change to false to break the previous loop
                        break;
                    }
                }
                if(!run){ // Check if loop should be broken
                    break;
                }
            }
            sbEncrypt.delete(0, sbEncrypt.length()); // Clear the StringBuilder to reuse it in the next circle of the loop

            // Step 2: Find the appriopriate letters in plaintext squares: Q1 and Q4 and add into decryptedText Sting array
            if(rowQ1 != -1 & colQ1 != -1 & rowQ4 != -1 & colQ4 != -1){ // Make sure that both letterQ2 nad letterQ3 were found in the 'matrix'
                String letterQ1 = matrix[rowQ1][colQ1];
                String letterQ4 = matrix[rowQ4][colQ4];
                String decryptedBigram = sbDecrypt.append(letterQ1).append(letterQ4).toString();
                decryptedText[i] = decryptedBigram; // Add 'decryptedBigram' to the 'decryptedText' String array
                sbDecrypt.delete(0, sbDecrypt.length()); // Clear the StringBuilder to reuse it in the next circle of the loop
            }else{
                System.out.println("The ciphered text has invalid character. The character was not found in the matrix. It can not be encrypted.");
                decryptedText = null;
                break;
            }
        }
        return decryptedText;
    }
}