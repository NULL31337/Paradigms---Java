import java.util.Arrays;

public class ReverseHexAbc {
	public static void main(String[] args) {
		Scanner scanner = new Scanner();
		scanner.typeOfSeparator = 1;
		int[][] ans = new int[1][1];
		int[] cntOfNumber = new int[1];
		int curLine = 0;
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
				String push = scanner.getNext();
				if (push.length() >= 2 && push.charAt(1) == 'x'){
					ans[curLine][cntOfNumber[curLine]] = Integer.parseUnsignedInt(push.substring(2), 16);
				} else {
					StringBuilder normalInt = new StringBuilder();
					for (int i = 0; i < push.length(); i++) {
						char pushChar = push.charAt(i);
						if (pushChar>= 'a' && pushChar <= 'j'){
							normalInt.append(pushChar - 'a');
						} else {
							normalInt.append(pushChar);
						}
					}
					ans[curLine][cntOfNumber[curLine]] = Integer.parseInt(normalInt.toString());
				}
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