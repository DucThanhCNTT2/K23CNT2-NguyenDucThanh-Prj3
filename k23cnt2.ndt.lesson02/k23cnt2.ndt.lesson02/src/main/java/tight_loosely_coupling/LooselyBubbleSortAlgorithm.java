package tight_loosely_coupling;

public class LooselyBubbleSortAlgorithm implements SortAlgorithm {
    @Override
    public void sort(int[] arr) {
        System.out.println("Sorted using Bubble Sort (LOOSE)");
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
