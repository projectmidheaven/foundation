package org.midheaven.time;

import java.time.*;

public class FixedClockProvider implements ClockProvider{

    private final Clock clock;

    public static FixedClockProvider newInstance(ZonedDateTime dateTime){
        return newInstance(dateTime.toInstant(), dateTime.getZone());
    }

    public static FixedClockProvider newInstanceAtUtc(LocalDateTime dateTime){
        return newInstance(dateTime.atZone(ZoneId.of("UTC")));
    }

    public static FixedClockProvider newInstance(LocalDateTime dateTime, ZoneId zoneId){
        return newInstance(dateTime.atZone(zoneId));
    }

    public static FixedClockProvider newInstance(Instant instant, ZoneId zoneId){
        return new FixedClockProvider(Clock.fixed(instant, zoneId));
    }

    private FixedClockProvider(Clock clock){
        this.clock = clock;
    }
    @Override
    public Clock clock() {
        return clock;
    }
}
