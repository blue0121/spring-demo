package io.jutil.spring.demo.jpa.util;

import com.alibaba.fastjson2.JSON;
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

    private static String dbType;
    private static String jsonType;

    public static String getJsonType() {
        return jsonType;
    }

    public static String getJsonString(String str) {
        return switch (dbType) {
            case "h2" -> JSON.parseObject(str, String.class);
            default -> str;
        };
    }

    @Override
    public void afterPropertiesSet() {
        dbType = this.deleteDbType();
        this.detectJsonType();
    }

    private String deleteDbType() {
        var url = dataSourceProperties.getUrl();
        var startPos = 5;
        var endPos = url.indexOf(JDBC_SEP, startPos);
        var type = url.substring(startPos, endPos);
        log.info("Database type: {}", type);
        return type;
    }

    private void detectJsonType() {
        this.jsonType = switch (dbType) {
            case "postgresql" -> "jsonb";
            default -> "json";
        };
    }
}
