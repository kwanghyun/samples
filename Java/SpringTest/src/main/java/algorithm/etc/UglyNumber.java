package algorithm.etc;

/*
 * Write a program to check whether a given number is an ugly number.
 * 
 * Ugly numbers are positive numbers whose prime factors only include 2, 3,
 * 5. For example, 6, 8 are ugly while 14 is not ugly since it includes
 * another prime factor 7.
 * 
 * Note that 1 is typically treated as an ugly number.
 */
public class UglyNumber {

	public boolean isUgly(int num) {
		if (num == 0)
			return false;
		if (num == 1)
			return true;

		if (num % 2 == 0) {
			num = num / 2;
			return isUgly(num);
		}

		if (num % 3 == 0) {
			num = num / 3;
			return isUgly(num);
		}

		if (num % 5 == 0) {
			num = num / 5;
			return isUgly(num);
		}

		return false;
	}

	public static void main(String[] args) {
		UglyNumber ob = new UglyNumber();
		for (int i = 4; i < 100; i++) {
			System.out.println("isUgly() : " + i + " => " + ob.isUgly(i));
		}
	}
}
