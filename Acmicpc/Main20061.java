import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
음 블럭을 놓을 때 구역에 쌓인 개수 증가시키고 구역에 블럭 개수 증가시키고 교차로 채워진 개수 증가시키면 될듯
 */

public class Main20061 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        int [] blue_stack = new int [4]; // 파랑색 구역에 쌓인 개수
        int [] blue_width = new int [7]; // 파랑색 구역에 세로로 채워진 개수
        int [] green_stack = new int [4]; // 초록색 구역에 쌓인 개수
        int [] green_width = new int [7]; // 초록색 구역에 가로로 채워진 개수
        int blue_cnt=0, green_cnt=0; // 각 공간에 블럭 개수를 세는 변수
        int score=0;
        for(int n=0; n<N; n++){
            ss = new StringTokenizer(bf.readLine());
            int t = Integer.parseInt(ss.nextToken());
            int x = Integer.parseInt(ss.nextToken());
            int y = Integer.parseInt(ss.nextToken());
            if(t==1){
                blue_stack[x]++;
                blue_width[blue_stack[x]]++;
                green_stack[y]++;
                green_width[green_stack[y]]++;
                blue_cnt++;
                green_cnt++;
            }else if(t==2){ // 2개면 max해서 더해주면 됨
                int y2 = y+1;
                blue_stack[x] += 2;
                blue_width[blue_stack[x]]++;
                blue_width[blue_stack[x]-1]++;

                int stack_max = Math.max(green_stack[y], green_stack[y2])+1;
                green_stack[y] = stack_max;
                green_stack[y2] = stack_max;
                green_width[stack_max] +=2; // 초록색 구역 기준 가로로 두 칸 채워지므로

                blue_cnt+=2;
                green_cnt+=2; // 밑에서 처리할 거니까 일단 카운트해줌
            }else{
                int x2 = x+1;
                int stack_max = Math.max(blue_stack[x], blue_stack[x2])+1;
                blue_stack[x] = stack_max;
                blue_stack[x2] = stack_max;
                blue_width[stack_max] += 2;

                green_stack[y] +=2;
                green_width[green_stack[y]]++;
                green_width[green_stack[y]-1]++;

                blue_cnt+=2;
                green_cnt+=2; // 밑에서 처리할 거니까 일단 카운트해줌
            }
            // 만약 칸 위나 교차 한 줄이 가득 찼으면 처리해주는 로직

            // 한 줄이 가득 찼을 때 먼저 처리
            for(int i=1; i<=4; i++){
                if(blue_width[i]==4){ // 4칸이 찼으면 다 채워진거임
                    for(int ii=i; ii<=5; ii++){
                        blue_width[ii] = blue_width[ii+1]; // 덮어씌우기
                    }
                    blue_width[6] = 0;
                    score++; // 점수를 증가시키고 구역의 블럭 개수를 감소시킴
                    blue_cnt-=4;
                    for(int ii=0; ii<=3; ii++){
                        blue_stack[ii]--;
                    }
                }
                if(green_width[i]==4){
                    for(int ii=i; ii<=5; ii++){
                        green_width[ii] = green_width[ii+1]; // 덮어씌우기
                    }
                    green_width[6] = 0;
                    score++;
                    green_cnt-=4;
                    for(int ii=0; ii<=3; ii++){
                        green_stack[ii]--;
                    }
                }
            }

            // 5번 인덱스에 블럭이 있는지 2번 확인해야 함

            for(int i=0; i<2; i++){
                if(blue_width[5]!=0){
                    blue_cnt-=blue_width[1];
                    for(int ii=1; ii<=5; ii++){
                        blue_width[ii] = blue_width[ii+1];
                    }
                    for(int ii=0; ii<=3; ii++){
                        blue_stack[ii]-=1;
                    }
                }
                if(green_width[5]!=0){
                    green_cnt-=green_width[1];
                    for(int ii=1; ii<=5; ii++){
                        green_width[ii] = green_width[ii+1];
                    }
                    for(int ii=0; ii<=3; ii++){
                        green_stack[ii]--;
                    }
                }
            }
        }
        System.out.println(score);
        System.out.println(blue_cnt+green_cnt);
    }
}
