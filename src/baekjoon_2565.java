import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_2565 {
    static int n, a, max, current;
    static int[][] input;
    static int[] price;
    static int[] dp;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1. 전깃줄 개수
        n = Integer.parseInt(st.nextToken());
        input = new int[n][2];
        price = new int[n+1];
        dp = new int[n+1];
        a = 1;
        max = 0;
        current = 0;

        // 2. A 그룹을 기준으로 정렬된 배열
        // 배열에 들어가는 인덱스값이 곧 B 그룹(전깃줄의 도착지)이다.
        for(int i=0; i<n; ++i){
            st = new StringTokenizer(br.readLine());
            input[i][0] = Integer.parseInt(st.nextToken());
            input[i][1] = Integer.parseInt(st.nextToken());
        }

        // 3. input[i][0] 값을 기준으로 sort 후 값 price 배열에 옮기기
        Arrays.sort(input, (i1, i2) -> {
            return i1[0] - i2[0];
        });
        
        for(int i=1; i<=n; ++i){
            price[i] = input[i-1][1];
        }


        // 3. DP 시작
        // dp[i] = 해당 단계에서 내가 
        for(int i=1; i<=n; i++){
            dp[i] = 1;
            // dp는 자기보다 전의 값을 보고 나를 결정하는 것이다.
            // 내 앞에 있는 값들을 전부 훑는다.
            for(int j = 0; j<i;  j++){
                // 내 값(price[i])이 앞의 값(price[j])보다 크다면, 해당 수열 뒤에 내가 붙을 수 있다.
                if(price[j] < price[i]){
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            max = Math.max(max, dp[i]);
        }

        System.out.println(n-max);
        br.close();
    }

}

