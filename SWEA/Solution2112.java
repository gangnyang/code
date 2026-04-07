import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution2112 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer ss;
    static int D, W, K, ans=0;
    static boolean [][] film;
    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            sb.append("#").append(t).append(" ");
            solve();
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }

    static void solve() throws IOException{
        ans=0;
        ss = new StringTokenizer(bf.readLine());
        D = Integer.parseInt(ss.nextToken());
        W = Integer.parseInt(ss.nextToken());
        K = Integer.parseInt(ss.nextToken());
        film = new boolean[D][W];
        for(int i=0; i<D; i++){
            ss = new StringTokenizer(bf.readLine());
            for(int ii=0; ii<W; ii++){
                film[i][ii] = Integer.parseInt(ss.nextToken())==1;
            }
        }
        if(K==1) return;
        if(check()) return;
        ans=1_000_000_007;
        dfs(0, 0);
    }

    static void dfs(int cell_num, int changed){
        if(changed>=ans) return;
        if(cell_num==D){
            if(check()){
                ans = Math.min(ans, changed);
            }
            return;
        }
        dfs(cell_num+1, changed);
        boolean [] tmp = Arrays.copyOf(film[cell_num], W);
        Arrays.fill(film[cell_num], false);
        dfs(cell_num+1, changed+1);

        Arrays.fill(film[cell_num], true);
        dfs(cell_num+1, changed+1);

        film[cell_num] = tmp;
    }

    static boolean check(){
        int goal_cnt = K-1;
        for(int i=0; i<W; i++){
            int local_cnt = 0;
            for(int ii=1; ii<D; ii++){
                if(film[ii][i]==film[ii-1][i]){
                    local_cnt++;
                }else{
                    local_cnt=0;
                }
                if(local_cnt>=goal_cnt) break;
            }
            if(local_cnt<goal_cnt){
                return false;
            }
        }
        return true;
    }
}
