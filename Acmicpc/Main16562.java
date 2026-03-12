import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main16562 {
    // parent: 각 노드의 부모 노드(대표자)를 저장하는 배열
    // rank: 트리의 높이(깊이)를 저장하여 트리가 한쪽으로 치우치는 것을 방지(Union 최적화)
    // min: 해당 노드 혹은 그 노드가 속한 그룹의 '최소 친구비'를 저장하는 배열
    static int [] parent, rank, min;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine(), " ");
        int N = Integer.parseInt(ss.nextToken()); // 학생 수 (노드 수)
        int M = Integer.parseInt(ss.nextToken()); // 친구 관계 수 (간선 수)
        int k = Integer.parseInt(ss.nextToken()); // 가지고 있는 돈

        parent = new int [N];
        rank = new int [N];
        min = new int [N];

        ss = new StringTokenizer(bf.readLine());
        // 1. 모든 노드의 초기 상태 설정
        for(int i=0; i<N; i++){
            parent[i] = i; // 처음에는 모두 자기 자신이 그룹의 대표(루트)임
            rank[i] = 1;   // 초기 트리의 높이는 1로 설정
            min[i] = Integer.parseInt(ss.nextToken()); // 각 개인의 요구 친구비 저장
        }

        // 2. M개의 친구 관계(간선)를 입력받아 두 사람을 같은 그룹으로 묶음 (Union)
        for(int i=0; i<M; i++){
            ss = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(ss.nextToken());
            int b = Integer.parseInt(ss.nextToken());
            union(a-1, b-1); // 배열 인덱스는 0부터 시작하므로 -1을 해줌
        }

        int ans = 0;
        // 3. 정답(필요한 최소 총비용) 계산
        // 의도: union 과정에서 대표(루트)가 아닌 노드의 min 값을 0으로 만들었으므로,
        // 배열 전체를 더하면 각 그룹의 최소 비용(루트의 min 값)들만 더해짐
        for(int i=0; i<N; i++){
            ans += min[i];
        }

        // 4. 총비용(ans)이 가진 돈(k) 이하이면 비용 출력, 아니면 "Oh no" 출력
        System.out.println(ans<=k?ans:"Oh no");
    }

    // [Find 연산] 해당 노드가 속한 그룹의 진짜 대표(루트 노드)를 찾는 함수
    static int find(int value){
        if(parent[value] == value) return value; // 자기 자신이 부모면 그게 루트임
        // 경로 압축(Path Compression): 찾는 과정에서 거쳐가는 모든 노드의 부모를 루트로 직접 연결하여 속도 최적화
        return parent[value] = find(parent[value]);
    }

    // [Union 연산] 두 노드(a, b)가 속한 두 그룹을 하나의 그룹으로 합치는 함수
    static void union(int a, int b){
        int m = Integer.MAX_VALUE;
        int pa = find(a); // a의 루트(대표)
        int pb = find(b); // b의 루트(대표)

        // a와 b, 그리고 각각의 루트들이 가진 비용 중 가장 작은 값을 m으로 최종 결정
        m = Math.min(m, min[pa]!=0?min[pa]:Integer.MAX_VALUE);
        m = Math.min(m, min[pb]!=0?min[pb]:Integer.MAX_VALUE);

        // Union-by-Rank 기법: 트리의 높이(rank)를 비교해 더 얕은 트리를 깊은 트리 밑에 붙임
        if(rank[pa] > rank[pb]){
            parent[pb] = pa; // pb의 부모를 pa로 변경 (pa가 합쳐진 그룹의 새 루트)
            min[pa] = m;     // 새 루트(pa)에 그룹 전체의 최소 비용 저장
            min[pb] = 0;     // 루트가 아니게 된 pb의 비용은 0으로 지움
        }else{
            parent[pa] = pb; // pa의 부모를 pb로 변경 (pb가 합쳐진 그룹의 새 루트)
            min[pb] = m;     // 새 루트(pb)에 그룹 전체의 최소 비용 저장
            min[pa] = 0;     // 루트가 아니게 된 pa의 비용은 0으로 지움

            // 두 트리의 높이가 같았다면, 한쪽을 다른 쪽에 붙였으니 전체 높이가 1 증가함
            if(rank[pa] == rank[pb]) rank[pb]++;
        }
    }
}