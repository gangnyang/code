import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 2번이 바이러스 후보 -> 2번 나오면 리스트에 저장해놨다가 DFS 돌려서 M 될 때까지 굴리고 M에 도달하면 BFS로 바이러스 침투하면 될듯


public class Main17141 {

    static int N, M, empty_cnt=0, ans=Integer.MAX_VALUE; // empty_cnt: 빈 칸 개수
    static int [][] board;
    static List<int []> viruses; // viruses: 칸이 2번일 경우 넣을 리스트
    static int [] selectedVirusIndex; // dfs에서 바이러스 index를 저장할 예정임
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        ss = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(ss.nextToken());
        M = Integer.parseInt(ss.nextToken());
        viruses = new ArrayList<>();
        board = new int[N][N];
        selectedVirusIndex = new int[M];
        for(int i=0; i<N; i++){
            ss = new StringTokenizer(bf.readLine());
            for(int ii=0; ii<N; ii++){
                board[i][ii] = Integer.parseInt(ss.nextToken());
                if(board[i][ii]==0){
                    empty_cnt++;
                }
                if(board[i][ii] == 2){ // 2번 칸도 다른 바이러스를 선택하면 빈 칸이나 다름없으므로 0으로 바꿔주고 빈칸 개수 늘림
                    viruses.add(new int [] {i, ii});
                    empty_cnt++;
                    board[i][ii]=0;
                }
            }
        }
        combination(0, 0);
        System.out.println(ans== Integer.MAX_VALUE?-1:ans);
    }

    public static void combination(int cnt, int start){ // 바이러스 순서는 상관없으니까 중복조합이다.
        if(cnt==M){
            bfs(); // M개의 바이러스를 뽑고 나면 bfs를 돌린다.
            return;
        }
        for(int i=start; i<viruses.size(); i++){
            selectedVirusIndex[cnt] = i;
            combination(cnt+1, i+1); // 중복조합이니까 start를 i+1로 시작
        }
    }

    static void bfs(){ // 뽑힌 바이러스로 bfs를 한다
        Queue<int []> queue = new ArrayDeque<>();
        int virus_cnt=0; // 바이러스에 몇 칸이나 걸렸는지
        int [][] time = new int [N][N]; // 각 빈칸 별 바이러스에 걸리는 시간
        for(int i=0; i<N; i++){
            Arrays.fill(time[i], -1); // max 연산을 위해 -1로 초기화하고
        }
        for(int i : selectedVirusIndex){ // 큐에 넣고
            int [] virus = viruses.get(i);
            queue.add(virus);
            time[virus[0]][virus[1]] = 0;
            virus_cnt++;
        }
        int max = 0;
        while(!queue.isEmpty()){
            int [] polled = queue.poll();
            max = Math.max(max, time[polled[0]][polled[1]]); 
            for(int i=0; i<4; i++){
                int tx = polled[0]+dx[i];
                int ty = polled[1]+dy[i];
                int tt = time[polled[0]][polled[1]]+1;
                if(tx<0||tx>N-1||ty<0||ty>N-1){ // ArrayOutOfBounds 처리
                    continue;
                }
                if(board[tx][ty]==0&&time[tx][ty]==-1){ // time이 할당되지 않았고 빈 칸일때만 큐에 넣는다
                    time[tx][ty] = tt;
                    virus_cnt++;
                    queue.add(new int[] {tx, ty});
                }
            }
        }
        if(virus_cnt==empty_cnt){ // 빈 칸의 수와 바이러스 수가 같아지면 ans 갱신
            ans = Math.min(ans, max);
        }
    }
}
