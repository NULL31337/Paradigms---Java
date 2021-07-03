import java.util.Arrays;
public class IntList {
	private int[] tab;
	private int size;
	private int cur;
	public int countOfWords;
	public int lastElementLine;//NOTE: should be private

	public IntList() {
		size = 1;
		cur = 0;
		tab = new int[1];
		countOfWords = 0;
		lastElementLine = -1;
	}

	public void push_back(int a) {
		if (cur + 1 >= size) {
			resize();
		}
		tab[cur++] = a;
	}

	public void resize() {
		tab = Arrays.copyOf(tab, size * 2);
		size *= 2;
	}

	public void pop_back() {
		cur--;
	}

	public int size() {
		return cur;
	}

	public int getEl(int a) {
		return tab[a];
	}

}