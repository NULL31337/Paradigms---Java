package search;
public class BinarySearchMax
{
    // Pre: args != null
    //      forall i: args[i] -> Integer
    //      exists k: forall i, j if 0 <= i < j <= k then args[i] < args[j]
    //      forall i, j if k <= i < j < args.length then args[i] > args[j]
    // Post: result == Max(args[0], args[1],  ..., args[args.length - 1])
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Incorrect input");
            return;
        }
        int[] tab = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            tab[i] = Integer.parseInt(args[i]);
        }
        System.out.println(iterativeBinarySearch(tab));
    }
    // Immutable: exists k: forall i, j if 0 <= i < j <= k then a[i] < a[j]
    //                      forall i, j if k <= i < j < args.length then a[i] > a[j]
    // Pre: Immutable
    // Post: result == Max(args[0], args[1],  ..., args[args.length - 1])
    private static int iterativeBinarySearch(int[] a) {
        int l = -1, r = a.length;
        // Prev: l < r && l == -1 && r == a.length
        // Inv: l < r && exist q: l < q <= r && result == a[q]
        while (r - l > 1) {
            // r - l > 1
            int m = (r - l) / 2 + l;
            // m > l
            // l < r => r + l < 2r => r - l + 2l < 2r => m < r
            //PREV: m -> Integer && l < m < r => 0 <= m < a.length => a[m] -> Integer
            if (m + 1 < a.length && a[m] < a[m + 1]) {
                // a[m] < a[m + 1] => forall i: 0 < i < m a[i] < a[m + 1] =>  q > m > l
                l = m;
            } else {
                // a[m] > a[m + 1] => forall i: m < i < a.length a[i] < a[m] =>  q <= m < r ||
                // m + 1 == a.length => q <= m
                r = m;
            }
            // (r' == m || l' == m) && m > l && m < r => r' - l' < r - l
            // q > l' && q <= r'
        }
        // l + 1 == r && q > l && q <= r => q == r
        return a[r];
    }

    // Pre: Immutable
    // Inv: l < r && exist q: l < q <= r && result == a[q]
    // Post: result == Max(args[0], args[1],  ..., args[args.length - 1])
    private static int recursiveBinarySearch(int[] a, int l, int r) {
        //Pre: r - l == 1 && l, r -> Integer && 0 <= r < a.length
        if (r - l == 1) {
            // l + 1 == r && q > l && q <= r => q == r
            return a[r];
        }
        int m = (r - l) / 2 + l;
        // m > l
        // l < r => r + l < 2r => r - l + 2l < 2r => m < r
        //PREV: m -> Integer && l < m < r => 0 <= m < a.length => a[m] -> Integer
        if (m + 1 < a.length && a[m] < a[m + 1]) {
            // a[m] < a[m + 1] => forall i: 0 < i < m a[i] < a[m + 1] =>  q > m > l
            l = m;
        } else {
            // a[m] > a[m + 1] => forall i: m < i < a.length a[i] < a[m] =>  q <= m < r ||
            // m + 1 == a.length => q <= m
            r = m;
        }
        // (r' == m || l' == m) && m > l && m < r => r' - l' < r - l
        // q > l' && q <= r'
        return recursiveBinarySearch(a, l, r);
    }

    // Pre: Immutable
    // Post: result == Max(args[0], args[1],  ..., args[args.length - 1])
    private static int recursiveBinarySearch(int[] a) {
        // l < r && exist q: l < q <= r && result == a[q] && Immutable
        return recursiveBinarySearch(a, -1, a.length);
    }
}

