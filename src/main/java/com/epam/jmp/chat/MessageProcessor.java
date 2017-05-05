package com.epam.jmp.chat;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.RemoteEndpoint;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MessageProcessor implements Runnable, DataAware {

    private boolean active = true;
    private final RemoteEndpoint.Async async;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessageProcessor(RemoteEndpoint.Async async) {
        this.async = async;
    }

    @Override
    public void run() {
        try {
            while(active) {
                String log = messageLog.entrySet()
                        .stream()
                        .sorted((e1, e2) -> (int) (e1.getKey().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - e2.getKey()
                                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                        .map(e -> "[" + e.getKey().format(DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss")) + "] " + e.getValue())
                        .collect(Collectors.joining("<br/>"));

                String activeUsers = users.values().stream().sorted().collect(Collectors.joining("<br/>"));

                async.sendText(objectMapper.writeValueAsString(new Response(log, activeUsers)));
                Thread.sleep(2000);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        active = false;
    }
}
