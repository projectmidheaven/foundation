package org.midheaven.culture;

import org.junit.jupiter.api.Test;
import org.midheaven.lang.ParsingException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CultureTestCases {

    @Test
    public void parseCulture(){
        assertEquals(LanguageCode.PT.culture(),  Culture.parse("pt"));
        assertEquals(LanguageCode.PT.in(CountryCode.parse("BR")),  Culture.parse("pt-BR"));
        assertEquals(CountryCode.parse("BR").speaking(LanguageCode.PT),  Culture.parse("pt-BR"));
        assertEquals(CountryCode.parse("BR").speaking(LanguageCode.PT),  Culture.parse("pt_BR"));

        assertThrows(ParsingException.class, () ->  Culture.parse("somethingPretty"));
        assertThrows(ParsingException.class, () ->  Culture.parse("pt=BR"));
        assertThrows(ParsingException.class, () ->  Culture.parse("PT-br"));
    }

    @Test
    public void cultureLocale(){
        assertEquals(Locale.of("pt", "BR"),  Culture.parse("pt-BR").toLocale());
        assertEquals(Locale.of("pt", "BR").toString(),  Culture.parse("pt-BR").toString());
        assertEquals(Locale.of("pt"),  Culture.parse("pt").toLocale());
    }
    
    
    @Test
    public void cultureLanguageAndCountry(){
        var culture = Culture.parse("pt-BR");
        assertEquals( LanguageCode.parse("pt"), culture.languageCode());
        assertEquals( CountryCode.parse("BR"), culture.countryCode().orNull());

    }
}
