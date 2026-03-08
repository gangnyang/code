import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main17070 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        int [][][] dp = new int [N+1][N+1][3]; // 0 가로 1 대각선 2 세로 방향
        int [][] board = new int [N+1][N+1];
        Arrays.fill(board[0], 0);
        for(int i=1; i<=N; i++){
            Arrays.fill(board[i], 0);
            ss = new StringTokenizer(bf.readLine());
            for(int ii=1; ii<=N; ii++){
                Arrays.fill(dp[i][ii], 0);
                board[i][ii] = Integer.parseInt(ss.nextToken());
            }
        }
        dp[1][2][0] = 1; // 초기에는 1, 2 칸에 가로 방향으로 파이프가 있음
        for(int i=1; i<=N; i++){ // DP 진행
            for(int ii=3; ii<=N; ii++){
                if(board[i][ii]!=1){
                    dp[i][ii][0] += dp[i][ii-1][0]; // 가로 세로는 두 방향에서 옴
                    dp[i][ii][0] += dp[i][ii-1][1];
                    dp[i][ii][2] += dp[i-1][ii][1];
                    dp[i][ii][2] += dp[i-1][ii][2];
                    if(board[i-1][ii]!=1&&board[i][ii-1]!=1){ // 대각선은 세 방향에서 출발
                        dp[i][ii][1] += dp[i-1][ii-1][0];
                        dp[i][ii][1] += dp[i-1][ii-1][1];
                        dp[i][ii][1] += dp[i-1][ii-1][2];
                    }
                }
            }
        }
        System.out.println(dp[N][N][0]+dp[N][N][1]+dp[N][N][2]); // 가로 대각선 세로 방향 다 더함
    }
}
