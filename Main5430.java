import java.io.*;
import java.util.StringTokenizer;

/**
 * 덱을 이용하는 것도 괜찮은 방법인 것 같음
 * 그러나 그냥 시작 위치 start, 끝 위치 end, rev(현재 뒤집힌 건지 여부) 변수를 둬서 자료 구조 없이 바로 해보려고 함
 * 그리고 start가 end랑 같으면 그냥 빈 배열이고
 * end보다 start가 크면 error 출력하는 식으로 하면 문제가 없을 듯 함
 *
 */

public class Main5430 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String p, s;
        StringTokenizer ss;
        int n;
        int start, end, rev, temp;
        int T = Integer.parseInt(bf.readLine());
        for(int t=1; t<=T; t++){
            p = bf.readLine();
            n = Integer.parseInt(bf.readLine());
            int [] arr = new int[n];
            s = bf.readLine();
            start = 0;
            end = n;
            rev = 0; // 시작은 0임(정방향)
            s = s.substring(1, s.length() - 1); // 끝의 대괄호 제거
            ss = new StringTokenizer(s, ","); // 반점 기준으로 토큰화
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(ss.nextToken()); // 반점 기준으로 배열에 담김
            }
            for (int i = 0; i < p.length(); i++) {
                if (p.charAt(i) == 'R') {
                    rev = (rev + 1) % 2; // 0 1을 뒤집음
                }
                else if(p.charAt(i)=='D'){
                    if(rev==0){
                        start++;
                    }else{
                        end--;
                    }
                }
            }
            if(start>end){
                bw.write("error\n");
            }else{
                bw.write("[");
                if(rev==1){
                    for(int i=end-1; i>=start; i--){
                        bw.write(arr[i]+"");
                        if(i!=start) bw.write(",");
                    }
                }else{
                    for(int i=start; i<end; i++){
                        bw.write(arr[i]+"");
                        if(i!=end-1) bw.write(",");
                    }
                }
                bw.write("]\n");
            }
        }
        bw.flush();
        bw.close();
    }
}