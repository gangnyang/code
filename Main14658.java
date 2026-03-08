import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
점 입력받을 때마다 cnt 배열에 더해주기는 트램폴린 한 변이 10만이라 불가능
한 점을 기준으로 4사분면으로 나눠서 하면 되나
그러면 근데 변에 걸쳐있으면 틀릴 확률이 있음
한 점을 변에 있다고 치고 순회하는 거는 100*100*400000? 이것도 안됨
맨해튼 거리 기준 2*L 안에 있는 별똥별을 뽑기 -> 이거는 방향까지 고려해야해서 구현이 너무 어려워짐
엄
 */

public class Main14658 {
    public static void main(String[] args) throws IOException { 
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer ss = new StringTokenizer(bf.readLine(), " ");
        int N = Integer.parseInt(ss.nextToken());
        int M = Integer.parseInt(ss.nextToken());
        int L = Integer.parseInt(ss.nextToken());
        int K = Integer.parseInt(ss.nextToken());
        int [] x = new int [K];
        int [] y = new int [K];
        for(int i=0; i<K; i++){
            ss = new StringTokenizer(bf.readLine(), " ");
            x[i] = Integer.parseInt(ss.nextToken());
            y[i] = Integer.parseInt(ss.nextToken());
        }
        int ans=0;
        for(int i=0; i<K; i++){ // 모든 두 점의 좌표에 대해서 x좌표, y좌표만 빼내서 변에다 놓을 때 생기는 사각형을 기준으로 검사
            for(int ii=0; ii<K; ii++){
                int temp = 0;
                int tx = x[i];
                int ty = y[ii];
                for(int p=0; p<K; p++){
                    if(x[p]>=tx&&tx+L>=x[p]&&y[p]>=ty&&ty+L>=y[p]){
                        temp++; // 지역 해 갱신
                    }
                }
                ans = Math.max(ans, temp); // ans에 갱신한다
            }
        }
        System.out.println(K-ans); // 정답은 충돌하는 별똥별 수기에 K에서 빼줌
    }
}
