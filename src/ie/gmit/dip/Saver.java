package ie.gmit.dip;

import java.io.*;

/**
 * The Saver class is used for saving text to the file.
 * 
 * @author  Pawel Zamorski
 * @version 1.0, 30 August 2018
 */
public class Saver {

    /**
     * Saves the text to the file.
     * <p>
     * It takes the String array and saves the values of this String array to the given file.
     * - String 'path': a file name to write to
     * - String array's': data to be saved
     * 
     * The 'path' can not be null. Otherwise it throws NullPointerException.
     * The 's' is processed in loop while reading data from array. It can not be null. Otherwise it throws NullPointerException.
     * 
     * This method throws NullPointerException, IOException, Exception. 
     * 
     * @param pathname the String with the name of the file to be written to
     * @param s the String array containing the Strings that will be save to the file.
     * @throws NullPointerException     if the value of the pathname String or the s String array is null
     *                                  or if the s String array contains null value
     * @throws FileNotFoundException    if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened
     * @throws IOException  if an I/O error occurs
     * @throws Exception    other exceptions
     */
    public void saveFile(String pathname, String[] s) throws NullPointerException, FileNotFoundException, IOException, Exception{
        if(pathname == null){ // if s is null, it throws NullPointerException
            throw new NullPointerException("Invalid argument: the 'path' value is null. The file name was not specify.");
        }
        if(s == null){ // if s is null, it throws NullPointerException (the loop is used below to read data from the array)
            throw new NullPointerException("Invalid argument: the 's' value is null. The data to be saved doesn't exist.");
        }
        
/*
 * REMEMBER
 * The try-with-resources statement is a try statement that declares one or more resources.
 * A resource is an object that must be closed after the program is finished with it.
 * The try-with-resources statement ensures that each resource is closed at the end of the statement.
 * Any object that implements java.lang.AutoCloseable, 
 * which includes all objects which implement java.io.Closeable, can be used as a resource.
 */
        // Use the try-with-resources statement. There is no need for finally block.
        try(
            FileWriter fw = new FileWriter(pathname);
            BufferedWriter br = new BufferedWriter(fw)
        ){
            for(int i = 0; i < s.length; i++){
                br.write(s[i]);
                // Add a line break after 70 characters (String array consist of bigrams, ie "AB" - each bigram has two characters. So, 70/2 = 35)
                if((i + 1)%35 == 0){
                    br.newLine();
                }
            }
        }
    }

    /**
     * Saves the text to the file. This method specialises in saving both keywords (keyQ2 and keyQ3) to the file.
     * The keys are written into separate lines. Make sure to write the keywords in the correct order.
     * <p>
     * 
     * @param pathname the String with the name of the file to be written to
     * @param keyQ2 the String containing the keyQ2 keyword
     * @param keyQ3 the String containing the keyQ3 keyword
     * @throws NullPointerException     if the value of the pathname String, the keyQ2 String or the keyQ3 String is null
     * @throws FileNotFoundException    if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened
     * @throws IOException  if an I/O error occurs
     * @throws Exception    other exceptions
     */
    public void saveKey(String pathname, String keyQ2, String keyQ3) throws NullPointerException, FileNotFoundException, IOException, Exception {
        if(pathname == null){ // if s is null, it throws NullPointerException
            throw new NullPointerException("Invalid argument: the 'pathname' value is null. The file name was not specify.");
        }
        if(keyQ2 == null){ // if s is null, it throws NullPointerException (the loop is used below to read data from the array)
            throw new NullPointerException("Invalid argument: the 'keyQ2' value is null. The data to be saved doesn't exist.");
        }
        if(keyQ3 == null){ // if s is null, it throws NullPointerException (the loop is used below to read data from the array)
            throw new NullPointerException("Invalid argument: the 'keyQ3' value is null. The data to be saved doesn't exist.");
        }

        BufferedWriter br = null;
        try{
            br = new BufferedWriter(new FileWriter(pathname));
            br.write(keyQ2);
            br.newLine();
            br.write(keyQ3);
        }finally{
            if(br != null){
                br.flush();
                br.close();                
            }
        }
    }
}