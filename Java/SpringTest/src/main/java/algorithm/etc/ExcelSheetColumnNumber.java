package algorithm.etc;

/*	Problem

Given a column title as appear in an Excel sheet, return its corresponding column number. For example:

    A -> 1
    B -> 2
    C -> 3
    ...
    Z -> 26
    AA -> 27
    AB -> 28 
    ...
    AAA -> 703 
*/

public class ExcelSheetColumnNumber {

	public int titleToNumber(String s) {
		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException("Input is not valid!");
		}

		int result = 0;
		int i = s.length() - 1;
		int t = 0;
		while (i >= 0) {
			char curr = s.charAt(i);
			result = result + (int) Math.pow(26, t) * (curr - 'A' + 1);
			t++;
			i--;
		}

		return result;
	}

	public int titleToNumberI(String s) {
		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException("Input is not valid!");
		}
		int result = 0;

		for (int i = 0; i < s.length(); i++) {
			int val = s.charAt(i) - 'A' + 1;
			result = result * 26 + val;
		}
		return result;
	}

	public static void main(String[] args) {
		ExcelSheetColumnNumber ob = new ExcelSheetColumnNumber();
		String test_str = "DFE";
		System.out.println(ob.titleToNumber(test_str));
		System.out.println(ob.titleToNumberI(test_str));
	}
}
