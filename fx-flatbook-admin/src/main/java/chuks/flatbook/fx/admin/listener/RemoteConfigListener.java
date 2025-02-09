/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.admin.listener;

/**
 *
 * @author user
 */
public interface RemoteConfigListener {
    void onWhitelistedIPs(String[] IPs);
    void onBlacklistedIPs(String[] IPs);
    void onMaxConnecionPerIP(int max);
    void onMaxRequestPerSecondPerIP(int max);

}
