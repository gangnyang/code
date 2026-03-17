import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution5656 {
    static int N, W, H, ans;
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            ans=Integer.MAX_VALUE;
            ss = new StringTokenizer(bf.readLine(), " ");
            N = Integer.parseInt(ss.nextToken());
            W = Integer.parseInt(ss.nextToken());
            H = Integer.parseInt(ss.nextToken());
            int [][] board = new int[H][W];
            for(int i=0; i<H; i++){
                ss = new StringTokenizer(bf.readLine(), " ");
                for(int ii=0; ii<W; ii++){
                    board[i][ii] = Integer.parseInt(ss.nextToken());
                }
            }
            // 이제 dfs로 N만큼 벽돌을 부숴야해
            dfs(0, board);
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
        System.out.print(sb);


    }
    static void dfs(int cnt, int[][] board){
        if(cnt==N){
            int local_cnt=0;
            for(int i=0; i<H; i++){
                for(int ii=0; ii<W; ii++){
                    if(board[i][ii]!=0) local_cnt++;
                }
            }
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
            if(k<H){ // 큐에 계속해서 넣으면서 연쇄적으로 다 파괴함
                // 가장 위의 벽돌을 찾았고 쏴서 없애야 함
                int splash;
                Queue<int []> queue = new ArrayDeque<>();
                queue.add(new int[] {k, i, new_board[k][i]});
                new_board[k][i]=0;
                while(!queue.isEmpty()){
                    int [] polled = queue.poll();
                    splash = polled[2];
                    for(int ii=1; ii<splash; ii++){
                        for(int dir =0; dir<4; dir++){
                            int tx = polled[0]+ii*dx[dir];
                            int ty = polled[1]+ii*dy[dir];
                            if(tx<0||tx>H-1||ty<0||ty>W-1) continue;
                            // 유효한 위치면 제거 하고 위에를 내려야 함
                            if(new_board[tx][ty]!=0){
                                queue.add(new int [] {tx, ty, new_board[tx][ty]});
                                new_board[tx][ty]=0;
                            }
                        }
                    }
                }
            }
            // 이제 파괴했으니까 new_board에 0인 부분을 매꿔줘야 함
            falling(new_board);

            // 전부 제거했으면 다음 단계로
            dfs(cnt+    1, new_board);
        }
    }

    static void falling(int [][] board){
        for(int i=0; i<W; i++){
            Queue<Integer> queue = new ArrayDeque<>();
            for(int ii=H-1; ii>=0; ii--){
                if(board[ii][i]!=0){
                    queue.add(board[ii][i]);
                }
                board[ii][i] = 0;
            }

            int y = H-1;
            while(y>=0&&!queue.isEmpty()){
                board[y--][i] = queue.poll();
            }
        }
    }
}
