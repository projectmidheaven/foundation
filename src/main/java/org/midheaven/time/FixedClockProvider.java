package org.midheaven.time;

import org.midheaven.lang.NotNullable;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provider for Fixed Clock instances.
 */
public class FixedClockProvider implements org.midheaven.time.ClockProvider {

    private final Clock clock;

    /**
     * Creates new Instance.
     * @param dateTime the dateTime value
     * @return the result of newInstance
     */
    public static @NotNullable FixedClockProvider newInstance(ZonedDateTime dateTime){
        return newInstance(dateTime.toInstant(), dateTime.getZone());
    }

    /**
     * Creates new Instance At Utc.
     * @param dateTime the dateTime value
     * @return the result of newInstanceAtUtc
     */
    public static @NotNullable FixedClockProvider newInstanceAtUtc(LocalDateTime dateTime){
        return newInstance(dateTime.atZone(ZoneId.of("UTC")));
    }

    /**
     * Creates new Instance.
     * @param dateTime the dateTime value
     * @param zoneId the zoneId value
     * @return the result of newInstance
     */
    public static @NotNullable FixedClockProvider newInstance(LocalDateTime dateTime, ZoneId zoneId){
        return newInstance(dateTime.atZone(zoneId));
    }

    /**
     * Creates new Instance.
     * @param instant the instant value
     * @param zoneId the zoneId value
     * @return the result of newInstance
     */
    public static @NotNullable FixedClockProvider newInstance(Instant instant, ZoneId zoneId){
        return new FixedClockProvider(Clock.fixed(instant, zoneId));
    }

    /**
     * Performs FixedClockProvider.
     * @param clock the clock value
     * @return the result of FixedClockProvider
     */
    private FixedClockProvider(Clock clock){
        this.clock = clock;
    }
    /**
     * Performs clock.
     * @return the result of clock
     */
    @Override
    /**
     * Performs clock.
     * @return the result of clock
     */
    public Clock clock() {
        return clock;
    }
}
