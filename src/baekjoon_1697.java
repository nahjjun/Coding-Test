import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_1697 {

    public static int K, N;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());


        int result = solution();

        System.out.println(result);

        br.close();
    }

    
    // bfs
    public static int solution(){
        // 1. queue 생성
        ArrayDeque<int[]> queue = new ArrayDeque<int[]>();

        int[] result = null; // 결과값이 저장될 변수
        boolean visited[] = new boolean[100001]; // visited 배열은 0~K까지만 생성

        // 2. 시작 노드 넣기
        int[] start = new int[2];
        start[0] = N; // 시작 지점
        start[1] = 0; // 시간
        queue.add(start);

        // 3. bfs 시작
        while (!queue.isEmpty()) { 
            // 1개를 꺼내온다
            int[] tmp = queue.poll();
            visited[tmp[0]] = true;

            // 꺼내온 것의 현재 위치가 K와 같은지 확인한다
            if(tmp[0] == K){
                result = tmp;
                break;
            }
            
            // 현재 위치가 K와 같지 않다면,
                // 1: N-1 / 2: N+1 / 3: N*2 로 갈 수 있는지 조건을 확인한 후에 Queue에 추가한다.
            // 1. N-1
            if(tmp[0]-1 >= 0 && tmp[0]-1 <= 100000 && !visited[tmp[0]-1]){
                int[] newPoint = new int[2];
                newPoint[0] = tmp[0]-1; // 시작 지점
                newPoint[1] = tmp[1]+1; // 시간
                queue.add(newPoint);
            }
            // 2. N+1
            if(tmp[0]+1 >= 0 && tmp[0]+1 <= 100000 && !visited[tmp[0]+1]){
                int[] newPoint = new int[2];
                newPoint[0] = tmp[0]+1; // 시작 지점
                newPoint[1] = tmp[1]+1; // 시간
                queue.add(newPoint);
            }
            // 3. N*2
            if(tmp[0]*2 >= 0 && tmp[0]*2 <= 100000 && !visited[tmp[0]*2]){
                int[] newPoint = new int[2];
                newPoint[0] = tmp[0]*2; // 시작 지점
                newPoint[1] = tmp[1]+1; // 시간
                queue.add(newPoint);
            }
        }

        if(result == null) return 0;
        
        return result[1];
    }

}
