package com.lxh.enjoy.dp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @author lixiaohao
 * @since 2020/4/10 18:19
 */
public class TestOne {

    public static void main(String[] args) {
        System.out.println(new BigDecimal(5).divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP));
    }

}
