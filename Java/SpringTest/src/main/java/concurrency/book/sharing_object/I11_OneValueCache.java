package concurrency.book.sharing_object;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * OneValueCache
 * 
 * Immutable holder for caching a number and its factors
 *
 * Race conditions in accessing or updating multiple related variables can be
 * eliminated by using an immutable object to hold all the variables. With a
 * mutable holder object, you would have to use locking to ensure atomicity;
 * with an immutable one, once a thread acquires a reference to it, it need
 * never worry about another thread modifying its state. If the variables are to
 * be updated, a new holder object is created, but any threads working with the
 * previous holder still see it in a consistent state.
 */

public class I11_OneValueCache {
	private final BigInteger lastNumber;
	private final BigInteger[] lastFactors;

	public I11_OneValueCache(BigInteger i, BigInteger[] factors) {
		lastNumber = i;
		lastFactors = Arrays.copyOf(factors, factors.length);
	}

	public BigInteger[] getFactors(BigInteger i) {
		if (lastNumber == null || !lastNumber.equals(i))
			return null;
		else
			return Arrays.copyOf(lastFactors, lastFactors.length);
	}
}