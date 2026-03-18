import java.io.*;
import java.util.*;

/*
음 블럭을 놓을 때 구역에 쌓인 개수 증가시키고 구역에 블럭 개수 증가시키고 교차로 채워진 개수 증가시키면 될듯
 */

public class Main20061 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        
        // 보드 배열 (파란색 보드는 다루기 쉽도록 90도 회전시켜 초록색처럼 아래로 떨어지게 구현됨)
        boolean [][] blue = new boolean [7][4];
        boolean [][] green = new boolean [7][4]; 
        int score = 0;
        
        for(int n=0; n<N; n++){
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
                    if(blue[i][x]){
                        target = i-1;
                        break;
                    }
                }
                blue[target][x] = true;
                
                // 초록색 보드 배치 (y좌표 기준)
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]){
                        target = i-1;
                        break;
                    }
                }
                green[target][y] = true;
                
            } else if(t==2){ // 1x2 블록 (가로)
                int y2 = y+1;
                
                // 파란색 보드에서는 세로(1x2) 모양으로 떨어짐
                target = 6;
                for(int i=1; i<=6; i++){
                    if(blue[i][x]){
                        target = i-1;
                        break;
                    }
                }
                blue[target][x] = true;
                blue[target-1][x] = true;
                
                // 초록색 보드에서는 가로(1x2) 모양으로 떨어짐 (두 칸 중 더 높은 곳에서 멈춤)
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y] || green[i][y2]){
                        target  = i-1;
                        break;
                    }
                }
                green[target][y] = true;
                green[target][y2] = true;
                
            } else { // 2x1 블록 (세로)
                target = 6;
                int x2 = x+1;
                
                // 파란색 보드에서는 가로(2x1) 모양으로 떨어짐
                for(int i=1; i<=6; i++){
                    if(blue[i][x] || blue[i][x2]){
                        target=i-1;
                        break;
                    }
                }
                blue[target][x] = true;
                blue[target][x2] = true;
                
                // 초록색 보드에서는 세로(2x1) 모양으로 떨어짐
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]){
                        target = i-1;
                        break;
                    }
                }
                green[target][y] = true;
                green[target-1][y] = true;
            }
            
            // 2. 한 줄이 가득 찼을 때 처리 및 점수 획득
            // 파란색 보드 줄 삭제 로직
            for(int i=6; i>=3; i--){
                if(blue[i][0] && blue[i][1] && blue[i][2] && blue[i][3]){
                    boolean [] clear = blue[i];
                    // 윗줄을 아래로 당겨옴
                    for(int ii=i; ii>=1; ii--){
                        blue[ii] = blue[ii-1];
                    }
                    blue[0] = clear;
                    Arrays.fill(blue[0], false); // 맨 윗줄 초기화
                    i++; // 삭제 후 당겨진 줄을 다시 검사하기 위해 인덱스 복구
                    score++;
                }
            }
            
            // 초록색 보드 줄 삭제 로직
            for(int i=6; i>=3; i--){
                if(green[i][0] && green[i][1] && green[i][2] && green[i][3]){
                    boolean [] clear = green[i];
                    for(int ii=i; ii>=1; ii--){
                        green[ii] = green[ii-1];
                    }
                    green[0] = clear;
                    Arrays.fill(green[0], false);
                    i++;
                    score++;
                }
            }

            // 3. 연한 색 구역(인덱스 2)에 블록이 있는지 확인 후 밀어내기
            // 블록이 최대 2줄 겹칠 수 있으므로 2번 반복 확인
            for(int i=0; i<2; i++){
                // 파란색 연한 구역 처리
                if(blue[2][0] || blue[2][1] || blue[2][2] || blue[2][3]){
                    boolean [] clear = blue[6];
                    for(int ii=6; ii>=1; ii--){
                        blue[ii] = blue[ii-1];
                    }
                    blue[0] = clear;
                    Arrays.fill(blue[0], false);
                }
                // 초록색 연한 구역 처리
                if(green[2][0] || green[2][1] || green[2][2] || green[2][3]){
                    boolean [] clear = green[6];
                    for(int ii=6; ii>=1; ii--){
                        green[ii] = green[ii-1];
                    }
                    green[0] = clear;
                    Arrays.fill(green[0], false);
                }
            }
        }
        
        // 4. 결과 출력
        System.out.println(score); // 얻은 점수
        
        // 보드에 남아있는 블록 칸 수 계산
        int blue_cnt = 0, green_cnt = 0;
        for(int i=1; i<=6; i++){
            for(int ii=0; ii<4; ii++){
                if(blue[i][ii]) blue_cnt++;
                if(green[i][ii]) green_cnt++;
            }
        }
        System.out.println(blue_cnt + green_cnt);
    }
}