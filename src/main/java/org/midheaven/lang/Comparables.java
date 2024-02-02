package org.midheaven.lang;

import java.util.*;

public class Comparables {

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

    public static <T extends Comparable<T>> T min(T a , T b, T ... others){
        return min(Comparator.naturalOrder(), a , b, others);
    }

    public static <T> T min(Comparator<T> comparator, T a , T b, T ... others){
        if (others.length == 0){
            return compare(comparator, a,b) <=0 ? a : b;
        }
        var all = new ArrayList<T>(2 + others.length);
        all.add(a);
        all.add(b);
        all.addAll(Arrays.asList(others));
        return min(comparator, all).orElse(null);
    }

    public static <T extends Comparable<T>> Optional<T> min(Iterable<T> elements){
       return min(Comparator.naturalOrder(), elements);
    }

    public static <T> Optional<T> min(Comparator<T> comparator, Iterable<T> elements){
        var iterator = elements.iterator();
        if (!iterator.hasNext()){
            return Optional.empty();
        }

        T min = iterator.next();
        while(iterator.hasNext()){
            var candidate = iterator.next();
            if (compare(comparator, candidate, min) < 0){
                min = candidate;
            }
        }
        return Optional.ofNullable(min);
    }


    public static <T extends Comparable<T>> T max(T a , T b, T ... others){
        return max(Comparator.naturalOrder(), a , b, others);
    }

    public static <T> T max(Comparator<T> comparator, T a , T b, T ... others){
        if (others.length == 0){
            return compare(comparator, a,b) >=0 ? a : b;
        }
        var all = new ArrayList<T>(2 + others.length);
        all.add(a);
        all.add(b);

        all.addAll(Arrays.asList(others));

        return max(comparator, all).orElse(null);
    }

    public static <T extends Comparable<T>> Optional<T> max(Iterable<T> elements){
        return max(Comparator.naturalOrder(), elements);
    }

    public static <T> Optional<T> max(Comparator<T> comparator, Iterable<T> elements){
        var iterator = elements.iterator();
        if (!iterator.hasNext()){
            return Optional.empty();
        }

        T max = iterator.next();
        while(iterator.hasNext()){
            var candidate = iterator.next();
            if (compare(comparator, candidate, max) > 0){
                max = candidate;
            }
        }
        return Optional.ofNullable(max);
    }

    private Comparables(){}
}
