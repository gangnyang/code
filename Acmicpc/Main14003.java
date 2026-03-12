import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main14003 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(bf.readLine());
        ss = new StringTokenizer(bf.readLine());
        List<Long> lis = new ArrayList<>();
        int [] pos = new int [N];
        long [] arr = new long [N];
        long top = -1_000_000_001;
        for(int i=0; i<N; i++){
            long temp = Long.parseLong(ss.nextToken());
            arr[i] = temp;
            if(temp>top){
                lis.add(temp);
                top = temp;
                pos[i] = lis.size()-1; // 담기는 index를 담음
            }else{
                // lower bound 찾아야 함
                int start = 0;
                int end = lis.size()-1;
                while(true){
                    int mid = (start+end)/2;
                    if(start==end){
                        lis.set(mid, temp);
                        pos[i] = mid; // 담는 index를 담음
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
        sb.append(lis.size()).append("\n");
        Stack<Long> stack = new Stack<>();
        int cnt=lis.size()-1;
        for(int i=N-1; i>=0; i--){
            if(pos[i]==cnt){
                stack.push(arr[i]);
                cnt--;
                if(cnt==-1) break;
            }
        }
        while(!stack.isEmpty()){
            sb.append(stack.pop()).append(" ");
        }
        sb.append("\n");
        System.out.print(sb);
    }
}
