package org.midheaven.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents Comparables.
 */
public class Comparables {

    /**
     * Performs compare.
     * @param a the a value
     * @param b the b value
     * @return the result of compare
     */
    public static <T extends Comparable<T>> int compare(T a , T b){
        if (a == b){
            return 0;
        } else if (a == null){
            return -1;
        } else if (b == null){
            return 1;
        }
        return a.compareTo(b);
    }

    /**
     * Performs compare.
     * @param comparator the comparator value
     * @param a the a value
     * @param b the b value
     * @return the result of compare
     */
    public static <T> int compare(Comparator<T> comparator, T a , T b){
        Objects.requireNonNull(comparator);
        if (a == b){
            return 0;
        } else if (a == null){
            return -1;
        } else if (b == null){
            return 1;
        }
        return comparator.compare(a,b);
    }

    /**
     * Performs min.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of min
     */
    public static <T extends Comparable<T>> T min(T a , T b, T ... others){
        return min(Comparator.naturalOrder(), a , b, others);
    }

    /**
     * Performs min.
     * @param comparator the comparator value
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of min
     */
    public static <T> T min(Comparator<T> comparator, T a , T b, T ... others){
        if (others.length == 0){
            return compare(comparator, a,b) <=0 ? a : b;
        }
        var all = new ArrayList<T>(2 + others.length);
        all.add(a);
        all.add(b);
        all.addAll(Arrays.asList(others));
        return min(comparator, all).orNull();
    }

    /**
     * Performs min.
     * @param elements the elements value
     * @return the result of min
     */
    public static <T extends Comparable<T>> Maybe<T> min(Iterable<T> elements){
       return min(Comparator.naturalOrder(), elements);
    }

    /**
     * Performs min.
     * @param comparator the comparator value
     * @param elements the elements value
     * @return the result of min
     */
    public static <T> Maybe<T> min(Comparator<T> comparator, Iterable<T> elements){
        var iterator = elements.iterator();
        if (!iterator.hasNext()){
            return Maybe.none();
        }

        T min = iterator.next();
        while(iterator.hasNext()){
            var candidate = iterator.next();
            if (compare(comparator, candidate, min) < 0){
                min = candidate;
            }
        }
        return Maybe.of(min);
    }


    /**
     * Performs max.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of max
     */
    public static <T extends Comparable<T>> T max(T a , T b, T ... others){
        return max(Comparator.naturalOrder(), a , b, others);
    }

    /**
     * Performs max.
     * @param comparator the comparator value
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of max
     */
    public static <T> T max(Comparator<T> comparator, T a , T b, T ... others){
        if (others.length == 0){
            return compare(comparator, a,b) >=0 ? a : b;
        }
        var all = new ArrayList<T>(2 + others.length);
        all.add(a);
        all.add(b);

        all.addAll(Arrays.asList(others));

        return max(comparator, all).orNull();
    }

    /**
     * Performs max.
     * @param elements the elements value
     * @return the result of max
     */
    public static <T extends Comparable<T>> Maybe<T> max(Iterable<T> elements){
        return max(Comparator.naturalOrder(), elements);
    }

    /**
     * Performs max.
     * @param comparator the comparator value
     * @param elements the elements value
     * @return the result of max
     */
    public static <T> Maybe<T> max(Comparator<T> comparator, Iterable<T> elements){
        var iterator = elements.iterator();
        if (!iterator.hasNext()){
            return Maybe.none();
        }

        T max = iterator.next();
        while(iterator.hasNext()){
            var candidate = iterator.next();
            if (compare(comparator, candidate, max) > 0){
                max = candidate;
            }
        }
        return Maybe.of(max);
    }

    /**
     * Performs Comparables.
     * @return the result of Comparables
     */
    private Comparables(){}
}
