package ie.gmit.dip;

import java.io.*;
import java.net.*;

/**
 * The Parser class is used for reading characters from the file or URL and processing them into the required format.
 * <p>
 * For the purpose of encryption or decryption process, the valid format is a set of the bigrams.
 * The bigram is a set of exacly two characters, ie: "AB", "CD", ect.
 * The valid bigram may contain only upper case letter from the range [A-Z], excluding 'J'.
 * It means, that from the file or URL:
 * - all lower case letters are converted to upper case letters
 * - the relarively rare letter 'J' is merged with the letter 'I'
 * - all invalid characters are removed
 * - all empty lines are removed
 * <p>
 * The bigram consists of two characters.
 * In case there is uneven number of characters in processed text, an 'X' is appended to the end to ensure the even length.
 * <p>
 * For the purpose of getting a keyword from the text the valid format is a String array containing two keywords
 * 
 * @author  Pawel Zamorski
 * @version 1.0, 30 August 2018
 */
public class Parser {
    /**
     * The default initial size of {@code charPairs} field.
     */
    private int SIZE = 1000;
    /**
     * Max size of the {@code charPairs} String[]
     */
    private final int MAX_SIZE = Integer.MAX_VALUE;
    /**
     * The charPairs are returned from {@code parse} method. It is the set of bigrams ready to encrypt or decrypt.
     */
    private String[] charPairs = null;
    /**
     * The current available index of the {@code charPairs} String[]. Used to populate charPairs with bigrams.
     */
    private int index = 0;

    /**
     * Returns {@code charPairs} String[] conntaining the pairs of characters (bigrams) valid to encrypt or decrypt, ie: "AB", "XZ" ect.
     * <p>
     * A pair of characters may only consist of the upper case letters from "A" to "Z" excluding "J" that is replaced by "I".
     * <p>
     * At first step it reads line from the file or URL, depending on isURL . It may thorw the exceptions (file, URL).
     * Then it removes all the characters that are not the letters.
     * Then it changes the letters to the upper cases.
     * Then it replaces "J" by "I".
     * If the length of the string is uneven it adds "X" as a last letter to the string.
     * Then it creates the pair of characters and inserts it to the String array that is returned.
     * 
     * @param resource      the file or URL path
     * @param isURL         {@code true} if it is URL, {@code false} if it is a file
     * @return              {@code charPairs} bigrams valid to encrypt or decrypt or zero-length String[] if there was no valid character in the file or URL
     * @trrows FileNotFoundException    if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading
     * @throws MalformedURLException    no legal protocol could be found in a specification string or the string could not be parsed
     * @throws IOException  if an I/O error occurs
     * @throws Exception    other exceptions
     */
    public String[] parse(String resource, boolean isURL) throws FileNotFoundException, MalformedURLException, IOException, Exception {        
        BufferedReader br = null;
        // The size of the file or URL that is parsed
        long fileSize = 0;

        try{
            if(isURL){
/* 
 * EXTRA COMMENTS FOR MYSELF :)
 * When using URLConnection there is no need to close the connection or close the underlying sockets.
 * However, the stream derived from the URLConnection must be closed.
 * In the HTTP request-response stateless protocol, the server will automatically close the socket after sending its response, 
 * and the client will automatically close its socket after receiving the response.
 * URLConnection is a 'high-level' class relieving programmer of the burden of dealing with the 'low-level' class like Socket.
 * 
 * What's the “Content-Length” field in HTTP header?
 * https://stackoverflow.com/questions/2773396/whats-the-content-length-field-in-http-header
 */

                // Create an instance of URL class, passing the String 'resource' to the constructor
                URL url = new URL(resource);
                // Use 'URLConnection' abstract class to use its methods. (Abstract classes cannot be instantiated, but they can be subclassed)
                // Use 'openConnection' method from 'URL' class. It returns a URLConnection instance that represents a connection to the remote object referred to by the URL
                URLConnection urlConn = url.openConnection();
                // Get the size of the file. 'getContentLengthLong' method returns the value of the content-length header field as a long
                fileSize = urlConn.getContentLengthLong();
                // Check if the half of 'fileSize' is not bigger than the max value of int.
                // If it is not, set up 'SIZE' to the half of 'fileSize'. Otherwise 'SIZE' will be default
                if(fileSize/2 <= (long) MAX_SIZE){
                    SIZE = (int) (fileSize/2);
                }
                charPairs = new String[SIZE];
                
                // Display the size for testing purpose
                System.out.println("Size of the initial 'charPairs' array: " + SIZE);
/*
 * EXTRA COMMENTS FOR MYSELF :)
 * Create an instance of BufferedReader object. It is a wrapper for InputStreamReader, that is a wrapper for InputStream
 * getInputStream() returns InputStream object.
 * 'openStream' method from URL class, that returns InputStream object, is a shorhand for openConnection().getInputStream() from URLConnection class
 */
                br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            }else{
                // Get the size of the file using 'length' method from File class
                File f = new File(resource);
                fileSize = f.length();
                // Check if the half of 'fileSize' is not bigger than the MAX_SIZE
                // If it is not, set up 'SIZE' to the half of 'fileSize'. Otherwise 'SIZE' will be default
                if(fileSize/2 <= (long) MAX_SIZE){
                    SIZE = (int) (fileSize/2);
                }

                // Display the size for testing purpose
                System.out.println("Size of the initial 'charPairs' array: " + SIZE);

                charPairs = new String[SIZE];            
                // Create an instance of BufferedReader class. It is a wrapper for a InputStreamReader, that is a wrapper for a FileInputStream
                br = new BufferedReader(new InputStreamReader(new FileInputStream(resource)));
            }
                        
            // Variable for storing the currently parsed line from the file or URL
            String line = null;
            while((line = br.readLine()) != null){
                // Remove all characters from the line that are not the letters. There may be an empty line after this operation
                line = line.replaceAll("[^A-Za-z]", "");
                // Check if the line is not empty.
                if(!line.equals("")){
                    // Change all letters to upper case
                    line = line.toUpperCase();
                    // Change 'J' to 'I'
                    line = line.replace('J', 'I');
                    // Add 'X' as a last letter in the line if the length of the line is uneven
                    if((line.length() % 2) != 0){
                        line = line.concat("X");
                    }
                    // Split the line into bigrams. (regex: lookbehind, start from the end of the previous match, match any character, exacly two)
                    String[] bigrams = line.split("(?<=\\G.{2})");
                    addBigrams(bigrams); // Add bigrams to the 'charPairs' global variable String array
                }
            }
        
            // Trim 'charPairs' array by removing all null values.
            String[] temp = new String[index]; //  The length of the final 'charPairs' array is equal 'index' value.
            for(int i = 0; i < index; i++){
                temp[i] = charPairs[i];
            }           
            charPairs = temp;
            
        }finally{ // Use finally block to close BufferedReader
            if(br != null){ // Close if BufferReader is not null. Otherwise it was not instantiated
                br.close();
            }
        }

        // Display the the size of the final charPairs arrayfor testing purposes
        System.out.println("Size of the final 'charPairs' array: " + charPairs.length);            

        return charPairs;
    }

    /**
     * Adds the bigram/-s to the {@code charPairs} String[]
     */
    private void addBigrams(String[] s){
        int length = s.length;
        // Use 'ensureCapacity' method to check if the size of 'charPairs' array is enough to append bigrams from 's' String array
        ensureCapacity(length);
        // Add the bigrams to the 'charPairs' array
        for(int i = 0; i < length; i++){
            charPairs[index] = s[i];
            index++; // Increment 'index'
        }
    }
    
    /**
     * This method checks if the size of {@code charPairs} String[] is enough to add the next String to this array.
     * <p>
     * If the size of the charPairs String[] is lower or equal to the sum of {@code index} and the length of the appended array of bigrams,
     * the size of the charPairs String[] is change to a double of the sum of {@code index} and the length of the appended array of bigrams
     * 
     * @param length int that is a size of the String[] from which the String will be appended to the charPairs String[]
     */
    private void ensureCapacity(int length){
        int newSize = 0;
        // Check if the capacity of 'charPairs' String array is greater than a sum of 'index' and 'length'
        if((index + length) >= charPairs.length){
            // If the length of the 'charPairs' array is lower than a sum of 'index' and 'length', expand the array twice of the sum of the 'index' and 'length'.
            if((index + length) * 2 <= MAX_SIZE){ // The new size of array can not be greater then MAX_SIZE
                newSize = (index + length) * 2;
            }else{
                newSize = MAX_SIZE;
            }
            String[] temp = new String[newSize];
            for(int i = 0; i < index; i++){
                temp[i] = charPairs[i];
            }
            charPairs = temp;

            // Display for test purposes the nes size of charPairs array
            System.out.println("The new size of chairPairs array (ensureCapacity method) is: " + charPairs.length);
        }
    }

    /**
     * Returns {@code keywords} String[2] containing the keywords (keyQ2 and keyQ3).
     * <p>
     * The methods reads only two first lines from the file.
     * The keyQ2 must be in the first line of the file.
     * The keyQ3 must be in the second line of the file.
     * 
     * @param resource      the file path
     * @return {@code keywords} String[2], where keyQ2 = keywords[0] and keyQ3 = keywords[1], if the file contain keywords in the first two lines
     *          Otherwise return:
     *          String[2] containing invalid keywords
     *          or null if there was no first or second line,
     *          or empty line if the line was empty
     * @trrows FileNotFoundException    if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading
     * @throws IOException  if an I/O error occurs
     * @throws Exception    other exceptions
     */    
    public String[] parseKeywords(String resource) throws FileNotFoundException, IOException, Exception{
        String[] keywords = new String[2];
        
        BufferedReader br = null;
        try{
            // Create an instance of BufferedReader class. It is a wrapper for a InputStreamReader, that is a wrapper for a FileInputStream
            br = new BufferedReader(new InputStreamReader(new FileInputStream(resource)));
            for(int i = 0; i < keywords.length; i++){ // Read only two first lines from the file
                keywords[i] = br.readLine();
            }
        }finally{
            if(br != null){
                br.close();
            }
        }
        return keywords;
    }    
}
