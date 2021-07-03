package search;

public class BinarySearch {
    // Pre: forall i: args[i] -> Integer
    //      result in [0, args.length - 1]
    //      forall i, j if 1 <= i <= j < args.length then args[i] >= args[j]
    // Post: result in [0, args.length - 1]
    public static void main(String[] args) {
        int[] tab = new int[args.length - 1];
        int x = Integer.parseInt(args[0]);
        for (int i = 1; i < args.length; i++) {
            tab[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(recursiveBinarySearch(tab, x));
    }
    // Immutable: forall i, j if 0 <= i <= j < a.length then a[i] >= a[j]
    // Pre: Immutable
    // Post: result in [0, a.length]
    private static int iterativeBinarySearch(int[] a, int x) {
        int l = -1, r = a.length;
        // Inv: result > l && result <= r && r - l > 1
        while (r - l > 1) {
            // r - l > 1
            int m = (r - l) / 2 + l;
            // m > l
            // l < r => r + l < 2r => r - l + 2l < 2r => m < r
            if (a[m] <= x) {
                // a[m] <= x => forall i if m <= i < a.length then a[i] <= x
                r = m;
                // result > l' && result <= r'
            } else {
                // a[m] > x => forall i if 0 <= i <= m then a[i] > x
                l = m;
                // result > l' && result <= r'
            }
            // (r' == m || l' == m) && m > l && m < r => r' - l' < r - l
            // result > l' && result <= r'
        }
        // l + 1 == r && result > l && result <= r => result == r
        return r;
    }
    // Pre: Immutable
    // Inv: l < r && l < result <= r
    // Post: result in [0, a.length]
    private static int recursiveBinarySearch(int[] a, int x, int l, int r) {
        if (r - l == 1) {
            // l < result && l + 1 == r && r <= result => result == r
            return r;
        }
        int m = (r - l) / 2 + l;
        // m > l
        // l < r => r + l < 2r => r - l + 2l < 2r => m < r
        if (a[m] <= x) {
            // a[m] <= x => forall i if m <= i < a.length then a[i] <= x
            r = m;
            // result > l' && result <= r'
        } else {
            // a[m] > x => forall i if 0 <= i <= m then a[i] > x
            l = m;
            // result > l' && result <= r'
        }
        // (r' == m || l' == m) && m > l && m < r => r' - l' < r - l
        // result > l' && result <= r'
        return recursiveBinarySearch(a, x, l, r);
    }

    // Pre: Immutable
    // Post: result in [0, a.length]
    private static int recursiveBinarySearch(int[] a, int x) {
        // l < r && l < result <= r && Immutable
        return recursiveBinarySearch(a, x, -1, a.length);
    }
}

