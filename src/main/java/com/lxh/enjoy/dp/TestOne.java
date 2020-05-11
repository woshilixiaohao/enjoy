package com.lxh.enjoy.dp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lixiaohao
 * @since 2020/4/10 18:19
 */
public class TestOne {

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));

//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int max = 0;
//        int[][] dp = new int[n + 1][n + 1];
//        for (int i = 1; i < n + 1; i++) {
//            for (int j = 1; j <= i; j++) {
//                int input = scanner.nextInt();
//                if (i == 1) {
//                    dp[i][j] = input;
//                } else {
//                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]) + input;
//                }
//                max = Math.max(dp[i][j],max);
//            }
//        }
//        System.out.println(max);

    }

}
