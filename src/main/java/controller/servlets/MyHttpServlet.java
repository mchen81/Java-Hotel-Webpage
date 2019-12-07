package controller.servlets;

import com.google.gson.stream.JsonReader;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class MyHttpServlet extends HttpServlet {

    private VelocityContext context;

    private VelocityEngine engine;

    private Template template;

    private StringWriter stringWriter;

    private static final String prefix = "static/html/";
    private static final String suffix = ".html";

    protected void initVelocityEngine(HttpServletRequest request) {
        context = new VelocityContext();
        engine = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
        stringWriter = new StringWriter();
    }

    protected void setBasicHtmlResponse(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void setJsonResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void setReturnHtml(String htmlFileName) {
        template = engine.getTemplate(prefix + htmlFileName + suffix);
    }

    protected void addAttribute(String attribute, Object object) {
        context.put(attribute, object);
    }

    protected String getOutput() {
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    protected void outPutHtml(HttpServletResponse response) throws IOException {
        response.getWriter().println(getOutput());
    }

    protected Map<String, String> getAjaxRequestParameterMap(BufferedReader bufferedReader) throws IOException {
        Map<String, String> parameterMap = new HashMap<>();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        JsonReader jsonReader = new JsonReader(new StringReader(stringBuilder.toString()));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            parameterMap.put(jsonReader.nextName(), jsonReader.nextString());
        }
        jsonReader.endObject();

        return parameterMap;
    }

}
