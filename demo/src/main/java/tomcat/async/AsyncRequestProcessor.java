package tomcat.async;

import javax.servlet.AsyncContext;

/**
 * Created by tisong on 2/24/17.
 */
public class AsyncRequestProcessor implements Runnable {


    private AsyncContext asyncContext;

    public AsyncRequestProcessor(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {

        System.out.println("i'm runing");
        asyncContext.complete();
    }
}
