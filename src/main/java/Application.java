import com.sun.net.httpserver.HttpsServer;

import java.net.InetSocketAddress;

public class Application {
    public static void main(String[] args) throws Exception {
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/hello", new Hello());
        server.setExecutor(null);

        server.start();


    }
}
