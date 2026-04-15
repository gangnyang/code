import java.io.*;
import java.util.*;

public class Main17135 {
    static int N, M, D;
    static boolean [][] board;
    static boolean [] archer;

    static int ans = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        ss = new StringTokenizer(bf.readLine(), " ");
        N = Integer.parseInt(ss.nextToken());
        M = Integer.parseInt(ss.nextToken());
        D = Integer.parseInt(ss.nextToken());
        board = new boolean[N][M];
        archer = new boolean[M];
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
            boolean [][] localBoard = new boolean[N][M];
            for(int i=0; i<N; i++){
                localBoard[i] = board[i].clone();
            }
            simulation(localBoard);
            return;
        }
        for(int i=start; i<M; i++){
            archer[i] = true;
            dfs(cnt+1, i+1);
            archer[i] = false;
        }
    }

    static void simulation(boolean [][] localBoard){
        // 위에서부터 배열 하나씩 내리면서 사거리 내에 있는 지 확인
        int localAns=0;
        for(int i=N; i>0; i--){
            List<Enemy> tmpList = new ArrayList<>();
            for(int j=0; j<M; j++){
                if(archer[j]){
                    // true일 때 D 범위 안에 pq에 싹 넣고 하나 뽑고 그 위치 제거
                    PriorityQueue<Enemy> pq = new PriorityQueue<>((Enemy a, Enemy b) -> {
                        if(a.dist==b.dist){
                            return a.y-b.y;
                        }
                        return a.dist-b.dist;
                    });
                    int dist;
                    for(int k=i-1; k>=0; k--){
                        for(int l=0; l<M; l++){
                            if(localBoard[k][l]){
                                dist = Math.abs(k-i) + Math.abs(l-j);
                                if(dist<=D){
                                    pq.offer(new Enemy(k, l, dist));
                                }
                            }
                        }

                    }
                    if(!pq.isEmpty()){ // 바로 제거하면 동시에 죽이는 걸 못하니까 리스트에 넣는다.
                        tmpList.add(pq.poll());
                    }
                }
            }
            for(Enemy e:tmpList){
                if(localBoard[e.x][e.y]) localAns++; // 겹치는 거 방지용
                localBoard[e.x][e.y] = false;
            }
            tmpList.clear();
        }
        ans = Math.max(ans, localAns); // 잡을 수 있는 최대값
    }

    static class Enemy{ // 적군을 담을 변수
        public int x, y;
        public int dist;
        public Enemy(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}