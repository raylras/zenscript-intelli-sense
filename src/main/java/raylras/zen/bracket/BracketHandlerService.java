package raylras.zen.bracket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BracketHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(BracketHandlerService.class);

    private static List<BracketHandlerMirror> bracketHandlerMirrors;

    public static BracketHandlerEntry queryEntryDynamic(String raw) {
        Map<String, Object> properties;
        try {
            properties = RpcClient.queryEntryDynamic(raw);
        } catch (ConnectException e) {
            logger.warn("Failed to query <{}>, make sure your Minecraft instance is running", raw);
            properties = Collections.emptyMap();
        } catch (Exception e) {
            logger.error("Failed to query <{}>: {}", raw, e.getMessage());
            properties = Collections.emptyMap();
        }
        return new BracketHandlerEntry(properties);
    }

}
