import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main19235 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());

        // 보드 배열 (파란색 보드는 다루기 쉽도록 90도 회전시켜 초록색처럼 아래로 떨어지게 구현됨)
        int [][] blue = new int [7][4];
        int [][] green = new int [7][4];
        int score = 0;

        for(int n=1; n<=N; n++){
            ss = new StringTokenizer(bf.readLine());
            int t = Integer.parseInt(ss.nextToken());
            int x = Integer.parseInt(ss.nextToken());
            int y = Integer.parseInt(ss.nextToken());
            int target;

            // 1. 블록 떨어뜨리기
            if(t==1){ // 1x1 블록
                // 파란색 보드 배치 (x좌표 기준)
                target = 6;
                for(int i=1; i<=6; i++){
                    if(blue[i][x]!=0){
                        target = i-1;
                        break;
                    }
                }
                blue[target][x] = n;

                // 초록색 보드 배치 (y좌표 기준)
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]!=0){
                        target = i-1;
                        break;
                    }
                }
                green[target][y] = n;

            } else if(t==2){ // 1x2 블록 (가로)
                int y2 = y+1;

                // 파란색 보드에서는 세로(1x2) 모양으로 떨어짐
                target = 6;
                for(int i=1; i<=6; i++){
                    if(blue[i][x]!=0){
                        target = i-1;
                        break;
                    }
                }
                blue[target][x] = n;
                blue[target-1][x] = n;

                // 초록색 보드에서는 가로(1x2) 모양으로 떨어짐 (두 칸 중 더 높은 곳에서 멈춤)
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]!=0 || green[i][y2]!=0){
                        target  = i-1;
                        break;
                    }
                }
                green[target][y] = n;
                green[target][y2] = n;

            } else { // 2x1 블록 (세로)
                target = 6;
                int x2 = x+1;

                // 파란색 보드에서는 가로(2x1) 모양으로 떨어짐
                for(int i=1; i<=6; i++){
                    if(blue[i][x]!=0 || blue[i][x2]!=0){
                        target=i-1;
                        break;
                    }
                }
                blue[target][x] = n;
                blue[target][x2] = n;

                // 초록색 보드에서는 세로(2x1) 모양으로 떨어짐
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]!=0){
                        target = i-1;
                        break;
                    }
                }
                green[target][y] = n;
                green[target-1][y] = n;
            }

            // 2. 한 줄이 가득 찼을 때 처리 및 점수 획득
            // 파란색 보드 줄 삭제 로직
            for(int i=6; i>=3; i--){
                if(blue[i][0]!=0 && blue[i][1]!=0 && blue[i][2]!=0 && blue[i][3]!=0){
                    blue[i][0] = 0; blue[i][1] = 0; blue[i][2] = 0; blue[i][3] = 0;
                    gravity(blue);
                    i=7; // 삭제 후 처음부터 다시 검사하기 위해 인덱스 복구
                    score++;
                }
            }

            // 초록색 보드 줄 삭제 로직
            for(int i=6; i>=3; i--){
                if(green[i][0]!=0 && green[i][1]!=0 && green[i][2]!=0 && green[i][3]!=0){
                    green[i][0] = 0; green[i][1] = 0; green[i][2] = 0; green[i][3] = 0;
                    gravity(green);
                    i=7;
                    score++;
                }
            }

            // 3. 연한 색 구역(인덱스 2)에 블록이 있는지 확인 후 밀어내기
            // 블록이 최대 2줄 겹칠 수 있으므로 2번 반복 확인
            for(int i=0; i<2; i++){
                // 파란색 연한 구역 처리
                if(blue[2][0]!=0 || blue[2][1]!=0 || blue[2][2]!=0 || blue[2][3]!=0){
                    int [] clear = blue[6];
                    for(int ii=6; ii>=1; ii--){
                        blue[ii] = blue[ii-1];
                    }
                    blue[0] = clear;
                    Arrays.fill(blue[0], 0);
                }
                // 초록색 연한 구역 처리
                if(green[2][0]!=0 || green[2][1]!=0 || green[2][2]!=0 || green[2][3]!=0){
                    int [] clear = green[6];
                    for(int ii=6; ii>=1; ii--){
                        green[ii] = green[ii-1];
                    }
                    green[0] = clear;
                    Arrays.fill(green[0], 0);
                }
            }
        }

        // 4. 결과 출력
        System.out.println(score); // 얻은 점수

        // 보드에 남아있는 블록 칸 수 계산
        int blue_cnt = 0, green_cnt = 0;
        for(int i=1; i<=6; i++){
            for(int ii=0; ii<4; ii++){
                if(blue[i][ii]!=0) blue_cnt++;
                if(green[i][ii]!=0) green_cnt++;
            }
        }
        System.out.println(blue_cnt + green_cnt);
    }

    static void gravity(int [][] board){
        boolean moved = true;
        while(moved){
            moved = false;
            for(int i=5; i>=1; i--){
                for(int ii=0; ii<4; ii++){
                    if(board[i][ii]==0) continue;

                    int id = board[i][ii];

                    if(ii<3&&board[i][ii+1]==id){
                        if(board[i+1][ii]==0&&board[i+1][ii+1]==0){
                            board[i+1][ii] = id;
                            board[i+1][ii+1] = id;
                            board[i][ii] = 0;
                            board[i][ii+1] = 0;
                            moved = true;
                        }
                        ii++;
                    }else{
                        if(board[i+1][ii]==0){
                            board[i+1][ii] = id;
                            board[i][ii] = 0;
                            moved = true;
                        }
                    }
                }
            }
        }
    }
}
