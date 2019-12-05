package controller.servlets;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

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
}
