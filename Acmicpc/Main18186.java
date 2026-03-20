import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main18186 {
    static long [] A = new long[1000002];
    static long N, B, C, ans, value1, value2, value3;
    static boolean flag2 = false;
    public static void main(String[] args) throws IOException {
        BufferedReader bf =  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine(), " ");
        N = Long.parseLong(ss.nextToken());
        B = Long.parseLong(ss.nextToken());
        C = Long.parseLong(ss.nextToken());
        ss = new StringTokenizer(bf.readLine(), " ");
        for(int i=0; i<N; i++) {
            A[i] = Long.parseLong(ss.nextToken());
        }
        value1 = B;
        value2 = (B+C);
        value3 = (B+2*C);
        if(value2/2>=(value1+value3/3)/2){
            flag2=true;
        }

        solve();
        System.out.println(ans);
    }
    static void solve(){
        int i = 0;
        if(B<C){

        }
        while(i<N){
            if(A[i]==0){
                i++;
                continue;
            }
            long i1 = A[i];
            long i2 = (i+1<N)?A[i+1]:0;
            long i3 = (i+2<N)?A[i+2]:0;
            long min2 = Math.min(A[i], i2);
            long min3 = Math.min(min2, i3);
            if(i2==0){
                ans+=value1*A[i];
                A[i]=0;
                i++;
            }
            else if(flag2&&(i3==0||i2 >= i1 + i3)){
                ans+=min2*value2;
                A[i]-=min2;
                A[i+1]-=min2;
                ans+=A[i]*value1;
                i++;
            }
            /*
            잠깐만, 여기서 1씩 빼니까 시간초과가 발생하는 듯
            i2 - k >= i1+i3 - 2k
            가 유지될만큼의 K를 빼야함
            -k <= i2 - i1 - i3
            k >= i1 + i3 - i2
             */
            else if(flag2){
                long k = i1 + i3 - i2; // k가 아마 0인 경우는 없음
                ans+=value3*k;
                A[i]-=k;
                A[i+1]-=k;
                A[i+2]-=k;
            }else{ // 만약 flag2가 false면 무조건 3개씩 먼저 하는게 이득
                ans+=value3*min3;
                A[i]-=min3;
                A[i+1]-=min3;
                A[i+2]-=min3;
            }
        }
    }
}
