package com.epam.jmp.chat;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ServerEndpoint("/chat/msg")
public class Chat implements DataAware {

    @OnOpen
    public void connect(Session session) {
        MessageProcessor processor = new MessageProcessor(session.getAsyncRemote());
        processors.put(session.getId(), processor);
        users.put(session.getId(), session.getRequestParameterMap().get("user").stream().collect(Collectors.joining()));
        messageLog.put(LocalDateTime.now(),"<b>" + users.get(session.getId()) + "</b> connected.");
        new Thread(processor).start();
    }

    @OnMessage
    public void send(String message, Session session) {
        messageLog.put(LocalDateTime.now(),"<b>" + users.get(session.getId()) + "</b>: " + message);
    }

    @OnClose
    public void disconnect(CloseReason reason, Session session) {
        MessageProcessor processor = processors.get(session.getId());
        processor.stop();
        messageLog.put(LocalDateTime.now(),"<b>" + users.get(session.getId()) + "</b> disconnected.");
        processors.remove(session.getId());
        users.remove(session.getId());
    }

}
