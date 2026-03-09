// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution3289 {
    static BufferedReader bf;
    static BufferedWriter bw;
    static StringTokenizer ss;
    static StringBuilder sb;
    static int [] rank = new int[1000001];
    static int [] parent = new int[1000001];

    public static void main(String[] args) throws Exception {
        bf = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();

        // TODO: solve
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            sb.append("#").append(t).append(" ");
            ss = new StringTokenizer(bf.readLine(), " ");
            int n = Integer.parseInt(ss.nextToken());
            int m = Integer.parseInt(ss.nextToken());
            for(int i=1; i<=n; i++){
                parent[i] = i;
                rank[i] = 1;
            }
            for(int i=1; i<=m; i++){
                ss = new StringTokenizer(bf.readLine());
                int command = Integer.parseInt(ss.nextToken());
                int a = Integer.parseInt(ss.nextToken());
                int b = Integer.parseInt(ss.nextToken());
                if(command==0){
                    union(a, b);
                }else{
                    sb.append(find(a)==find(b)?1:0);
                }
            }
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        bf.close();
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
        if(parent[v] == v){
            return parent[v];
        }else{
            return parent[v] = find(parent[v]);
        }
    }
}