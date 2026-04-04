package org.midheaven.time;

import java.time.Clock;

/**
 * Provides a {@link Clock} matching the system's clock and time zone
 */
public final class MachineClockProvider implements ClockProvider{

    /**
     * {@inheritDoc}
     */
    @Override
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
