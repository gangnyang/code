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
        boolean [][] blue = new boolean [7][4]; // 파랑색 구역에 채워진
        boolean [][] green = new boolean [7][4]; // 초록색 구역에 채워진
        int score=0;
        for(int n=0; n<N; n++){
            ss = new StringTokenizer(bf.readLine());
            int t = Integer.parseInt(ss.nextToken());
            int x = Integer.parseInt(ss.nextToken());
            int y = Integer.parseInt(ss.nextToken());
            int target;
            if(t==1){
                target = 6;
                for(int i=1; i<=6; i++){
                    if(blue[i][x]){
                        target = i-1;
                        break;
                    }
                }
                blue[target][x] = true;
                target =6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]){
                        target = i-1;
                        break;
                    }
                }
                green[target][y] = true;
            }else if(t==2){ // 2개면 max해서 더해주면 됨
                int y2 = y+1;
                target = 6;
                for(int i=1; i<=6; i++){
                    if(blue[i][x]){
                        target = i-1;
                        break;
                    }
                }
                blue[target][x] = true;
                blue[target-1][x] = true;
                target = 6;
                for(int i=1; i<=6; i++){
                    if(green[i][y]||green[i][y2]){
                        target  = i-1;
                        break;
                    }
                }
                green[target][y] = true;
                green[target][y2] = true;
            }else{
                target = 6;
                int x2 = x+1;
                for(int i=1; i<=6; i++){
                    if(blue[i][x]||blue[i][x2]){
                        target=i-1;
                        break;
                    }
                }
                blue[target][x] = true;
                blue[target][x2] = true;
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
            // 만약 칸 위나 교차 한 줄이 가득 찼으면 처리해주는 로직

            // 한 줄이 가득 찼을 때 먼저 처리
            for(int i=6; i>=3; i--){
                if(blue[i][0]&&blue[i][1]&&blue[i][2]&&blue[i][3]){
                    boolean [] clear = blue[i];
                    for(int ii=i; ii>=1; ii--){
                        blue[ii] = blue[ii-1];
                    }
                    blue[0] = clear;
                    Arrays.fill(blue[0], false);
                    i++;
                    score++;
                }
            }
            for(int i=6; i>=3; i--){
                if(green[i][0]&&green[i][1]&&green[i][2]&&green[i][3]){
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

            // 5번 인덱스에 블럭이 있는지 2번 확인해야 함

            for(int i=0; i<2; i++){
                if(blue[2][0]||blue[2][1]||blue[2][2]||blue[2][3]){
                    boolean [] clear = blue[6];
                    for(int ii=6; ii>=1; ii--){
                        blue[ii] = blue[ii-1];
                    }
                    blue[0] = clear;
                    Arrays.fill(blue[0], false);
                }
                if(green[2][0]||green[2][1]||green[2][2]||green[2][3]){
                    boolean [] clear = green[6];
                    for(int ii=6; ii>=1; ii--){
                        green[ii] = green[ii-1];
                    }
                    green[0] = clear;
                    Arrays.fill(green[0], false);
                }
            }
        }
        System.out.println(score);
        int blue_cnt=0, green_cnt=0;
        for(int i=1; i<=6; i++){
            for(int ii=0 ;ii<4; ii++){
                if(blue[i][ii]) blue_cnt++;
                if(green[i][ii]) green_cnt++;
            }
        }
        System.out.println(blue_cnt+green_cnt);
    }
}
