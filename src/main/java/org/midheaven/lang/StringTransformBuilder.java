package org.midheaven.lang;


/**
 * Builder for String Transform instances.
 */
public interface StringTransformBuilder extends Strings.Transform {
    
    /**
     * Performs thenReplace.
     * @param match the match value
     * @param replacement the replacement value
     * @return the result of thenReplace
     */
    StringTransformBuilder thenReplace(char match, char replacement);
    /**
     * Performs thenReplaceAll.
     * @param match the match value
     * @param replacement the replacement value
     * @return the result of thenReplaceAll
     */
    StringTransformBuilder thenReplaceAll(CharSequence match, CharSequence replacement);
    /**
     * Performs thenTrim.
     * @return the result of thenTrim
     */
    StringTransformBuilder thenTrim();
    /**
     * Performs thenLowerCase.
     * @return the result of thenLowerCase
     */
    StringTransformBuilder thenLowerCase();
    /**
     * Performs thenRaiseCase.
     * @return the result of thenRaiseCase
     */
    StringTransformBuilder thenRaiseCase();
    /**
     * Performs thenRemoveAllNumerics.
     * @return the result of thenRemoveAllNumerics
     */
    StringTransformBuilder thenRemoveAllNumerics();
    /**
     * Performs thenRetainNumericsOnly.
     * @return the result of thenRetainNumericsOnly
     */
    StringTransformBuilder thenRetainNumericsOnly();
    /**
     * Performs thenRemoveAllAlphabetic.
     * @return the result of thenRemoveAllAlphabetic
     */
    StringTransformBuilder thenRemoveAllAlphabetic();
    /**
     * Performs thenRetainAlphabeticOnly.
     * @return the result of thenRetainAlphabeticOnly
     */
    StringTransformBuilder thenRetainAlphabeticOnly();
    /**
     * Performs thenRemoveAllSymbols.
     * @return the result of thenRemoveAllSymbols
     */
    StringTransformBuilder thenRemoveAllSymbols();
    /**
     * Performs thenRemoveAllSymbolsExcept.
     * @param symbols the symbols value
     * @return the result of thenRemoveAllSymbolsExcept
     */
    StringTransformBuilder thenRemoveAllSymbolsExcept(char ... symbols);
    /**
     * Performs thenRetainSymbolsOnly.
     * @return the result of thenRetainSymbolsOnly
     */
    StringTransformBuilder thenRetainSymbolsOnly();

}
