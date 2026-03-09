import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution1767 {
    static int ans, max, cell_cnt, N;
    static int [] dx = {-1, 0, 1, 0}; // 상, 우, 하, 좌 (시계방향 탐색)
    static int [] dy = {0, 1, 0, -1};
    static int [] cell_x, cell_y; // 가장자리가 아닌 코어들의 좌표 저장

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());

        for(int t=1; t<=T; t++){
            ans = Integer.MAX_VALUE; // 최소 전선 길이 (Integer.MAX_VALUE)
            max = 0;          // 최대 연결 코어 수
            sb.append("#").append(t).append(" ");
            N = Integer.parseInt(bf.readLine());

            boolean [][] powered = new boolean[N+2][N+2];
            cell_x = new int [12]; // 최대 코어 개수 12개
            cell_y = new int [12];
            cell_cnt = 0;

            for(int i=1; i<=N; i++){
                ss = new StringTokenizer(bf.readLine());
                for(int ii=1; ii<=N; ii++){
                    if(ss.nextToken().equals("1")){
                        powered[i][ii] = true;
                        // 가장자리에 붙어있는 코어는 이미 연결된 것이므로 제외하고 저장
                        if((i>1 && i<N) && (ii<N && ii>1)){
                            cell_x[cell_cnt] = i;
                            cell_y[cell_cnt++] = ii;
                        }
                    }
                }
            }
            solve(powered, 0, 0, 0); // 백트래킹 시작
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
        bf.close();
    }

    /**
     * @param cnt      현재 고려 중인 코어의 인덱스
     * @param length   현재까지 설치된 총 전선의 길이
     * @param cell_on  현재까지 연결에 성공한 코어의 개수
     */
    public static void solve(boolean [][] powered, int cnt, int length, int cell_on){
        // [가지치기] 남은 코어를 다 연결해도 현재 찾은 최대 코어 수(max)보다 작으면 탐색 중단
        if(cell_on + cell_cnt - cnt < max) return;

        // 모든 코어를 다 확인했을 때 (기저 조건)
        if(cnt == cell_cnt){
            if(cell_on > max){          // 1. 더 많은 코어를 연결한 경우 업데이트
                max = cell_on;
                ans = length;
            } else if(cell_on == max){  // 2. 연결 코어 수가 같다면 전선 길이 최소값 선택
                ans = Math.min(ans, length);
            }
            return;
        }

        int x = cell_x[cnt];
        int y = cell_y[cnt];

        // 현재 코어에 대해 4방향 시도
        for(int i=0; i<4; i++){
            int tx = x, ty = y;
            boolean flag = false; // 전선 경로에 장애물(다른 전선/코어)이 있는지 확인

            // 1. 특정 방향으로 전선을 끝까지 깔아보기
            while(true){
                tx += dx[i];
                ty += dy[i];

                // 경계를 벗어남 (성공적으로 벽에 도달)
                if(tx > N || tx == 0 || ty > N || ty == 0) break;

                // 가는 길에 다른 코어나 전선이 있는 경우 (실패)
                if(powered[tx][ty]){
                    flag = true;
                    break;
                }
                // 일단 전선을 깔며 전진
                powered[tx][ty] = true;
            }

            // 2. 장애물 없이 벽까지 도달했다면 다음 코어로 이동
            if(!flag) {
                // Math.max를 이용해 0 또는 N+1 위치와 원래 코어 위치 사이의 길이를 계산
                solve(powered, cnt + 1, length + Math.max(Math.abs(tx - x) - 1, Math.abs(ty - y) - 1), cell_on + 1);
            }

            // 3. [복구/백트래킹] 깔았던 전선을 다시 회수 (true -> false)
            // tx, ty가 현재 위치한 곳(벽 밖 혹은 장애물 위치)에서 코어 방향으로 되돌아옴
            tx -= dx[i];
            ty -= dy[i];
            while(tx != x || ty != y){
                // 장애물 때문에 중단된 지점 이전까지만 false로 만들어야 함
                // (만약 장애물 위치까지 닿았다면 flag 처리에 의해 건드리지 않게 됨)
                if(powered[tx][ty]) powered[tx][ty] = false;
                tx -= dx[i];
                ty -= dy[i];
            }

            // 4. 현재 방향으로 연결하지 않는 경우도 고려하여 다음 코어로 진행
            solve(powered, cnt + 1, length, cell_on);
        }
    }
}