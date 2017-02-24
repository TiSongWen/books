package tomcat.async;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tisong on 2/24/17.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final String EXECUTOR_KEY = AppContextListener.class.getName();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        servletContextEvent.getServletContext().setAttribute("executor", executor);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) servletContextEvent.getServletContext().getAttribute(EXECUTOR_KEY);
        executor.shutdown();
    }
}
