package com.voidmirror.playerremotecontrol;

import java.io.IOException;

public class LinkExecutor implements Executor {


    @Override
    public String execute(String link) {
        try {
            // TODO: check link by regexp (youtube.com / youtu.be)
            Runtime.getRuntime().exec("explorer " + link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
