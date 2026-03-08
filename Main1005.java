import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main1005 {
    static int [] dp = new int[1001]; // 같은 노드에 여러번 도착할 수 있기 때문에 메모이제이션이 필요함
    static int [] D = new int[1001];
    static List<Integer>[] edges = new ArrayList[1001];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        for(int i=0; i<=1000; i++){ // 프로그램 시작 시 edges 초기화
            edges[i] = new ArrayList<>();
        }
        for(int t=1; t<=T; t++){
            ss = new StringTokenizer(bf.readLine());
            int N = Integer.parseInt(ss.nextToken());
            int K = Integer.parseInt(ss.nextToken());

            Arrays.fill(dp, 1, N+1, -1); // dp 배열을 사용할 만큼 초기화

            ss = new StringTokenizer(bf.readLine());
            for(int i=1; i<=N; i++){
                edges[i].clear(); // edges 재초기화
                D[i] = Integer.parseInt(ss.nextToken()); // 건설시간을 넣고
            }
            for(int i=0; i<K; i++){
                ss = new StringTokenizer(bf.readLine()); // 간선을 삽입함 역방향으로 넣을거임
                int X = Integer.parseInt(ss.nextToken());
                int Y = Integer.parseInt(ss.nextToken());
                edges[Y].add(X);
            }
            int ans = dfs(Integer.parseInt(bf.readLine())); // 역방향으로 dfs
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }

    static int dfs(int number){
        if(dp[number]!=-1){ // 이미 방문한 적 있으면 바로 반환
            return dp[number];
        }
        int max=0;
        for(int node : edges[number]){
            max = Math.max(max, dfs(node)); // 연결된 노드 별로 최댓값 계산
        }
        dp[number] = max+D[number]; // 현재 노드의 건설 시간 더해서 반환
        return dp[number];
    }
}
