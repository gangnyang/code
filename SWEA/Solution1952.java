import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution1952 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            sb.append("#").append(t).append(" ");
            int [] dp = new int [13];
            Arrays.fill(dp, 1_000_000_007);
            dp[0]=0;
            ss= new StringTokenizer(bf.readLine());
            int f1=Integer.parseInt(ss.nextToken());
            int f2=Integer.parseInt(ss.nextToken());
            int f3=Integer.parseInt(ss.nextToken());
            int f4=Integer.parseInt(ss.nextToken());
            ss = new StringTokenizer(bf.readLine());
            int val;
            for(int i=1; i<=12; i++){
                val = Integer.parseInt(ss.nextToken());
                dp[i] = Math.min(dp[i], dp[i-1] + val*f1);
                dp[i] = Math.min(dp[i], dp[i-1] + f2);
                if(i>=3){
                    dp[i] = Math.min(dp[i], dp[i-3] + f3);
                }
                if(i==12){
                    dp[i] = Math.min(dp[i], dp[i-12] + f4);
                }
            }
            sb.append(dp[12]).append("\n");
        }
        System.out.print(sb);
    }
}
