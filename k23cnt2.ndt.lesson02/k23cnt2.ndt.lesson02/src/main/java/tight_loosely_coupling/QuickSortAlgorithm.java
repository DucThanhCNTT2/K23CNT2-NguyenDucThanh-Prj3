package tight_loosely_coupling;

import java.util.Arrays;

public class QuickSortAlgorithm implements SortAlgorithm {
    @Override
    public void sort(int[] array) {
        System.out.println("Sorted using QuickSort-like (Arrays.sort)");
        Arrays.sort(array);
    }
}
