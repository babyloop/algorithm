import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static ArrayList<Wall> wall;
	static int N;
	static int maxDist;

	public static class TreeNode {
		ArrayList<TreeNode> children = new ArrayList<>();
	}

	public static class Wall {
		int x;
		int y;
		int r;

		public Wall(int x2, int y2, int r2) {
			this.x = x2;
			this.y = y2;
			this.r = r2;
		}
	}

	public static void main(String[] args) throws Exception {

		System.setIn(new FileInputStream("sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int TC = Integer.parseInt(br.readLine());

		for (int i = 0; i < TC; i++) {
			N = Integer.parseInt(br.readLine().trim());
			wall = new ArrayList<>();

			for (int j = 0; j < N; j++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int r = Integer.parseInt(st.nextToken());
				wall.add(new Wall(x, y, r));
			}

			TreeNode treeNode = setTree(0); // 빈 트리 노드 생성
			maxDist = 0;
			int dist = getDist(treeNode);

			System.out.println(Math.max(maxDist, dist));

			wall = null;
		}

	}

	private static int getDist(TreeNode root) {

		ArrayList<TreeNode> chidren = root.children;
		int size = chidren.size();

		if (size == 0) {
			return 0;
		}

		int[] dists = new int[size]; //
		for (int i = 0; i < size; i++) {
			dists[i] = getDist(chidren.get(i));
		}
		if (size >= 2) {
			Arrays.sort(dists);
			int longest = dists[size - 1] + 1;
			int second = dists[size - 2] + 1;
			maxDist = Math.max(maxDist, longest + second);
		}

		return dists[size - 1] + 1;
	}

	private static TreeNode setTree(int root) {
		TreeNode rootNode = new TreeNode();
		for (int i = 0; i < N; i++) {
			if (isChild(root, i)) { // 정렬 없이 모든 노드 확인
				rootNode.children.add(setTree(i));
				// 모든 노드 확인 하여 자신의 바로 아래 child 노드를 찾아 연결 한다.
			}
		}
		return rootNode;
	}

	private static boolean isChild(int parent, int child) {
		if (!enclose(parent, child)) { // 포함 하는가?
			return false;
		}

		for (int i = 0; i < N; i++) { // 중간에 성벽이 있는가?
			if (i != parent && i != child) { // r값으로도 가지치기 가능
				if (enclose(parent, i) && enclose(i, child)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean enclose(int parent, int child) {
		Wall parentWall = wall.get(parent);
		Wall childWall = wall.get(child);
		if (parentWall.r > childWall.r) {
			// x^2+y^2 두 점 사이의 거리
			double dist = Math.pow(parentWall.x - childWall.x, 2) + Math.pow(parentWall.y - childWall.y, 2);
			// (r-r)^2 지름의 차이
			double rdist = Math.pow(parentWall.r - childWall.r, 2);
			if (dist < rdist) {
				return true;
			}
		}
		return false;
	}
}
