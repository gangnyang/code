import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2169 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine());
        int N = Integer.parseInt(ss.nextToken());
        int M = Integer.parseInt(ss.nextToken());
        int [][] board = new int [N+1][M+1];
        int [][] dp = new int [N+1][M+1];
        for(int i=1; i<=N; i++){
            ss = new StringTokenizer(bf.readLine());
            for(int ii=1; ii<=M; ii++){
                board[i][ii] = Integer.parseInt(ss.nextToken());
            }
        }
        dp[1][1] = board[1][1];
        for(int ii=2; ii<=M; ii++){
            dp[1][ii] = dp[1][ii-1] + board[1][ii];
        }
        for (int i=2; i<=N; i++){
            int [] ldp = new int [M+2]; // 좌방향
            int [] rdp = new int [M+2]; // 우방향
            rdp[1] = dp[i-1][1] + board[i][1]; // 1번 인덱스는 위에서 옴
            for(int ii=2; ii<=M; ii++){ // 위랑 왼쪽이랑 비교해서 갱신
                rdp[ii] = Math.max(dp[i-1][ii], rdp[ii-1]) + board[i][ii];
            }
            ldp[M] = dp[i-1][M] + board[i][M]; 
            for(int ii=M-1; ii>=1; ii--){ // 위랑 오른쪽이랑 비교해서 갱신
                ldp[ii] = Math.max(dp[i-1][ii], ldp[ii+1]) + board[i][ii];
            }

            for(int ii=1; ii<=M; ii++){ // ldp랑 rdp랑 비교해서 갱신
                dp[i][ii] = Math.max(ldp[ii], rdp[ii]);
            }
        }
        System.out.println(dp[N][M]);
    }
}
