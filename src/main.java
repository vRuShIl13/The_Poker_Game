class Main {

    public static void main(String[] args) {
        int[] A = {78,6,5,46,78,1,46};
        int lo=0;
        int hi= A.length-1;
        int wah = mergeSortRecurse(A,lo,hi);
        System.out.println( wah);
//        for(int i=0; i < A.length; i++);
//        {
//
//        }
        System.out.print("[ ");
        for(int i=0; i < A.length; i++)
        {
            System.out.print(A[i] + " ");
        }
        System.out.print("]");

    }
    private static int mergeSortRecurse(int[] A, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            mergeSortRecurse(A, lo, mid);
            mergeSortRecurse(A, mid + 1, hi);
            int m = merge(A, lo, mid, hi);
            return m;
        }
            return 0;
    }

    private static int merge(int[] A, int lo, int mid, int hi) {
        int n = hi - lo + 1;

        int currSize = 0;
        int[] merged = new int[n];
//        for (int i = 0; i < n; i++) {
//            merged[i] = Integer.MAX_VALUE;
//        }
        int i = lo;
        int j = mid + 1;
        int k = 0;
        while ((i < mid + 1) && (j <= hi)) {
            if (A[i] < A[j]) {
                merged[k] = A[i];
                k++;
                i++;
            } else if (A[i] > A[j]) {
                merged[k] = A[j];
                k++;
                j++;
            } else {
                merged[k] = A[j];
                k++;
                i++;
                j++;
            }
            currSize++;
        }
        while (i < mid + 1) {
            merged[k] = A[i];
            k++;
            i++;
            currSize++;
        }
        while (j <= hi) {
            merged[k] = A[j];
            k++;
            j++;
            currSize++;
        }
        for (k = 0; k < n; k++) {
            A[lo + k] = merged[k];
        }
        return currSize;
    }
}