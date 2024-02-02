package org.midheaven.time;

import java.time.Clock;

public final class MachineClockProvider implements ClockProvider{

    @Override
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}