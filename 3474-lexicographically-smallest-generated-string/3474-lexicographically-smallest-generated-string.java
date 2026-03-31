class Solution {
    public String generateString(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        char[] res = new char[m + n - 1];
        boolean[] fixed = new boolean[m + n - 1];

        Arrays.fill(res, 'a');

        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    if (!fixed[i + j]) {
                        res[i + j] = str2.charAt(j);
                    } else if (res[i + j] != str2.charAt(j)) {
                        return "";
                    }
                    fixed[i + j] = true;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (res[i + j] != str2.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    boolean changed = false;
                    for (int j = i + m - 1; j >= i; j--) {
                        if (!fixed[j]) {
                            res[j] = 'b';
                            changed = true;
                            break;
                        }
                    }
                    if (!changed) return "";
                }
            }
        }

        return new String(res);
    }
}