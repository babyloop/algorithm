
//https://www.acmicpc.net/problem/11437

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LCA2_Main {

	static int N; // 2<=N<=100000
	static int M; // 1<=M<=100000 // ��� �� ����
	static int MAX = 21; // roudup log (2,1000000);
	static int[][] parent = new int[100000][MAX]; // ���� ���̿� �ش��ϴ� ���� ����
	static int[] depth = new int[100000]; // ������ ���̸� ����?
	static ArrayList<Integer>[] adj;

	static void findParentDFS(int curValue, int dep, int parentValue) {
		// 1���� ��� depth�� 1, �θ��� 0
		depth[curValue] = dep; // �� ������ ���� ����
		parent[0][curValue] = parentValue; // 0��° �ٿ� �θ��� ����
		// for (int nxt : adj[cur]) {
		for (int i = 0; i < adj[curValue].size(); i++) {
			int nxt = adj[curValue].get(i);
			if (nxt != parentValue) { // �θ��� �ɷ�����
				findParentDFS(nxt, dep + 1, curValue); // �߰�
			}
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {

		System.setIn(new FileInputStream("sample_input.txt"));
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();

		adj = (ArrayList[]) new ArrayList[N + 1];

		for (int i = 1; i <= N; ++i) {
			adj[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < N - 1; i++) { // �� ���鿡 ����� ������ �з�

			int u, v;
			u = sc.nextInt();
			v = sc.nextInt();

			adj[u].add(v);
			adj[v].add(u);

		}

		findParentDFS(1, 1, 0);

		for (int j = 0; j < MAX - 1; j++) { // ���� �ε��� ������ �ִ밪
			for (int i = 1; i <= N; i++) {
				int pari = parent[j][i];
				int getValue = parent[j][pari];
				parent[j + 1][i] = getValue; // j,i ���� �θ��� 2^j �ε����� �ִ´�.
			}
		}
		M = sc.nextInt();

		for (int i = 0; i < M; ++i) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			System.out.println(LCA(x, y));
		}

		sc.close();
	}

	static int LCA(int u, int v) {

		// initial part
		if (depth[u] < depth[v]) {
			return LCA(v, u); // ���̰� ���� ���� ������ �ϰ� �ٽ� ȣ��
		} else if (u == v) {
			return u; // ���� ã��
		}

		int d = depth[u] - depth[v];
		for (int j = M; j >= 0; j--) { // ���� �����ֱ�
			// if ((d & (1 << j)) > 0) { // 1, 2^1, 2^2 ...
			// u = parent[j][u]; // ���� ������ ���� ã�� ����
			// }
			int depthv = depth[v];
			int depthu = depth[parent[j][u]];
			if (depth[v] <= depth[parent[j][u]]) {
				u = parent[j][u]; // �� ã�ƾ� ��, �θ����� ����
			}
		}

		if (u == v)
			return u; // ���� ã��

		for (int i = M - 1; i >= 0; --i) {
			if (0 == parent[i][u]) {
				continue;
			} else if (parent[i][u] != parent[i][v]) { // Ʈ���� �������� �ٸ������� ã�� ��
				u = parent[i][u];
			}
		}
		return parent[0][u];
	}
}