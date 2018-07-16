import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Message implements HttpHandler {

    List<Map> messages = new ArrayList<Map>();

    public void handle(HttpExchange httpExchange) throws IOException {


        String text_response = "";
        String method = httpExchange.getRequestMethod();


        if (method.equals("GET")) {
            text_response = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"static/css/style.css\"></head><body><h1>GUESTBOOK</h1><h2>";
            int i = 0;
            text_response = displayAllMessegesInCollection(text_response, i);
            text_response += "</h2><br><form method=\"POST\">\n" +
                    "  Message:<br>\n" +
                    "  <input type=\"text\" name=\"message\" placeholder=\"TEXT\">\n" +
                    "  <br>\n" +
                    "  Name:<br>\n" +
                    "  <input type=\"text\" name=\"name\" placeholder=\"YOUR NAME\">\n" +
                    "  <br><br>\n" +
                    "  <input type=\"submit\" value=\"Submit\">\n" +
                    "</form> " +
                    "</body></html>";

        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = parseFormData(formData);
            System.out.println("inputs" + inputs);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            inputs.put("date", dateFormat.format(date));
            messages.add(inputs);

            text_response = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"static/css/style.css\"></head><body><h1>GUESTBOOK</h1><h2>";
            int i = 0;
            text_response = displayAllMessegesInCollection(text_response, i);
            text_response += "</h2><br><form method=\"POST\">\n" +
                        "  Message:<br>\n" +
                        "  <input type=\"text\" name=\"message\" value=\"TEXT\">\n" +
                        "  <br>\n" +
                        "  Name:<br>\n" +
                        "  <input type=\"text\" name=\"name\" value=\"Your name\">\n" +
                        "  <br><br>\n" +
                        "  <input type=\"submit\" value=\"Submit\">\n" +
                        "</form> " +
                        "</body></html>";
        }
        httpExchange.sendResponseHeaders(200, text_response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(text_response.getBytes());
        os.close();
    }

    private String displayAllMessegesInCollection(String text_response, int i) {
        while (i < messages.size()) {
            text_response +="Message: " + messages.get(i).get("message");
            text_response += "<br>Name: " + messages.get(i).get("name");
            text_response += "<br>Date: " + messages.get(i).get("date") + "<br><br>";
                i++;
            }
        return text_response;
    }


    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
