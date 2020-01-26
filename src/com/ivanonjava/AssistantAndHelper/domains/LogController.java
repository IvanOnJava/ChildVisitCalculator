package com.ivanonjava.AssistantAndHelper.domains;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class xLogController extends Thread {
    private static final ArrayList<String> log = new ArrayList<>();
    private static xLogController instance;
    private static String nameLog;

    private xLogController() {
        nameLog = CalendarController.getNow().toString() + ".log";
        try {
            Files.createFile(Paths.get(nameLog));
        } catch (IOException ignored) {

        }
        setDaemon(true);
        start();


    }

    public static void Instantiate() {
        instance = instance == null ?
                new xLogController() : instance;
    }

    @Override
    public void run() {
        xLogController.write("LogController::start[" + nameLog + "]");
        while (true) {
            synchronized (log) {
                if (!log.isEmpty()) {
                    try {
                        Files.write(Paths.get(nameLog), log, StandardOpenOption.APPEND);
                        log.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void write(String lg) {
        synchronized (log) {
            log.add(lg + CalendarController.getTime());
        }
    }
}
