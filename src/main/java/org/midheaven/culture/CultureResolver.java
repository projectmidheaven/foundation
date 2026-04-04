package org.midheaven.culture;


public interface CultureResolver {

    /**
     * @return the {@link Culture} of interest in the context
     */
    Culture resolveCulture();
}
