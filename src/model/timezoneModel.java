package model;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  @author Piano Hhagens
 */
public class timezoneModel {
    public static ZoneId currentZone = ZoneId.systemDefault();

    // This method will create UTC zone date time */
    public static ZonedDateTime createUTCzdt(LocalDateTime zdtUTC){
        ZoneId utc = ZoneId.of("UTC");
        return ZonedDateTime.of(zdtUTC, utc);
    }

    //  This method will create EST zone date time */
    public static ZonedDateTime createESTzdt(LocalDateTime zdtEST){
        ZoneId est = ZoneId.of("EST");
        return ZonedDateTime.of(zdtEST , est);
    }

    //  This method will create Locale zone date time */
    public static ZonedDateTime createLOCALzdt(LocalDateTime zdtLOCAL){
        ZoneId lz = currentZone;
        return ZonedDateTime.of(zdtLOCAL, lz);
    }

    //  This method will covert UTC zdt to LOCAL zdt */
    public static ZonedDateTime utcToLOCAL(ZonedDateTime utcToLocal){
        ZonedDateTime uTl = utcToLocal.withZoneSameInstant(currentZone);
        return uTl;
    }

    //  This method will covert EST zdt to LOCAL zdt */
    public static ZonedDateTime estToLOCAL(ZonedDateTime estToLocal){
        ZonedDateTime eTl = estToLocal.withZoneSameInstant(currentZone);
        return eTl;
    }

    // This method will convert LOCAL zdt to UTC zdt */
    public static ZonedDateTime localToUTC(ZonedDateTime localToutc){
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime utcT = localToutc.withZoneSameInstant(utc);
        return utcT;
    }

    //  This method will convert LOCAL zdt to EST zdt */
    public static ZonedDateTime localToEST(ZonedDateTime localToest){
        ZoneId est = ZoneId.of("EST");
        ZonedDateTime estT = localToest.withZoneSameInstant(est);
        return estT;
    }

    //  This method will String query ZoneDateTime */
    public static ZonedDateTime strQzdt(String strInput, String strFormat) {
        ZonedDateTime qZDT = ZonedDateTime.parse(strInput, DateTimeFormatter.ofPattern(strFormat).withZone(ZoneId.of("UTC")));
        return qZDT;
    }

    // This method will convert query time format to my time format */
    public static String qZDTtoMyTformat(ZonedDateTime myTformat){
        DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myTformat.format(tFormat);
    }

    //  This method will create Time format */
    public static LocalDateTime createTimeFormat(String timeFormat){
        DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timeFormat, tFormat);
    }

}
