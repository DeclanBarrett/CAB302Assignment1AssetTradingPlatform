package App_Start;

import org.junit.jupiter.api.Test;

public class TestSetupServer {

    @Test
    public void TestSetupServer() {
        SetupServer setupServer = new SetupServer();
        setupServer.setsUpTheServer();
    }
}
