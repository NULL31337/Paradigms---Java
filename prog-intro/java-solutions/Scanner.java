import java.io.*;

public class Scanner {
	private final BufferedReader inputString;
	private StringBuilder stringBuilder;
	private int currentCharInt = ' ';
	private int prevCharInt = ' ';
	public int typeOfSeparator;
	private int position = 0;
	public Scanner (BufferedReader bufferedReader) {
		inputString = bufferedReader;
	}

	public Scanner () {
		inputString = new BufferedReader(new InputStreamReader (System.in));
	}

	private boolean isNeed(int i) {
		Character c = Character.toLowerCase((char) i);
		int t = Character.getType(c);
		if (typeOfSeparator == 0) {
			return  t == Character.DASH_PUNCTUATION  || t == Character.LOWERCASE_LETTER || c == '\'';
		} else {
			return t == Character.DECIMAL_DIGIT_NUMBER || c == '-' || c == '+' ||
					(c - 'a' >= 0 && c - 'j' <= 0) || c == 'x' || t == Character.DASH_PUNCTUATION
					|| t == Character.LOWERCASE_LETTER || c == '\'';

		}
	}

	private boolean isLineSeparator() {
		if  (currentCharInt == '\n' && prevCharInt == '\r') {
			return false;
		} else {
			return currentCharInt == '\r' || currentCharInt == '\n';
		}
	}

	private void get(){
		try {
			prevCharInt = currentCharInt;
			currentCharInt = inputString.read();
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: "  + e.getMessage());
		} catch (SecurityException e) {
			System.out.println("SecurityException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading input file: "  + e.getMessage());
		}
	}

	private boolean nextWord(boolean isWordOrBegin) {
		while (currentCharInt != -1 && !isNeed(currentCharInt)) {
			if (isLineSeparator()) {
				return true;
			}
			get();
		}
		if (currentCharInt == -1) {
			return false;
		}
		stringBuilder = new StringBuilder();
		if (isWordOrBegin) {
			while (currentCharInt != -1 && isNeed(currentCharInt)) {
				stringBuilder.append((char)(currentCharInt));
				get();
			}
		}
		return true;
	}

	public boolean hasNext() {
		return nextWord(false);
	}

	public int getPosition(){
		return position;
	}

	public boolean hasNextLine() {
		if (nextWord(false)){
			if (isLineSeparator()) {
				position = 0;
				get();
				return true;
			}
		}
		return false;
	}
	public String getNext() {
		position++;
		nextWord(true);
		return (stringBuilder.toString()).toLowerCase();
	}
}
