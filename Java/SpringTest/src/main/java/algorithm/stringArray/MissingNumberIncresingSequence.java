package algorithm.stringArray;

/*
 * Given an increasing sequence of numbers from 1 to n with only one missing
 * number, how can you find that missing number without traversing the
 * sequence in linear fashion. In other words, time complexity of your
 * algorithm must be less than O(n)?
 * 
 * For example, if the given sequence is 1,2,4,5,6,7,8 then the missing
 * number is 3. If the given sequence is 1,3,4,5 then the missing number is
 * 2. For the input 2,3,4,5 output returned should be 1 as it is the missing
 * number.
 */
public class MissingNumberIncresingSequence {

	/*
	 * In the given sequence, we search for the first element from the left
	 * which is incorrectly placed. An incorrectly placed element would not
	 * satisfy condition: valueOfElement = index of element + 1. Once we find
	 * this first incorrectly placed element, we know that the missing number
	 * must be equal to value of this element minus 1.
	 */
	private int findMissingElement(int[] array, int low, int high) {
		// Invalid input case: if the array size is 0 or less than 0
		if (low > high) {
			System.out.println("Invalid Input");
			return -1;
		}

		// if the last element of the given array is correctly placed, then we
		// can say that
		// all the elements of the given array are correctly placed
		if (correctlyPlaced(high, array[high])) {
			System.out.println("No missing number. All elements are correctly placed");
			return 0;
		}

		// we have found the first element from the left in the given sequence
		// which is incorrectly placed.
		// Missing number must be this element's value minus 1
		if (low == high) {
			return array[high] - 1;
		}

		int mid = (low + high) / 2;

		// Array[mid] also could be the first element which is incorrectly
		// placed, so we need to include that element in the search
		if (!correctlyPlaced(mid, array[mid])) {
			high = mid;
		} else {
			low = mid + 1;
		}

		return findMissingElement(array, low, high);
	}

	private boolean correctlyPlaced(int index, int number) {
		// remember we are using 0 based indexing scheme
		if (number == (index + 1)) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 4, 5, 6 };
		MissingNumberIncresingSequence ob = new MissingNumberIncresingSequence();
		System.out.println(ob.findMissingElement(arr, 0, arr.length - 1));
	}

}
