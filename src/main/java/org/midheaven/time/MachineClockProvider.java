package org.midheaven.time;

import java.time.Clock;

/**
 * Provider for Machine Clock instances.
 */
public final class MachineClockProvider implements ClockProvider{

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
        return Clock.systemDefaultZone();
    }
}
