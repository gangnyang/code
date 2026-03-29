import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main2011 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        int size = s.length();
        char [] cipher = s.toCharArray();
        int [] dp = new int[size+1];
        dp[0] = 1; // 아무것도 안 골랐을 때
        dp[1] = 1;
        for(int i=1; i<size; i++){
            if(cipher[i]=='0'){
                // 현재 자리가 0이면 앞은 무조건 1이나 2여야 함
                if(cipher[i-1]=='1'||cipher[i-1]=='2'){
                    dp[i+1] = dp[i-1];
                }else{
                    dp[size]=0;
                    break;
                }
            }else{
                if(cipher[i-1]=='1'){
                    // 앞자리가 1이면 두 가지 중에 선택 가능
                    dp[i+1] = (dp[i] + dp[i-1])%1000000;
                }else if(cipher[i-1]=='2'){
                    if(cipher[i]>='7'){
                        // 27부터는 없는 코드이므로 이어감
                        dp[i+1] = dp[i];
                    }else{
                        // 26 아래로는 두 가지로 선택 가능
                        dp[i+1] = (dp[i] + dp[i-1])%1000000;
                    }
                }else{
                    // 만약 앞자리가 그 외 숫자라면 그냥 이어가야 함
                    dp[i+1] = dp[i];
                }
            }
        }
        if(cipher[0]=='0'){
            dp[size]=0;
        }
        System.out.println(dp[size]%1000000);
    }
}
