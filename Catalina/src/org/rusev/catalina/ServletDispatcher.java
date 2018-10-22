package org.rusev.catalina;

import org.rusev.catalina.servlet.*;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.HttpStatus;
import org.softuni.javache.io.Reader;
import org.softuni.javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/*
This will be our RequestHandler
When this class recieves a request it searches for a servlet
 */
public class ServletDispatcher implements RequestHandler {
    private boolean hasIntercepted;

    private Map<String, HttpServlet> servletMap;

    public ServletDispatcher() {
        this.hasIntercepted = false;
        this.servletMap = new HashMap<>();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream) {
        try {
            HttpServletRequest request = new HttpServletRequestImpl(Reader.readAllLines(inputStream), inputStream);
            HttpServletResponse response = new HttpServletResponseImpl(outputStream);

            response.setStatusCode(HttpStatus.OK);

            request.addHeader("Content-Type", "text/html");

            response.setContent("Hi from Catalina".getBytes());

            Writer.writeBytes(response.getBytes(), response.getOutputStream());

            this.hasIntercepted = true;
        }catch (IOException e) {
            e.printStackTrace();
            this.hasIntercepted = false;
        }
    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}