package python;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.websocket.WebSockets;
import connection.Connection;
import connection.WebSocketConnection;
import data.PendingMessages;
import logger.Logger;

public class ConnectionFactory
{
    private final Logger logger;
    private final WebSockets webSockets;
    private final PendingMessages pendingMessages;
    private final int attackId;

    public ConnectionFactory(
            Logger logger,
            WebSockets webSockets,
            PendingMessages pendingMessages,
            int attackId
    )
    {
        this.logger = logger;
        this.webSockets = webSockets;
        this.pendingMessages = pendingMessages;
        this.attackId = attackId;
    }

    @SuppressWarnings("unused") // called by Python
    public Connection create(HttpRequest upgradeRequest)
    {
        return new WebSocketConnection(logger, webSockets, pendingMessages, attackId, upgradeRequest);
    }
}
