import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution4014 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            int ans=0;
            ss = new StringTokenizer(bf.readLine());
            int N = Integer.parseInt(ss.nextToken());
            int X = Integer.parseInt(ss.nextToken());
            int [][] board = new int [N][N];
            for(int i=0; i<N; i++){
                ss = new StringTokenizer(bf.readLine(), " ");
                for(int ii=0; ii<N; ii++){
                    board[i][ii] = Integer.parseInt(ss.nextToken());
                }
            }
            for(int i=0; i<N; i++){
                Stack<Integer> stack = new Stack<>();
                boolean flag = true;
                for(int ii=0; ii<N; ii++){
                    if(stack.isEmpty()){
                        stack.push(board[i][ii]);
                    }else{
                        int peek = stack.peek();
                        if(peek==board[i][ii]){
                            stack.push(board[i][ii]);
                        }
                        else if(peek == board[i][ii]-1){
                            if(stack.size()<X){
                                flag = false;
                                break;
                            }else{
                                stack.clear();
                                stack.push(board[i][ii]);
                            }
                        }else if(peek == board[i][ii]+1){
                            for(int k=0; k<X; k++){
                                if(board[i][ii++]!=peek-1){
                                    flag=false;
                                    break;
                                }
                            }
                            stack.clear();
                            stack.push(board[i][ii-1]);
                            ii--;
                        }else{
                            flag=false;
                            break;
                        }
                    }
                }
                if(flag) ans++;
            }
            for(int i=0; i<N; i++){
                Stack<Integer> stack = new Stack<>();
                boolean flag = true;
                for(int ii=0; ii<N; ii++){
                    if(stack.isEmpty()){
                        stack.push(board[ii][i]);
                    }else{
                        int peek = stack.peek();
                        if(peek==board[ii][i]){
                            stack.push(board[ii][i]);
                        }
                        else if(peek == board[ii][i]-1){
                            if(stack.size()<X){
                                flag = false;
                                break;
                            }else{
                                stack.clear();
                                stack.push(board[ii][i]);
                            }
                        }else if(peek == board[ii][i]+1){
                            stack.clear();
                            stack.push(board[ii][i]);
                        }else{
                            flag=false;
                            break;
                        }
                    }
                }
                if(flag) ans++;
            }
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
        System.out.print(sb);
    }
}
