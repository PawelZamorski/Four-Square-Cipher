
########## FORUSQUARE CIPHER ############
version 1.1

### OVERWIEV ###
 
The FOURSQUARE CIPHER is a command line program for encrypting and decrypting a file or URL.

### AUTHOR ###
Pawel Zamorski, G00364553@gmit.ie

### INSTALL ###

The java files are contained in the ie.gmit.dip Java package.

List of Files:
	- Runner.java
	- Menu.java
	- Cipher.java
	- Parser.java
	- Saver.java
	- README.txt: this file

To compile Java files use the following command:
javac ie/gmit/dip/*.java

To start the program use the following command:
Java ie.gmit.dip.Runner

### GETTING STARTED ###

Once the program is lunched the menu is displayed to the console. It is a multi-level menu. The menu tree is presented below:

--- MAIN MENU ---
(1) Keys Menu
	--- KEYS MENU ---
        (1) Insert the keys to the console
		--- INSERT KEY MENU ---
            	(1) Insert the first key (keyQ2)
            	(2) Insert the second key (keyQ3)
            	(3) Insert one keyword for both keys (keyQ2 & keyQ3)
            	(4) Go back to -> KEY MENU
        (2) Insert the keys from the file
        (3) Insert the random keys
        (4) Save or display the inserted keys
		--- SAVE OR DISPLAY KEY MENU ---
		(1) Save keys to the file
		(2) Display keys to the console
            	(3) Display matrix to the console
            	(4) Go back
        (5) Go to the MAIN MENU
(2) Encrypt Menu
	--- ENCRYPT MENU ---
	(1) Encrypt file
        (2) Encrypt URL
        (3) Save or display to the console
		--- SAVE OR DISPLAY TEXT MENU ---
		(1) Save text to the file
            	(2) Display text to the console
            	(3) Go back
        (4) Go to the MAIN MENU	
(3) Decrypt Menu
	--- DECRYPT MENU ---
        (1) Decrypt file
        (2) Save or display to the console
		--- SAVE OR DISPLAY TEXT MENU ---
		(1) Save text to the file
            	(2) Display text to the console
            	(3) Go back
        (3) Go to the MAIN MENU
(4) Quit


Only integers from the given range may be used for selecting the option from the menu.

There is also a menu during the validation process of the keyword (keyQ2 or keyQ3). It enables the User to process automaticly through checking invalid letters, duplicate letters or populate the keyword with the missing letters. The User has option to procedure or to quit the validation process.

### EXTRAS ###

The program has the option to set up a random key. The Fisher–Yates shuffle algorithm is used to generate it.

The file size is checked to set up a size of the file. It was done in order to speed up a process of parsing a file or URL. The size of array is a half of the file/URL size, that should be enough. However, if during parsing process the size of array is too small, the size (sum of the current array size and the size of array from which the data are taken) is doubled.

### RUNNING THE TEST ###

There is a test method in Menu class. It may be run by inserting 0 in the MAIN MANU.

This method tests the part of the program: setting up the keywords, encrypting and decrypting. It is based on the example given in a project description.

        String[] keyQ2 = {"Z", "G", "P", "T", "F", "O", "I", "H", "M", "U", "W", "D", "R", "C", "N", "Y", "K", "E", "Q", "A", "X", "V", "S", "B", "L"};
        String[] keyQ3 = {"M", "F", "N", "B", "D", "C", "R", "H", "S", "A", "X", "Y", "O", "G", "V", "I", "T", "U", "E", "W", "L", "Q", "Z", "K", "P"};
        String plaintext = "THECURFEWTOLLSTHEKNELLOFPARTINGDAY";
        String ciphertext = "ESPDKWUMBTWGRIESFANNWXWSWDQTHGMFTL";
        String[] plaintextBigram = {"TH", "EC", "UR", "FE", "WT", "OL", "LS", "TH", "EK", "NE", "LL", "OF", "PA", "RT", "IN", "GD", "AY"};
        String[] ciphertextBigram = {"ES", "PD", "KW", "UM", "BT", "WG", "RI", "ES", "FA", "NN", "WX", "WS", "WD", "QT", "HG", "MF", "TL"};

It sets up keywords, gets from the matrix and check (after converting to the String) if original one is equal the one from the matrix.

It takes plaintext and ciphertext, encrypting and decrypting them respectively.
Then it checks if plaintext is equal decrypted text, and if ciphertext is equal encrypted text.

The final result is that all above are true.

Running this test does not change the global variable correctKeyQ2 and correctKeyQ3 that are used in other part of the Menu class.
