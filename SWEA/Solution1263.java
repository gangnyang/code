import java.io.*;
import java.util.*;

public class Solution1263 {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            sb.append("#").append(t).append(" ");
            ss = new StringTokenizer(bf.readLine(), " ");
            int N =  Integer.parseInt(ss.nextToken());
            int [][] graph = new int[N+1][N+1];
            for(int i=1; i<=N; i++){
                Arrays.fill(graph[i], 100_000);
                for(int j=1; j<=N; j++){
                    int tmp = Integer.parseInt(ss.nextToken());
                    graph[i][j] = tmp==0?100_000:tmp;
                    if(i==j) graph[i][j]=0;
                }
            }

            for(int k=1; k<=N; k++){
                for(int i=1; i<=N; i++){
                    for(int j=1; j<=N; j++){
                        if(graph[i][k]<100_000&&graph[k][j]<100_000){
                            graph[i][j] = Math.min(graph[i][j], graph[i][k]+graph[k][j]);
                        }
                    }
                }
            }

            int ans = 100_000;
            for(int i=1; i<=N; i++){
                int sum = 0;
                for(int j=1; j<=N; j++){
                    sum+=graph[i][j];
                }
                ans = Math.min(ans, sum);
            }
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }
}
