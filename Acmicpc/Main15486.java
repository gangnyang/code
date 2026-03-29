import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main15486 {
    static int [] dp = new int [1500002];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        dp[0]=0;
        for(int i=1; i<=N; i++){
            ss = new StringTokenizer(bf.readLine());
            int T = Integer.parseInt(ss.nextToken());
            int P = Integer.parseInt(ss.nextToken()); // 입력 받고
            dp[i] = Math.max(dp[i-1], dp[i]); // 일단 이전 요소가 최댓값일 수 있으니까 받아옵니다
            if(T+i<N+2){
                dp[T+i]=Math.max(dp[T+i], dp[i]+P); // 상담 완료한 다음 날짜 수익 갱신
            }
        }
        dp[N+1] = Math.max(dp[N+1], dp[N]); // 마지막에 빌 수도 있어서 한 번더 계산
        System.out.println(dp[N+1]);
    }
}
