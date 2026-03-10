// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution3124 {
    static BufferedReader bf;
    static BufferedWriter bw;
    static StringTokenizer st;
    static StringBuilder sb;
    static long [] parent = new long [100001];
    static long [] rank = new long [100001];

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

    static class Edge{
        int a;
        int b;
        long v;
        Edge(int a, int b, long v){
            this.a = a;
            this.b = b;
            this.v = v;
        }
    }

    static void solve() throws Exception {
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            long ans=0;
            st = new StringTokenizer(bf.readLine());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            for(int i=1; i<=V; i++){
                parent[i]=i;
                rank[i]=1;
            }
            List<Edge> edges = new ArrayList<>();
            for(int i=1; i<=E; i++){
                st = new StringTokenizer(bf.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());
                int C = Integer.parseInt(st.nextToken());
                edges.add(new Edge(A, B, C));
            }
            Collections.sort(edges, Comparator.comparingLong(a -> a.v));
            for(Edge e : edges){
                int a = e.a;
                int b = e.b;
                long v = e.v;
                if(find(a)!=find(b)){
                    union(a, b);
                    ans+=v;
                }
            }
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
    }

    static long find(int value){
        if(parent[value] ==value) return value;
        else{
            return parent[value] = find((int)parent[value]);
        }
    }

    static void union(int a, int b){
        int pa = (int)find(a);
        int pb = (int)find(b);
        if(rank[pa]>rank[pb]){
            parent[pb] = pa;
        }else{
            parent[pa] = pb;
            if(rank[pa]==rank[pb]){
                rank[pb]++;
            }
        }
    }
}