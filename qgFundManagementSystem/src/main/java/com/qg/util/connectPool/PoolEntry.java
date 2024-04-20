package com.qg.util.connectPool;

import java.sql.Connection;

public class PoolEntry {

    private Connection conn;
    private long useStartTime;

    public Connection getConn() {
        return conn;
    }



    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public long getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(long useStartTime) {
        this.useStartTime = useStartTime;
    }

    public PoolEntry(Connection conn, long useStartTime) {
        super();
        this.conn = conn;
        this.useStartTime = useStartTime;
    }
}
