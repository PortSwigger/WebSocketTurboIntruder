package burp;

import attack.AttackHandler;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.websocket.BinaryMessage;
import burp.api.montoya.websocket.TextMessage;
import burp.api.montoya.websocket.extension.ExtensionWebSocketMessageHandler;
import connection.WebSocketConnection;
import data.WebSocketConnectionMessage;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketExtensionWebSocketMessageHandler implements ExtensionWebSocketMessageHandler
{
    private final Logging logging;
    private final AttackHandler attackHandler;
    private final WebSocketConnection connection;
    private final ExecutorService executorService;

    public WebSocketExtensionWebSocketMessageHandler(
            Logging logging,
            AttackHandler attackHandler,
            WebSocketConnection connection
    )
    {
        this.logging = logging;
        this.attackHandler = attackHandler;
        this.connection = connection;

        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void textMessageReceived(TextMessage textMessage)
    {
        WebSocketConnectionMessage websocketConnectionMessage = new WebSocketConnectionMessage(textMessage.payload(), textMessage.direction(), LocalDateTime.now(), null, connection);

        executorService.execute(() -> attackHandler.executeCallback(websocketConnectionMessage));
    }

    @Override
    public void binaryMessageReceived(BinaryMessage binaryMessage)
    {
        logging.logToOutput("Unhandled binary message received");
    }
}
