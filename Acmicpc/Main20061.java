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
        int [][] blue = new int [4][7]; // 파랑색 구역에 채워진
        int [][] green = new int [7][4]; // 초록색 구역에 채워진
        int blue_cnt=0, green_cnt=0; // 각 공간에 블럭 개수를 세는 변수
        int score=0;
        for(int n=0; n<N; n++){
            ss = new StringTokenizer(bf.readLine());
            int t = Integer.parseInt(ss.nextToken());
            int x = Integer.parseInt(ss.nextToken());
            int y = Integer.parseInt(ss.nextToken());
            if(t==1){

                blue_cnt++;
                green_cnt++;
            }else if(t==2){ // 2개면 max해서 더해주면 됨


                blue_cnt+=2;
                green_cnt+=2; // 밑에서 처리할 거니까 일단 카운트해줌
            }else{


                blue_cnt+=2;
                green_cnt+=2; // 밑에서 처리할 거니까 일단 카운트해줌
            }
            // 만약 칸 위나 교차 한 줄이 가득 찼으면 처리해주는 로직

            // 한 줄이 가득 찼을 때 먼저 처리
            for(int i=1; i<=4; i++){

            }

            // 5번 인덱스에 블럭이 있는지 2번 확인해야 함

            for(int i=0; i<2; i++){

            }
        }
        System.out.println(score);
        System.out.println(blue_cnt+green_cnt);
    }
}
