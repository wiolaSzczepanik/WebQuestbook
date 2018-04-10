import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Application {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/hello", new Hello());
        server.createContext("/forum", new Forum());
        server.createContext("/message", new Message());


        server.setExecutor(null);

        server.start();


    }
}
