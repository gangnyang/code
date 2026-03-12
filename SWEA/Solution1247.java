// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution1247 {
    static BufferedReader bf;
    static BufferedWriter bw;
    static StringTokenizer st;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        bf = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();

        // TODO: solve
        solve();

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        bf.close();
    }

    static int ans=0;
    static int N, endx, endy;
    static int [][] edges;
    static int [] x, y;
    static boolean[] visited;
    static void solve() throws Exception {
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            ans=Integer.MAX_VALUE;
            N = Integer.parseInt(bf.readLine());
            st = new StringTokenizer(bf.readLine());
            x = new int [N+1];
            y = new int [N+1];
            visited = new boolean[N+1];
            int startx = Integer.parseInt(st.nextToken());
            int starty = Integer.parseInt(st.nextToken());
            endx = Integer.parseInt(st.nextToken());
            endy = Integer.parseInt(st.nextToken());
            for(int i=1; i<=N; i++){
                x[i] = Integer.parseInt(st.nextToken());
                y[i] = Integer.parseInt(st.nextToken());
            }

            for(int i=1; i<=N; i++){
                visited[i] = true;
                int dist = Math.abs(x[i]-startx) + Math.abs(y[i]-starty);
                dfs(0, i, dist);
                visited[i] = false;
            }
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
    }
    static void dfs(int cnt, int index, int sum){
        if(sum>=ans) return;
        if(cnt==N-1){
            int dist = Math.abs(x[index]-endx) + Math.abs(y[index]-endy);
            ans = Math.min(dist + sum, ans);
            return;
        }
        for(int i=1; i<=N; i++){
            if(!visited[i]){
                visited[i] = true;
                int dist = Math.abs(x[index]-x[i]) + Math.abs(y[index]-y[i]);
                dfs(cnt+1, i, sum+dist);
                visited[i] = false;
            }

        }
    }
}