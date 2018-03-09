import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

	static int[][] map = new int[1001][1001];
	static long[][] dp = new long[1001][1001];
	static int modv = 1000081;

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();

		System.setIn(new FileInputStream("sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine().trim()); // 노드는 N+1개
			for (int i = 0; i < N + 1; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine().trim());
				for (int j = 0; j < N + 1; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			for (int i = 0; i < N + 1; i++) {
				for (int j = 0; j < N + 1; j++) {
					if (j == 0 && i == 0) {
						dp[0][0] = 1;
						continue;
					}
					findroute(i, j);

				}
			}
			System.out.println("#" + t + " " + dp[N][N]);
		}

		long end = System.currentTimeMillis();
		System.out.println((end - start) / (double) 1000 +"초");

	}

	private static void findroute(int i, int j) {
		// TODO Auto-generated method stub
		int dist = map[i][j];
		if (dist <= 0) {
			return;
		}
		dp[i][j] = 0;
		for (int k = 0; k <= dist; k++) { // k = 0,1,2,3,4,5 ~ dist
			int ii = k;
			int ji = dist - k;
			if (i - ii >= 0 && j - ji >= 0) {
				dp[i][j] += dp[i - ii][j - ji];
			}
		}
		dp[i][j] %= modv;
	}
}
