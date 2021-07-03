import java.util.Arrays;

public class Reverse {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(); 
		int[][] ans = new int[1][1]; 
		int[] cntOfNumber = new int[1];
		int curLine = 0;
		scanner.typeOfSeparator = 1;
		while (scanner.hasNext()) {
			while (scanner.hasNextLine()) {
				curLine++;
			}
			while (curLine >= ans.length)
				ans = Arrays.copyOf(ans, ans.length * 2);
			while (curLine >= cntOfNumber.length)
				cntOfNumber = Arrays.copyOf(cntOfNumber, cntOfNumber.length * 2);
			if (scanner.hasNext()) {
				if (ans[curLine] == null)
					ans[curLine] = new int[1];
				if (cntOfNumber[curLine] >= ans[curLine].length)
					ans[curLine] = Arrays.copyOf(ans[curLine], ans[curLine].length * 2);
				ans[curLine][cntOfNumber[curLine]] = Integer.parseInt(scanner.getNext());
				cntOfNumber[curLine]++;
			}
		}
		for (int i = curLine - 1; i >= 0; i--) {
			if (ans[i] == null || ans[i].length != 0)
				for (int j = cntOfNumber[i] - 1; j >= 0; j--)
					System.out.printf("%d ", ans[i][j]);
			System.out.println();
		}
	} 
}