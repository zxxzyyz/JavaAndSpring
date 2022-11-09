package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourcesTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageV1() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null)).isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageV2() {
        assertThat(ms.getMessage("no_code", null,"defaultMessage", null)).isEqualTo("defaultMessage");
    }

    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, Locale.KOREA);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    void noLangMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, Locale.ENGLISH);
        assertThat(result).isEqualTo("Hello Spring");
    }
}
