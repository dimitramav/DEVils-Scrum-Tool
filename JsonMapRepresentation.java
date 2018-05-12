package ys09.api;

import com.google.gson.Gson;
import org.restlet.data.MediaType;
import org.restlet.representation.WriterRepresentation;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class JsonMapRepresentation extends WriterRepresentation {

    private final Map map;

    public JsonMapRepresentation(Map map) {
        // Invoke json data
        super(MediaType.APPLICATION_JSON);
        this.map = map;
    }

    @Override
    public void write(Writer writer) throws IOException {
        Gson gson = new Gson();
        writer.write(gson.toJson(map));
    }
}
