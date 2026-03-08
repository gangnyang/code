import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main14567 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        ss = new StringTokenizer(bf.readLine());
        int N = Integer.parseInt(ss.nextToken());
        int M = Integer.parseInt(ss.nextToken());
        int [] cnt =  new int [N+1]; // 위상 정렬 입력 차수
        int [] semester = new int [N+1]; // 몇 학기에 이수 가능한지 정보를 담는 배열
        List<List<Integer>> edges = new ArrayList<>(); // 간선
        for(int i=0; i<=N; i++){
            edges.add(new ArrayList<>());
            cnt[i]=0; // 초기화
        }
        for(int i=0; i<M; i++){
            ss = new StringTokenizer(bf.readLine());
            int from = Integer.parseInt(ss.nextToken());
            int to = Integer.parseInt(ss.nextToken());
            cnt[to]++;
            edges.get(from).add(to); // 입력 차수 증가시키고 간선 연결
        }
        Queue<int []> queue = new ArrayDeque<>();
        for(int i=1; i<=N; i++){
            if(cnt[i]==0){
                queue.add(new int[] {i, 1}); // 입력 차수 없는 과목은 1학기로 넣는다
            }
        }
        while(!queue.isEmpty()){
            int [] polled = queue.poll();
            semester[polled[0]] = polled[1];
            for(int to : edges.get(polled[0])){
                cnt[to]--;
                if(cnt[to]==0){
                    queue.add(new int[] {to, polled[1]+1}); // 큐에 학기 수를 1씩 증가시키면서 넣는다
                }
            }
        }
        for(int i=1; i<=N; i++){
            sb.append(semester[i]).append(" "); // 각 과목 별 최소 이수 학기 수를 출력한다.
        }
        System.out.println(sb);
    }
}
