package org.hse.baseproject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeIntervalProvider {

    private static final String timeApiRoute = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";

    private final HttpRequestSender requestSender = new HttpRequestSender();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public TimeInterval getTimeInterval(MainActivity.SheduleType type) {
        Date currentDateTime = getCurrentDateTimeOrDefault();

        switch (type) {
            case DAY:
                return calculateTimeIntervalForDay(currentDateTime);
            case WEEK:
                return calculateTimeIntervalForWeek(currentDateTime);
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Date getCurrentDateTimeOrDefault() {
        try {
            String dateTimeString;

            try {
                dateTimeString = (requestSender.get(timeApiRoute)).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }

            Gson gson = new Gson();
            TimeResponse timeResponse = gson.fromJson(dateTimeString, TimeResponse.class);
            String currentTimeString = timeResponse.getTimeZone().getCurrentTime();

            String regex = "[0-9]+-[0-9]+-[0-9]+";
            Matcher matcher = Pattern.compile(regex).matcher(currentTimeString);
            if (matcher.find()) {
                return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(matcher.group());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TimeInterval calculateTimeIntervalForDay(Date currentDateTime) {
        TimeInterval timeIntervalForDay = new TimeInterval();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

        String startAndFinishTime = dataFormat.format(currentDateTime);

        timeIntervalForDay.setFinishTime(startAndFinishTime);
        timeIntervalForDay.setStartTime(startAndFinishTime);

        return timeIntervalForDay;
    }

    private TimeInterval calculateTimeIntervalForWeek(Date currentDateTime) {
        TimeInterval timeIntervalForDay = new TimeInterval();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDateTime);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date startDate = calendar.getTime();
        String start = dataFormat.format(startDate);

        calendar.add(Calendar.DATE, 7);
        Date finishDate = calendar.getTime();
        String finish = dataFormat.format(finishDate);

        timeIntervalForDay.setFinishTime(finish);
        timeIntervalForDay.setStartTime(start);

        return timeIntervalForDay;
    }
}