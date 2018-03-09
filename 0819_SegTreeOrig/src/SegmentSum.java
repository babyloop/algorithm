import java.io.*;
import java.util.StringTokenizer;

/**
 * BOJ#2042 구간의 합 구하기 https://www.acmicpc.net/problem/2042
 */
public class SegmentSum {

	static final int QUERY_CHANGE = 1; // 바꾸는 명령
	static final int QUERY_RANGESUM = 2; // 합을 구하는 명령

	public static void main(String[] args) throws IOException {

		int N; // Data의 개수
		int nQuery; // 몇번 변경

		System.setIn(new FileInputStream("sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		nQuery = Integer.parseInt(st.nextToken());
		nQuery += Integer.parseInt(st.nextToken()); // 오오 이런 방법이? 2번 바꾸고 2번 구함

		int[] arr = new int[N];

		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine()); // 수 입력
		}

		// solve

		SegmentTree segmentTree = new SegmentTree(arr, N);
		// 보통 데이터 배열과 개수를 입력해 줌
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

			// 구간 합 구하기
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

		System.out.println(n + "개의 입력 ");
		int x = (int) Math.ceil(Math.log(n) / Math.log(2));
		int segmentSize = (int) Math.pow(2, x) * 2;
		// 그냥 복잡하니 4곱하자
		segmentArr = new long[segmentSize];
		init(arr, 0, n - 1, 1);
		// data 배열, 0 ~ n-1까지, 1번 노드 부터
	}

	// node를 root로 하는 서브트리를 초기화하고, 이 구간의 최소치를 반환한다
	long init(int[] arr, int left, int right, int node) {
		if (left == right) { // 리프 노드 도달 => Data 입력
			return segmentArr[node] = arr[left];
		}
		// 반으로 쪼갬
		int mid = (left + right) / 2;
		segmentArr[node] = init(arr, left, mid, node * 2) + init(arr, mid + 1, right, node * 2 + 1);
		// 올라오면서 합을 구하여 입력
		return segmentArr[node];
	}

	// 구간 합 query
	long query(int left, int right, int node, int nodeLeft, int nodeRight) {

		// 두 구간이 겹치지 않는 경우
		if (left > nodeRight || right < nodeLeft)
			return 0;

		// 노드 구간이 완전히 속하는 경우
		if (left <= nodeLeft && right >= nodeRight)
			return segmentArr[node];

		// 그 외의 경우
		int mid = (nodeLeft + nodeRight) / 2;

		return query(left, right, node * 2, nodeLeft, mid) + query(left, right, (node * 2) + 1, mid + 1, nodeRight);
	}

	// Segment Tree를 갱신하고 해당 노드 구간의 합을 반환한다
	long update(int index, int newValue, int node, int nodeLeft, int nodeRight) {

		// Node 구간에 포함되지 않는 경우
		if (index < nodeLeft || index > nodeRight)
			return segmentArr[node];

		// Node 구간에 포함되는 경우
		// Leaf인 경우
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
