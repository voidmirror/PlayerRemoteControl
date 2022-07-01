package com.voidmirror.playerremotecontrol;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import javax.xml.transform.Source;

public class NetSearch {

    private Context context;

    public NetSearch(Context context) {
        this.context = context;
    }

    private String getSubnetAddress(int address)
    {
        String ipString = String.format(
                "%d.%d.%d",
                (address & 0xff),
                (address >> 8 & 0xff),
                (address >> 16 & 0xff));

        return ipString;
    }

    public void search() {

        System.out.println("### START SEARCH ###");
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String subnet = getSubnetAddress(wifiManager.getDhcpInfo().gateway);
        System.out.println("gateway " + wifiManager.getDhcpInfo().gateway);
        System.out.println("ipadress " + wifiManager.getDhcpInfo().ipAddress);
        System.out.println("serveradress " + wifiManager.getDhcpInfo().serverAddress);
        System.out.println("Subnet " + subnet);
        System.out.println("### FINISH SEARCH ###");


    }

}
