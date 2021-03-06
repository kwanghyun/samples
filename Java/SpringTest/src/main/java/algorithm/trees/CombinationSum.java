package algorithm.trees;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Given a set of candidate numbers (C) and a target number (T), find all
 * unique combinations in C where the candidate numbers sums to T. The same
 * repeated number may be chosen from C unlimited number of times. Note: All
 * numbers (including target) will be positive integers. Elements in a
 * combination (a1, a2, ... , ak) must be in non-descending order. (ie, a1
 * <= a2 <= ... <= ak). The solution set must not contain duplicate
 * combinations. For example, given candidate set 2,3,6,7 and target 7, A
 * solution set is: 
 * [7] 
 * [2, 2, 3]
 * 
 * The first impression of this problem should be depth-first search(DFS).
 * To solve DFS problem, recursion is a normal implementation. Note that the
 * candidates array is not sorted, we need to sort it first.
 */
public class CombinationSum {

	public void combinationSum(int[] arr, int target, int idx, ArrayList<Integer> list,
			ArrayList<ArrayList<Integer>> result) {

		if (target == 0) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		for (int i = idx; i < arr.length; i++) {
			if (target < arr[i]) {
				return;
			}

			list.add(arr[i]);
			combinationSum(arr, target - arr[i], i, list, result);
			list.remove(list.size() - 1);
		}
	}

	public void combinationSumI(int[] arr, int target, int idx, ArrayList<Integer> list,
			ArrayList<ArrayList<Integer>> result) {

		if (target == 0) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		for (int i = idx; i < arr.length; i++) {
			if (target < arr[i]) {
				return;
			}

			list.add(arr[i]);
			combinationSumI(arr, target - arr[i], idx + 1, list, result);
			list.remove(list.size() - 1);
		}
	}

	public void combinationSumII(int[] arr, int sum, ArrayList<ArrayList<Integer>> result, ArrayList<Integer> list) {

		if (sum == 0) {
			ArrayList<Integer> newlist = new ArrayList<Integer>();
			newlist.addAll(list);
			result.add(newlist);
			return;
		}

		for (int i = 0; i < arr.length; i++) {
			if (sum < arr[i])
				return;
			list.add(arr[i]);
			combinationSumII(arr, sum - arr[i], result, list);
			list.remove(list.size() - 1);
		}
	}

	public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (candidates == null || candidates.length == 0)
			return result;

		ArrayList<Integer> current = new ArrayList<Integer>();
		Arrays.sort(candidates);

		combinationSum(candidates, target, 0, current, result);

		return result;
	}

	public ArrayList<ArrayList<Integer>> combinationSumI(int[] candidates, int target) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (candidates == null || candidates.length == 0)
			return result;

		ArrayList<Integer> current = new ArrayList<Integer>();
		Arrays.sort(candidates);

		combinationSumI(candidates, target, 0, current, result);

		return result;
	}

	public ArrayList<ArrayList<Integer>> combinationSumII(int[] arr, int sum) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		Arrays.sort(arr);
		combinationSumII(arr, sum, result, list);
		return result;
	}

	public boolean subsetSum(int arr[], int sum) {

		boolean dp[][] = new boolean[arr.length + 1][sum + 1];
		for (int i = 0; i <= arr.length; i++) {
			dp[i][0] = true;
		}

		for (int r = 1; r <= arr.length; r++) {
			for (int c = 1; c <= sum; c++) {
				if (c - arr[r - 1] >= 0) {
					dp[r][c] = dp[r - 1][c] || dp[r - 1][c - arr[r - 1]];
				} else {
					dp[r][c] = dp[r - 1][c];
				}
			}
		}
		return dp[arr.length][sum];
	}

	public boolean subsetSumI(int arr[], int sum, int idx) {
		if (sum == 0)
			return true;

		for (int i = idx; i < arr.length; i++) {
			if (arr[i] > sum)
				break;

			if (subsetSumI(arr, sum - arr[i], i + 1))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		CombinationSum obj = new CombinationSum();
		int[] candidates = { 2, 3, 4, 7 };
		System.out.println("----------------------combinationSum-----------------------");
		ArrayList<ArrayList<Integer>> result = obj.combinationSum(candidates, 7);
		System.out.println(result);
		System.out.println("----------------------combinationSumI-----------------------");
		ArrayList<ArrayList<Integer>> result1 = obj.combinationSumI(candidates, 7);
		System.out.println(result1);
		System.out.println("----------------------combinationSumII-----------------------");
		ArrayList<ArrayList<Integer>> result2 = obj.combinationSumII(candidates, 7);
		System.out.println(result2);

		System.out.println("----------------------subsetSum-----------------------");
		System.out.println(obj.subsetSum(candidates, 7));
		System.out.println(obj.subsetSumI(candidates, 7, 0));
	}
}
