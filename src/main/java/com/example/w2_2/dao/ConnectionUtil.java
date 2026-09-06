package com.example.w2_2.dao;
/* DAO가 DB에 접근하기 위해 사용하는 도구 */
/* DAO가 많다면 각각 DB연결 코드를 반복해야 하기에, DB 연결 설정을 한 곳에 모아놓은 것 */
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection; // Java에서 DB 연결을 표현하는 객체

public enum ConnectionUtil {

    INSTANCE; // 하나의 ConnectionUtil 객체를 만들어서 사용
              // -> DAO에서 ConnectionUtil.INSTANCE 라고 접근할 수 있음

    private HikariDataSource ds; // DB연결을 필요할 때 빌려주는 관리자

    // DAO "Connection 하나 빌려주세요."
    // HikariCP "작업 끝나면 반환해." (-> Connection Pool 라이브러리)

    ConnectionUtil() { /* 생성자 */
        HikariConfig config = new HikariConfig();
        // HikariConfig 생성 -> DB 연결 정보 설정 -> HikariDataSource 생성

        config.setDriverClassName("org.mariadb.jdbc.Driver"); // "MariaDB에 연결할 때 MariaDB JDBC Driver를 사용해."
        config.setJdbcUrl("jdbc:mariadb://localhost:3307/webdb"); // DB의 주소
        config.setUsername("webuser"); // DB 로그인 정보
        config.setPassword("1234");
        config.addDataSourceProperty("cachePrepStmts", "true"); // 연결을 좀 더 효율적으로 사용하기 위한 설정
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("preStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config); // ★ 앞에서 만든 설정을 이용해서 HikariDataSource를 실제로 생성 ★
    }

    public Connection getConnection() throws Exception {
        return ds.getConnection();
        // DAO에서 사용, "HikariDataSource에서 COnnection 하나 가져와서 주세요."
    }
}
