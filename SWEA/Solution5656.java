import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution5656 {
    static int N, W, H, w_cnt, ans;
    static int [] dx = {0, 1, 0};
    static int [] dy = {1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            ans=Integer.MAX_VALUE;
            w_cnt=0;
            ss = new StringTokenizer(bf.readLine(), " ");
            N = Integer.parseInt(ss.nextToken());
            W = Integer.parseInt(ss.nextToken());
            H = Integer.parseInt(ss.nextToken());
            int [][] board = new int[H][W];
            for(int i=0; i<H; i++){
                ss = new StringTokenizer(bf.readLine(), " ");
                for(int ii=0; ii<W; ii++){
                    board[i][ii] = Integer.parseInt(ss.nextToken());
                    if(board[i][ii]!=0) w_cnt++; // 벽돌의 개수 세기
                }
            }
            // 이제 dfs로 N만큼 벽돌을 부숴야해
            dfs(0, board, w_cnt);
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
        System.out.print(sb);


    }
    static void dfs(int cnt, int[][] board, int local_cnt){
        if(cnt==N){
            ans = Math.min(ans, local_cnt);
            return;
        }
        // 0부터 W-1까지 쏘고 쏜 보드판을 전달해서 DFS Depth Up
        for(int i=0; i<W; i++){
            int [][] new_board  = new int[H][W];
            for(int c = 0; c<H; c++){
                new_board[c] = board[c].clone();
            }
            int k = 0;
            while(k<H&&new_board[k][i]==0){
                k++;
            }
            if(k<H){
                // 가장 위의 벽돌을 찾았고 쏴서 없애야 함
                int splash = new_board[k][i];
                new_board[k][i] = 0;
                local_cnt--;
                for(int ii=1; ii<splash; ii++){
                    for(int dir =0; dir<3; dir++){
                        int tx = k+ii*dx[dir];
                        int ty = i+ii*dy[dir];
                        if(tx<0||tx>H-1||ty<0||ty>W-1) continue;
                        // 유효한 위치면 제거 하고 위에를 내려야 함
                        if(new_board[tx][ty]!=0){
                            local_cnt--;
                            for(int pos = tx; pos>=1; pos--){
                                new_board[pos][ty] = new_board[pos-1][ty];
                            }
                            new_board[0][ty] = 0; // 첫 칸은 무조건 0됨
                        }
                    }
                }
            }

            // 전부 제거했으면 다음 단계로
            dfs(cnt+1, new_board, local_cnt);
        }
    }
}
