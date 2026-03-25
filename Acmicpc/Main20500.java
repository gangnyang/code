import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main20500 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        long [] ans = new long [1516];
        ans[1]=0;
        ans[2]=1;
        for(int i=3; i<=N; i++){
            ans[i] = (ans[i-1]+2*ans[i-2])%1000000007;
        }
        System.out.println(ans[N]);
    }
}
