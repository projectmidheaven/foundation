package org.midheaven.time;

import org.midheaven.lang.NotNullable;

import java.time.Clock;

/**
 * Provides a reference {@link Clock}.
 */
public interface ClockProvider {
    
    /**
     * Returns the reference {@link Clock}
     * @return
     */
    @NotNullable Clock clock();
}
