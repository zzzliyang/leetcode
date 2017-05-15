package com.Yuzu;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Liyang, Zhang on 5/12/2017.
 */
public class Solution {

    //34. Search for a Range
    public int[] searchRange(int[] nums, int target) {
        int[] notFound = new int[]{-1, -1};
        if (nums.length == 0) return notFound;
        int left = 0;
        int right = nums.length - 1;
        int leftValue = nums[left];
        int rightValue = nums[right];
        if (leftValue > target || rightValue < target) return notFound;
        while (left < right) {
            int mid = (left + right)/2;
            int midValue = nums[mid];
            if (midValue < target) left = mid + 1;
            else if (midValue > target) right = mid;
            else {
                int leftIndex = findLeft(nums, left, mid, target);
                int rightIndex = findRight(nums, mid, right, target);
                return new int[]{leftIndex, rightIndex};
            }
        }
        if (nums[left] == target) return new int[]{left, left};
        return notFound;
    }

    private int findRight(int[] nums, int left, int right, int target) {
        if (nums[right] == target) return right;
        while (left < right) {
            int mid = (left + right)/2 + 1;
            int midValue = nums[mid];
            if (midValue > target) right = mid - 1;
            else left = mid;
        }
        return right;
    }

    private int findLeft(int[] nums, int left, int right, int target) {
        if (nums[left] == target) return left;
        while (left < right) {
            int mid = (left + right)/2;
            int midValue = nums[mid];
            if (midValue < target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    public String[] prefix(String[] prefixes) {
        String[] result = new String[prefixes.length];
        for (int i = 0; i < prefixes.length; i++) {
            String prefix = prefixes[i];
            Stack stackOp = new Stack();
            Stack stackCount = new Stack();
            String postFix = "";
            for (int j = 0; j < prefix.length(); j++) {
                char dig = prefix.charAt(j);
                if (dig == '+' || dig == '-' || dig == '*' || dig == '/') {
                    stackOp.push(dig);
                    stackCount.push(0);
                } else {
                    postFix+=dig;
                    stackCount.push((int)stackCount.pop() + 1);
                }
                if ((int)stackCount.peek() == 2) {
                    postFix+=stackOp.pop();
                    stackCount.pop();
                    stackCount.push((int)stackCount.pop() + 1);
                }
            }
            while (!stackOp.empty()) postFix+=stackOp.pop();
            result[i] = postFix;
        }
        return result;
    }

    public void solveSudoku(char[][] board) {
        solveOneBox(0, 0, board);
    }

    private boolean solveOneBox(int i, int j, char[][] board) {
        final int RADIX = 10;
        boolean valid = false;
        if (i != 8 || j != 8) {
            int nextI = j==8?i+1:i;
            int nextJ = j==8?0:j+1;
            if (board[i][j] == '.') {
                for (int v = 1; v < 10; v++) {
                    board[i][j] = Character.forDigit(v, RADIX);
                    if (!isBoxValueValid(i, j, board)) continue;
                    if (solveOneBox(nextI, nextJ, board)) {
                        valid = true;
                        break;
                    }
                }
            } else if (!isBoxValueValid(i, j, board)) return false;
            else return solveOneBox(nextI, nextJ, board);
        } else if (board[i][j] == '.') {
                for (int v = 1; v < 10; v++)
                    if (isBoxValueValid(i, j, board)) return true;
            } else return isBoxValueValid(i, j, board);
        return valid;
    }

    private boolean isBoxValueValid(int i, int j, char[][] board) {
        Set set = new HashSet();
        Set set2 = new HashSet();
        Set set3 = new HashSet();
        for (int p = 0; p < 9; p++) {
            char value = board[i][p];
            if (value == '.') continue;
            if (set.contains(value)) return false;
            set.add(value);
            char value2 = board[p][j];
            if (value2 == '.') continue;
            if (set2.contains(value2)) return false;
            set2.add(value2);
        }
        for (int p = i/3*3; p < i/3*3+3; p++) {
            for (int q = j/3*3; q < i/3*3+3; q++) {
                char value = board[p][q];
                if (value == '.') continue;
                if (set3.contains(value)) return false;
                set3.add(value);
            }
        }
        return true;
    }

    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++)
            if (!checkValidity(i, i + 1, 0, 9, board))
                return false;
        for (int j = 0; j < 9; j++)
            if (!checkValidity(0, 9, j, j + 1, board))
                return false;
        for (int i = 0; i < 7; i+=3)
            for (int j = 0; j < 7; j+=3)
                if (!checkValidity(i, i + 3, j, j + 3, board))
                    return false;
        return true;
    }

    private boolean checkValidity(int iLeft, int iRight, int jLeft, int jRight, char[][] board) {
        Set set = new HashSet();
        for (int i = iLeft; i < iRight; i++) {
            for (int j = jLeft; j < jRight; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
        }
        return true;
    }

    public boolean isValidSudoku2(char[][] board) {
        Set set;
        Set set1;
        Set set2;
        for (int i = 0;i < 9; i++) {
            set = new HashSet();
            for (int j = 0; j < 9; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
        }
        for (int j = 0;j < 9; j++) {
            set = new HashSet();
            for (int i = 0; i < 9; i++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
        }
        set = new HashSet();
        set1 = new HashSet();
        set2 = new HashSet();
        for (int i = 0;i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
            for (int j = 3; j < 6; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set1.contains(value)) return false;
                set1.add(value);
            }
            for (int j = 6; j < 9; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set2.contains(value)) return false;
                set2.add(value);
            }
        }
        set = new HashSet();
        set1 = new HashSet();
        set2 = new HashSet();
        for (int i = 3;i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
            for (int j = 3; j < 6; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set1.contains(value)) return false;
                set1.add(value);
            }
            for (int j = 6; j < 9; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set2.contains(value)) return false;
                set2.add(value);
            }
        }
        set = new HashSet();
        set1 = new HashSet();
        set2 = new HashSet();
        for (int i = 6;i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
            for (int j = 3; j < 6; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set1.contains(value)) return false;
                set1.add(value);
            }
            for (int j = 6; j < 9; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set2.contains(value)) return false;
                set2.add(value);
            }
        }
        return true;
    }

}
