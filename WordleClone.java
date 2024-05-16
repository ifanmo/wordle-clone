
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class WordleClone {

	public static void main(String[] args) throws Exception {

		ArrayList<String> guesses = new ArrayList<>();
		ArrayList<String> dict = new ArrayList<>();

		final String RESET = "\u001B[0m";
		final String RED = "\u001B[091m";
		final String YELLOW = "\u001B[093m";
		final String GREEN = "\u001B[092m";
		final String MAGENTA = "\u001B[095m";

		final String WELCOME_MESSAGE = "Welcome to " + MAGENTA + "WORDLE" + RESET
				+ "!\n\nYou have as many attempts as you like to guess the secret five-letter word\n\n"
				+ "If you would like help during the game, please type the following commands:\n\n" + MAGENTA + ":HELP"
				+ RESET + "- gives you an explanation of the games rules\n" + MAGENTA + ":GIVEUP" + RESET
				+ "- quits the game and tells you the secret answer\n";

		if (args.length == 0) {
			return;
		}

		File file = new File(args[0]);
		Scanner in = new Scanner(file);

		while (in.hasNext()) {
			dict.add(in.nextLine().toUpperCase());

		}

		String answer = getRandomWord(dict);
		
		System.out.println(WELCOME_MESSAGE);
		String guess;
		int counter = 0;
		Scanner in1 = new Scanner(System.in);

		do {
			System.out.println("Please guess the secret five-letter word: ");
			guess = in1.nextLine().toUpperCase();
			System.out.println();

			if (guess.equals(":HELP") || guess.equals(":GIVEUP")) {
				userMenu(guess, answer, RESET, RED, YELLOW, GREEN, MAGENTA);

			} else if (isValid(guess, dict)) {
				guesses.add(guess);

				for (String guess1 : guesses) {
					System.out.println(colourString(guess1, answer, RESET, RED, YELLOW, GREEN));

				}

				counter++;

			} else {
				System.out.println("Sorry, that's an invalid guess. Type ':HELP' for the game rules\n");
			}

		} while (!(guess.equalsIgnoreCase(answer)));
		
		int playerScore = calculateScore(answer, counter);

		System.out.println("Well done you got it in " + counter + " tries!!! Your score is " + playerScore);

		in.close();
		in1.close();
	}

	// Returns a random file from the dictionary ArrayList
	public static String getRandomWord(ArrayList<String> dict) {
		Random rand = new Random();
		int upperBound = dict.size();
		int randomInt = rand.nextInt(upperBound);

		return dict.get(randomInt);

	}

	// Checks if user input is 5 letters, alphabetical and exists in the dictionary
	public static boolean isValid(String guess, ArrayList<String> dict) {

		if (guess.length() == 5 && guess.matches("[a-zA-Z]+") && dict.contains(guess)) {
			return true;
		} else {
			return false;
		}
	}

	// Returns a coloured string. Letters in the right position are green. Letters
	// shared by the guess and answer are yellow. Letters that are not shared by
	// either are coloured red
	public static String colourString(String guess, String answer, String reset, String red, String yellow,
			String green) {

		String colouredString = "";

		for (int i = 0; i < guess.length(); i++) {

			char answerChar = answer.charAt(i);
			char guessChar = guess.charAt(i);

			if (guessChar == answerChar) {
				colouredString += colourChar(green, guessChar);

			} else if (answer.indexOf(guessChar) != -1) {
				colouredString += colourChar(yellow, guessChar);

			} else {
				colouredString += colourChar(red, guessChar);
			}

		}

		return colouredString + reset;
	}

	
	public static String colourChar(String colour, char guess) {
		String colouredChar = colour + guess;

		return colouredChar;

	}

	public static void userMenu(String userCommand, String answer, String reset, String red, String yellow,
			String green, String magenta) {
		switch (userCommand) {
		case ":HELP":
			displayGameRules(reset, red, yellow, green, magenta);
			break;
		case ":GIVEUP":
			quitGame(answer);
			break;
		}

	}
	//Print out the rules of the game
	public static void displayGameRules(String reset, String red, String yellow, String green, String magenta) {
		System.out.println("GAME RULES\n\n" + "- The game will pick a random 5-letter word"
				+ "\n" + "- The player has unlimited guesses to get the word\n"
				+ "- The guesses must be alphabetical, 5 letters long and exist in the dictionary\n\n"
				+ "- If a letter in the players' guess occurs in the same position as the answer it will be highlighted in "
				+ green + "GREEN" + reset + "\n"
				+ "- If a letter in the players' guess occurs in the answer but is in the wrong position it will be highlighted in "
				+ yellow + "YELLOW" + reset + "\n"
				+ "- If a letter in the players' guess does not occur in the answer the output will be highlighted in "
				+ red + "RED" + reset + "\n\n" + "EXAMPLES\n\n"
				+ "- If the answer is VOICE and the player guesses METER the output will be " + red + "M" + yellow + "E"
				+ red + "T" + yellow + "E" + red + "R" + reset + "\n"
				+ "- If the answer is VOICE and the player guesses POISE the output will be " + red + "P" + green + "OI"
				+ red + "S" + green + "E" + reset + "\n");
	}

	// Prints out the secret answer and exits the program
	public static void quitGame(String answer) {
		System.out.println("Sorry, the answer was " + answer + ". Better luck next time");
		System.exit(0);
	}
	
	//Calculates the player's score based on the values of Scrabble Tiles divided by the number of guesses
	
	public static int calculateScore(String answer, int attempts) {
		int scrabbleScore = 0;
		
		for (int i = 0; i < answer.length(); i++) {
			if("EAIONRTLSU".indexOf(answer.charAt(i)) != 1) {
				scrabbleScore += 10;
				
			} else if ("DG".indexOf(answer.charAt(i)) != 1) {
				scrabbleScore += 20;
				
			} else if ("BCMP".indexOf(answer.charAt(i)) != 1) {
				scrabbleScore += 30;
				
			} else if ("FHVWY".indexOf(answer.charAt(i)) != 1) {
				scrabbleScore += 40;
				
			} else if ("K".indexOf(answer.charAt(i)) != 1) {
				scrabbleScore += 50;
				
			} else if ("JX".indexOf(answer.charAt(i)) != 1) {
				scrabbleScore +=80;
				
			} else {
				scrabbleScore +=100;
			}
		}
		
		int overallScore = scrabbleScore / attempts;
		return overallScore;
	}
}