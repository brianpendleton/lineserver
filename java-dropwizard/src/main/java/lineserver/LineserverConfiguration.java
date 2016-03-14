package lineserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;


public class LineserverConfiguration extends Configuration {
    @NotEmpty
    private String mongoServer;

    @NotEmpty
    private String mongoDatabase;

    @NotEmpty
    private String mongoCollection;

    @JsonProperty
    public String getMongoServer() {
        return this.mongoServer;
    }

    @JsonProperty
    public String getMongoDatabase() {
        return this.mongoDatabase;
    }

    @JsonProperty
    public String getMongoCollection() {
        return this.mongoCollection;
    }

    @JsonProperty
    public void getMongoServer(String server) {
        this.mongoServer = server;
    }

    @JsonProperty
    public void setMongoDatabase(String database) {
        this.mongoDatabase = database;
    }

    @JsonProperty
    public void setMongoCollection(String collection) {
        this.mongoCollection = collection;
    }

}
