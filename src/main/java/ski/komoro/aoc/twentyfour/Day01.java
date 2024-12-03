package ski.komoro.aoc.twentyfour;

import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Stream;
import ski.komoro.aoc.utils.Tuple;

final class Day01 extends AOCBase {

    public static void main(final String[] args) throws Exception {
        new Day01().run();
    }

    private Day01() {
        super("day-1", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {
        final var col1 = new LinkedList<Integer>();
        final var col2 = new LinkedList<Integer>();
        fileInput.map(line -> line.split("   "))
                .map(s -> Tuple.of(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .iterator()
                .forEachRemaining(t -> {
                    col1.push(t.left());
                    col2.push(t.right());
                });
        Collections.sort(col1);
        Collections.sort(col2);

        long sum = 0;
        while (!col1.isEmpty() && !col2.isEmpty()) {
            final var one = col1.pop();
            final var two = col2.pop();
            sum += Math.abs(one - two);

        }
        System.out.println("Part 1: " + sum);
    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        final var col1 = new LinkedList<Integer>();
        final var col2 = new LinkedList<Integer>();
        fileInput.map(line -> line.split("   "))
                .map(s -> Tuple.of(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .iterator()
                .forEachRemaining(t -> {
                    col1.push(t.left());
                    col2.push(t.right());
                });
        Collections.sort(col1);
        Collections.sort(col2);

        long similarityScore = 0;
        for (final var i1 : col1) {
            long matches = 0;
            for (final var i2 : col2) {
                if  (i2 > i1) {
                    break;
                } else if (i2.equals(i1)) {
                    matches++;
                }
            }
            similarityScore += (matches * i1);
        }
        System.out.println("Part 2: " + similarityScore);

    }

}
