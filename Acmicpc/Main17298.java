import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main17298 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        int [] nge = new int[N]; // 결과를 담을 배열
        ss = new StringTokenizer(bf.readLine(), " ");
        Stack<int []> stack = new Stack<>();
        for(int i=0; i<N; i++){
            int k = Integer.parseInt(ss.nextToken());
            if(!stack.isEmpty()){ // 만약 스택이 비어있다면
                while(true){ // 현재 원소가 스택 모든 원소의 오큰수일 수도 있으니까 반복해야 한다.
                    if(stack.isEmpty()) break; // 비면 오류나니까 break
                    int [] peek = stack.peek();
                    if(peek[0]>=k) { // 만약 현재 원소가 작거나 같으면 바로 스택에 넣어야 하므로 break
                        break;
                    }
                    nge[peek[1]]=k; // 오큰수가 맞다면 nge에 넣고 stack은 pop
                    stack.pop();
                }
            }
            stack.push(new int[]{k, i}); // 스택이 비어있든 차있든 결국 현재 요소를 스택에 넣어야 함
        }
        while(!stack.isEmpty()){ // 남은 원소에 대해 -1로 처리해줌
            nge[stack.pop()[1]] = -1;
        }
        for(int i=0; i<N; i++){
            sb.append(nge[i]).append(" ");
        }
        System.out.println(sb);
    }
}
