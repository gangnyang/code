import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Solution2382 {

    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, 1, 0, -1};

    static class Element{
        int x;
        int y;
        int number;
        int dir;
        boolean isAble;
        Element(int x, int y, int number, int dir, boolean isAble){
            this.x = x;
            this.y = y;
            this.number = number;
            this.dir = dir;
            this.isAble = isAble;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            sb.append("#").append(t).append(" ");
            int ans=0;
            ss = new StringTokenizer(bf.readLine(), " ");
            int N = Integer.parseInt(ss.nextToken());
            int M = Integer.parseInt(ss.nextToken());
            int K = Integer.parseInt(ss.nextToken());
            int [][] visited = new int[N][N];
            for(int i=0; i<N; i++){
                Arrays.fill(visited[i], -1);
            }
            List<Element> list = new ArrayList<>();
            for(int i=0; i<K; i++){
                ss = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(ss.nextToken());
                int b = Integer.parseInt(ss.nextToken());
                visited[a][b] = i; // index를 저장할 거임
                int c = Integer.parseInt(ss.nextToken());
                int d = Integer.parseInt(ss.nextToken());
                ans+=c;
                list.add(new Element(a, b, c, d-1, true));
            }
            for(int i=0; i<M; i++){
                for(int ii=0; ii<K; ii++){
                    Element temp = list.get(ii);
                    if(temp.isAble){ // isAble이 true일 때만
                        int tx = temp.x + dx[temp.dir];
                        int ty = temp.y + dy[temp.dir];
                        if(tx==0||tx==N-1||ty==0||ty==N-1){
                            temp.dir = (temp.dir+2)%4;
                            int k = temp.number;
                            if(k%2==0){
                                k = k/2;
                            }else{
                                k = k/2+1;
                            }
                            temp.number -=k;
                            ans-=k;
                        }
                        if(visited[tx][ty]!=-1){ // 겹쳤을 때
                            Element temp2 = list.get(visited[tx][ty]);
                            if(temp.number>temp2.number){
                                temp2.isAble=false;
                                temp.number += temp2.number;
                                visited[tx][ty] = ii;
                            }else{
                                temp.isAble = false;
                                temp2.number +=temp.number;
                            }
                        }
                        // 아무 조건도 안걸렸으면 이동
                        visited[temp.x][temp.y] = -1;
                        temp.x = tx;
                        temp.y = ty;
                        visited[tx][ty] = ii;
                    }
                }
            }
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }
}
