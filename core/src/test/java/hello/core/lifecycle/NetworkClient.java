package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.remote.JMXServerErrorException;

public class NetworkClient {
    private  String url;

    public NetworkClient() {
        System.out.println("constructor... url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect... url = " + url);
    }

    public void call(String message) {
        System.out.println("call... url = " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("disconnect... url = " + url);
    }

    @PreDestroy
    public void close() throws Exception {
        disconnect();
    }

    @PostConstruct
    public void init() throws Exception {
        connect();
        call("Initialize message");
    }
}
