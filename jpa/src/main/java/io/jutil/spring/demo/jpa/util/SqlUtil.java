package io.jutil.spring.demo.jpa.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2023-04-17
 */
@Slf4j
@Component
public class SqlUtil implements InitializingBean {
    private static final String JDBC_SEP = ":";

    @Autowired
    DataSourceProperties dataSourceProperties;

    private String jsonType;

    public String getJsonType() {
        return jsonType;
    }


    @Override
    public void afterPropertiesSet() {
        var dbType = this.deleteDbType();
        this.detectJsonType(dbType);
    }

    private String deleteDbType() {
        var url = dataSourceProperties.getUrl();
        var startPos = 5;
        var endPos = url.indexOf(JDBC_SEP, startPos);
        var type = url.substring(startPos, endPos);
        log.info("Database type: {}", type);
        return type;
    }

    private void detectJsonType(String dbType) {
        this.jsonType = switch (dbType) {
            case "postgresql" -> "jsonb";
            default -> "json";
        };
    }
}
