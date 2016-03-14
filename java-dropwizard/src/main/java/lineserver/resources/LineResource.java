package lineserver.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.ws.http.HTTPException;

import io.dropwizard.jersey.params.LongParam;
import lineserver.dao.LineDAO;
import org.bson.Document;

import java.util.Optional;

@Path("/line/{lineNumber}")
public class LineResource {

    private final LineDAO lineDAO;

    public LineResource(LineDAO lineDAO) {
        this.lineDAO = lineDAO;
    }

    @GET
    public String getLine(@PathParam("lineNumber") LongParam lineNumber) {
        return safeFind(lineNumber.get());
    }

    private String safeFind(long lineNumber) {
        final Optional<Document> line = lineDAO.findByLineNumber(lineNumber);
        if (!line.isPresent()) {
            throw new HTTPException(413);
        }
        return line.get().getString("value");
    }
}
