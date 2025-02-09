/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.admin.listener;

/**
 *
 * @author user
 */
public interface RemoteLogListener {
    void onDebugLogs(String[] records);
    void onInfoLogs(String[] records);
    void onWarnLogs(String[] records);
    void onErrorLogs(String[] records);
}
