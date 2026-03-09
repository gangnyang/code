import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
// 이렇게 두개씩 비교하는 문제는 위상 정렬이었는데 이 문제는 좀 다르다.
// 처음에 위상 정렬로 풀다가 문제가 그런 느낌이 아니라는 것을 깨닫고 살짝 변형함
// 위상 정렬 과정은 그대로 유지하면서 low와 high 배열을 두고 low 내용물을 전달하면서 풀이하였다.

public class Solution5643 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int T = Integer.parseInt(bf.readLine());
        int temp1, temp2;
        for(int t=1; t<=T; t++){
            int N = Integer.parseInt(bf.readLine());
            int M = Integer.parseInt(bf.readLine());
            HashSet<Integer>[] low = new HashSet[N+1]; // 나보다 낮은 학생을 담을 셋
            HashSet<Integer>[] high = new HashSet[N+1]; // 나보다 높은 학생을 담을 셋
            int [] end_cnt = new int [N+1]; // 위상 정렬을 위한 입력 차수
            int ans=0;
            Arrays.fill(end_cnt, 0);
            List<List<Integer>> connect = new ArrayList<>(N+1);
            for(int i=0; i<=N; i++){
                connect.add(new ArrayList<>());
                low[i] = new HashSet<>();
                high[i] = new HashSet<>();
            }
            for(int i=0;i<M; i++){
                ss = new StringTokenizer(bf.readLine());
                temp1 = Integer.parseInt(ss.nextToken());
                temp2 = Integer.parseInt(ss.nextToken());
                end_cnt[temp2]++; // 입력 차수 카운팅 및 간선 연결
                connect.get(temp1).add(temp2);
            }
            Queue<Integer> queue = new ArrayDeque<>();
            for(int i=1; i<=N; i++){
                if(end_cnt[i]==0){
                    queue.add(i); // 입력 차수가 0이면 맨 처음 노드임
                }
            }

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int node : connect.get(cur)) { // 현재 큐의 상단 원소와 연결된 요소들 중에
                    low[node].add(cur); // 연결된 요소 밑엔 현재 cur가 있으므로 추가
                    low[node].addAll(low[cur]); // cur 밑에 있는 요소도 전부 추가
                    end_cnt[node]--;
                    if (end_cnt[node] == 0) {
                        queue.add(node);
                    }
                }
            }

            for(int i=1; i<=N; i++){ // 상단 노드에 대해서도 처리해줌
                for(int node : low[i]){ // i 보다 작은 노드의 high에 i를 추가해줌
                    high[node].add(i);
                }
            }

            for(int i=1; i<=N; i++){
                if(low[i].size() + high[i].size()==N-1){ // low와 high의 크기 합이 N-1이면 모두 연결되어 있는 것임
                    ans++;
                }
            }

            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
        System.out.print(sb);
    }
}
