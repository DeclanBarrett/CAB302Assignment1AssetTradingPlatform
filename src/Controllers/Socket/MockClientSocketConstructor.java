package Controllers.Socket;

import Models.IDataSource;

public class MockClientSocketConstructor {

    IDataSource dataSource;

    public MockClientSocketConstructor() {
    }

    public MockClientSocketConstructor(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public IDataSource GetDataSource() {
        return dataSource;
    }
}
