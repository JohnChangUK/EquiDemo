package lambda.handler;

import com.amazonaws.serverless.proxy.internal.testutils.Timer;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import controller.BookController;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import service.BookService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GetBooksHandler implements RequestStreamHandler {

    private static final ResourceConfig jerseyApp = new ResourceConfig()
            .packages("controller")
            .packages("lambda.handler")
            .register(new BookController(new BookService()))
            .register(JacksonFeature.class);

    private static final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler =
            JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApp);

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }

    public GetBooksHandler() {
        Timer.enable();
    }
}
