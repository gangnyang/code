import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2253 {
    static boolean [] small = new boolean[10001];
    static int [][] dp = new int[10001][150];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine());
        int N = Integer.parseInt(ss.nextToken());
        int M = Integer.parseInt(ss.nextToken());
        for(int i=0; i<M; i++){
            int k = Integer.parseInt(bf.readLine());
            small[k] = true;
        }
        for(int i=0; i<=N; i++){
            Arrays.fill(dp[i], 1000000007);
        }
        dp[1][0] = 0; // 초기값

        for(int i=2; i<=N; i++){ // 순회하면서 x-1 x x+1로 뛸 때 계산(그 전값이 있으면 무조건 가능한거임)
            if(small[i]) continue;
            for(int x=1; x<149; x++){
                if(i-x>=1){
                    dp[i][x] = Math.min(dp[i-x][x-1], Math.min(dp[i-x][x], dp[i-x][x+1]))+1; // x+1, x, x-1 순임
                }
            }
        }
        int min = Integer.MAX_VALUE; // 최솟값 계산
        for(int i=1; i<150; i++){
            min = Math.min(dp[N][i], min);
        }
        System.out.println(min<1000000007?min:-1); // 만약 초기값보다 작으면 min 출력
    }
}
