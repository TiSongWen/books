package test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tisong on 3/30/17.
 */
public class Main {
//    public static void main(String[] args) {
//
//        int n;
//        int m;
//        Scanner scanner = new Scanner(System.in);
//        n = scanner.nextInt();
//        m = scanner.nextInt();
//
//
//        TrieST trie = new TrieST();
//        for (int i = 1; i <= n; i++) {
//            trie.put(String.valueOf(i));
//        }
//
//        for (int number: trie.keys()) {
//            m--;
//            if (m == 0) {
//                System.out.println(number);
//                break;
//            }
//        }
//    }

    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    public static void main(String[] args) throws IOException {
        long n = nextLong(), m = nextLong();
        long result = rec(0, n + 1, m);
        System.out.println(result);

        AtomicInteger atomicInteger = new AtomicInteger(1);


    }

    /**
     * @param current
     * @param top:< top
     * @param rank
     * @return
     */
    static long rec(long current, long limit, long rank) {
        // System.out.println(current + "," + limit + "," + rank);
        if (rank == 0)
            return current;
        rank -= 1;
        long digit = 1, next = -1;
        for (digit = 0; digit < 10; digit++) {
            if (current == 0 && digit == 0)
                continue;
            next = current * 10 + digit;
            long base = 1;
            long gap = 0;
            while (true) {
                long lower = next * base;
                long upper = (next + 1) * base;
                if (limit <= lower)
                    break;
                gap += Math.min(upper, limit) - lower;
                base = base * 10;
            }
            // System.out.println(rank + " bi " + gap);
            if (rank < gap)
                break;
            rank -= gap;
        }
        return rec(next, limit, rank);
    }

    static long nextLong() throws IOException {
        in.nextToken();
        return (long) in.nval;
    }

}

class TrieST {
    private static int R = 10;
    private Node root = new Node();

    private static class Node {
        Node[] next = new Node[R];
        boolean flag;
        public Node() {
            flag = false;
        }
    }

    public Queue<Integer> keys() {
        Queue<Integer> queue = new LinkedList<Integer>();
        collect(root, 0, queue);
        return queue;
    }

    public void collect(Node x, int number, Queue<Integer> queue) {
        if (x == null) {
            return ;
        }

        if (x.flag) {
            queue.add(number);
        } else if (x != root) {
            return ;
        }

        for (int i = 0; i < R; i++) {
            collect(x.next[i], number * 10 + i, queue);
        }
    }

    public void put(String number) {
        root = put(root, number, 0);
    }

    private Node put(Node node, String number, int d) {
        if (node == null) {
            node = new Node();
        }

        if (number.length() == d) {
            node.flag = true;
            return node;
        }
        char c = number.charAt(d);

        node.next[c-'0'] = put(node.next[c-'0'], number, d+1);
        return node;
    }

}
