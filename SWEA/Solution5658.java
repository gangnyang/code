import java.io.*;
import java.util.*;

public class Solution5658 {
    static TreeSet<Integer> set;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            int ans=0;
            set = new TreeSet<>(Comparator.comparingInt(a->-1*a));
            ss = new StringTokenizer(bf.readLine(), " ");
            int N = Integer.parseInt(ss.nextToken());
            int K = Integer.parseInt(ss.nextToken());
            String s = bf.readLine();
            Deque<Character> deque = new ArrayDeque<>();
            for(int i=0; i<N; i++){
                deque.add(s.charAt(i));
            }
            for(int i=0; i<N/4; i++){
                for(int ii=0; ii<4; ii++){
                    int temp = 0;
                    for(int iii=0; iii<N/4; iii++){
                        char c = deque.pollFirst();
                        temp *=16;
                        temp += Character.digit(c, 16);
                        deque.addLast(c);
                    }
                    set.add(temp);
                }
                deque.addLast(deque.pollFirst());
            }
            List<Integer> templist = new ArrayList<>(set);

            sb.append("#").append(t).append(" ").append(templist.get(K-1)).append("\n");
        }
        System.out.print(sb);
    }
}
