import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_14503 {
    static int N, M;
    static int[] current;
    static int currentDirection;
    static int[] direction = {0,1,2,3};
    static int[][] grid;
    static int cleanedNum = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 1. 현재 청소기 위치, 방향 입력받기
        st = new StringTokenizer(br.readLine());
        current = new int[2];
        current[0] = Integer.parseInt(st.nextToken());
        current[1] = Integer.parseInt(st.nextToken());
        currentDirection = Integer.parseInt(st.nextToken());

        // 2. 전체 배열 입력받기
        grid = new int[N][M];
        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; ++j){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 3. 전부 입력받았으니, 로봇 청소기 동작 시작
        while(true){
            // (1) 현재 칸이 청소 안되어있으면, 현재칸 청소 (2로 채우기)
            cleanCurrent();

            // (2) 현재 칸의 주변 4칸이 모두 청소되어있는 상태인지 확인
            if(allCleaned()){
                // 2-1) 현재 칸의 주변 4칸이 모두 청소되어있는 상태라면, 
                // 1. 바라본 칸에서 한칸 후진한다. 만약 한칸 후진에 실패했다면 반복문 종료
                if(!goBack()) break;
            } else {
                // 2-2) 현재 칸의 주변 4칸이 아직 청소되지 않은 경우,
                // 1. 첫번째 "아직 청소되지 않은 칸"이 나올때까지 반시계로 회전한다.
                spinUntil();
                // 2. 바라보는 앞 칸으로 한칸 전진한다.
                go();
            }
        }

        System.out.println(cleanedNum);
        br.close();
    }

    public static void cleanCurrent() {
        if(grid[current[0]][current[1]] == 0) {
            grid[current[0]][current[1]] = 2;
            cleanedNum++;
        }
    }
    
    public static boolean allCleaned(){
        int dx[] = {0,0,1,-1};
        int dy[] = {1, -1, 0, 0};
        boolean allCleaned = true;
        for(int i=0; i<4; ++i){
            int nY = dy[i] + current[0];
            int nX = dx[i] + current[1];
            if(nY>=0 && nY<N && nX>=0 && nX<M){
                if(grid[nY][nX] == 0){
                    allCleaned=false;
                    break;
                }
            }
        }
        return allCleaned;
    }

    public static boolean goBack(){
        int row = current[0];
        int col = current[1];

        switch(currentDirection){
            case 0 : row += 1; break;
            case 1 : col -= 1; break;
            case 2 : row -= 1; break;
            case 3 : col += 1; break;
        }

        if(!(row>=0 && row<N && col>=0 && col<M)) return false;
        if(grid[row][col] == 1) return false;
        current[0] = row;
        current[1] = col;
        return true;
    }

    public static void go(){
        int row = current[0];
        int col = current[1];

        switch(currentDirection){
            case 0 : row -= 1; break;
            case 1 : col += 1; break;
            case 2 : row += 1; break;
            case 3 : col -= 1; break;
        }
        current[0] = row;
        current[1] = col;
    }

    public static void spinUntil(){

        while (true) { 
            // 1. 우선 반시계로 회전
            spin();
            // 2. 앞이 청소 안된건지 확인
            if(isFrontUnCleaned()) break;
        }

    }

    public static void spin(){
        switch(currentDirection){
            case 0 : currentDirection = 3; break;
            case 1 : currentDirection = 0; break;
            case 2 : currentDirection = 1; break;
            case 3 : currentDirection = 2; break;
        }
    }

    public static boolean isFrontUnCleaned(){
        int row = current[0];
        int col = current[1];

        switch(currentDirection){
            case 0 : row--; break;
            case 1 : col++; break;
            case 2 : row++; break;
            case 3 : col--; break;
        }
        return grid[row][col] == 0;
    }
}
