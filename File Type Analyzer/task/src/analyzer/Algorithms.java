package analyzer;

public class Algorithms {
    public static boolean searchKPM(String t, String p) {
        // find the prefix
        int[] prefix = getPrefixForKPM(p);

        int j = 0;
        for (int i = 0; i < t.length(); i++) {
            while (j > 0 && t.charAt(i) != p.charAt(j)) {
                j = prefix[j - 1];
            }

            if (t.charAt(i) == p.charAt(j)) {
                j += 1;
            }

            if (j == p.length()) {
                return true;

            }
        }

        return false;
    }

    private static int[] getPrefixForKPM(String s) {
        int len = s.length();
        int[] prefix = new int[len];

        for (int i = 1; i < len; i++) {
            int j = prefix[i - 1];

            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = prefix[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j += 1;
            }
            prefix[i] = j;
        }

        return prefix;
    }
}