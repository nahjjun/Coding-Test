import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class baekjonn_1260 {
    static int N, M, V;
    static StringTokenizer st;
    static BufferedReader br;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        ArrayList<DfsNode> nodeList1 = new ArrayList<>();
        ArrayList<BfsNode> nodeList2 = new ArrayList<>();
        for(int i=0; i<N; ++i){
            nodeList1.add(new DfsNode(i+1));
            nodeList2.add(new BfsNode(i+1));
        }
        
        // M번동안 돌면서 입력받음 & 양방향 매핑
        for(int i=0; i<M; ++i){
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            nodeList1.get(n1-1).queue.add(n2);
            nodeList1.get(n2-1).queue.add(n1);
            nodeList2.get(n1-1).queue.add(n2);
            nodeList2.get(n2-1).queue.add(n1);
        }


        ArrayList<Integer> dfsResult = dfs(nodeList1, N, M, V);
        ArrayList<Integer> bfsResult = bfs(nodeList2, N, M, V);


        dfsResult.forEach(d -> System.out.print(d + " "));
        System.out.println("");
        bfsResult.forEach(d -> System.out.print(d + " "));
    }

    public static ArrayList<Integer> bfs(ArrayList<BfsNode> nodeList, int N, int M, int V){
        // 1. 방문 여부 확인용 배열 생성 & 결과 배열 생성
        boolean visited[] = new boolean[N+1];
        ArrayList<Integer> result = new ArrayList<>();
        

        // 2. Queue 생성
        Queue<BfsNode> queue = new ArrayDeque<>();

        // 3. queue에 시작값 추가
        queue.add(nodeList.get(V-1));

        // 4. while문 시작
        while(!queue.isEmpty()){
            // (1) 큐에서 하나 가져오기
            BfsNode polled = queue.poll();

            // (2) 해당 Node 방문 여부 확인
            if(!visited[polled.num]){
                // (3) 아직 해당 노드를 방문하지 않았다면, 방문처리 & 결과에 추가
                visited[polled.num] = true;
                result.add(polled.num);

                // (4) 해당 노드와 연결된 요소들 큐에 추가 (아직 방문 안한 애들만)
                while(!polled.queue.isEmpty()){
                    int tmp = polled.queue.poll();
                    if(!visited[tmp]) queue.add(nodeList.get(tmp-1));
                }
            }
        }
        return result;


    }

    public static ArrayList<Integer> dfs(ArrayList<DfsNode> nodeList, int N, int M, int V){
        // 1. 방문 여부 확인용 배열 생성 & 결과 배열 생성
        boolean visited[] = new boolean[N+1];
        ArrayList<Integer> result = new ArrayList<>();

        // 2. Stack 생성
        Stack<DfsNode> stack = new Stack<>();

        // 3. Stack에 시작값 추가
        stack.add(nodeList.get(V-1));

        // 4. while문 시작
        while(!stack.isEmpty()){
            // (1) stack에서 하나 가져오기
            DfsNode poped = stack.pop();

            // (2) 해당 Node 방문 여부 확인
            if(!visited[poped.num]){
                // (3) 아직 해당 노드를 방문하지 않았다면, 방문처리 & 결과에 추가
                visited[poped.num] = true;
                result.add(poped.num);

                // (4) 해당 노드와 연결된 요소들 Stack에 추가 (아직 방문 안한 애들만)
                while(!poped.queue.isEmpty()){
                    int tmp = poped.queue.poll();
                    if(!visited[tmp]) stack.add(nodeList.get(tmp-1));
                }
            }
        }
        return result;
    }


    public static class DfsNode{
        int num;
        PriorityQueue<Integer> queue;

        public DfsNode(int num){
            this.num = num;
            queue = new PriorityQueue<Integer>((e1, e2) -> e2-e1);
        }
    }
    
    public static class BfsNode{
        int num;
        PriorityQueue<Integer> queue;

        public BfsNode(int num){
            this.num = num;
            queue = new PriorityQueue<Integer>((e1, e2) -> e1-e2);
        }
    }
}
