// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution1251_2 {
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
                x[i] = Long.parseLong(st.nextToken());
                y[i] = Long.parseLong(st2.nextToken());
            }
            double E = Double.parseDouble(bf.readLine());
            double [][] edges = new double [N+1][N+1];
            for(int i=1; i<=N; i++){
                edges[i][i] = 0;
                for(int ii=i+1; ii<=N; ii++){
                    edges[i][ii] = E*(Math.pow((x[i]-x[ii]), 2) + Math.pow((y[i]-y[ii]), 2));
                    edges[ii][i] = E*(Math.pow((x[i]-x[ii]), 2) + Math.pow((y[i]-y[ii]), 2));
                }
            }
            boolean [] visited = new boolean[N+1];
            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a.v));
            for(int i=2; i<=N; i++){
                pq.add(new Edge(1, i, edges[1][i]));
            }
            visited[1] = true;
            while(!pq.isEmpty()){
                Edge polled = pq.poll();
                if(!visited[polled.b]){
                    ans += polled.v;
                    visited[polled.b] = true;
                    for(int i=1; i<=N; i++){
                        if(edges[polled.b][i]!=0&&!visited[i]){
                            pq.add(new Edge(polled.b, i, edges[polled.b][i]));
                        }
                    }
                }
            }


            sb.append("#").append(t).append(" ").append(Math.round(ans)).append("\n");
        }
    }

}