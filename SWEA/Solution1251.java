// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution1251 {
    static BufferedReader bf;
    static BufferedWriter bw;
    static StringTokenizer st;
    static StringBuilder sb;
    static long [] parent = new long [1001];
    static long [] rank = new long [1001];

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
        double v;
        Edge(int a, int b, double v){
            this.a = a;
            this.b = b;
            this.v = v;
        }
    }

    static void solve() throws Exception {
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            double ans=0;
            int N = Integer.parseInt(bf.readLine());
            long [] x = new long [N+1];
            long [] y = new long [N+1];
            st = new StringTokenizer(bf.readLine());
            StringTokenizer st2 = new StringTokenizer(bf.readLine());
            for(int i=1; i<=N; i++){
                parent[i]=i;
                rank[i]=1;
                x[i] = Long.parseLong(st.nextToken());
                y[i] = Long.parseLong(st2.nextToken());
            }
            double E = Double.parseDouble(bf.readLine());
            List<Edge> edges = new ArrayList<>();
            for(int i=1; i<=N; i++){
                for(int ii=i+1; ii<=N; ii++){
                    edges.add(new Edge (i, ii,  E*(Math.pow((x[i]-x[ii]), 2) + Math.pow((y[i]-y[ii]), 2))));
                }
            }
            Collections.sort(edges, Comparator.comparingDouble(a -> a.v));
            for(Edge e : edges){
                int a = e.a;
                int b = e.b;
                double v = e.v;
                if(find(a)!=find(b)){
                    union(a, b);
                    ans+=v;
                }
            }
            sb.append("#").append(t).append(" ").append(Math.round(ans)).append("\n");
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