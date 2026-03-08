import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main16236 {
    static class Fish{ // 물고기(빈칸) 정보를 담을 클래스
        int x;
        int y;
        int dist;

        public Fish(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    static int [][] board;
    static int N;
    static int shark_x=0, shark_y=0;
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        N = Integer.parseInt(bf.readLine());
        board = new int [N][N];
        int grow_cnt = 2; // 처음에 2마리의 물고기를 먹어야 함
        int eat_cnt = 0; // 현재 먹은 물고기의 수
        int ans=0; // 상어가 요청 안하고 물고기 잡아 먹은 시간
        for(int i=0; i<N; i++){ // 입력받고
            ss = new StringTokenizer(bf.readLine());
            for(int ii=0;ii<N; ii++){
                board[i][ii] = Integer.parseInt(ss.nextToken());
                if(board[i][ii]==9){
                    shark_x = i;
                    shark_y = ii;
                    board[i][ii] = 0; // 시작점 0으로 바꿔줘야 함(이거때매 틀림)
                }
            }
        }
                // 시작점에서 BFS(최단 거리일 때는 BFS를 돌려야 함)
        while(true){
            int find_result = find_fish(shark_x, shark_y, grow_cnt);
            if(find_result==-1){ // -1 이면 물고기를 찾지 못한 경우임
                break;
            }else{
                ans+=find_result; // -1 이 아닐 경우 가장 가깝고 조건에 맞는 물고기와의 거리를 ans에 추가
            }
            eat_cnt++;
            if(eat_cnt == grow_cnt){ // 자기보다 작은 물고기를 먹은 횟수가 성장 카운터와 같아지면 성장
                grow_cnt++;
                eat_cnt=0;
            }
        }
        System.out.println(ans);
    }

    public static int find_fish(int x, int y, int shark_size){ // priority queue를 활용하여 가장 가까운 물고기를 찾고 shark_x와 shark_y 변경
        PriorityQueue<Fish> pq = new PriorityQueue<>((a, b) -> { // 거리가 가까운게 최우선, 행이 작은 것(위에 있는 것) 우선, 열이 작은 것 우선
            if(a.dist != b.dist){
                return a.dist-b.dist;
            }
            if(a.x != b.x){
                return a.x - b.x;
            }
            return a.y - b.y;
        });
        int min_dist=Integer.MAX_VALUE; // 만약 가장 가까운 물고기가 나오면 진행을 멈추기 위함
        Queue<Fish> queue = new ArrayDeque<>();
        boolean [][] visited = new boolean[N][N];
        visited[x][y] = true;
        queue.add(new Fish(x, y, 0));
        while(!queue.isEmpty()){
            Fish pollFish = queue.poll();
            if(board[pollFish.x][pollFish.y]!=0&&board[pollFish.x][pollFish.y]<shark_size){
                pq.add(pollFish);
                min_dist = pollFish.dist; // 굳이 최소로 계산안해도 무조건 같은 dist만 나오기 때문에 괜찮음
            }
            if(pollFish.dist==min_dist){
                continue; // 이미 최소 dist와 같아졌으면 생략하고 넘어감
            }
            for(int i=0; i<4; i++){
                int tx = pollFish.x + dx[i];
                int ty = pollFish.y + dy[i];
                if(tx>=0&&tx<N&&ty>=0&&ty<N&&!visited[tx][ty]&&board[tx][ty]<=shark_size){ // tx, ty 범위 검사, 이미 방문했는지(visited), shark_size 이하여야 진행 가능
                    visited[tx][ty] = true;
                    queue.add(new Fish(tx, ty, pollFish.dist+1));
                }
            }
        }
        if(pq.isEmpty()){ // 먹을 수 있는 물고기가 없는 상태임
            return -1;
        }else{
            Fish pollFish = pq.poll();
            shark_x = pollFish.x;
            shark_y = pollFish.y;
            board[shark_x][shark_y] = 0; // 물고기가 없는 것으로 설정
            return pollFish.dist;
        }
    }
}
