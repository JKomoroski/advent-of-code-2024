package ski.komoro.aoc.twentyfour;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.stream.Stream;
import ski.komoro.aoc.utils.Utils;

public class Day02 extends AOCBase {

    public static void main(final String[] args) throws Exception {
        new Day02().run();
    }

    Day02() {
        super("day-2", "in.txt");
    }

    @Override
    void part1(final Stream<String> fileInput) throws Exception {
        final var comp = differenceLessThan(4).thenComparing(differenceGreaterThan(0));
        final var count = fileInput.map(line -> line.split(" "))
                .map(Day02::toIntList)
                .filter(r -> Utils.isSorted(r, Comparator.naturalOrder()) || Utils.isSorted(r, Comparator.reverseOrder()))
                .filter(r -> Utils.isSorted(r, comp))
                .count();
        System.out.println("Part 1: " + count);
    }

    @Override
    void part2(final Stream<String> fileInput) throws Exception {
        final var count = fileInput.map(line -> line.split(" "))
                .map(Day02::toIntList)
                .filter(Day02::isSortedWithRemove)
                .count();

        System.out.println("Part 2: " + count);
    }

    private static List<Integer> toIntList(String[] l) {
            var r = new ArrayList<Integer>();
            for (final var s : l) {
                r.add(Integer.parseInt(s));
            }
            return r;
    }


    static Comparator<Integer> differenceGreaterThan(int diff) {
        return (prev, curr) -> Math.abs(prev - curr) > diff ? 0 : 1;
    }

    static Comparator<Integer> differenceLessThan(int diff) {
        return (prev, curr) -> Math.abs(prev - curr) < diff ? 0 : 1;
    }

    // Brute force it
    static <T> boolean isSortedWithRemove(List<Integer> r) {
        final Comparator<Integer> comp = differenceLessThan(4).thenComparing(differenceGreaterThan(0));
        if(Utils.isSorted(r, Comparator.naturalOrder()) || Utils.isSorted(r, Comparator.reverseOrder())) {
            if(Utils.isSorted(r, comp)) {
                return true;
            }
        }

        for (var i = 0; i < r.size(); i++) {
            final var copy = new ArrayList<Integer>(r);
            copy.remove(i);
            if(Utils.isSorted(copy, Comparator.naturalOrder()) || Utils.isSorted(copy, Comparator.reverseOrder())) {
                if(Utils.isSorted(copy, comp)) {
                    return true;
                }
            }
        }
        return false;
    }
}

