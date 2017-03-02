/* Larson Young
 * CSC 249
 * Lab02 
 */

import java.util.Scanner;

public class Vigenere {

	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);		//scanner object
		System.out.print("Enter your message: ");
		String message = scan.nextLine();
		message = message.toLowerCase();			//convert message toLowerCase()				
		System.out.print("Enter the key(one word): ");
		String key = scan.nextLine();
		key = key.toLowerCase();					//convert key toLowerCase()	
		System.out.println("The encoded message is: " + encrypt(message,key));
		System.out.println("The decoded message is: " + decrypt(encrypt(message,key), key));
		
		
	}
	 
	public static char[][] createVigSquare()
	{
		char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();		//create char array of alphabet letters
		char[][] alphArray = new char[alpha.length][26];				//make 2d array dimensions alpha.length x 26
		for (int i = 0; i < alpha.length; i++)
		{
			for (int j = 0; j < alpha.length; j++)
			{
				if((j + i) < alpha.length)								//if j+i is less than alpha.length (26), set that coordinate to alpha[i + j]
				{														//rows will keep getting smaller because j + i will start with 1, 2, 3, and so on
					alphArray[i][j] = alpha[j + i];						//2nd row is bcdefg, 3rd is cdefghi, 4th is defghijk.....
				}
				else if((j + i) >= alpha.length)						//if j + i is greater than or equal to 26 (if it is at 'z') bring it back to 0 and
				{														//start it over (start it back at 'a' the 1st time, 'b', 'a', the 2nd time....)
					alphArray[i][j] = alpha[j - (alpha.length-i)];
				}
				//System.out.print(alphArray[i][j] + " ");				//print for testing
			}
			
			//System.out.println();										//print for testing
		}  
		return alphArray;
	}
	
	public static int getColIndex(char ch)							
	{																
		int col = -1;
		char [][] vigSquare = createVigSquare();
		for (int i = 0; i < 26; i ++)
		{
			if (vigSquare[0][i] == ch)
			{
				col = i;
			}
		}
		return col;
	}
	
	public static int getRowIndex(char keyCh)
	{
		int row = -1;
		char [][] vigSquare = createVigSquare();
		for (int i = 0; i < 26; i ++)
		{
			if (vigSquare[i][0] == keyCh)
			{
				row = i;
			}
		}
		return row;
		
	}
	
	public static String encrypt(String message, String key)
	{
		char [][] vigSquare = createVigSquare();
		char [] mess = message.toCharArray();
		char [] messKey = key.toCharArray();
		
        char [] encryptedMessage = new char[mess.length];
        int messKeyCount = 0;
       	for (int i = 0; i < mess.length; i++)
		{
            // *** check if char is not alphabetic, just place in 
            // *** encrypted string - as is ...
			char ch = mess[i];
			if (Character.isAlphabetic(ch))
			{
                
                //if((i + key.length()) % key.length() == 0)
                if (messKeyCount >= key.length())    
                {
                    messKeyCount = 0;
                }

                int rowIndex = getRowIndex(messKey[messKeyCount]);
                int columnIndex = getColIndex(mess[i]);
                
                encryptedMessage[i] = vigSquare[rowIndex][columnIndex];
                messKeyCount++;
                
			}
			else
			{
				encryptedMessage[i] = ch;
			}
		}
		//String eMessage = encryptedMessage.toString();		//to.String() must've been causing a pointer so set eMessage to "" and add the char[i]
        String eMessage = "";									//to the end of it
		for (int i = 0; i < encryptedMessage.length; i++)
		{
            eMessage += encryptedMessage[i];
            
		}
		return eMessage;
		
	}
	
	public static char getPlainTextChar(int rowIndex, char ch)		//finds plainText char of original message
	{
		char [][] vigSquare = createVigSquare();
		char [] alpha = vigSquare[rowIndex];
		String alphaStr = "";
		for(int i = 0; i < alpha.length; i++)
		{
			alphaStr += alpha[i];
		}
		int loc = alphaStr.indexOf(ch);
		char plainText = vigSquare[0][loc];
		
		return plainText;
	}
	
	
	public static String decrypt(String message, String key)
	{
		//*** same for decrypt --
		char [] mess = message.toCharArray();
		char [] messKey = key.toCharArray();
		int messKeyCount = 0;
        String decodedMessage = "";
       
		// loop through the plainText characters to encode them
		for (int i = 0; i < message.length(); i++)
		{
			// grab plain text
			char ch = message.charAt(i);
			if (Character.isAlphabetic(ch))
			{
				if (messKeyCount >= key.length())    
                {
                    messKeyCount = 0;
                }
				// decrypt
                
                decodedMessage += getPlainTextChar(getRowIndex(messKey[messKeyCount]),mess[i]);
                messKeyCount++;
			}
			else
			{
                  // just add char as is
				decodedMessage += ch;
			}
		}
		return decodedMessage;
	}

}
