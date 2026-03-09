import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main14725 {
    static StringBuilder sb = new StringBuilder();

    static class Node{
        String value;
        int level; // 출력할 때 -- 출력하기 위한 변수
        PriorityQueue<Node> child;
        HashMap<String, Node> map;
        Node(String value, int level){
            this.value = value;
            this.child = new PriorityQueue<Node>((a, b)->{
                return a.value.compareTo(b.value);
            });
            this.level = level;
            this.map = new HashMap<>();
        }
    }

    static class Trie{
        Node root; // 루트 노드(아무것도 담지 말아야 함)
        Trie(){
            root = new Node("", -1);
        }

        void add(StringTokenizer ss, int K){ // K는 문자열 길이임(단어 개수)
            Node temp = root;
            String value;
            for(int i=0; i<K; i++){
                value = ss.nextToken();
                if(temp.map.containsKey(value)){
                    temp = temp.map.get(value); // 이미 있으면 옮김
                }else{
                    Node tempnode = new Node(value, temp.level+1);
                    temp.map.put(value, tempnode); // 만약 값이 없으면 삽입
                    temp.child.add(tempnode);
                    temp = tempnode;
                }
            }
        }

        void check(){ // 루트는 출력하면 안되기 때문에 처리해준다.
            Node temp = root;
            while(!temp.child.isEmpty()){
                Node polled = temp.child.poll();
                dfs(polled);
            }
        }

        void dfs(Node node){
            for(int i=0; i<node.level; i++){ // level만큼 작대기 두개 출력
                sb.append("--");
            }
            sb.append(node.value).append("\n");
            while(!node.child.isEmpty()){ // pq에 사전 순으로 정렬해놓았기 때문에 사전 순으로 방문하게 된다
                Node polled = node.child.poll();
                dfs(polled);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        Trie trie = new Trie();
        for(int i=0; i<N; i++){
            ss = new StringTokenizer(bf.readLine(), " ");
            int K = Integer.parseInt(ss.nextToken());
            trie.add(ss, K);
        }
        trie.check();
        System.out.print(sb);
    }
}
