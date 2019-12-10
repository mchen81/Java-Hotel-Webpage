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

/**
 * my http servlet for all servlets to extend
 */
public abstract class MyHttpServlet extends HttpServlet {

    private VelocityContext context;

    private VelocityEngine engine;

    private Template template;

    private StringWriter stringWriter;

    private static final String prefix = "static/html/";
    private static final String suffix = ".html";

    /**
     * inititial velocity engine, if return html page is produced by velocity, must do this
     * @param request
     */
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

    /**
     * set which html in file name will return
     * @param htmlFileName
     */
    protected void setReturnHtml(String htmlFileName) {
        template = engine.getTemplate(prefix + htmlFileName + suffix);
    }

    /**
     * set which html in path will return
     * @param htmlPath
     */
    protected void setReturnHtmlPath(String htmlPath) {
        template = engine.getTemplate(htmlPath);
    }

    /**
     * add attribute to velocity context
     * @param attribute a  attribute  that for velocity access in front end
     * @param object
     */
    protected void addAttribute(String attribute, Object object) {
        context.put(attribute, object);
    }

    /**
     * get the current string in velocity
     * @return
     */
    protected String getOutput() {
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    /**
     * out put the velocity
     * @param response
     * @throws IOException
     */
    protected void outPutHtml(HttpServletResponse response) throws IOException {
        response.getWriter().println(getOutput());
    }

    /**
     * if request is from ajax, use this method to parse request's body to a map
     * @param bufferedReader request.gerReader()
     * @return a map as <parameter, value>
     * @throws IOException
     */
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

    /**
     * see if user is logged in
     * @param request
     * @return boolean
     */
    protected boolean isLoggedIn(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null || userId <= 1) {
            return false;
        } else {
            return true;
        }
    }
}
