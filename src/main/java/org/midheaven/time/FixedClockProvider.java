package org.midheaven.time;

import org.midheaven.lang.NotNullable;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provides a {@link Clock} fixed at a given instant
 */
public class FixedClockProvider implements org.midheaven.time.ClockProvider {

    private final Clock clock;

    /**
     * Created a {@link FixedClockProvider} fixed at a given instant
     * @param dateTime the fixed instant as a ZonedDateTime
     * @return the resulting provider
     */
    public static @NotNullable FixedClockProvider newInstance(ZonedDateTime dateTime){
        return newInstance(dateTime.toInstant(), dateTime.getZone());
    }
    
    /**
     * Created a {@link FixedClockProvider} fixed at a given instant
     * @param dateTime the fixed instant as a LocalDateTime in UTC
     * @return the resulting provider
     */
    public static @NotNullable FixedClockProvider newInstanceAtUtc(LocalDateTime dateTime){
        return newInstance(dateTime.atZone(ZoneId.of("UTC")));
    }
    
    /**
     * Created a {@link FixedClockProvider} fixed at a given instant in a given zoneId
     * @param dateTime the fixed instant as a LocalDateTime ar the given zoneId
     * @param zoneId the reference {@code ZoneId}
     * @return the resulting provider
     */
    public static @NotNullable FixedClockProvider newInstance(LocalDateTime dateTime, ZoneId zoneId){
        return newInstance(dateTime.atZone(zoneId));
    }
    
    /**
     * Created a {@link FixedClockProvider} fixed at a given instant in a given zoneId
     * @param instant the fixed instant
     * @param zoneId the reference {@code ZoneId}
     * @return the resulting provider
     */
    public static @NotNullable FixedClockProvider newInstance(Instant instant, ZoneId zoneId){
        return new FixedClockProvider(Clock.fixed(instant, zoneId));
    }


    private FixedClockProvider(Clock clock){
        this.clock = clock;
    }
    
    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Clock clock() {
        return clock;
    }
}
