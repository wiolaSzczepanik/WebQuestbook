import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Forum implements HttpHandler {

    List<String> responses =  new ArrayList<String>();

    public void handle(HttpExchange httpExchange) throws IOException {

        String text_response = "";
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")){
            text_response = "<html><body>" +
                    "<form method=\"POST\">\n" +
                    "  First name:<br>\n" +
                    "  <input type=\"text\" name=\"firstname\" value=\"Mickey\">\n" +
                    "  <br>\n" +
                    "  Last name:<br>\n" +
                    "  <input type=\"text\" name=\"lastname\" value=\"Mouse\">\n" +
                    "  <br><br>\n" +
                    "  <input type=\"submit\" value=\"Submit\">\n" +
                    "</form> " +
                    "</body></html>";
        }

        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);

            text_response = "<html><body>" +
                    "<h1>Hello " +
                    inputs.get("firstname") + " " + inputs.get("lastname") +
                    "!</h1>" +
                    "</body><html>";
        }

        httpExchange.sendResponseHeaders(200, text_response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(text_response.getBytes());
        os.close();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException{
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");

            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
