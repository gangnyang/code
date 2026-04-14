import java.io.*;
import java.util.*;

public class Main17135 {
    static int N, M, D;
    static boolean [][] board;
    static boolean [] archer;
    static int ans = 1_000_000_007;
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        ss = new StringTokenizer(bf.readLine(), " ");
        N = Integer.parseInt(ss.nextToken());
        M = Integer.parseInt(ss.nextToken());
        D = Integer.parseInt(ss.nextToken());
        board = new boolean[N][N];
        archer = new boolean[N];
        for(int i=0; i<N; i++){
            ss= new StringTokenizer(bf.readLine(), " ");
            for(int j=0; j<M; j++){
                board[i][j] = ss.nextToken().charAt(0)=='1';
            }
        }
        // DFS로 궁수 위치 뽑고 돌려보고 ANS 뽑아서 최솟값으로 갱신
        dfs(0, 0);
        System.out.println(ans);
    }

    static void dfs(int cnt, int start){
        if(cnt==3){
            // 시뮬레이션
            return;
        }else{
            for(i       nt i=start; i<M; i++){
                archer[i] = true;
                dfs(cnt+1, i+1);
                archer[i] = false;
            }
        }
    }

    static void simulation(){
        // 위에서부터 배열 하나씩 내리면서 사거리 내에 있는 지 확인
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(archer[j]){
                    // true일 때 D 범위 안에 pq에 싹 넣고 하나 뽑고 그 위치 제거
                    PriorityQueue<Enemy> pq = new PriorityQueue<>((a, b) -> {
                        if(a.dist==b.dist){
                            return a.y-b.y;
                        }
                        return a.dist-b.dist;
                    });


                }
            }

        }
    }

    static class Enemy{
        int x, y;
        int dist;
        Enemy(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
`      }
    }
}
