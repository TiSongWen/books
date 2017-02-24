package tomcat.async;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by tisong on 2/24/17.
 */
@WebServlet(value = "/asyncservlet", asyncSupported = true)
public class AsyncServleet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();

        asyncContext.addListener(new AppListener());
        asyncContext.setTimeout(200000);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) req.getServletContext().getAttribute("executor");
        executor.execute(new AsyncRequestProcessor(asyncContext));

        System.out.println("完成");
       // req.getRequestDispatcher("/index.jsp").forward(req, resp);
        return ;
    }
}
