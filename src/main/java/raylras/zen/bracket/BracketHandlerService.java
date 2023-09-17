package raylras.zen.bracket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.Collections;
import java.util.Map;

public class BracketHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(BracketHandlerService.class);

    public static BracketHandlerEntity queryEntity(String raw) {
        Map<String, String> properties;
        try {
            properties = RpcClient.queryEntityDynamic(raw);
        } catch (ConnectException e) {
            logger.warn("Failed to query <{}>, make sure your Minecraft instance is running", raw);
            properties = Collections.emptyMap();
        } catch (Exception e) {
            logger.error("Failed to query <{}>: {}", raw, e.getMessage());
            properties = Collections.emptyMap();
        }
        return new BracketHandlerEntity(properties);
    }

}
