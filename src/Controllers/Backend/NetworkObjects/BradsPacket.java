package Controllers.Backend.NetworkObjects;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Brad Kent
 * @author n10632999@qut.edu.au
 * @version 1.0
 * @since 0.1
 */

public class BradsPacket implements Serializable
{
    @Serial
    private static final long serialVersionUID = 6577942131260991750L;

    private static int packetNo;

    public BradsPacket()
    {

    }

    @Override
    public String toString()
    {
        packetNo++;
        return "Brads Awesome Packet :)" + packetNo;
    }
}
