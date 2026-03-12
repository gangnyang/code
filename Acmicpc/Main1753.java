import java.io.*;
import java.util.*;

public class Main1753 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine(), " ");
        StringBuilder sb = new StringBuilder();

        // 정점(V), 간선(E) 개수 받고 시작점(K) 세팅함
        int V = Integer.parseInt(ss.nextToken());
        int E = Integer.parseInt(ss.nextToken());
        int K = Integer.parseInt(bf.readLine());

        // 최단 거리 저장할 배열이랑, 연결 정보 담을 인접 리스트 선언임
        int [] dist = new int [V+1];
        List<int []> [] edges = new ArrayList[V+1];

        // 일단 처음엔 아무데도 못가니까 거리를 다 무한대(MAX_VALUE)로 꽉 채워둠
        for(int i=0; i<=V; i++){
            dist[i] = Integer.MAX_VALUE;
            edges[i] = new ArrayList<>();
        }

        // 내 위치(시작점)니까 거리는 당연히 0임
        dist[K] = 0;

        // 간선 정보 입력받아서 인접 리스트에 쏙쏙 넣음
        // 배열 형태는 {도착지, 가중치} 느낌임
        for(int i=0; i<E; i++){
            ss = new StringTokenizer(bf.readLine(), " ");
            int a = Integer.parseInt(ss.nextToken());
            int b = Integer.parseInt(ss.nextToken());
            int v = Integer.parseInt(ss.nextToken());
            edges[a].add(new int [] {b, v});
        }

        // 다익스트라의 꽃 PQ 등장!
        // 정렬 기준은 무조건 '누적 거리(인덱스 1)' 오름차순임. (가장 짧은 길부터 빼려고)
        PriorityQueue<int []> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // 출발지 정보 PQ에 던져놓고 본격적으로 탐색 시작
        pq.add(new int [] {K, 0});

        while(!pq.isEmpty()){
            int [] polled = pq.poll();
            int b = polled[0]; // 현재 도착한 정점

            // [핵심 가지치기 - 지연 삭제]
            // 방금 큐에서 꺼낸 거리가 이미 기록된 최단 거리보다 멀다?
            // 그럼 굳이 볼 필요 없는 쓸모없는 옛날 정보니까 걍 스킵하는 거임
            if(dist[b]<polled[1]){
                continue;
            }

            // 현재 정점이랑 연결된 길 싹 다 뒤져봄
            for(int [] edge : edges[b]){
                // 지금 있는 곳 거쳐서 가는 게 원래 알던 길보다 빠르면?
                if(dist[b] + edge[1] < dist[edge[0]]){
                    dist[edge[0]] = dist[b] + edge[1]; // 갱신해주고
                    pq.add(new int[] {edge[0], dist[edge[0]]}); // 새 정보 큐에 다시 넣어줌
                }
            }
        }

        // 출력은 속도 편안하게 StringBuilder로 모아서 한 방에 처리함. 못 가는 곳이면 INF 출력~
        for(int d=1; d<=V; d++){
            sb.append(dist[d]!=Integer.MAX_VALUE?dist[d]:"INF").append("\n");
        }
        System.out.print(sb);
    }
}