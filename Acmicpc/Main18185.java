import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main18185 {
    static int [] A = new int[10002];
    static int N, ans;
    public static void main(String[] args) throws IOException {
        BufferedReader bf =  new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(bf.readLine());
        StringTokenizer ss = new StringTokenizer(bf.readLine(), " ");
        for(int i=0; i<N; i++){
            A[i] = Integer.parseInt(ss.nextToken());
        }

        solve();
        System.out.println(ans);
    }
    static void solve(){
        int i = 0;
        while(i<N){
            if(A[i]==0){
                i++;
                continue;
            }
            int i1 = A[i];
            int i2 = (i+1<N)?A[i+1]:0;
            int i3 = (i+2<N)?A[i+2]:0;
            int min2 = Math.min(A[i], i2);
            if(i2==0){
                ans+=3*A[i];
                A[i]=0;
                i++;
            }
            else if(i3==0||i2 >= i1 + i3){
                ans+=min2*5;
                A[i]-=min2;
                A[i+1]-=min2;
                ans+=A[i]*3;
                i++;
            }else {
                ans+=7;
                A[i]--;
                A[i+1]--;
                A[i+2]--;
            }
        }
    }
}
