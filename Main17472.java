import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main17472 {
    public static class Bridge{ // 다리 정보를 담을 클래스
        int from;
        int to;
        int length;
        Bridge(int from, int to, int length){
            this.from = from;
            this.to = to;
            this.length = length;
        }
    }
    static int N, M, island_num;
    static int [][] sea;
    static int [] dx = {-1, 0, 1, 0}; // 상 우 하 좌
    static int [] dy = {0, 1, 0, -1};
    static int [] parent, rank;
    static PriorityQueue<Bridge> pq = new PriorityQueue<>((a, b) -> a.length-b.length); // 다리 길이를 기준으로 짧은 것이 top으로 오는 우선순위 큐
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(ss.nextToken());
        M = Integer.parseInt(ss.nextToken());
        sea = new int[N+2][M+2];
        island_num=2;
        for(int i=1; i<=N; i++){ // 바다 정보를 입력받기
            ss = new StringTokenizer(bf.readLine());
            for(int ii=1; ii<=M; ii++){
                sea[i][ii] = Integer.parseInt(ss.nextToken());
            }
        }
        for(int i=1; i<=N; i++){ // 섬마다 BFS로 섬 번호를 등록함
            for(int ii=1; ii<=M; ii++){
                if(sea[i][ii] == 1){
                    make_island(island_num, i, ii);
                    island_num++;
                }
            }
        }
        parent = new int[island_num];
        rank = new int[island_num];
        for(int i=1; i<=N; i++){ // 섬끼리 잇기
            for(int ii=1; ii<=M; ii++){
                if(sea[i][ii] !=0 ){
                    make_bridge(i, ii);
                }
            }
        }
        for(int i=2; i<island_num; i++){ // kruskal 알고리즘을 위한 parent와 rank 초기화
            parent[i] = i;
            rank[i] = 0;
        }
        int ans = bridgeSolve(); // kruskal 알고리즘 돌리고 결과를 ans에 담음
        System.out.println(ans);
    }

    public static void make_island(int cnt, int x, int y){ // 섬 넘버링
        Queue<int []> queue =  new ArrayDeque<>();
        queue.add(new int[]{x, y});
        sea[x][y] = cnt;
        while(!queue.isEmpty()){ // 일반적인 BFS 풀이로 섬에 번호를 매긴다
            int [] pos = queue.poll();
            for(int i=0; i<4; i++){
                int tx = pos[0]+dx[i];
                int ty = pos[1]+dy[i];
                if(sea[tx][ty]==1){ // 번호를 바꾸고 큐에 넣기 때문에 visited 배열이 필요가 없음
                    sea[tx][ty] = cnt;
                    queue.add(new int[] {tx, ty});
                }
            }
        }
    }

    public static void make_bridge(int x, int y){ // 다리를 만들어주는 함수
        int from = sea[x][y];
        for(int i=0; i<4; i++){ // 4방향으로 끝없이 전진하면서 섬을 만나면 우선순위 큐에 넣어준다.
            int tx = x;
            int ty = y;
            int length =0;
            while(true){
                tx += dx[i];
                ty += dy[i];

                if(tx<1||tx>N||ty<1||ty>M) break; // 바다 바깥을 만나면 끝
                if(sea[tx][ty]==from) break; // 이전 섬과 다르면서

                if(sea[tx][ty]==0){ // 바다라면 다리 길이를 늘리고 큐에 푸쉬
                    length++;
                    continue;
                }
                if(length>=2){ // 다리 길이는 2 이상이어야 함
                    pq.add(new Bridge(from, sea[tx][ty], length)); // 실제로 다리 길이는 두 칸 사이의 길이라서 length가 맞음
                }
                break;
            }
        }
    }

    public static int bridgeSolve() { // kruskal 알고리즘으로 계산, 이미 priority queue에 length 순으로 다리가 정렬이 되어 있음
        int solve_ans=0;
        int bridge_cnt=0; // 다리 연결이 전부 되어야 함(N-1개가 되어야 함)
        while(!pq.isEmpty()){
            Bridge bridge = pq.poll();
            if(find(bridge.from)!=find(bridge.to)){
                solve_ans+=bridge.length;
                union(bridge.from, bridge.to);
                bridge_cnt++;
            }
        }
        if(bridge_cnt==island_num-3){ // N-1개 인데 다리 번호를 편의상 2번부터 시작했기 때문에 island_num-3으로 설정
            return solve_ans;
        }else {
            return -1;
        }
    }

    public static int find(int num){ // union find 알고리즘의 find함수
        if(parent[num]!=num){
            return parent[num] = find(parent[num]);
        }
        return parent[num];
    }

    public static void union(int a, int b){
        int ap = find(a);
        int bp = find(b);
        if(ap!=bp){
            /*
             rank를 두어 트리가 무한정 확장되는 것을 방지한다. parent(a) = b, parent(b) = c, parent(c) = d ... parent(x) = y
             위 예시와 같이 parent가 일자로 길어지면 find 함수를 돌리는 데 시간이 매우 많이 소요됨
             짧은 부분을 큰 부분 parent에 붙인다 => 큰 부분의 루트 노드에 자식 그룹을 추가하는 것과 같음
             예를 들어 2레벨 트리가 있고 10레벨 트리가 있다면
             큰 트리에 작은 트리를 연결하면 큰 트리의 레벨은 10레벨 그대로임
             그러나 작은 트리에 큰 트리를 붙이면 전체 트리 레벨이 11레벨로 증가하게 된다.
             따라서 밑 코드에서 rank[ap]가 rank[bp]보다 작을 때(트리 레벨이 낮음)
             ap의 부모를 bp로 함으로써 작은 트리를 큰 트리에 연결해주는 것이다.
             */
            if(rank[ap]<rank[bp]){
                parent[ap] = bp;
            }else if(rank[ap]>rank[bp]){
                parent[bp] = ap;
            }else{
                parent[bp] = ap;
                rank[ap]++;
            }
        }
    }
}
