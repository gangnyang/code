// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution3124_2 {
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
            List<long[]>[] edges = new ArrayList[V+1];
            for(int i=0; i<=V; i++){
                edges[i] = new ArrayList<>();
            }
            for(int i=1; i<=E; i++){
                st = new StringTokenizer(bf.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());
                long C = Integer.parseInt(st.nextToken());
                edges[A].add(new long[] {B, C});
                edges[B].add(new long[] {A, C});
            }
            PriorityQueue<Edge> pq =  new PriorityQueue<>(Comparator.comparingLong(a -> a.v));
            boolean [] visited= new boolean[V+1];
            for(long[] e : edges[1]){
                pq.add(new Edge(1, (int) e[0], e[1]));
            }
            visited[1] =true;
            while(!pq.isEmpty()){
                Edge polled = pq.poll();
                if(!visited[polled.b]){
                    visited[polled.b] = true;
                    ans+=polled.v;
                    for(long[] e: edges[polled.b]){
                        pq.add(new Edge(polled.b, (int) e[0], e[1]));
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
    }

}