package chapter8;

/**
 * Created by tisong on 3/23/17.
 */
public class Offer52 {
    public static int[] multiply(int[] A) {
        if (A == null || A.length < 1) {
            return null;
        }
        int i;

        int[] C = new int[A.length];
        for (i = 1, C[i-1] = 1; i < C.length; i++) {
            C[i] = C[i-1] * A[i-1];
        }

        int[] D = new int[A.length];
        for (i = D.length - 2, D[i+1] = 1; i >=0; i--) {
            D[i] = D[i+1] * A[i+1];
        }

        int[] B = new int[A.length];
        for (i = 0; i < B.length; i++) {
            B[i] = C[i] * D[i];
        }

        return B;
    }

    public static void main(String[] args) {
        int[] A = {1,2,3,4,5};
        int[] B = multiply(A);
        for (int b :B) {
            System.out.println(b);
        }
    }
}
