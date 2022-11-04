package hello.core.lifecycle;

import javax.management.remote.JMXServerErrorException;

public class NetworkClient {
    private  String url;

    public NetworkClient() {
        System.out.println("constructor... url = " + url);
        connect();
        call("Initialize message");
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
}
