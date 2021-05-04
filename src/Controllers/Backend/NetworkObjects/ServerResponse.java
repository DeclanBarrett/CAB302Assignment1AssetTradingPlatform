package Controllers.Backend.NetworkObjects;

import java.io.Serializable;

/**
 * A generic response containing a message from the server regarding
 * an insert or create where it is not expected to return any values
 */
public class ServerResponse implements Serializable {
    private String response;

    public ServerResponse(String response) {
        this.response = response;
    }

    public String GetResponse() {
        return response;
    }
}
