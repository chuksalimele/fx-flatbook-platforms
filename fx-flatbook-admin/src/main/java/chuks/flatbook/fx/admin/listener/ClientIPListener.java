/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.admin.listener;

import chuks.flatbook.fx.common.account.profile.ClientIPInfo;
import java.util.List;

/**
 *
 * @author user
 */
public interface ClientIPListener {
    
    void onConnectedWhitelistedIP(ClientIPInfo ipInfo);
    void onConnectedBlacklistedIP(ClientIPInfo ipInfo);    
    void onDiconnectedWhitelistedIP(ClientIPInfo ipInfo);
    void onDiconnectedBlacklistedIP(ClientIPInfo ipInfo);
    void onWhitelistedIPList(List<ClientIPInfo> ipInfoList);
    void onBlacklistedIPList(List<ClientIPInfo> ipInfoList);
}
