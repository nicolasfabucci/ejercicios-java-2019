package com.eiv.server;

import java.sql.Connection;

public interface ConnectionProvider {

    public Connection getConnection();
}
