import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main5052 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            int N = Integer.parseInt(bf.readLine());
            boolean flag = true; // 현재 목록이 일관성이 있는지에 대한 변수
            HashMap<String, Boolean> map = new HashMap<>(); // 접두어를 담을 해시맵
            String [] list = new String[N]; // 재순회를 위해 list에 담을거임
            for(int i=0; i<N; i++){
                String s = bf.readLine();
                list[i] = s;
                map.put(s, true); // 맵에 통째로 집어넣는다
            }
            for(int i=0; i<N; i++){
                for(int ii=1; ii<list[i].length(); ii++){
                    String temp = list[i].substring(0, ii); // 각 문자열은 10자를 넘지 않으므로 모두 확인함
                    if(map.containsKey(temp)){ // 여기서 예시로 문자열이 example이면 e부터 exampl까지 맵에 있는지 확인하는 것이다.
                        flag = false; // 만약 있으면 접두어가 겹치므로 flag를 false로 바꿔줌
                        break;
                    }
                }
                if(!flag) break;
            }
            if(flag){ // flag에 따라 답을 출력
                sb.append("YES\n");
            }else{
                sb.append("NO\n");
            }
        }
        System.out.print(sb);
    }
}
