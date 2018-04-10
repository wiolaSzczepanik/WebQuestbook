import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Hello implements HttpHandler{
    public void handle(HttpExchange httpExchange) throws IOException {
        String text = "Hello World!";
        httpExchange.sendResponseHeaders(200,text.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(text.getBytes());
        outputStream.close();
    }


}
