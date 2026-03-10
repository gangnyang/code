import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Solution2382 {

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static class Element{
        int x;
        int y;
        int number;
        int dir;
        boolean isAble;
        int temp_number;
        Element(int x, int y, int number, int dir, boolean isAble){
            this.x = x;
            this.y = y;
            this.number = number;
            this.dir = dir;
            this.isAble = isAble;
            this.temp_number = 0;
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
            List<Element> list = new ArrayList<>();
            for(int i=0; i<K; i++){
                ss = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(ss.nextToken());
                int b = Integer.parseInt(ss.nextToken());
                int c = Integer.parseInt(ss.nextToken());
                int d = Integer.parseInt(ss.nextToken());
                ans+=c;
                list.add(new Element(a, b, c, d-1, true));
            }
            for(int i=0; i<M; i++){
                int [][] visited = new int[N][N];
                for(int j=0; j<N; j++){
                    Arrays.fill(visited[j], -1);
                }
                for(Element e:list){
                    e.temp_number=e.number;
                }
                for(int ii=0; ii<K; ii++){
                    Element temp = list.get(ii);
                    if(temp.isAble){ // isAble이 true일 때만
                        int tx = temp.x + dx[temp.dir];
                        int ty = temp.y + dy[temp.dir];
                        if(tx==0||tx==N-1||ty==0||ty==N-1){
                            temp.dir = (temp.dir % 2 == 0) ? temp.dir + 1 : temp.dir - 1;
                            int k = temp.number/2;
                            ans-=(temp.number-k);
                            temp.number = k;
                            temp.temp_number = temp.number;
                            if(temp.number == 0) temp.isAble = false;
                        }
                        if(!temp.isAble) continue;
                        if(visited[tx][ty]!=-1){ // 겹쳤을 때
                            Element temp2 = list.get(visited[tx][ty]);
                            if(temp.temp_number>temp2.temp_number){
                                temp2.isAble=false;
                                temp.number += temp2.number;
                                visited[tx][ty] = ii;
                            }else{
                                temp.isAble = false;
                                temp2.number +=temp.number;
                            }
                        }else{
                            visited[tx][ty] = ii;
                        }
                        // 아무 조건도 안걸렸으면 이동
                        temp.x = tx;
                        temp.y = ty;
                    }
                }
            }
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }
}
