class Solution {
    public void rotate(int[][] matrix) {
        int l = matrix.length;
        for (int i = 0; i < l; i++) {
            for (int j = i+1; j <l; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int i = 0; i < l; i++) {
            int left = 0;
            int right = l-1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }

    }
}