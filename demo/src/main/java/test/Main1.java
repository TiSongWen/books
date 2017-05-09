package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by tisong on 4/26/17.
 */
public class Main1 {

    static class NFA {
        private Digraph graph;
        private String regexp;
        private final int m;


        public NFA(String regexp) {
            this.regexp = regexp;
            m = regexp.length();
            Stack<Integer> ops = new Stack<Integer>();
            graph = new Digraph(m + 1);
            for (int i = 0; i < m; i++) {
                int lp = i;
                if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|')
                    ops.push(i);
                else if (regexp.charAt(i) == ')') {
                    int or = ops.pop();
                    if (regexp.charAt(or) == '(')
                        lp = or;
                }

                if (i < m - 1 && regexp.charAt(i + 1) == '*') {
                    graph.addEdge(lp, i + 1);
                    graph.addEdge(i + 1, lp);
                }
                if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
                    graph.addEdge(i, i + 1);
            }

        }


        public int recognizes(String txt) {
            DirectedDFS dfs = new DirectedDFS(graph, 0);
            List<Integer> pc = new ArrayList<Integer>();
            for (int v = 0; v < graph.V(); v++)
                if (dfs.marked(v)) pc.add(v);

            for (int i = 0; i < txt.length(); i++) {

                List<Integer> match = new ArrayList<Integer>();
                for (int v : pc) {
                    if (v == m) continue;
                    if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.')
                        match.add(v + 1);
                }
                dfs = new DirectedDFS(graph, match);
                pc = new ArrayList<Integer>();
                for (int v = 0; v < graph.V(); v++)
                    if (dfs.marked(v)) pc.add(v);


                if (pc.size() == 0) return 0;
            }

            for (int v : pc)
                if (v == m) return 1;
            return 0;
        }
    }

    public static void main(String[] args) {
//        String regexp = "(" + args[0] + ")";
//        String txt = args[1];

        Scanner scanner = new Scanner(System.in);
        String txt = scanner.nextLine();
        String regexp = "(" + scanner.nextLine() + ")";

        NFA nfa = new NFA(regexp);
        System.out.println(nfa.recognizes(txt));
    }



    static class Digraph {

        private final int V;
        private int E;
        private List<Integer>[] adj;
        private int[] indegree;


        public Digraph(int V) {
            this.V = V;
            this.E = 0;
            indegree = new int[V];
            adj = (List<Integer>[]) new List[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<Integer>();
            }
        }


        public int V() {
            return V;
        }


        public int E() {
            return E;
        }


        public void addEdge(int v, int w) {
            adj[v].add(w);
            indegree[w]++;
            E++;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    static class DirectedDFS {

        private boolean[] marked;

        private int count;


        public DirectedDFS(Digraph G, int s) {
            marked = new boolean[G.V()];
            dfs(G, s);
        }


        public DirectedDFS(Digraph G, Iterable<Integer> sources) {
            marked = new boolean[G.V()];
            for (int v : sources) {
                if (!marked[v]) dfs(G, v);
            }
        }

        private void dfs(Digraph G, int v) {
            count++;
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) dfs(G, w);
            }
        }

        public boolean marked(int v) {
            return marked[v];
        }

    }
}
