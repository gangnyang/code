import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution6782 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            long N = Long.parseLong(bf.readLine());
            sb.append("#").append(t).append(" ");
            long ans=0;
            double temp;
            long temp2;
            while(true){
                if(N==2){
                    break;
                }
                temp = Math.sqrt(N);
                if(Math.floor(temp)==Math.ceil(temp)){
                    N = (long)temp;
                    ans++;
                }
                else{
                    temp2 = (long)Math.pow(Math.floor(temp)+1, 2);
                    ans+=temp2-N;
                    N = temp2;
                }
            }
            sb.append(ans).append("\n");
        }
        System.out.print(sb.toString());
    }
}
