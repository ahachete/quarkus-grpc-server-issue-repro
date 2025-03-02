package es.aht.tmp;

import io.quarkus.grpc.GrpcClient;
import org.jboss.logging.Logger;
import picocli.CommandLine.Command;


@Command(name = "greeting", mixinStandardHelpOptions = true)
public class GreetingCommand implements Runnable {
    private static final Logger LOG = Logger.getLogger(GreetingCommand.class);

    @GrpcClient("test")
    TestService testService;

    @Override
    public void run() {
        var request = Main.Request.newBuilder().setId("as").build();
        var response = testService.aMethod(request);
        response.subscribe().with(
                item -> LOG.info(item.getId()),
                error -> LOG.error(error.getMessage())
        );
    }
}
