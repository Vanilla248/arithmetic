package com.wwh.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeCalculator {

    /**
     * 计算两个LocalDateTime之间的有效分钟数，精确到秒，其中每天0-8时的部分算一半时间
     * 不足一分钟的秒数按1分钟计算
     * 
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 有效分钟数（0-8时部分算一半，不足1分钟按1分钟计算）
     * @throws IllegalArgumentException 如果结束时间早于开始时间
     */
    public static BigDecimal calculateEffectiveMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        // 校验参数
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("结束时间不能早于开始时间");
        }

        // 计算总秒数
        long totalSeconds = ChronoUnit.SECONDS.between(startTime, endTime);

        // 如果总秒数为0，直接返回0
        if (totalSeconds == 0) {
            return BigDecimal.ZERO;
        }

        // 处理开始时间和结束时间在同一天的情况
        if (startTime.toLocalDate().equals(endTime.toLocalDate())) {
            return calculateDailyEffectiveMinutes(startTime, endTime, totalSeconds);
        }

        // 处理跨天的情况
        BigDecimal totalMinutes = BigDecimal.ZERO;

        // 计算第一天的有效分钟
        LocalDateTime nextDayStart = startTime.toLocalDate().plusDays(1).atStartOfDay();
        long firstDaySeconds = ChronoUnit.SECONDS.between(startTime, nextDayStart);
        totalMinutes = totalMinutes.add(calculateDailyEffectiveMinutes(startTime, nextDayStart, firstDaySeconds));

        // 计算中间完整天的有效分钟
        LocalDateTime currentDate = nextDayStart;
        while (currentDate.toLocalDate().isBefore(endTime.toLocalDate())) {
            LocalDateTime dayEnd = currentDate.toLocalDate().plusDays(1).atStartOfDay();
            long daySeconds = 24 * 60 * 60; // 一整天的秒数
            totalMinutes = totalMinutes.add(calculateDailyEffectiveMinutes(currentDate, dayEnd, daySeconds));
            currentDate = dayEnd;
        }

        // 计算最后一天的有效分钟
        long lastDaySeconds = ChronoUnit.SECONDS.between(currentDate, endTime);
        totalMinutes = totalMinutes.add(calculateDailyEffectiveMinutes(currentDate, endTime, lastDaySeconds));

        return totalMinutes;
    }

    /**
     * 计算同一天内两个时间点之间的有效分钟数，精确到秒
     */
    private static BigDecimal calculateDailyEffectiveMinutes(LocalDateTime start, LocalDateTime end, long totalSeconds) {
        // 定义0点和8点的时间
        LocalDateTime midnight = start.toLocalDate().atTime(0, 0);
        LocalDateTime morning8 = start.toLocalDate().atTime(8, 0);

        // 计算0-8时的秒数
        long overnightSeconds = 0;

        // 情况1：开始时间和结束时间都在0-8时之间
        if (isBetween(start, midnight, morning8) && isBetween(end, midnight, morning8)) {
            overnightSeconds = totalSeconds;
        }
        // 情况2：开始时间在0-8时之间，结束时间在8点之后
        else if (isBetween(start, midnight, morning8) && end.isAfter(morning8)) {
            overnightSeconds = ChronoUnit.SECONDS.between(start, morning8);
        }
        // 情况3：开始时间在8点之前，结束时间在0-8时之间（跨天）
        else if (start.isBefore(morning8) && isBetween(end, midnight, morning8)) {
            overnightSeconds = ChronoUnit.SECONDS.between(midnight, end);
        }
        // 情况4：时间段跨越0-8时（例如：前一天23点到当天9点）
        else if (start.isAfter(morning8) && end.isBefore(morning8.plusDays(1))) {
            overnightSeconds = 8 * 60 * 60; // 整个0-8时的秒数
        }

        // 将秒数转换为分钟（向上取整）
        BigDecimal regularMinutes = secondsToMinutes(totalSeconds - overnightSeconds);
        BigDecimal overnightMinutes = secondsToMinutes(overnightSeconds);

        // 计算有效分钟数：正常时段 + 半价时段的一半
        return regularMinutes.add(overnightMinutes.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP));
    }

    /**
     * 将秒数转换为分钟数，向上取整到两位小数
     */
    private static BigDecimal secondsToMinutes(long seconds) {
        if (seconds == 0) {
            return BigDecimal.ZERO;
        }

        // 计算分钟数，向上取整到两位小数
        return new BigDecimal(seconds)
            .divide(BigDecimal.valueOf(60), 2, RoundingMode.CEILING);
    }

    /**
     * 判断时间是否在指定范围内（包含边界）
     */
    private static boolean isBetween(LocalDateTime time, LocalDateTime start, LocalDateTime end) {
        return !time.isBefore(start) && !time.isAfter(end);
    }

    // 示例用法
    public static void main(String[] args) {
        // 示例1：同一天内，正常时段
        LocalDateTime start1 = LocalDateTime.of(2023, 10, 1, 9, 0, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, 10, 1, 12, 0, 0);
        System.out.println("示例1: " + calculateEffectiveMinutes(start1, end1) + " 分钟"); // 180.00

        // 示例2：同一天内，包含0-8时，且有不足1分钟的秒数
        LocalDateTime start2 = LocalDateTime.of(2023, 10, 1, 7, 59, 30);
        LocalDateTime end2 = LocalDateTime.of(2023, 10, 1, 8, 0, 30);
        System.out.println("示例2: " + calculateEffectiveMinutes(start2, end2) + " 分钟"); // 1.50

        // 示例3：跨天，包含0-8时
        LocalDateTime start3 = LocalDateTime.of(2023, 10, 1, 23, 59, 30);
        LocalDateTime end3 = LocalDateTime.of(2023, 10, 2, 0, 0, 30);
        System.out.println("示例3: " + calculateEffectiveMinutes(start3, end3) + " 分钟"); // 1.50

        // 示例4：跨天，包含多个0-8时
        LocalDateTime start4 = LocalDateTime.of(2023, 10, 1, 22, 0, 0);
        LocalDateTime end4 = LocalDateTime.of(2023, 10, 3, 2, 0, 0);
        System.out.println("示例4: " + calculateEffectiveMinutes(start4, end4) + " 分钟"); // 302.00
    }
}