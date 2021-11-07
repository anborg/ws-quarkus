package util;

import io.quarkus.elytron.security.common.BcryptUtil;
import service.ServiceException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Util {
    static final DateTimeFormatter formatterYYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Instant validateSearchDate(String sinceDateString){
        final int minDays = - 6*30, maxDays = 0;
        final var errDays =  "Query date should be between minDays="+ minDays +", maxDays="+maxDays;
        //
        var localDate = LocalDate.parse(sinceDateString, formatterYYYY_MM_DD);
        var sinceDate = localDate.atStartOfDay(ZoneId.of("America/Montreal")).toInstant();
        var now = LocalDate.now();
        long dayDiff = ChronoUnit.DAYS.between(now, localDate);
        if(dayDiff < minDays || dayDiff > maxDays) throw new ServiceException(errDays +". dayDiff="+dayDiff+ ". createDate/sinceDate ="+sinceDateString);
        return sinceDate;
    }
    public static LocalDate nowLocalDate(){
        return LocalDate.now();
    }
    public static Instant nowInstant(){
        return nowLocalDate().atStartOfDay(ZoneId.of("America/Montreal")).toInstant();
    }

    public static String now_yyyy_MM_dd() {
        return nowLocalDate().format(formatterYYYY_MM_DD);
    }

    public static String to_yyyy_MM_dd(LocalDate date) {
        return date.format(formatterYYYY_MM_DD);
    }

    public static String hash(String password) {
        return BcryptUtil.bcryptHash(password);
    }
}
