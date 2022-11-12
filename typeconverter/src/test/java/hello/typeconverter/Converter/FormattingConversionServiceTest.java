package hello.typeconverter.Converter;

import hello.typeconverter.formatter.MyNumberFormatter;
import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

class FormattingConversionServiceTest {

    @Test
    void formattingConversionTest() {
        // Registration
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        // Converter registration
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        // Formatter registration
        conversionService.addFormatter(new MyNumberFormatter());


        // Converter Usage 1
        String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(ipPortString).isEqualTo("127.0.0.1:8080");
        // Converter Usage 2
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
        // Formatter Usage 1
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        // Formatter Usage 2
        assertThat(conversionService.convert("1000", Long.class)).isEqualTo(1000L);

    }
}