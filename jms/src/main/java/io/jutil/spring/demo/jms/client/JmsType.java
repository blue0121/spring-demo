package io.jutil.spring.demo.jms.client;

/**
 * @author Jin Zheng
 * @since 2023-04-23
 */
public enum JmsType {
    QUEUE,
    TOPIC,
    ;

    public static JmsType getType(String strType) {
        for (var type : JmsType.values()) {
            if (type.name().equalsIgnoreCase(strType)) {
                return type;
            }
        }
        return null;
    }
}
