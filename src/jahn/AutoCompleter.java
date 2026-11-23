/*
 * Course: CSC1020
 * Lab 14 - Auto Complete
 * Name: TODO
 */
package jahn;

public interface AutoCompleter {

    boolean add(String word);

    boolean exactMatch(String target);

    int size();

    String[] allMatches(String prefix);


    static String format(long nanoseconds) throws IllegalArgumentException {
        String result;
        String minutes = nanoseconds % Time.HOUR.duration / Time.MINUTE.duration != 1 ? "s " : "";
        String seconds = nanoseconds % Time.MINUTE.duration / Time.SECOND.duration != 1 ? "s" : "";

        if(nanoseconds < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        switch ((Long) nanoseconds) {
            case Long l when l >= Time.DAY.duration -> {
                long days = nanoseconds / Time.DAY.duration;
                result = days + " day" + (nanoseconds / Time.DAY.duration != 1 ? "s " : " ");
                long hours = nanoseconds % Time.DAY.duration / Time.HOUR.duration;
                result += hours + " hour" +
                        (nanoseconds % Time.DAY.duration / Time.HOUR.duration != 1 ? "s " : " ");
                long mins = nanoseconds % Time.HOUR.duration / Time.MINUTE.duration;
                result += mins + " minute" + minutes;
            }
            case Long l when l >= Time.HOUR.duration -> {
                long hours = nanoseconds / Time.HOUR.duration;
                result = hours + " hour" + (nanoseconds / Time.HOUR.duration != 1 ? "s " : " ");
                long mins = nanoseconds % Time.HOUR.duration / Time.MINUTE.duration;
                result += mins + " minute" + minutes;
                long secs = (nanoseconds % Time.MINUTE.duration) / Time.SECOND.duration;
                result += secs + " second" + (secs != 1 ? "s" : "");
            }
            case Long l when l >= Time.MINUTE.duration -> {
                long mins = nanoseconds / Time.MINUTE.duration;
                result = mins + " minute" + (nanoseconds / Time.MINUTE.duration != 1 ? "s " : " ");
                double secs = (double) (nanoseconds % Time.MINUTE.duration) / Time.SECOND.duration;
                result += secs > 0 && secs % 1 == 0 ? (int) secs + " second"
                        : secs == 0 ? "0 seconds"
                        : String.format("%.1f second", secs) + seconds;
            }
            case Long l when l >= Time.SECOND.duration -> {
                double secs = (double) nanoseconds / Time.SECOND.duration;
                result = secs % 1 == 0 ? (int) secs + " second"
                        : String.format("%.1f second", secs) + seconds;
            }
            case Long l when l >= Time.MICRO.duration ->
                    result = nanoseconds / Time.MICRO.duration == 1 ? "1 microsecond" :
                    String.format("%.1f microseconds", (double) nanoseconds / Time.MICRO.duration);
            case Long l when l >= Time.MILLI.duration ->
                    result = nanoseconds / Time.MILLI.duration == 1 ? "1 millisecond"
                    : String.format("%.1f milliseconds",
                            (double) nanoseconds / Time.MILLI.duration);
            default -> result = nanoseconds + " nanosecond" + (nanoseconds > 1 ? "s" : "");
        }
        return result.trim();
    }

    static String getString(int length) {
        final int a = 97;
        final int z = 122;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; ++i) {
            sb.append((char)((int)(Math.random() * (z - a + 1)) + a));
        }
        return sb.toString();
    }

    enum Time {
        MICRO(1_000),
        MILLI(1_000_000L),
        SECOND(1_000_000_000L),
        MINUTE(60_000_000_000L),
        HOUR(60 * 60_000_000_000L),
        DAY(24 * 60 * 60_000_000_000L);
        private final long duration;

        Time(long duration) {
            this.duration = duration;
        }
    }
}

