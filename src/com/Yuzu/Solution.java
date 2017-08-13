package com.Yuzu;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            int mid = (left + right) / 2;
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
            int mid = (left + right) / 2 + 1;
            int midValue = nums[mid];
            if (midValue > target) right = mid - 1;
            else left = mid;
        }
        return right;
    }

    private int findLeft(int[] nums, int left, int right, int target) {
        if (nums[left] == target) return left;
        while (left < right) {
            int mid = (left + right) / 2;
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
                    postFix += dig;
                    stackCount.push((int) stackCount.pop() + 1);
                }
                if ((int) stackCount.peek() == 2) {
                    postFix += stackOp.pop();
                    stackCount.pop();
                    stackCount.push((int) stackCount.pop() + 1);
                }
            }
            while (!stackOp.empty()) postFix += stackOp.pop();
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
        char boxValue = board[i][j];
        if (i != 8 || j != 8) {
            int nextI = j == 8 ? i + 1 : i;
            int nextJ = j == 8 ? 0 : j + 1;
            if (boxValue == '.') {
                for (int v = 1; v < 10; v++) {
                    board[i][j] = Character.forDigit(v, RADIX);
                    if (!isBoxValueValid(i, j, board)) continue;
                    if (solveOneBox(nextI, nextJ, board)) {
                        valid = true;
                        break;
                    }
                }
            } else return solveOneBox(nextI, nextJ, board);
        } else if (boxValue == '.') {
            for (int v = 1; v < 10; v++) {
                board[i][j] = Character.forDigit(v, RADIX);
                if (isBoxValueValid(i, j, board)) return true;
            }
        } else return isBoxValueValid(i, j, board);
        if (!valid) board[i][j] = boxValue;
        return valid;
    }

    private boolean isBoxValueValid(int i, int j, char[][] board) {
        Set set = new HashSet();
        Set set2 = new HashSet();
        Set set3 = new HashSet();
        for (int p = 0; p < 9; p++) {
            char value = board[i][p];
            if (value != '.') {
                if (set.contains(value))
                    return false;
                set.add(value);
            }
            char value2 = board[p][j];
            if (value2 == '.') continue;
            if (set2.contains(value2)) return false;
            set2.add(value2);
        }
        for (int p = i / 3 * 3; p < i / 3 * 3 + 3; p++) {
            for (int q = j / 3 * 3; q < j / 3 * 3 + 3; q++) {
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
        for (int i = 0; i < 7; i += 3)
            for (int j = 0; j < 7; j += 3)
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
        for (int i = 0; i < 9; i++) {
            set = new HashSet();
            for (int j = 0; j < 9; j++) {
                char value = board[i][j];
                if (value == '.') continue;
                if (set.contains(value)) return false;
                set.add(value);
            }
        }
        for (int j = 0; j < 9; j++) {
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
        for (int i = 0; i < 3; i++) {
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
        for (int i = 3; i < 6; i++) {
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
        for (int i = 6; i < 9; i++) {
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

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Map<Integer, List<List<Integer>>> result = new HashMap<>();
        result.put(0, Stream.of(new ArrayList<Integer>()).collect(Collectors.toList()));
        result.put(target, new ArrayList<>());
        for (int candidate : candidates) {
            for (int i = candidate; i <= target; i++) {
                List<List<Integer>> resultP = result.get(i - candidate);
                List<List<Integer>> resultI = result.containsKey(i) ? result.get(i) : new ArrayList<>();
                if (resultP != null && resultP.size() > 0) {
                    for (List<Integer> list : resultP) {
                        List<Integer> listI = new ArrayList<>();
                        listI.addAll(list);
                        listI.add(candidate);
                        resultI.add(listI);
                    }
                }
                if (!result.containsKey(i)) result.put(i, resultI);
            }
        }
        return result.get(target);
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        getCombination(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    private void getCombination(List<List<Integer>> result, List<Integer> temp, int[] candidates, int remain, int current) {
        if (remain < 0) return;
        if (remain == 0) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = current; i < candidates.length; i++) {
            if (i > current && candidates[i] == candidates[i - 1]) continue;
            temp.add(candidates[i]);
            getCombination(result, temp, candidates, remain - candidates[i], i + 1);
            temp.remove(temp.size() - 1);
        }
    }

    /*public int firstMissingPositive(int[] nums) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (int num: nums)
            map.put(num, true);
        int min = 1;
        while (map.containsKey(min)) min++;
        return min;
    }*/

    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0) return 1;
        int i = 0;
        while (i < nums.length) {
            if (nums[i] <= 0 || nums[i] >= nums.length || nums[i] == i + 1) i++;
            else if (nums[nums[i] - 1] == nums[i]) i++;
            else {
                int temp = nums[i];
                nums[i] = nums[temp - 1];
                nums[temp - 1] = temp;
            }
        }
        for (int j = 0; j < nums.length; j++)
            if (nums[j] != j + 1)
                return j + 1;
        return nums[nums.length - 1] + 1;
    }

    public int jump(int[] nums) {
        int length = nums.length;
        if (length < 2) return 0;
        int[] steps = new int[length];
        steps[length - 1] = 0;
        for (int i = length - 2; i > -1; i--) {
            if (nums[i] == 0) {
                steps[i] = length;
                continue;
            }
            if (nums[i] + i >= length - 1) steps[i] = 1;
            else steps[i] = steps[nums[i] + i] + 1;
            int j = i + 1;
            while (steps[j] > steps[i]) {
                steps[j] = steps[i];
                j++;
            }
        }
        return steps[0];
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        getPermute(result, new ArrayList<Integer>(), nums, nums.length);
        return result;
    }

    private void getPermute(List<List<Integer>> result, ArrayList temp, int[] nums, int length) {
        if (temp.size() == length) result.add(new ArrayList<Integer>(temp));
        else for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            if (i > 0 && value == nums[i - 1]) continue;
            temp.add(value);
            int[] newNums = new int[nums.length - 1];
            System.arraycopy(nums, 0, newNums, 0, i);
            System.arraycopy(nums, i + 1, newNums, i, nums.length - i - 1);
            getPermute(result, temp, newNums, length);
            temp.remove(temp.size() - 1);
        }
    }

    public void rotate(int[][] matrix) {
        int length = matrix.length;
        int range = length / 2;
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[length - 1 - j][i];
                matrix[length - 1 - j][i] = matrix[length - 1 - i][length - 1 - j];
                matrix[length - 1 - i][length - 1 - j] = matrix[j][length - 1 - i];
                matrix[j][length - 1 - i] = temp;
            }
        }
        if (length % 2 != 0) {
            for (int i = 0; i < range; i++) {
                int temp = matrix[i][range];
                matrix[i][range] = matrix[length - 1 - range][i];
                matrix[length - 1 - range][i] = matrix[length - 1 - i][length - 1 - range];
                matrix[length - 1 - i][length - 1 - range] = matrix[range][length - 1 - i];
                matrix[range][length - 1 - i] = temp;
            }
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = String.valueOf(chars);
            if (map.containsKey(s)) map.get(s).add(str);
            else map.put(s, new ArrayList<>(Arrays.asList(str)));
        }
        return map.values().stream().collect(Collectors.toList());
    }

    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == -2147483648) return 1 / myPow(x, -1 - (n + 1));
        if (n < 0) return 1 / myPow(x, -n);
        double multiplier = x;
        double result = 1;
        String binary = Integer.toBinaryString(n);
        if (binary.charAt(binary.length() - 1) == '1') result *= x;
        for (int i = binary.length() - 2; i > -1; i--) {
            multiplier *= multiplier;
            if (binary.charAt(i) == '1') result *= multiplier;
        }
        return result;
    }

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int left = 0;
        int right = n - 1;
        int top = 0;
        int bot = n - 1;
        int num = 1;
        int end = n * n;
        while (num <= end) {
            if (left <= right) {
                for (int i = left; i <= right; i++) matrix[top][i] = num++;
                if (num > end) break;
                top++;
            }
            if (top <= bot) {
                for (int i = top; i <= bot; i++) matrix[i][right] = num++;
                if (num > end) break;
                right--;
            }
            if (left <= right) {
                for (int i = right; i >= left; i--) matrix[bot][i] = num++;
                if (num > end) break;
                bot--;
            }
            if (top <= bot) {
                for (int i = bot; i >= top; i--) matrix[i][left] = num++;
                if (num > end) break;
                left++;
            }
        }
        return matrix;
    }

    public String getPermutation(int n, int k) {
        String result = "";
        int[] total = new int[n];
        List values = new ArrayList();
        for (int i = 1; i < n; i++) {
            total[i] = factorial(i);
            values.add("" + i);
        }
        values.add("" + n);
        int current = n - 1;
        while (k > 1) {
            int count = k / total[current];
            k %= total[current--];
            if (k == 0) {
                result += values.get(count - 1);
                values.remove(count - 1);
                for (int i = values.size() - 1; i >= 0; i--) {
                    result += values.get(i);
                }
                return result;
            }
            result += values.get(count);
            values.remove(count);
        }
        for (int i = 0; i < values.size(); i++) result += values.get(i);
        return result;
    }

    private int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        Map<Integer, ListNode> map = new HashMap();
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int i = 0;
        while (head.next != null) {
            map.put(i++, head);
            head = head.next;
        }
        map.put(i++, head);
        int shift = k % i;
        if (shift == 0) return dummy.next;
        head.next = dummy.next;
        dummy.next = map.get(i - shift);
        map.get(i - shift - 1).next = null;
        return dummy.next;
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public int uniquePaths(int m, int n) {
        BigInteger result = factorialBigInt(m + n - 2).divide(factorialBigInt(m - 1)).divide(factorialBigInt(n - 1));
        return result.intValue();
    }

    private BigInteger factorialBigInt(int n) {
        BigInteger fact = BigInteger.ONE; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] path = new int[m][n];
        path[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        for (int i = 1; i < m; i++)
            path[i][0] = obstacleGrid[i][0] == 1 ? 0 : (path[i - 1][0]);
        for (int j = 1; j < n; j++)
            path[0][j] = obstacleGrid[0][j] == 1 ? 0 : (path[0][j - 1]);
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                path[i][j] = obstacleGrid[i][j] == 1 ? 0 : (path[i][j - 1] + path[i - 1][j]);
        return path[m - 1][n - 1];
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] path = new int[m][n];
        path[0][0] = grid[0][0];
        for (int i = 1; i < m; i++)
            path[i][0] = path[i - 1][0] + grid[i][0];
        for (int j = 1; j < n; j++)
            path[0][j] = path[0][j - 1] + grid[0][j];
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                path[i][j] = grid[i][j] + Math.min(path[i][j - 1], path[i - 1][j]);
        return path[m - 1][n - 1];
    }

    public void setZeroes(int[][] matrix) {
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }
        for (int i : rows)
            for (int k = 0; k < n; k++)
                matrix[i][k] = 0;
        for (int j : cols)
            for (int k = 0; k < m; k++)
                matrix[k][j] = 0;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        return !(matrix.length == 0 || matrix[0].length == 0) && searchMatirxRecur(matrix, target, 0, matrix.length - 1);
    }

    private boolean searchMatirxRecur(int[][] matrix, int target, int lo, int hi) {
        if (target < matrix[lo][0] || target > matrix[hi][matrix[0].length-1]) return false;
        if (target >= matrix[hi][0]) return searchInRow(matrix[hi], target);
        else if (lo + 1 >= hi) return searchInRow(matrix[lo], target);
        else {
            int mid = (lo + hi) / 2;
            if (matrix[mid][0] > target) return searchMatirxRecur(matrix, target, lo, mid - 1);
            else return searchMatirxRecur(matrix, target, mid, hi - 1);
        }
    }

    private boolean searchInRow(int[] row, int target) {
        return searchInRowRecur(row, target, 0, row.length - 1);
    }

    private boolean searchInRowRecur(int[] row, int target, int lo, int hi) {
        if (target < row[lo] || target > row[hi]) return false;
        if (lo == hi) return target == row[lo];
        int mid = (lo + hi) / 2;
        if (row[mid] == target) return true;
        else if (row[mid] < target) return searchInRowRecur(row, target, mid + 1, hi);
        else  return searchInRowRecur(row, target, lo, mid - 1);
    }



    /*public String minWindow(String s, String t) {
        if (s.equals("") || t.equals("")) return "";
        Map<Character, Integer> charCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            charCount.put(ch, charCount.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (charCount.containsKey(ch)) charCount.put(ch, charCount.get(ch) - 1);
        }
        for (int count: charCount.values())
            if (count > 0)
                return "";
        return minWindowRecur(s, charCount, 0, s.length() - 1);
    }

    private String minWindowRecur(String s, Map<Character, Integer> charCount, int start, int end) {
        String path1 = "";
        String path2 = "";
        int i = start;
        int j = end;
        while (i < end) {
            char ch = s.charAt(i);
            if (charCount.containsKey(ch)) {
                if (charCount.get(ch) < 0) {
                    charCount.put(ch, charCount.get(ch) + 1);
                    path1 = minWindowRecur(s, charCount, i + 1, end);
                }
                break;
            }
            i++;
        }
        while (j > start) {
            char ch = s.charAt(i);
            if (charCount.containsKey(ch)) {
                if (charCount.get(ch) < 0) {
                    charCount.put(ch, charCount.get(ch) + 1);
                    path2 = minWindowRecur(s, charCount, start, j - 1);
                }
                break;
            }
            j--;
        }
        if (path1.equals("") && path2.equals("")) return s.substring(i, j + 1);
        if (path1.equals("")) return path2;
        if (path2.equals("")) return path1;
        return path1.length() < path2.length() ? path1 : path2;
    }*/

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> chars = new HashMap<>();
        int begin = 0;
        int end = 0;
        int counter = 0;
        int max = 0;
        while (end < s.length()) {
            char c1 = s.charAt(end);
            chars.put(c1, chars.getOrDefault(c1, 0) + 1);
            if (chars.get(c1) == 2) counter++;
            while (counter > 0) {
                char c2 = s.charAt(begin);
                chars.put(c2, chars.get(c2) - 1);
                if (chars.get(c2) == 1) counter--;
                begin++;
            }
            max = Math.max(max, end - begin + 1);
            end++;
        }
        return max;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int num = nums[i];
            if (i > 0 && nums[i-1] == num) continue;
            List<List<Integer>> lists = twoSum(nums, i + 1, 0 - num);
            if (lists.size() > 0) lists.forEach(integers -> integers.add(num));
            result.addAll(lists);
        }
        return result;
    }

    private List<List<Integer>> twoSum(int[] nums, int i, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        if (nums[i] + nums[i+1] > target || nums[length-1] + nums[length-2] < target) return result;
        int begin = i;
        int end = length - 1;
        while (begin < end) {
            int num = nums[begin];
            if (begin > i && num == nums[begin-1]) {
                begin++;
                continue;
            }
            int sum = num + nums[end];
            if (sum == target) {
                List<Integer> list = new ArrayList<>();
                list.add(num);
                list.add(nums[end]);
                result.add(list);
                begin++;
                end--;
            } else if (sum < target) begin++;
            else end--;
        }
        return result;
    }

    public List<List<String>> solveNQueens(int n) {
        Set<Integer> check1 = new HashSet<>();
        Set<Integer> check2 = new HashSet<>();
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> remain = new HashSet<>();
        for (int i = 0; i < n; i++) remain.add(i);
        solveNQueensRec(0, check1, check2, new ArrayList<Integer>(), remain, result);
        List<List<String>> stringResult = new ArrayList<>();
        for (List<Integer> integers: result) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String string = "";
                for (int j = 0; j < n; j++) {
                    if (integers.get(i) == j) string+="Q";
                    else string+=".";
                }
                strings.add(string);
            }
            stringResult.add(strings);
        }
        return stringResult;
    }

    private void solveNQueensRec(int i, Set<Integer> checkSum, Set<Integer> checkDiff, List<Integer> current, Set<Integer> remain, List<List<Integer>> result) {
        if (remain.size() == 0) result.add(current);
        for (int r: remain) {
            int sum = r + i;
            int diff = r - i;
            if (!checkSum.contains(sum) && !checkDiff.contains(diff)) {
                Set<Integer> check1 = new HashSet<>();
                Set<Integer> check2 = new HashSet<>();
                List<Integer> list = new ArrayList<>();
                Set<Integer> set = new HashSet<>();
                check1.addAll(checkSum);
                check1.add(sum);
                check2.addAll(checkDiff);
                check2.add(diff);
                list.addAll(current);
                list.add(r);
                set.addAll(remain);
                set.remove(r);
                solveNQueensRec(i + 1, check1, check2, list, set, result);
            }
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        getCombineFor(1, n, k, new ArrayList<Integer>(), result);
        return result;
    }

    private void getCombineFor(int begin, int n, int k, ArrayList<Integer> current, List<List<Integer>> result) {
        if (k == 0) result.add(current);
        if (begin > n) return;
        for (int i = begin; i <= n; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(current);
            temp.add(i);
            getCombineFor(i + 1, n, k - 1, temp, result);
        }
    }

    public boolean exist(char[][] board, String word) {
        if (word.equals("")) return true;
        if (board.length == 0) return false;
        boolean exist = false;
        char firstChar = word.charAt(0);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char current = board[i][j];
                if (current == firstChar) {
                    int[] index = new int[]{i, j};
                    List<int[]> usedIndex = Arrays.asList(index);
                    exist = exist || existRecur(i, j, board, usedIndex, word.substring(1));
                }
            }
        }
        return exist;
    }

    private boolean existRecur(int i, int j, char[][] board, List<int[]> usedIndex, String word) {
        return word.equals("") || validateAndContinue(i + 1, j, board, usedIndex, word) || validateAndContinue(i - 1, j, board, usedIndex, word) || validateAndContinue(i, j + 1, board, usedIndex, word) || validateAndContinue(i, j - 1, board, usedIndex, word);
    }

    private boolean validateAndContinue(int i, int j, char[][] board, List<int[]> usedIndex, String word) {
        char firstChar = word.charAt(0);
        int m = board.length;
        int n = board[0].length;
        if (i >= 0 && i < m && j >= 0 && j < n && board[i][j] == firstChar && !indexUsed(i, j, usedIndex)) {
            List<int[]> tempList = new ArrayList<>();
            tempList.addAll(usedIndex);
            tempList.add(new int[]{i, j});
            return existRecur(i, j, board, tempList, word.substring(1));
        }
        return false;
    }

    private boolean indexUsed(int i, int j, List<int[]> usedIndex) {
        for (int[] index: usedIndex)
            if (index[0] == i && index[1] == j)
                return true;
        return false;
    }

    public List<List<Integer>> subsets(int[] nums) {
        int length = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, length); i++) {
            List<Integer> subset = new ArrayList<>();
            String s = Integer.toBinaryString(i);
            for (int j = s.length() - 1; j >= 0; j--) {
                if (s.charAt(j) == '1') subset.add(nums[s.length() - 1 - j]);
            }
            result.add(subset);
        }
        return result;
    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        int i = 0;
        int begin = 0;
        while (i < heights.length) {
            int height = heights[i];
            if (stack.empty() || stack.peek() <= height) {
                stack.push(height);
            } else {
                stack.peek();
            }
        }
    }

}
