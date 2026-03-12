import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution2819 {
    static int [][] board = new int [4][4];
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, 1, 0, -1};
    static HashMap<Integer, Boolean> map;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            map = new HashMap<>();
            for(int i=0; i<4; i++){
                ss = new StringTokenizer(bf.readLine(), " ");
                for(int ii=0; ii<4; ii++){
                    board[i][ii] = Integer.parseInt(ss.nextToken());
                }
            }
            for(int i=0; i<4; i++){
                for(int ii=0; ii<4; ii++){
                    dfs(1, i, ii, board[i][ii]);
                }
            }
            sb.append("#").append(t).append(" ").append(map.size()).append("\n");
        }
        System.out.print(sb);
    }
    static void dfs(int cnt, int x, int y, int local){
        if(cnt==7){
            map.put(local, true);
            return;
        }
        local*=10;
        for(int i=0; i<4; i++){
            int tx = x + dx[i];
            int ty = y + dy[i];
            if(tx<0||tx>3||ty<0||ty>3) continue;
            dfs(cnt+1, tx, ty, local+board[tx][ty]);
        }
    }
}
