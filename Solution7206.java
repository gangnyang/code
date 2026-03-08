import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Solution7206 {
    static HashMap<Integer, Integer> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(bf.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            String num = bf.readLine();
            sb.append(solve(num)).append("\n");
        }
        System.out.print(sb.toString());
    }

    public static int solve(String s) {
        int si = Integer.parseInt(s);
        if(si <10) { // 10 이하면 진행 불가라 함수 탈출!
            return 0;
        }
        if(map.containsKey(si)){
            return map.get(si);
        }
        int temp_max=0; // Hashmap으로 값 저장하기(시간 초과 해결)
        for(int i=1; i<s.length(); i++){
            int left = Integer.parseInt(s.substring(0, i)); // 일단 왼쪽 쪼개고
            temp_max = Math.max(temp_max, split(s.substring(i), left)); // 추가로 쪼개기
        }
        map.put(si, temp_max); // 메모이제이션
        return temp_max;
    }

    public static int split(String s, int mul){
        int temp_mul=mul*Integer.parseInt(s);
        int split_max = solve(String.valueOf(temp_mul))+1;
        for (int i = 1; i < s.length(); i++) { // 1이면 바로 끝날 것이고 11, 12 21, 13 22 31 이런식으로 잘릴거임
            int left = Integer.parseInt(s.substring(0, i)); // 왼쪽 부분
            String right = s.substring(i); // 오른쪽 부분
            // 더 쪼갤 수 있으면 들어가보자
            split_max = Math.max(split_max, split(right, mul*left));
        }
        return split_max;
    }
}
