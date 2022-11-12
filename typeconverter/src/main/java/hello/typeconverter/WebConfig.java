package hello.typeconverter;

import hello.typeconverter.Converter.IntegerToStringConverter;
import hello.typeconverter.Converter.IpPortToStringConverter;
import hello.typeconverter.Converter.StringToIntegerConverter;
import hello.typeconverter.Converter.StringToIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
        // Disable converter because converter>formatter
        //registry.addConverter(new StringToIntegerConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addFormatter(new MyNumberFormatter());

    }
}
