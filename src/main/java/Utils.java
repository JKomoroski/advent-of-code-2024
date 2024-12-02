import java.util.Collection;
import java.util.Comparator;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> boolean isSorted(Collection<T> collection, Comparator<T> comparator) {
        if (collection.isEmpty() || collection.size() == 1) {
            return true;
        }

        var iter = collection.iterator();
        T current, previous = iter.next();

        while (iter.hasNext()) {
            current = iter.next();
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }

        return true;
    }
}
