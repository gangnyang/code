// Main 클래스로 시작하는 코드를 작성해주세요.
import java.io.*;
import java.util.*;

public class Solution1868 {
    static BufferedReader bf;
    static BufferedWriter bw;
    static StringTokenizer st;
    static StringBuilder sb;
    static int [] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int [] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws Exception {
        bf = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();

        // TODO: solve
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            sb.append("#").append(t).append(" ");
            int N = Integer.parseInt(bf.readLine());
            int [][] cnt = new int [N][N];
            boolean [][] visited = new boolean[N][N];
            for(int i=0; i<N; i++){
                String s = bf.readLine();
                for(int ii=0; ii<N; ii++){
                    if(s.charAt(ii)=='*'){
                        visited[i][ii] = true;
                        cnt[i][ii] = -1*Integer.MAX_VALUE;
                        for(int dir=0; dir<8; dir++){
                            int tx = i+dx[dir];
                            int ty = ii+dy[dir];
                            if(tx<0||tx>N-1||ty<0||ty>N-1) continue;
                            cnt[tx][ty]++;
                        }
                    }
                }
            }
            int ans=0;
            for(int i=0; i<N; i++){
                for(int ii=0; ii<N; ii++){
                    if(!visited[i][ii]&&cnt[i][ii]==0){
                        ans++;
                        Queue<int []> queue = new ArrayDeque<>();
                        queue.add(new int [] {i, ii});
                        visited[i][ii] = true;
                        while(!queue.isEmpty()){
                            int [] polled = queue.poll();
                            for(int dir=0; dir<8; dir++){
                                int tx = polled[0]+dx[dir];
                                int ty = polled[1]+dy[dir];
                                if(tx<0||tx>N-1||ty<0||ty>N-1) continue;
                                if(!visited[tx][ty]&&cnt[tx][ty]>=0){
                                    visited[tx][ty] = true;
                                    if(cnt[tx][ty]==0){
                                        queue.add(new int [] {tx, ty});
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(int i=0; i<N; i++){
                for(int ii=0; ii<N; ii++){
                    if(!visited[i][ii]) ans++;
                }
            }
            sb.append(ans).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        bf.close();
    }
}