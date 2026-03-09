// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution7465 {
    static BufferedReader bf;
    static BufferedWriter bw;
    static StringTokenizer ss;
    static StringBuilder sb;
    static int [] parent = new int [101];
    static int [] rank = new int [101];
    static boolean [] check = new boolean[101];

    public static void main(String[] args) throws Exception {
        bf = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();

        solve();
        // TODO: solve

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        bf.close();
    }

    static void solve() throws Exception {
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            int ans = 0;
            Arrays.fill(check, false);
            sb.append("#").append(t).append(" ");
            ss = new StringTokenizer(bf.readLine(), " ");
            int N = Integer.parseInt(ss.nextToken());
            int M = Integer.parseInt(ss.nextToken());
            for(int i=1; i<=N; i++){
                parent[i] = i;
                rank[i] = 1;
            }
            for(int i=1; i<=M; i++){
                ss = new StringTokenizer(bf.readLine(), " ");
                int a = Integer.parseInt(ss.nextToken());
                int b = Integer.parseInt(ss.nextToken());
                union(a, b);
            }
            for(int i=1; i<=N; i++){
                if(!check[find(i)]){
                    check[find(i)] = true;
                    ans++;
                }
            }
            sb.append(ans).append("\n");
        }
    }

    static void union(int a, int b){
        int pa = find(a);
        int pb = find(b);
        if(rank[pa]>rank[pb]){
            parent[pb] = pa;
        }else if(rank[pa]==rank[pb]){
            parent[pb] = pa;
            rank[pa]++;
        }else{
            parent[pa] = pb;
        }
    }

    static int find(int v){
        if(parent[v]==v){
            return v;
        }else{
            return parent[v] = find(parent[v]);
        }
    }
}