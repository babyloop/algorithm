import java.util.Scanner;
public class Main {
    static int N;
    static int carNumbers[] = new int[1000001];
    static int relativeMap[] = new int[1000001];
    static int DP[][] = new int[1000001][3];
    static int INF = 987654321;
     
    static int getDist(int cur, int next) {
        int dist = Math.abs(relativeMap[cur] - relativeMap[next]);
        return Math.min(dist, N - dist);
    }
     
    public static void main (String []args) throws Exception {
        Scanner sc = new Scanner(System.in);
         
        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
             
            for (int i = 0; i < N; i++) {
                carNumbers[i] = sc.nextInt();
                relativeMap[carNumbers[i]] = i;
            }
             
            DP[1][0] = INF;
            DP[1][1] = getDist(carNumbers[0], 1);
            DP[1][2] = getDist(carNumbers[0], 2);
             
            for (int i = 2; i <= N; i++) {
                DP[i][0] = DP[i-1][2] + getDist(i, i-1);
                DP[i][1] = Math.min(DP[i-1][0] + getDist(i-2, i), DP[i-1][1] + getDist(i-1, i));
                DP[i][2] = Math.min(DP[i-1][0] + getDist(i-2, i+1), Math.min(DP[i-1][1] + getDist(i-1, i+1), DP[i-1][2] + getDist(i, i+1)));
            }
             
            System.out.println("#" + tc + " " + Math.min(DP[N][0], DP[N][1]));
        }
         
        sc.close();
    }
}
