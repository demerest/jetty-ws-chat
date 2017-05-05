package com.epam.jmp.chat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public interface DataAware {
    Map<LocalDateTime, String> messageLog = new HashMap<>();
    Map<String, MessageProcessor> processors = new HashMap<>();
    Map<String, String> users = new HashMap<>();
}
