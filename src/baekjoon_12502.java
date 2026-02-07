
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class baekjoon_12502{
    static int N, M;
    static int[][] map;
    static int max = 0;
    static Node[] viruses;
    static int virusNum = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        // 1. map 입력받기
        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; ++j){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 2) virusNum++;
            }
        }

        viruses = new Node[virusNum];
        int tmp = 0;
        for(int i=0; i< N; ++i){
            for(int j=0; j< M; ++j){
                if(map[i][j] == 2){
                    viruses[tmp++] = new Node(i, j);
                }
            }
        }

        // 2. 벽을 설치할 위치를 고른다. (백트래킹 사용)
        createWall(0, 0);
        
        System.out.println(max);
        br.close();
    }

    public static void createWall(int current, int depth){
        // 1. 만약 현재 깊이가 3인 경우(3개의 벽을 이미 설치한 경우), 해당 상태에서 병원균을 퍼트리고 안전 구역의 개수를 구한다.
        if(depth == 3){
            spread(map);
            return;
        }

        // 2. 아직 깊이가 3이 아닌 경우엔, 현재 위치 이후로 벽을 설치하도록 for문을 수행한다.
        for(int i=current; i<N*M; ++i){
            int row = i / M;
            int col = i % M;

            if(map[row][col] == 0){
                // (1) 현재 위치가 0인 경우, 현재 위치에 벽 설치
                map[row][col] = 1;
                // (2) 다음 벽 설치
                createWall(i+1, depth+1);
                // (3) 현재 위치에 있던 벽 원상복구
                map[row][col] = 0;
            }
        }
    }

    public static void spread(int[][] map){
        // 1. 전달받은 map을 복사한다.
        int[][] testMap = Arrays.stream(map).map(int[]::clone).toArray(int[][]::new);
        boolean[][] visited = new boolean[N][M];

        // 2. BFS로 virus들을 전파한다
        for(Node virus:viruses){
            bfs(testMap, virus, visited);
        }

        // 3. 해당 testMap에서 0인 부분들 뽑기
        int count = 0;
        for(int i=0; i<N; ++i){
            for(int j=0; j<M; ++j){
                if(testMap[i][j] == 0) count++;
            }
        }

        // 4. count값 max랑 비교하여 최신화
        max = Math.max(count, max);
    }

    public static void bfs(int[][] testMap, Node virus, boolean[][] visited){
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(virus);
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        while(!queue.isEmpty()){
            Node tmp = queue.poll();
            if(!visited[tmp.row][tmp.col]){
                visited[tmp.row][tmp.col] = true;
                testMap[tmp.row][tmp.col] = 2;
                for(int i=0; i<4; ++i){
                    int newRow = tmp.row+dy[i];
                    int newCol = tmp.col+dx[i];
                    if(newRow >= 0 && newRow < N && newCol >= 0 && newCol < M && testMap[newRow][newCol] == 0){
                        queue.add(new Node(newRow, newCol));
                    }
                }
            }
        }
    }



    static class Node {
        int row;
        int col;
        public Node(int r, int c){
            this.row = r;
            this.col = c;
        }
    }

}