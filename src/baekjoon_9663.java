import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class baekjoon_9663 {
    static int N;
    static int result = 0; // 결과
    static ArrayList<Point> queenList = new ArrayList<>(); // 퀸들의 위치를 담는 리스트


    // 1. 한개의 퀸이 (n,m)의 위치에 있다면, 다른 퀸들은 n행이나 m열에 존재하면 안된다.
    // 2. 힌개의 퀸이 (n,m)의 위치에 있다면, 다른 퀸의 위치가 (a,b)라고 했을 때, |(a-n)|/|(b-m)| != 1이어야한다.
    // ㄴ> 즉, 대각선에 위치해서는 안된다.


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        solution(0);

        System.out.println(result);
        br.close();
    }

    public static void solution(int current){
        // 만약 퀸이 N개가 골라져있다면, result값을 증가시키고 함수 종료
        if(queenList.size() >= N){
            result++;
            return;
        }
        
        // 퀸을 놓을 자리를 탐색한다.
        for(int i=current; i<(N*N); ++i){
            if(canPlace(i)){
                int x = i%N;
                int y = i/N;
                queenList.add(new Point(x, y));
                solution(i+1);
                queenList.remove(queenList.size()-1);
            }
        }

    }

    // 해당 자리가 퀸을 둘 수 있는 자리인지 판별하는 함수
    public static boolean canPlace(int current){
        int x = current%N;
        int y = current/N;

        // queenList를 순회하면서 각 퀸의 경로와 겹치지는 않는지 확인
        for(int i=0; i<queenList.size(); ++i){
            Point p = queenList.get(i);
            // 1. p의 x열, y행과 같지 않는지?
            if(p.x == x || p.y == y) return false;

            // 2. 대각선에 있는지
            int tmp1 = Math.abs(p.x-x);
            int tmp2 = Math.abs(p.y-y);
            if(tmp1/tmp2 == 1 && tmp1%tmp2==0) return false;
        }
        return true;
    }
}
