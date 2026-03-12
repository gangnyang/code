import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main12738 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        ss = new StringTokenizer(bf.readLine());
        List<Long> lis = new ArrayList<>();
        long top = -1_000_000_001;
        for(int i=0; i<N; i++){
            long temp = Long.parseLong(ss.nextToken());
            if(temp>top){
                lis.add(temp);
                top = temp;
            }else{
                // lower bound 찾아야 함
                int start = 0;
                int end = lis.size()-1;
                while(true){
                    int mid = (start+end)/2;
                    if(start==end){
                        lis.set(mid, temp);
                        if(mid==lis.size()-1){
                            top = temp;
                        }
                        break;
                    }
                    if(lis.get(mid)<temp){
                        start = mid+1;
                    }else{
                        end = mid;
                    }
                }
            }
        }
        System.out.println(lis.size());
    }
}