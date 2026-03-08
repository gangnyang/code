import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 이전에는 스택으로 풀어서 문자열은 그대로 결과 큐에 넣고 연산자는 스택에 담고 괄호나 + - 가 나올 때 따로 처리해주는 방식으로 했었는데
 * 좌변, 연산자, 우변 식으로 세 갈래로 나눠서 재귀적으로 풀어보면 좀 더 간단하게 풀 수 있을 것 같아서 바꿔봄
 * result는 ArrayList로 선언하여 하나씩 뒤로 추가할 수 있게끔 함
 * 근데 단순히 변 처리식으로 함수를 만들어서 재귀적 접근하면 안에서 if문으로 처리하는게 기존 스택하는거랑 큰 차이가 없음
 * 우선순위를 세 개로 나눠서 처리하였다
 */

public class Main {
    static int pos=0;
    static List<Character> result = new ArrayList<>();
    static String s;

    static void solution1(){
        solution2();
        while(pos<s.length() && (s.charAt(pos) =='+'||s.charAt(pos)=='-')){
            char temp = s.charAt(pos++);
            solution2();
            result.add(temp);
        }
    }

    static void solution2(){
        solution3();
        while(pos<s.length() && (s.charAt(pos)=='*'||s.charAt(pos)=='/')){
            char temp = s.charAt(pos++);
            solution3();
            result.add(temp);
        }
    }

    static void solution3(){
        if(s.charAt(pos)=='('){
            pos++; // (는 아예 걍 미처리
            solution1(); // 괄호 내부 식 처리
            pos++; // 닫는 괄호도 미처리
        }else{
            result.add(s.charAt(pos++));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        s = bf.readLine();
        solution1();
        for(char c : result){
            bw.write(c);
        }
        bw.write("\n");
        bw.flush();
        bw.close();
    }
}
