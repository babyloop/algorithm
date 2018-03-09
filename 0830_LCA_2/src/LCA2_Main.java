
//https://www.acmicpc.net/problem/11437

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LCA2_Main {

	static int N; // 2<=N<=100000
	static int M; // 1<=M<=100000 // 노드 쌍 개수
	static int MAX = 21; // roudup log (2,1000000);
	static int[][] parent = new int[100000][MAX]; // 노드와 깊이에 해당하는 정점 저장
	static int[] depth = new int[100000]; // 정점의 깊이를 저장?
	static ArrayList<Integer>[] adj;

	static void findParentDFS(int curValue, int dep, int parentValue) {
		// 1부터 출발 depth는 1, 부모값은 0
		depth[curValue] = dep; // 각 노드들의 깊이 저장
		parent[0][curValue] = parentValue; // 0번째 줄에 부모값을 저장
		// for (int nxt : adj[cur]) {
		for (int i = 0; i < adj[curValue].size(); i++) {
			int nxt = adj[curValue].get(i);
			if (nxt != parentValue) { // 부모값을 걸러내어
				findParentDFS(nxt, dep + 1, curValue); // 추가
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

		for (int i = 0; i < N - 1; i++) { // 각 노드들에 연결된 노드들을 분류

			int u, v;
			u = sc.nextInt();
			v = sc.nextInt();

			adj[u].add(v);
			adj[v].add(u);

		}

		findParentDFS(1, 1, 0);

		for (int j = 0; j < MAX - 1; j++) { // 세로 인덱스 높이의 최대값
			for (int i = 1; i <= N; i++) {
				int pari = parent[j][i];
				int getValue = parent[j][pari];
				parent[j + 1][i] = getValue; // j,i 값의 부모값을 2^j 인덱스에 넣는다.
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
			return LCA(v, u); // 깊이가 깊은 것을 앞으로 하고 다시 호출
		} else if (u == v) {
			return u; // 값을 찾음
		}

		int d = depth[u] - depth[v];
		for (int j = M; j >= 0; j--) { // 깊이 맞춰주기
			// if ((d & (1 << j)) > 0) { // 1, 2^1, 2^2 ...
			// u = parent[j][u]; // 같은 깊이의 값을 찾아 변경
			// }
			int depthv = depth[v];
			int depthu = depth[parent[j][u]];
			if (depth[v] <= depth[parent[j][u]]) {
				u = parent[j][u]; // 더 찾아야 함, 부모값으로 갱신
			}
		}

		if (u == v)
			return u; // 값을 찾음

		for (int i = M - 1; i >= 0; --i) {
			if (0 == parent[i][u]) {
				continue;
			} else if (parent[i][u] != parent[i][v]) { // 트리의 꼭대기부터 다를때까지 찾는 중
				u = parent[i][u];
			}
		}
		return parent[0][u];
	}
}