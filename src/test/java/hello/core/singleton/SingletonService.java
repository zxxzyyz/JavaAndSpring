package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }
}
