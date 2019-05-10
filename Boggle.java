// Java program for Boggle game 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
public class Boggle { 
	
	// Alphabet size 
	static final int SIZE = 26; 
	
	static int score=0;
	static int flag=0;

	private static BufferedReader in;
	
	

    // function to check whether a word is a valid english word or not
	public static boolean contains(String word) {
        System.out.println(word);
        try {
            in = new BufferedReader(new FileReader(
                "words"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.equals(word)) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
        }

        return false;
    }
	
	// trie Node 
	static class TrieNode 
	{ 
		TrieNode[] Child = new TrieNode[SIZE]; 
	
		// isLeaf is true if the node represents 
		// end of a word 
		boolean leaf; 
		
		//constructor 
		public TrieNode() { 
			leaf = false; 
			for (int i =0 ; i< SIZE ; i++) 
			Child[i] = null; 
		} 
	} 
	
	// If not present, inserts a key into the trie 
	// If the key is a prefix of trie node, just 
	// marks leaf node 
	static void insert(TrieNode root, String Key) 
	{ 
		int n = Key.length(); 
		TrieNode pChild = root; 
	
		for (int i=0; i<n; i++) 
		{ 
			int index = Key.charAt(i) - 'a'; 
	
			if (pChild.Child[index] == null) 
				pChild.Child[index] = new TrieNode(); 
	
			pChild = pChild.Child[index]; 
		} 
	
		// make last node as leaf node 
		pChild.leaf = true; 
	} 
	
	// function to check that current location 
	// (i and j) is in matrix range 
	static boolean isSafe(int i, int j, boolean visited[][], int len) 
	{ 
		return (i >=0 && i < len && j >=0 && 
				j < len && !visited[i][j]); 
	} 
	
	// A recursive function to print all words present on boggle 
	static void searchWord(TrieNode root, char boggle[][], int i, 
					int j, boolean visited[][], String str, int len) 
	{ 
		// if we found word in trie / dictionary 8
		
		if (root.leaf == true){ 
			System.out.println("Correct answer");
			score=score+str.length();
			flag=1;
		}
		// If both I and j in range and we visited 
		// that element of matrix first time 
		if (isSafe(i, j, visited, len)) 
		{ 
			// make it visited 
			visited[i][j] = true; 
	
			// traverse all child of current root 
			for (int K =0; K < SIZE; K++) 
			{ 
				if (root.Child[K] != null) 
				{ 
					// current character 
					char ch = (char) (K + 'a') ; 
	
					// Recursively search reaming character of word 
					// in trie for all 8 adjacent cells of 
					// boggle[i][j] 
					if (isSafe(i+1,j+1,visited,len) && boggle[i+1][j+1] 
														== ch) 
						searchWord(root.Child[K],boggle,i+1,j+1, 
												visited,str+ch,len); 
					if (isSafe(i, j+1,visited,len) && boggle[i][j+1] 
														== ch) 
						searchWord(root.Child[K],boggle,i, j+1, 
												visited,str+ch,len); 
					if (isSafe(i-1,j+1,visited,len) && boggle[i-1][j+1] 
														== ch) 
						searchWord(root.Child[K],boggle,i-1, j+1, 
												visited,str+ch,len); 
					if (isSafe(i+1,j, visited,len) && boggle[i+1][j] 
														== ch) 
						searchWord(root.Child[K],boggle,i+1, j, 
												visited,str+ch,len); 
					if (isSafe(i+1,j-1,visited,len) && boggle[i+1][j-1] 
														== ch) 
						searchWord(root.Child[K],boggle,i+1, j-1, 
												visited,str+ch,len); 
					if (isSafe(i, j-1,visited,len)&& boggle[i][j-1] 
														== ch) 
						searchWord(root.Child[K],boggle,i,j-1, 
												visited,str+ch,len); 
					if (isSafe(i-1,j-1,visited,len) && boggle[i-1][j-1] 
														== ch) 
						searchWord(root.Child[K],boggle,i-1, j-1, 
												visited,str+ch,len); 
					if (isSafe(i-1, j,visited,len) && boggle[i-1][j] 
														== ch) 
						searchWord(root.Child[K],boggle,i-1, j, 
											visited,str+ch,len); 
				} 
			} 
	
			// make current element unvisited 
			visited[i][j] = false; 
		} 
		
	} 
	
	
	static void findWords(char boggle[][], TrieNode root,int len) 
	{ 
		// Mark all characters as not visited 
		boolean[][] visited = new boolean[len][len]; 
		TrieNode pChild = root ; 
	
		String str = ""; 
	
		// traverse all elements of the board
		for (int i = 0 ; i < len; i++) 
		{ 
			for (int j = 0 ; j < len ; j++) 
			{ 
				
				if (pChild.Child[(boggle[i][j]) - 'a'] != null) 
				{ 
					str = str+boggle[i][j]; 
					searchWord(pChild.Child[(boggle[i][j]) - 'a'], 
							boggle, i, j, visited, str, len); 
					str = ""; 
				} 
			} 
		} 
	} 
	
	// populate random values on the board
	public static void populate_array(char array[][],int len)
	{
		Random rnd = new Random();
	    for(int i = 0; i < len; i++)
	    {
	        for(int j = 0; j < len; j++)
	        {
	            int x = rnd.nextInt(26); //0 to 25
	            switch(x){
	                case 0:{
	                    array[i][j] = 'a';
	                    break;
	                }
	                case 1:{
	                    array[i][j] = 'b';
	                    break;
	                }
	                case 2:{
	                    array[i][j] = 'c';
	                    break;
	                }
	                case 3:{
	                    array[i][j] = 'd';
	                    break;
	                }
	                case 4:{
	                    array[i][j] = 'e';
	                    break;
	                }
	                case 5:{
	                    array[i][j] = 'f';
	                    break;
	                }
	                case 6:{
	                    array[i][j] = 'g';
	                    break;
	                }
	                case 7:{
	                    array[i][j] = 'h';
	                    break;
	                }
	                case 8:{
	                    array[i][j] = 'i';
	                    break;
	                }
	                case 9:{
	                    array[i][j] = 'j';
	                    break;
	                }
	                case 10:{
	                    array[i][j] = 'k';
	                    break;
	                }
	                case 11:{
	                    array[i][j] = 'l';
	                    break;
	                }
	                case 12:{
	                    array[i][j] = 'm';
	                    break;
	                }
	                case 13:{
	                    array[i][j] = 'n';
	                    break;
	                }
	                case 14:{
	                    array[i][j] = 'o';
	                    break;
	                }
	                case 15:{
	                    array[i][j] = 'p';
	                    break;
	                }
	                case 16:{
	                    array[i][j] = 'q';
	                    break;
	                }
	                case 17:{
	                    array[i][j] = 'r';
	                    break;
	                }
	                case 18:{
	                    array[i][j] = 's';
	                    break;
	                }
	                case 19:{
	                    array[i][j] = 't';
	                    break;
	                }
	                case 20:{
	                    array[i][j] = 'u';
	                    break;
	                }
	                case 21:{
	                    array[i][j] = 'v';
	                    break;
	                }
	                case 22:{
	                    array[i][j] = 'w';
	                    break;
	                }
	                case 23:{
	                    array[i][j] = 'x';
	                    break;
	                }
	                case 24:{
	                    array[i][j] = 'y';
	                    break;
	                }
	                case 25:{
	                    array[i][j] = 'z';
	                    break;
	                }
	            }
	        }
	    }
	}
	
	// print board on console
	public static void print_array(char array[][])
	{
	   for(char[] row : array )
	   	{
	         System.out.println(row);
	    }
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException 
	{ 
	
		// root Node of trie 
		TrieNode root = new TrieNode(); 
		
		// Get the size of board from user
		BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter N for NxN matrix:");
		int len=Integer.parseInt(reader.readLine());
		
		//create and populate the board with random values
		char boggle[][]=new char[len][len];
		populate_array(boggle,len);
		print_array(boggle);
		
		while(true){
			String choice="no";
			String w="no";
			
			//getting the choice from user
			System.out.println("You want to continue playing the game (yes/no):");
			choice=reader.readLine();
			
			if(choice.toLowerCase().equals("yes")){
				//if the user wants to play the get the input from user
				System.out.println("Enter word:");
				w=reader.readLine();
				flag=0;
				
				if(contains(w)){
				//if the word is a valid english word then insert it into trie
				insert(root, w);
				
				//check whether the word is present in the board under rules
				findWords(boggle,root,len);
				
					if(flag==0){
					System.out.println("Wrong answer");
					score=score-1;
					}
				}
				else{
					System.out.println("Enter a meaningful word");
					score=score-1;
				}
			}
			else
				break;
		}
		
		//print the score of the user
		System.out.println("The Final Score :" + score);
		reader.close();
			
	} 
} 


