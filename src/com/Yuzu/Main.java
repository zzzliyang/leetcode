package com.Yuzu;

public class Main {

    public static void main(String[] args) {
        Solution s = new Solution();
        //34. Search for a Range
        /*int[] ints = s.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(ints[0] + "," + ints[1]);*/
        // agoda
        //s.prefix(new String[]{"+1**23/14"});
        s.solveSudoku(new char[][]{{'.','.','9','7','4','8','.','.','.'},{'7','.','.','.','.','.','.','.','.,".2.1.9...","..7...24.",".64.1.59.",".98...3..","...8.3.2.","........6","...2759.."});
    }
}
