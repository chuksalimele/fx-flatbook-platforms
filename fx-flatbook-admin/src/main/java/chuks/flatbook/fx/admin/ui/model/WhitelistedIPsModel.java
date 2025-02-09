/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.ui.model;

import chuks.flatbook.fx.admin.listener.ClientIPListener;
import chuks.flatbook.fx.common.account.profile.ClientIPInfo;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class WhitelistedIPsModel extends AbstractTableModel implements ClientIPListener {

    private final String ip = "IP";
    private final String accountNumber = "Acct. Number";    
    private final String accountName = "Acct. Name";
    private final String handwareInfo = "hardware";        
    private final String LastSeen = "Last Seen";

    protected String[] columnNames = {ip, accountNumber, accountName, handwareInfo, LastSeen};
    protected LinkedList<ClientIPInfo> clientIPInfoList = new LinkedList<>();

    @Override
    public int getRowCount() {
        return clientIPInfoList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientIPInfo ipInfo = clientIPInfoList.get(rowIndex);


        switch (columnIndex) {
            case 0 -> {
                return ipInfo.getIp();
            }
            case 1 -> {
                return ipInfo.getUserID();
            }
            case 2 -> {
                return ipInfo.getUserFullName();
            }
            case 3 -> {
                return ipInfo.getOriginHardwareInfo();
            }
            case 4 -> {
                return ipInfo.getLastSeenSecondsAgo();
            }
        }

        return null;        
    }
    void updateRow(ClientIPInfo ipInfo){
        for( int i=0; i < clientIPInfoList.size(); i++){
            if(clientIPInfoList.get(i).getIp().equals(ipInfo.getIp())){
                clientIPInfoList.set(i, ipInfo);
                fireTableRowsUpdated(i, i);
                break;
            }
        }        
    }
    @Override
    public void onConnectedWhitelistedIP(ClientIPInfo ipInfo) {
        updateRow(ipInfo);
    }

    @Override
    public void onConnectedBlacklistedIP(ClientIPInfo ipInfo) {
    }

    @Override
    public void onDiconnectedWhitelistedIP(ClientIPInfo ipInfo) {
        updateRow(ipInfo);
    }

    @Override
    public void onDiconnectedBlacklistedIP(ClientIPInfo ipInfo) {
    }

    @Override
    public void onWhitelistedIPList(List<ClientIPInfo> ipInfoList) {
        clientIPInfoList.clear();
        clientIPInfoList.addAll(ipInfoList);
        fireTableRowsInserted(0, ipInfoList.size() - 1);
    }

    @Override
    public void onBlacklistedIPList(List<ClientIPInfo> ipInfoList) {
    }
    
}
