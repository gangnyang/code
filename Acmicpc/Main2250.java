import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2250 {
    // 중위 순회(In-order)를 하며 부여할 x좌표 값 (열 번호)
    static int pos=1;
    static int ans_level=0;
    static int ans_max=-1;

    static class Node{ // left가 좌변 right가 우변
        int left;
        int right;
        Node(int left, int right){
            this.left= left;
            this.right =right;
        }
    }

    /**
     * 중위 순회(In-order Traversal)를 수행하는 DFS 함수
     * 순서: 왼쪽 자식 -> 현재 노드(x좌표 부여) -> 오른쪽 자식
     */
    static void solve(Node [] node, int [] max, int [] min, int num, int level){ // num은 현재 보고있는 노드 번호, level은 노드 레벨
        // 좌변 가운데 우변 순으로 하면 됨

        // 1. 왼쪽 자식 탐색
        if(node[num].left!=-1){
            solve(node, max, min, node[num].left, level+1);
        }

        // 2. 현재 노드 처리 (중위 순회 시점)
        // 현재 레벨(level)에서 가장 오른쪽 위치(max)와 가장 왼쪽 위치(min) 갱신
        // pos는 중위 순회 순서에 따라 1씩 증가하므로 x좌표 역할을 함
        max[level] = Math.max(max[level], pos);
        min[level] = Math.min(min[level], pos++);

        // 3. 오른쪽 자식 탐색
        if(node[num].right!=-1){
            solve(node, max, min, node[num].right, level+1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());

        Node [] node = new Node[N+1];
        int [] cnt = new int[N+1]; // 루트 노드를 찾기 위해서 (부모 노드의 개수를 저장하는 진입차수 배열)
        int [] max = new int[N+1];
        int [] min = new int[N+1]; // max와 min은 최대 최소값을 담을 배열

        // 초기화: max는 가장 작은 값으로, min은 가장 큰 값으로 설정
        Arrays.fill(max, 0);
        Arrays.fill(min, N+100);
        Arrays.fill(cnt, 0);

        int a, b, c, root=0;
        for(int i=1; i<=N; i++){
            ss = new StringTokenizer(bf.readLine());
            a = Integer.parseInt(ss.nextToken());
            b = Integer.parseInt(ss.nextToken());
            c = Integer.parseInt(ss.nextToken());

            // 자식 노드들의 진입차수(cnt)를 증가시켜 부모가 누구인지 체크
            // -1이 아닐 때만 카운팅 (루트가 아닌 노드들)
            cnt[b!=-1?b:0]++;
            cnt[c!=-1?c:0]++;
            node[a] = new Node(b, c);
        }

        // 루트 노드 찾기: 부모가 없는 노드 (진입차수 cnt가 0인 노드)
        for(int i=1; i<=N; i++){
            if(cnt[i] == 0){
                root = i; // cnt가 0이면 부모 노드가 없으므로 루트 노드임
            }
        }

        // DFS 탐색 시작 (루트부터 시작, 레벨은 1부터)
        // 이 함수가 끝나면 min, max 배열에 각 레벨별 최소/최대 x좌표가 채워짐
        solve(node, max, min, root, 1);

        // 정답 찾기: 모든 레벨을 순회하며 최대 너비 계산
        for(int i=1; i<=N; i++){
            // 해당 레벨에 노드가 존재할 경우 (min값이 갱신된 경우)
            // 너비 계산: (최대 x좌표 - 최소 x좌표)
            if(max[i]-min[i]>ans_max){
                ans_max = max[i]-min[i];
                ans_level = i;
            }
            // 중요: 문제 조건 "너비가 같을 때는 레벨이 작은 것을 출력"
            // 위 조건문이 '>(크다)' 이므로 너비가 같을 때는 갱신되지 않음 -> 자연스럽게 작은 레벨 유지
        }

        sb.append(ans_level);
        sb.append(" ");
        sb.append(ans_max+1); // 실제 너비는 (인덱스 차이 + 1)
        System.out.println(sb.toString());
        bf.close();
    }
}