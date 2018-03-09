//https://www.acmicpc.net/problem/11437

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class LCA_Main {
    static int N;
    static ArrayList<ArrayList<Integer>> adj;
    static ArrayList<Integer> dfsPath;
    static int[] visited;
    static SegmentTree segTree;
    static int id;
    static int[] idToNode;
     
    public static class SegmentTree {
        ArrayList<Integer> data;
        int size;
        int[] values;
        SegmentTree(ArrayList<Integer> data) {
            this.data = data;
            size = data.size();
            values = new int[size * 4];
            init(1, 0, size - 1);
        }
        public int init(int node, int left, int right) {
            if (left == right) {
                values[node] = data.get(left);
            } else {
                int mid = (left + right) / 2;
                values[node] = Math.min(init(node * 2, left, mid), init(node * 2 + 1, mid + 1, right));
            }
            return values[node];
        }
        public int query(int node, int queryL, int queryR, int left, int right) {
            if (left >= queryL && right <= queryR) {
                return values[node];
            } else if (right < queryL || left > queryR) {
                return Integer.MAX_VALUE;
            }
            int mid = (left + right) / 2;
            return Math.min(query(node * 2, queryL, queryR, left, mid),
                    query(node * 2 + 1, queryL, queryR, mid + 1, right));
        }
        public int query(int queryL, int queryR) {
            if (queryL > queryR) {
                int temp = queryL;
                queryL = queryR;
                queryR = temp;
            }
            return query(1, queryL, queryR, 0, size - 1);
        }
    }
    public static void dfs(int node) {
        id++;
        int myId = id;
        idToNode[id] = node;
        dfsPath.add(myId);
        visited[node] = dfsPath.size();
        for (int next : adj.get(node)) {
            if (visited[next] == 0) {
                dfs(next);
                dfsPath.add(myId);
            }
        }
    }
    public static void initData() {
        dfsPath = new ArrayList<Integer>();
        visited = new int[N + 1];
        idToNode = new int[N + 1];
        dfs(1);
        segTree = new SegmentTree(dfsPath);
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        
    	System.setIn(new FileInputStream("sample_input.txt"));
    	Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        adj = new ArrayList<ArrayList<Integer>>(); 
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<Integer>());
        }
        int u, v;
        for (int i = 1; i < N; i++) {
            u = sc.nextInt();
            v = sc.nextInt();
            adj.get(u).add(v); 
            adj.get(v).add(u);
        }
        initData();
        
        
        int queryCnt = sc.nextInt();
        int lca;
        for (int i = 1; i <= queryCnt; i++) {
            u = sc.nextInt();
            v = sc.nextInt();
            lca = segTree.query(visited[u] - 1, visited[v] - 1);
            System.out.println(idToNode[lca]);
        }
        sc.close();
    }
}