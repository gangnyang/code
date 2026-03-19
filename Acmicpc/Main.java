import java.io.*;
import java.util.*;

public class Main {
    static int[][] green = new int[6][4];
    static int[][] blue = new int[4][6];
    static int score = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            dropGreen(t, y);
            dropBlue(t, x);

            clearGreen();
            clearBlue();

            lightGreen();
            lightBlue();
        }

        int count = countBlocks();

        System.out.println(score);
        System.out.println(count);
    }

    // 초록 보드에 블록 떨어뜨리기
    static void dropGreen(int t, int y) {
        if (t == 1) {
            int r = 0;
            while (r + 1 < 6 && green[r + 1][y] == 0) {
                r++;
            }
            green[r][y] = 1;
        } else if (t == 2) {
            int r = 0;
            while (r + 1 < 6 && green[r + 1][y] == 0 && green[r + 1][y + 1] == 0) {
                r++;
            }
            green[r][y] = 1;
            green[r][y + 1] = 1;
        } else { // t == 3
            int r = 1; // 세로 블록의 아래 칸 기준
            while (r + 1 < 6 && green[r + 1][y] == 0) {
                r++;
            }
            green[r][y] = 1;
            green[r - 1][y] = 1;
        }
    }

    // 파랑 보드에 블록 떨어뜨리기
    static void dropBlue(int t, int x) {
        if (t == 1) {
            int c = 0;
            while (c + 1 < 6 && blue[x][c + 1] == 0) {
                c++;
            }
            blue[x][c] = 1;
        } else if (t == 2) {
            // 초록의 세로 2x1 -> 파랑에서는 가로 1x2
            int c = 1; // 가로 블록의 오른쪽 칸 기준
            while (c + 1 < 6 && blue[x][c + 1] == 0) {
                c++;
            }
            blue[x][c] = 1;
            blue[x][c - 1] = 1;
        } else { // t == 3
            // 초록의 가로 1x2 -> 파랑에서는 세로 2x1
            int c = 0;
            while (c + 1 < 6 && blue[x][c + 1] == 0 && blue[x + 1][c + 1] == 0) {
                c++;
            }
            blue[x][c] = 1;
            blue[x + 1][c] = 1;
        }
    }

    // 초록 보드에서 꽉 찬 행 제거
    static void clearGreen() {
        for (int r = 2; r < 6; r++) {
            boolean full = true;
            for (int c = 0; c < 4; c++) {
                if (green[r][c] == 0) {
                    full = false;
                    break;
                }
            }

            if (full) {
                score++;
                removeGreenRow(r);
                r--; // 같은 행 다시 검사
            }
        }
    }

    static void removeGreenRow(int row) {
        for (int r = row; r >= 1; r--) {
            for (int c = 0; c < 4; c++) {
                green[r][c] = green[r - 1][c];
            }
        }
        for (int c = 0; c < 4; c++) {
            green[0][c] = 0;
        }
    }

    // 파랑 보드에서 꽉 찬 열 제거
    static void clearBlue() {
        for (int c = 2; c < 6; c++) {
            boolean full = true;
            for (int r = 0; r < 4; r++) {
                if (blue[r][c] == 0) {
                    full = false;
                    break;
                }
            }

            if (full) {
                score++;
                removeBlueCol(c);
                c--; // 같은 열 다시 검사
            }
        }
    }

    static void removeBlueCol(int col) {
        for (int c = col; c >= 1; c--) {
            for (int r = 0; r < 4; r++) {
                blue[r][c] = blue[r][c - 1];
            }
        }
        for (int r = 0; r < 4; r++) {
            blue[r][0] = 0;
        }
    }

    // 초록 연한 영역 처리
    static void lightGreen() {
        int cnt = 0;
        for (int r = 0; r <= 1; r++) {
            boolean exists = false;
            for (int c = 0; c < 4; c++) {
                if (green[r][c] == 1) {
                    exists = true;
                    break;
                }
            }
            if (exists) cnt++;
        }

        while (cnt-- > 0) {
            for (int r = 5; r >= 1; r--) {
                for (int c = 0; c < 4; c++) {
                    green[r][c] = green[r - 1][c];
                }
            }
            for (int c = 0; c < 4; c++) {
                green[0][c] = 0;
            }
        }
    }

    // 파랑 연한 영역 처리
    static void lightBlue() {
        int cnt = 0;
        for (int c = 0; c <= 1; c++) {
            boolean exists = false;
            for (int r = 0; r < 4; r++) {
                if (blue[r][c] == 1) {
                    exists = true;
                    break;
                }
            }
            if (exists) cnt++;
        }

        while (cnt-- > 0) {
            for (int c = 5; c >= 1; c--) {
                for (int r = 0; r < 4; r++) {
                    blue[r][c] = blue[r][c - 1];
                }
            }
            for (int r = 0; r < 4; r++) {
                blue[r][0] = 0;
            }
        }
    }

    // 남아 있는 블록 개수 세기
    static int countBlocks() {
        int cnt = 0;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                if (green[r][c] == 1) cnt++;
            }
        }
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 6; c++) {
                if (blue[r][c] == 1) cnt++;
            }
        }
        return cnt;
    }
}