package org.midheaven.time;

import org.midheaven.lang.NotNullable;

import java.time.Clock;

/**
 * Provider for Clock instances.
 */
public interface ClockProvider {

    @NotNullable Clock clock();
}
