import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class baekjoon_9663 {
    static int N;
    static int result = 0; // 결과


    // 1. 각 행별로 1개의 퀸씩만 선택하면 됨
    static int list[]; 
    

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        list = new int[N];
        solution(0);
        

        System.out.println(result);
        br.close();
    }


    public static void solution(int row){
        // 만약 깊이가 N까지 도달했다면 result 증가시키고 함수 종료
        if(row >= N){
            result++;
            return;
        }
        
        // 해당 row에 둘 열 순회
        for(int col=0; col<N; ++col){
            if(canPlace(row, col)){
                list[row] = col;
                solution(row+1);
            }
        }
    }

    // 해당 자리가 퀸을 둘 수 있는 자리인지 판별하는 함수
    public static boolean canPlace(int d, int col){
        for(int row=0; row<d; ++row){
            // 1. 이전에 둔 퀸들의 열들과 같은게 있는지 확인하기
            if(list[row] == col) return false;

            // 2. 대각선에 있는지 확인
            if(Math.abs(row-d) == Math.abs(list[row]-col)) return false;
        }
        return true;
    }
}
