package io.jutil.spring.demo.jpa.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author Jin Zheng
 * @since 2023-04-17
 */
@ExtendWith(MockitoExtension.class)
public class SqlUtilTest {
    @Mock
    DataSourceProperties prop;

    @InjectMocks
    SqlUtil sqlUtil;

    @CsvSource({"jdbc:postgresql://localhost:5432/mgmt,jsonb",
            "jdbc:h2:mem:testdb,json"})
    @ParameterizedTest
    public void testGetJsonType(String url, String type) {
        Mockito.when(prop.getUrl()).thenReturn(url);
        sqlUtil.afterPropertiesSet();
        Assertions.assertEquals(type, sqlUtil.getJsonType());
    }
}
