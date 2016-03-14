package lineserver;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lineserver.dao.LineDAO;
import lineserver.resources.LineResource;

public class LineserverApplication extends Application<LineserverConfiguration> {
    public static void main(String[] args) throws Exception {
        new LineserverApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<LineserverConfiguration> bootstrap) {

        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

    }

    @Override
    public String getName() {
        return "lineservice";
    }

    @Override
    public void run(LineserverConfiguration configuration, Environment environment) {
        final LineDAO dao = new LineDAO(configuration.getMongoServer(), configuration.getMongoDatabase(), configuration.getMongoCollection());
        environment.jersey().register(new LineResource(dao));
    }
}
