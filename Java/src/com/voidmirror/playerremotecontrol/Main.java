package com.voidmirror.playerremotecontrol;

public class Main {
    public static void main(String[] args) {
        PlayerController playerController = new PlayerController();
        Server server = new Server(playerController);
    }
}
