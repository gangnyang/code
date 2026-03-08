import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main3217 {

    static HashMap<String, int[]> addrinfo = new HashMap<>(); // 각 변수들의 메모리 주소 정보를 담고 있는 해시맵
    static class Page{
        int pos; // 페이지의 시작점
        int size; // 페이지 크기
        Page(int pos, int size){
            this.pos = pos;
            this.size = size;
        }
    }

    static class Memory{
        List<Page> space;
        Memory(){
            space = new ArrayList<>();
            space.add(new Page(1, 100000)); // 기본적으로 빈 공간이 있음
        }

        int add(String var, int varsize){ // 빈 메모리 공간 앞에서부터 확인 후 메모리에 할당하는 함수
            int ans=0;
            for(int i=0; i<space.size(); i++){
                int spacesize = space.get(i).size;
                if(spacesize>=varsize){
                    int spacepos = space.get(i).pos;
                    // 현재 빈 공간이 내가 할당하려는 사이즈보다 클 경우
                    if(spacesize-varsize!=0){ // 만약 딱 맞는 크기면 빈 공간을 추가할 필요가 없음
                        space.add(i+1, new Page(spacepos+varsize, spacesize-varsize)); // 빈공간 삽입
                    }
                    space.remove(i); // 기존의 공간은 제거해야 함
                    ans = spacepos;
                    break;
                }
            }
            addrinfo.put(var, new int[] {ans, varsize}); // 할당이 불가능해도 일단 0으로 할당하긴 해야 print 가능
            return ans; // 문제에서 할당이 가능하면 주소값을 리턴하고, 할당이 불가능하면 0을 리턴하라고 했으므로 기본값을 0으로 설정함
        }


        void free(String var){ // var로 할당 된 곳을 제거하고 페이지를 늘리는 함수
            if(!addrinfo.containsKey(var)) { // 맵에 값이 없거나 스택이 비어있으면 처리안함
                return;
            }
            int [] varinfo = addrinfo.get(var);
            int varpos = varinfo[0];
            int varsize = varinfo[1];

            if(varpos==0) return; // add에서 0이 삽입될 수 있어서 체크용

            addrinfo.put(var, new int [] {0, 0});
            for(int i=0; i<space.size(); i++){
                int pres_spacepos=space.get(i).pos;
                int pres_spacesize=space.get(i).size;
                if(pres_spacepos>varpos){
                    // 현재 순회하고 있는 space의 원소가 varpos(var이 할당 된 위치 다음일 경우)
                    // space.get(i-1) => 즉 이전 공간에 var의 크기만큼 추가해주어야 함
                    space.add(i, new Page(varpos, varsize)); // 일단 var 공간을 비웠다고 가정함
                    if(i!=0){
                        int prev_spacepos=space.get(i-1).pos;
                        if(i<space.size()&&varpos+varsize==pres_spacepos){ // (이전 페이지)(?)(var)(현재 페이지)로 되어 있으면 현재 페이지 크기만큼 더해주면 됨
                            space.get(i).size += pres_spacesize;
                            space.remove(i+1); // i 자리에 삽입했기 때문에 i+1이 현재 있고 이 페이지를 제거해주면 됨
                        }
                        if(prev_spacepos + space.get(i-1).size == varpos) { // 메모리 공간에 현재 (이전 페이지)(var)(?)(현재 페이지) 이렇게 있음
                            space.get(i-1).size += space.get(i).size; // 이전 페이지와 커진 var 공간을 합친다
                            space.remove(i); // 현재 페이지 제거
                        }
                        return; // 제거해줬으면 종료
                    }
                    // 첫 번째 빈 공간일 경우
                    if(varsize+varpos==pres_spacepos){ // 만약 원소를 제거하면 바로 이 공간과 닿는 경우 => (var)(현재 페이지)
                        space.get(0).size+=space.get(1).size; // 현재 페이지 크기를 더해주어야 함
                        space.remove(1); // 현재 페이지는 제거함
                    }
                    return;
                }
            }
            space.add(new Page(varpos, varsize)); // 만약 아예 마지막 공간일 경우 위의 if문에서 안 걸리니까 공간 생성
            int index = space.size()-1;
            if(index>0){
                Page prev = space.get(index-1);
                if(prev.pos + prev.size == varpos){
                    prev.size += varsize; // varsize만큼 더해주고
                    space.remove(index); // 마지막 공간이 복사되었으므로 삭제
                }
            }
        }


    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer ss;
        int N = Integer.parseInt(bf.readLine());
        Memory memory = new Memory();
        for(int i=0; i<N; i++){
            ss = new StringTokenizer(bf.readLine(), "=();");
            String var = ss.nextToken();
            if(var.equals("print")){ // print일 때 처리
                var = ss.nextToken();
                if(!addrinfo.containsKey(var)){
                    sb.append("0\n");
                }else{
                    sb.append(addrinfo.get(var)[0]).append("\n");
                }
            }else if(var.equals("free")){
                var = ss.nextToken();
                memory.free(var);
            }else{
                ss.nextToken(); // malloc은 버리고
                int size = Integer.parseInt(ss.nextToken());
                memory.add(var, size); // 메모리에 할당
            }
        }
        System.out.print(sb);
    }
}
