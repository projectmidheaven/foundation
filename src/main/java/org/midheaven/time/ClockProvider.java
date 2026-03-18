package org.midheaven.time;

import org.midheaven.lang.NotNullable;

import java.time.Clock;

public interface ClockProvider {

    @NotNullable Clock clock();
}
