package tight_loosely_coupling;

import java.util.Arrays;
public class LooselyCoupledService {
    private SortAlgorithm sortAlgorithm;

    public LooselyCoupledService() { }

    public LooselyCoupledService(SortAlgorithm sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public void complexBusiness(int[] array) {
        sortAlgorithm.sort(array);                // chỉ gọi qua interface
        Arrays.stream(array).forEach(System.out::println);
    }

    public static void main(String[] args) {
        // Cắm thuật toán Bubble:
        LooselyCoupledService s1 =
                new LooselyCoupledService(new LooselyBubbleSortAlgorithm());
        s1.complexBusiness(new int[]{11, 21, 13, 42, 15});

        // Đổi sang "QuickSort" mà KHÔNG sửa service:
        LooselyCoupledService s2 =
                new LooselyCoupledService(new QuickSortAlgorithm());
        s2.complexBusiness(new int[]{11, 21, 13, 42, 15});
    }
}
