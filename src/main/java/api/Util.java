package api;

import service.ServiceException;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Util {
    static Response badRequest(String err) {
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), err).build();
    }

    static Response notFound() {
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
    }
    static Response serverError(String err) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), err).build();
    }

    public static Instant validateSearchDate(String sinceDateString){
        final int minDays = - 6*30, maxDays = 0;
        final var errDays =  "Query date should be between minDays="+ minDays +", maxDays="+maxDays;
        //
        var localDate = LocalDate.parse(sinceDateString, WorkorderResource.formatter);
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
        return LocalDate.now().atStartOfDay(ZoneId.of("America/Montreal")).toInstant();
    }
}
