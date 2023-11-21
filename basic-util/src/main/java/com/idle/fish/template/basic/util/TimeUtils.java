package com.idle.fish.template.basic.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 时间工具类
 *
 * @author idle fish
 * @since 2023/11/9
 */
@UtilityClass
public class TimeUtils {

    @Getter
    @AllArgsConstructor
    public enum DateFormat {
        QUANKUN("[yyyy-MM-dd][yyyy-MM-d][yyyy-M-d][yyyy-M-dd][yyyy/MM/dd][yyyy/MM/d][yyyy/M/d][yyyy/M/dd]"),
        /**
         * yyyy
         */
        CT_Y("yyyy"),
        CT_M("yyyy-MM"),
        CT_D("yyyy-MM-dd"),
        CT_D_SLASH("yyyy/MM/dd"),
        CT_H("yyyy-MM-dd HH"),
        CT_MM("yyyy-MM-dd HH:mm"),
        CT_S("yyyy-MM-dd HH:mm:ss"),
        CT_SS("HH:mm:ss"),

        CP_M("yyyyMM"),
        CP_D("yyyyMMdd"),
        CP_H("yyyyMMddHH"),
        CP_MM("yyyyMMddHHmm"),
        CP_S("yyyyMMddHHmmss"),
        CP_SS("HHmmss"),

        CN_Y("yyyy年"),
        CN_M("yyyy年MM月"),
        CN_D("yyyy年MM月dd日"),
        CN_H("yyyy年MM月dd日 HH时"),
        CN_MM("yyyy年MM月dd日 HH时mm分"),
        CN_MM2("yyyy年MM月dd日 HH:mm"),
        CN_S("yyyy年MM月dd日 HH时mm分ss秒"),
        CN_SS("HH时mm分ss秒");

        private final String format;
    }

    public static LocalDate timestamp2LocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }

    public static LocalDateTime timestamp2LocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * 计算年龄
     *
     * @param birthday 生日时间戳
     * @return 年龄
     */
    public static Integer calculateAge(long birthday) {
        return toLocalDate(birthday).until(currentDate()).getYears();
    }

    /**
     * 计算年龄
     *
     * @param birthday 生日时间戳
     * @return 年龄
     */
    public static BigDecimal calculateDecimalAge(long birthday) {
        Period period = toLocalDate(birthday).until(currentDate());
        return BigDecimal.valueOf(period.getYears())
                .add(BigDecimal.valueOf(period.getMonths()).divide(BigDecimal.valueOf(12), 1, RoundingMode.DOWN));
    }

    /**
     * 当前日期时间
     *
     * @return 当前日期时间
     */
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(ZoneOffset.ofHours(8));
    }

    /**
     * 当前日期
     *
     * @return 当前日期
     */
    public static LocalDate currentDate() {
        return currentDateTime().toLocalDate();
    }

    /**
     * 当前时间
     *
     * @return 当前时间
     */
    public static LocalTime currentTime() {
        return currentDateTime().toLocalTime();
    }

    public static Long currentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 转时间戳
     *
     * @param localDate 时间戳
     * @return
     */
    public static long toTimestamp(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * LocalDateTime 转 毫秒时间戳
     */
    public static Long toTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime 转 Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 格式化 时间字符串
     */
    public static String format(LocalDateTime localDateTime, DateFormat format) {
        if (localDateTime == null || format == null) {
            return null;
        }
        return format(localDateTime, format.format);
    }

    /**
     * LocalDateTime 格式化 时间字符串
     */
    public static String format(LocalDateTime localDateTime, String format) {
        if (localDateTime == null || format == null || format.isEmpty() || format.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .toFormatter();
        return localDateTime.format(formatter);
    }

    public static String format(LocalDate localDate, DateFormat format) {
        if (localDate == null || format == null) {
            return null;
        }
        return format(localDate, format.format);
    }

    public static String format(LocalDate localDate, String format) {
        if (localDate == null || format == null || format.trim().isEmpty()) {
            return null;
        }
        return format(localDate.atTime(LocalTime.MIN), format);
    }

    public static String format(LocalTime localTime, DateFormat format) {
        if (localTime == null || format == null) {
            return null;
        }
        return format(localTime, format.format);
    }

    public static String format(LocalTime localTime, String format) {
        if (localTime == null || format == null || format.trim().isEmpty()) {
            return null;
        }
        // LocalTime 不允许年月日
        if (Pattern.matches(".*(y{4}|M{2}|d{2}).*", format)) {
            return null;
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .toFormatter();
        return localTime.format(formatter);
    }

    /**
     * 毫秒时间戳 转 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * 毫秒时间戳 转 LocalDate
     */
    public static LocalDate toLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }

    /**
     * 毫秒时间戳 转 Date
     */
    public static Date toDate(long timestamp) {
        return toDate(toLocalDateTime(timestamp));
    }

    /**
     * 毫秒时间戳 转 时间字符串
     */
    public static String format(long timestamp, DateFormat format) {
        if (format == null) {
            return null;
        }
        return format(timestamp, format.format);
    }

    /**
     * 毫秒时间戳 转 时间字符串
     */
    public static String format(long timestamp, String format) {
        if (format == null || format.trim().isEmpty()) {
            return null;
        }
        return format(toLocalDateTime(timestamp), format);
    }

    /**
     * Date 转 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * Date 转 毫秒时间戳
     */
    public static Long toTimestamp(Date date) {
        return toTimestamp(toLocalDateTime(date));
    }

    /**
     * Date 转 时间字符串
     */
    public static String format(Date date, DateFormat format) {
        if (date == null || format == null) {
            return null;
        }
        return format(date, format.format);
    }

    /**
     * Date 转 时间字符串
     */
    public static String format(Date date, String format) {
        if (date == null || format == null || format.trim().isEmpty()) {
            return null;
        }
        return format(toLocalDateTime(date), format);
    }

    /**
     * 时间字符串 解析为 LocalDateTime
     */
    public static LocalDateTime parse(String dateTime, DateFormat format) {
        if (dateTime == null || dateTime.trim().isEmpty() || format == null) {
            return null;
        }
        return parse(dateTime, format.format);
    }

    /**
     * 时间字符串 解析为 LocalDateTime
     */
    public static LocalDateTime parse(String dateTime, String format) {
        if (dateTime == null || dateTime.isEmpty() || format == null || format.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .toFormatter();
        TemporalAccessor accessor = formatter.parse(dateTime);
        if (Stream.of(ChronoField.HOUR_OF_DAY, ChronoField.MINUTE_OF_HOUR, ChronoField.SECOND_OF_MINUTE).anyMatch(accessor::isSupported)) {
            return LocalDateTime.parse(dateTime, formatter);
        }
        return LocalDate.parse(dateTime, formatter).atTime(LocalTime.MIN);
    }

    public static Long parse(String dateTime, String format, Long defaultValue) {
        if (dateTime == null || dateTime.isEmpty() || format == null || format.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return toTimestamp(parse(dateTime, format));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Long parse(String dateTime, DateFormat format, Long defaultValue) {
        if (dateTime == null || dateTime.isEmpty() || format == null) {
            return defaultValue;
        }
        try {
            return toTimestamp(parse(dateTime, format));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 今天开始时间
     */
    public static LocalDateTime todayStartDateTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 今天开始时间
     */
    public static long todayStartTimestamp() {
        return toTimestamp(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    }

    /**
     * 今天开始时间
     */
    public static LocalDateTime todayStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    public static Long todayStartTime(long timestamp) {
        return toTimestamp(todayStartTime(toLocalDateTime(timestamp)));
    }

    /**
     * 今天结束时间
     */
    public static LocalDateTime todayEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }

    public static Long todayEndTime(long timestamp) {
        return toTimestamp(todayEndTime(toLocalDateTime(timestamp)));
    }

    /**
     * 昨天开始时间
     */
    public static LocalDateTime yesterdayStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.DAYS), LocalTime.MIN);
    }

    /**
     * 昨天结束时间
     */
    public static LocalDateTime yesterdayEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.DAYS), LocalTime.MAX);
    }

    /**
     * 最近7天开始时间
     */
    public static LocalDateTime last7DaysStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(6L, ChronoUnit.DAYS), LocalTime.MIN);
    }


    /**
     * 最近30天开始时间
     */
    public static LocalDateTime last30DaysStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(29L, ChronoUnit.DAYS), LocalTime.MIN);
    }


    /**
     * 最近一年开始时间
     */
    public static LocalDateTime last1YearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.YEARS).plus(1L, ChronoUnit.DAYS), LocalTime.MIN);
    }


    /**
     * 本周开始时间
     */
    public static LocalDateTime weekStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        return LocalDateTime.of(localDate.minusDays(localDate.getDayOfWeek().getValue() - 1L), LocalTime.MIN);
    }

    /**
     * 本周结束时间
     */
    public static LocalDateTime weekEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        return LocalDateTime.of(localDate.plusDays(7L - localDate.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 本月开始时间
     */
    public static LocalDateTime monthStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 本月开始时间
     */
    public static Long monthStartTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return toTimestamp(localDate.with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 本月开始时间
     */
    public static Long monthStartTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return toTimestamp(toLocalDate(timestamp).with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 本月结束时间
     */
    public static LocalDateTime monthEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }


    /**
     * 本月结束时间
     */
    public static Long monthEndTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return toTimestamp(LocalDateTime.of(localDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX));
    }

    /**
     * 本季度开始时间
     */
    public static LocalDateTime quarterStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month month = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue());
        return LocalDateTime.of(LocalDate.of(localDate.getYear(), month, 1), LocalTime.MIN);
    }

    /**
     * 本季度开始时间
     */
    public static Long quarterStartTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        LocalDate localDate = toLocalDate(timestamp);
        Month month = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue());
        return toTimestamp(LocalDate.of(localDate.getYear(), month, 1));
    }

    /**
     * 本季度结束时间
     */
    public static LocalDateTime quarterEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month month = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue()).plus(2L);
        return LocalDateTime.of(LocalDate.of(localDate.getYear(), month, month.length(localDate.isLeapYear())), LocalTime.MAX);
    }

    /**
     * 本季度结束时间
     */
    public static LocalDateTime quarterEndTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        Month month = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue()).plus(2L);
        return LocalDateTime.of(LocalDate.of(localDate.getYear(), month, month.length(localDate.isLeapYear())), LocalTime.MAX);
    }

    /**
     * 本季度结束时间
     */
    public static Long quarterEndTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        LocalDate localDate = toLocalDate(timestamp);
        Month month = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue()).plus(2L);
        return toTimestamp(LocalDateTime.of(LocalDate.of(localDate.getYear(), month, month.length(localDate.isLeapYear())), LocalTime.MAX));
    }

    /**
     * 本半年开始时间
     */
    public static LocalDateTime halfYearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month month = (localDate.getMonthValue() > 6) ? Month.JULY : Month.JANUARY;
        return LocalDateTime.of(LocalDate.of(localDate.getYear(), month, 1), LocalTime.MIN);
    }

    /**
     * 本半年结束时间
     */
    public static LocalDateTime halfYearEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month month = (localDate.getMonthValue() > 6) ? Month.DECEMBER : Month.JUNE;
        return LocalDateTime.of(LocalDate.of(localDate.getYear(), month, month.length(localDate.isLeapYear())), LocalTime.MAX);
    }

    /**
     * 本年开始时间
     */
    public static LocalDateTime yearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 本年开始时间
     */
    public static Long yearStartTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return toTimestamp(localDate.with(TemporalAdjusters.firstDayOfYear()));
    }

    /**
     * 本年开始时间
     */
    public static Long yearStartTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return toTimestamp(LocalDateTime.of(toLocalDate(timestamp).with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));
    }

    /**
     * 本年结束时间
     */
    public static LocalDateTime yearEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }

    /**
     * 本年结束时间
     */
    public static Long yearEndTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return toTimestamp(LocalDateTime.of(toLocalDate(timestamp).with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX));
    }

    /**
     * 上周开始时间
     */
    public static LocalDateTime lastWeekStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate lastWeek = localDateTime.toLocalDate().minus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(lastWeek.minusDays(lastWeek.getDayOfWeek().getValue() - 1L), LocalTime.MIN);
    }

    /**
     * 上周结束时间
     */
    public static LocalDateTime lastWeekEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate lastWeek = localDateTime.toLocalDate().minus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(lastWeek.plusDays(7L - lastWeek.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 上月开始时间
     */
    public static LocalDateTime lastMonthStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 上月结束时间
     */
    public static LocalDateTime lastMonthEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 上季度开始时间
     */
    public static LocalDateTime lastQuarterStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month firstMonthOfQuarter = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfLastQuarter = firstMonthOfQuarter.minus(3L);
        int yearOfLastQuarter = firstMonthOfQuarter.getValue() < 4 ? localDate.getYear() - 1 : localDate.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfLastQuarter, firstMonthOfLastQuarter, 1), LocalTime.MIN);
    }

    /**
     * 上季度结束时间
     */
    public static LocalDateTime lastQuarterEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month firstMonthOfQuarter = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfLastQuarter = firstMonthOfQuarter.minus(1L);
        int yearOfLastQuarter = firstMonthOfQuarter.getValue() < 4 ? localDate.getYear() - 1 : localDate.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfLastQuarter, firstMonthOfLastQuarter, firstMonthOfLastQuarter.maxLength()), LocalTime.MAX);
    }

    /**
     * 上半年开始时间
     */
    public static LocalDateTime lastHalfYearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        int lastHalfYear = (localDate.getMonthValue() > 6) ? localDate.getYear() : localDate.getYear() - 1;
        Month firstMonthOfLastHalfYear = (localDate.getMonthValue() > 6) ? Month.JANUARY : Month.JULY;
        return LocalDateTime.of(LocalDate.of(lastHalfYear, firstMonthOfLastHalfYear, 1), LocalTime.MIN);
    }

    /**
     * 上半年结束时间
     */
    public static LocalDateTime lastHalfYearEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        int lastHalfYear = (localDate.getMonthValue() > 6) ? localDate.getYear() : localDate.getYear() - 1;
        Month lastMonthOfLastHalfYear = (localDate.getMonthValue() > 6) ? Month.JUNE : Month.DECEMBER;
        return LocalDateTime.of(LocalDate.of(lastHalfYear, lastMonthOfLastHalfYear, lastMonthOfLastHalfYear.maxLength()), LocalTime.MAX);
    }

    /**
     * 上一年开始时间
     */
    public static LocalDateTime lastYearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.YEARS).with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 上一年结束时间
     */
    public static LocalDateTime lastYearEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().minus(1L, ChronoUnit.YEARS).with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }

    /**
     * 下周开始时间
     */
    public static LocalDateTime nextWeekStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate nextWeek = localDateTime.toLocalDate().plus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(nextWeek.minusDays(nextWeek.getDayOfWeek().getValue() - 1L), LocalTime.MIN);
    }

    /**
     * 下周结束时间
     */
    public static LocalDateTime nextWeekEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate nextWeek = localDateTime.toLocalDate().plus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(nextWeek.plusDays(7L - nextWeek.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 下月开始时间
     */
    public static LocalDateTime nextMonthStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().plus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 下月结束时间
     */
    public static LocalDateTime nextMonthEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().plus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 下季度开始时间
     */
    public static LocalDateTime nextQuarterStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate nlocalDate = localDateTime.toLocalDate();
        Month firstMonthOfQuarter = Month.of(nlocalDate.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfNextQuarter = firstMonthOfQuarter.plus(3L);
        int yearOfNextQuarter = firstMonthOfQuarter.getValue() > 9 ? nlocalDate.getYear() + 1 : nlocalDate.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfNextQuarter, firstMonthOfNextQuarter, 1), LocalTime.MIN);
    }

    /**
     * 下季度结束时间
     */
    public static LocalDateTime nextQuarterEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        Month firstMonthOfQuarter = Month.of(localDate.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfNextQuarter = firstMonthOfQuarter.plus(5L);
        int yearOfNextQuarter = firstMonthOfQuarter.getValue() > 9 ? localDate.getYear() + 1 : localDate.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfNextQuarter, firstMonthOfNextQuarter, firstMonthOfNextQuarter.maxLength()), LocalTime.MAX);
    }

    /**
     * 上半年开始时间
     */
    public static LocalDateTime nextHalfYearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        int nextHalfYear = (localDate.getMonthValue() > 6) ? localDate.getYear() + 1 : localDate.getYear();
        Month firstMonthOfNextHalfYear = (localDate.getMonthValue() > 6) ? Month.JANUARY : Month.JULY;
        return LocalDateTime.of(LocalDate.of(nextHalfYear, firstMonthOfNextHalfYear, 1), LocalTime.MIN);
    }

    /**
     * 上半年结束时间
     */
    public static LocalDateTime nextHalfYearEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        LocalDate localDate = localDateTime.toLocalDate();
        int lastHalfYear = (localDate.getMonthValue() > 6) ? localDate.getYear() + 1 : localDate.getYear();
        Month lastMonthOfNextHalfYear = (localDate.getMonthValue() > 6) ? Month.JUNE : Month.DECEMBER;
        return LocalDateTime.of(LocalDate.of(lastHalfYear, lastMonthOfNextHalfYear, lastMonthOfNextHalfYear.maxLength()), LocalTime.MAX);
    }

    /**
     * 下一年开始时间
     */
    public static LocalDateTime nextYearStartTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().plus(1L, ChronoUnit.YEARS).with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 下一年结束时间
     */
    public static LocalDateTime nextYearEndTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.of(localDateTime.toLocalDate().plus(1L, ChronoUnit.YEARS).with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }
}
