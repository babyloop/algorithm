import java.io.*;
import java.util.StringTokenizer;

/**
 * BOJ#2042 ������ �� ���ϱ� https://www.acmicpc.net/problem/2042
 */
public class SegmentSum {

	static final int QUERY_CHANGE = 1; // �ٲٴ� ���
	static final int QUERY_RANGESUM = 2; // ���� ���ϴ� ���

	public static void main(String[] args) throws IOException {

		int N; // Data�� ����
		int nQuery; // ��� ����

		System.setIn(new FileInputStream("sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		nQuery = Integer.parseInt(st.nextToken());
		nQuery += Integer.parseInt(st.nextToken()); // ���� �̷� �����? 2�� �ٲٰ� 2�� ����

		int[] arr = new int[N];

		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine()); // �� �Է�
		}

		// solve

		SegmentTree segmentTree = new SegmentTree(arr, N);
		// ���� ������ �迭�� ������ �Է��� ��
		segmentTree.printnode();

		while (nQuery-- > 0) {

			st = new StringTokenizer(br.readLine());

			int queryType = Integer.parseInt(st.nextToken());
			int idx1 = Integer.parseInt(st.nextToken());
			int idx2 = Integer.parseInt(st.nextToken());

			// Segment Tree update
			if (queryType == QUERY_CHANGE) {

				segmentTree.update(idx1 - 1, idx2, 1, 0, N - 1);
				segmentTree.printnode();

			}

			// ���� �� ���ϱ�
			else if (queryType == QUERY_RANGESUM) {

				bw.write(segmentTree.query(idx1 - 1, idx2 - 1, 1, 0, N - 1) + "\n");
			}

		} // ~query loop

		bw.flush();

		bw.close();
		br.close();
	}
}

class SegmentTree {

	long[] segmentArr; // The array that stores segment tree nodes

	SegmentTree(int[] arr, int n) {

		System.out.println(n + "���� �Է� ");
		int x = (int) Math.ceil(Math.log(n) / Math.log(2));
		int segmentSize = (int) Math.pow(2, x) * 2;
		// �׳� �����ϴ� 4������
		segmentArr = new long[segmentSize];
		init(arr, 0, n - 1, 1);
		// data �迭, 0 ~ n-1����, 1�� ��� ����
	}

	// node�� root�� �ϴ� ����Ʈ���� �ʱ�ȭ�ϰ�, �� ������ �ּ�ġ�� ��ȯ�Ѵ�
	long init(int[] arr, int left, int right, int node) {
		if (left == right) { // ���� ��� ���� => Data �Է�
			return segmentArr[node] = arr[left];
		}
		// ������ �ɰ�
		int mid = (left + right) / 2;
		segmentArr[node] = init(arr, left, mid, node * 2) + init(arr, mid + 1, right, node * 2 + 1);
		// �ö���鼭 ���� ���Ͽ� �Է�
		return segmentArr[node];
	}

	// ���� �� query
	long query(int left, int right, int node, int nodeLeft, int nodeRight) {

		// �� ������ ��ġ�� �ʴ� ���
		if (left > nodeRight || right < nodeLeft)
			return 0;

		// ��� ������ ������ ���ϴ� ���
		if (left <= nodeLeft && right >= nodeRight)
			return segmentArr[node];

		// �� ���� ���
		int mid = (nodeLeft + nodeRight) / 2;

		return query(left, right, node * 2, nodeLeft, mid) + query(left, right, (node * 2) + 1, mid + 1, nodeRight);
	}

	// Segment Tree�� �����ϰ� �ش� ��� ������ ���� ��ȯ�Ѵ�
	long update(int index, int newValue, int node, int nodeLeft, int nodeRight) {

		// Node ������ ���Ե��� �ʴ� ���
		if (index < nodeLeft || index > nodeRight)
			return segmentArr[node];

		// Node ������ ���ԵǴ� ���
		// Leaf�� ���
		if (nodeLeft == nodeRight)
			return segmentArr[node] = newValue;

		int mid = (nodeLeft + nodeRight) / 2;

		return segmentArr[node] = update(index, newValue, node * 2, nodeLeft, mid)
				+ update(index, newValue, (node * 2) + 1, mid + 1, nodeRight);
	}

	public void printnode() {
		int j = 2;
		for (int i = 0; i < segmentArr.length; i++) {

			System.out.print(segmentArr[i] + " ");
			if (i == 0) {
				System.out.println();
			} else if ((i + 1) % j == 0) {
				System.out.println();
				j = j * 2;
			} else if (i == 1) {
				System.out.println();
			}
		}
		System.out.println();
		return; //
	}

}
