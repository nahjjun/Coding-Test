
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_11401 {
    public static int N, K;
    public static long divide = 1000000007;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 이항계수 공식
        // nCk = n! / ((n-k)! * k!)

        // 모듈러 연산식 변환 결과
        // n * (n-1) * ... *(n-k+1) * k!^(p-2)
        // 이는 결국, 아래 식이다.
                // ((N! mod P) * (K!(N-K)!)^(P-2) mod P) ) mod P

        // 이때, 우리는 큰 수의 거듭제곱이 필요함 (k!^(p-2))
        // 이때 분할정복 개념이 나온다.
        // a^m = a^(m/2) * a^(m/2)

        // 1. (N! mod P)
        long A = factorialMod(N);

        // 2. (K!(N-K)!)^(P-2) mod P)
            // 2-1. K!(N-K)!
        long B = factorialMod(K) * factorialMod(N-K);

            // 2-2. B^(P-2) 
        long tmp = customPow(B, divide-2);
        
            // 2-3. tmp mod P
        tmp %= divide;
        
        long result = (A*tmp) % divide;

        System.out.println(result);

        br.close();
    }

    // 각 팩토리얼마다 모듈러 연산을 누적해서 실행
    public static long factorialMod(long value){
         long r = 1;
        for (long i = 2; i <= value; i++) r = (r * i) % divide;
        return r;
    }


    // 거대한 수의 제곱 연산을 분할정복으로 수행하는 함수
    // 분할된 구조에서도 계속해서 매번 모듈러 연산을 수행하여, 오버플로우를 방지한다.
    public static long customPow(long b, long m){
        // 지수가 1일 경우, base^1이므로 base % P를 return
        if(m==1) return b % divide; // m은 제곱 인자

        // 분리한 수의 제곱값을 구함
        long tmp = customPow(b, m/2);

        // 1. 지수가 홀수인 경우, ((tmp^2) * b) mod P
        if(m % 2 == 1){
            return (((tmp * tmp) % divide) * (b % divide)) % divide;
        }
        
        // 2. 지수가 짝수인 경우, (tmp ^ 2) mode P
        return tmp * tmp % divide;
    }

}
