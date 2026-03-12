import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main14621 {
    // 유니온 파인드를 위한 부모 노드 배열과 트리 높이(최적화용) 배열
    static int [] parent, rank;

    // 간선 정보를 담을 클래스 (Kruskal 알고리즘의 핵심)
    static class Edge{
        int a;
        int b;
        int v;
        Edge(int a, int b, int v){
            this.a = a;
            this.b = b;
            this.v = v;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine(), " ");
        int N = Integer.parseInt(ss.nextToken()); // 대학교(정점)의 수
        int M = Integer.parseInt(ss.nextToken()); // 도로(간선)의 수

        // 남초(M) / 여초(W) 대학교 정보 입력
        String s = bf.readLine();
        char [] MW = new char[N];
        parent = new int [N];
        rank = new int [N];

        // 1. 초기 세팅: 유니온 파인드 배열 초기화 및 성별 배열 채우기
        for(int i=0; i<N; i++){
            parent[i] = i;  // 처음엔 모두 자기 자신이 루트
            rank[i] = 1;    // 트리 높이 초기화
            // 💡 천재적인 부분: "M W M W" 처럼 공백이 섞인 문자열에서
            // 짝수 인덱스(0, 2, 4...)만 뽑아서 공백을 무시하고 성별만 쏙쏙 빼옴!
            MW[i] = s.charAt(2*i);
        }

        // 2. 간선 가중치(v)를 기준으로 오름차순 정렬하는 우선순위 큐 (가장 짧은 도로 먼저 뽑기 위함)
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.v));

        // 3. 간선 정보 입력 및 필터링
        for(int i=0; i<M; i++){
            ss = new StringTokenizer(bf.readLine(), " ");
            int a = Integer.parseInt(ss.nextToken());
            int b = Integer.parseInt(ss.nextToken());
            int v = Integer.parseInt(ss.nextToken());

            // 💡 문제의 핵심 조건: 남초-남초, 여초-여초는 연결하면 안 됨!
            // 따라서 성별이 다를 때만 큐에 넣어서 애초에 유효한 도로만 남김
            if(MW[a-1]!=MW[b-1]) {
                pq.add(new Edge(a-1, b-1, v));
            }
        }

        int ans = 0; // 최소 신장 트리의 총 가중치 합
        int cnt = 1; // 연결된 정점의 수 (시작 정점 1개부터 카운트 시작)

        // 4. Kruskal 알고리즘 진행
        while(!pq.isEmpty()){
            Edge polled = pq.poll(); // 가중치가 가장 작은 간선부터 꺼냄 (그리디)

            // 두 정점이 아직 연결되지 않았다면 (사이클이 발생하지 않는다면)
            if(find(polled.a)!=find(polled.b)){
                ans += polled.v;             // 총 거리에 추가
                union(polled.a, polled.b);   // 두 정점을 연결 (하나의 그룹으로 합침)
                cnt++;                       // 연결된 정점 수 증가
            }
        }

        // 5. 정답 출력
        // N개의 정점을 모두 연결하려면 정점 카운트가 N이 되어야 함 (간선 기준으로는 N-1개)
        // 만약 cnt가 N이 아니라면, 모든 대학교를 연결할 수 없는 상태이므로 -1 출력
        System.out.println(cnt==N?ans:"-1");
    }

    // [Find 연산] 해당 노드가 속한 그룹의 진짜 대표(루트 노드)를 찾는 함수
    static int find(int value){
        if(parent[value] == value) return value; // 자기 자신이 부모면 그게 루트임
        // 경로 압축(Path Compression): 찾는 과정에서 거쳐가는 모든 노드의 부모를 루트로 직접 연결하여 속도 최적화
        return parent[value] = find(parent[value]);
    }

    // [Union 연산] 두 노드(a, b)가 속한 두 그룹을 하나의 그룹으로 합치는 함수
    static void union(int a, int b){
        int pa = find(a); // a의 루트(대표)
        int pb = find(b); // b의 루트(대표)
        if(pa==pb) return; // 💡 저번 피드백 완벽 반영! 이미 같은 그룹이면 합칠 필요 없음

        // Union-by-Rank 기법: 트리의 높이(rank)를 비교해 더 얕은 트리를 깊은 트리 밑에 붙임
        if(rank[pa] > rank[pb]){
            parent[pb] = pa; // pb의 부모를 pa로 변경 (pa가 합쳐진 그룹의 새 루트)
        }else{
            parent[pa] = pb; // pa의 부모를 pb로 변경 (pb가 합쳐진 그룹의 새 루트)

            // 두 트리의 높이가 같았다면, 한쪽을 다른 쪽에 붙였으니 전체 높이가 1 증가함
            if(rank[pa] == rank[pb]) rank[pb]++;
        }
    }
}