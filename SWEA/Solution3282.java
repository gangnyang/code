import java.util.*;
import java.io.*;

/*
민수에게는 1번부터 N번까지의 번호가 부여된 N(1≤N≤100)개의 물건과 최대 K(1≤K≤1000) 부피만큼을 넣을 수 있는 가방이 있다.

1번 물건부터 N번 물건 각각은 부피  Vi와 가치 Ci 를 가지고 있다. (1≤Vi, Ci≤100)

민수는 물건들 중 몇 개를 선택하여 가방에 넣어서 그 가치의 합을 최대화하려고 한다.

단, 선택한 물건들의 부피 합이 K 이하여야 한다.

민수가 가방에 담을 수 있는 최대 가치를 계산하자.

[입력]

첫 번째 줄에 테스트 케이스의 수 T가 주어진다.

각 테스트 케이스의 첫째 줄에 물건의 개수와 가방의 부피인 N K가 주어진다.

다음 N개의 줄에 걸쳐서 i번 물건의 정보를 나타내는 부피  Vi와 가치 Ci가 주어진다.

[출력]

각 테스트 케이스마다 가방에 담을 수 있는 최대 가치를 출력한다.
 */

public class Solution3282 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer ss;
    static StringBuilder sb = new StringBuilder();
    static int ans=0;
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(bf.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            solve();
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }

    static void solve() throws Exception{
        ans=0;
        ss = new StringTokenizer(bf.readLine());
        int N = Integer.parseInt(ss.nextToken());
        int K = Integer.parseInt(ss.nextToken());
        int [][] dp = new int [N+1][K+1];
        int [] V = new int[N+1];
        int [] C = new int[N+1];
        dp[0][0] = 0;
        for(int i = 1; i <= N; i++){
            dp[i][0] = 0;
            ss = new StringTokenizer(bf.readLine());
            V[i] = Integer.parseInt(ss.nextToken());
            C[i] = Integer.parseInt(ss.nextToken());
        }
        Arrays.fill(dp[0], 0);
        for(int i=1; i <= N; i++){
            for(int ii=1; ii <=K; ii++){
                if(V[i]>ii){
                    dp[i][ii] = dp[i-1][ii];
                }else{
                    dp[i][ii] = Math.max(C[i] + dp[i-1][ii-V[i]], dp[i-1][ii]);
                }
            }
        }
        ans = dp[N][K];
    }
}
