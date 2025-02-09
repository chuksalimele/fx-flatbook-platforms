/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.ui.model;

import chuks.flatbook.fx.admin.listener.RemoteLogListener;
import static chuks.flatbook.fx.common.util.log.LogConst.STR_SUSPICIOUS_IP;
import chuks.flatbook.fx.common.util.log.LogEntry;
import chuks.flatbook.fx.common.util.log.LogReader;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class SuspiciousIPsModel  extends AbstractTableModel implements RemoteLogListener {

    private final String timestamp = "Time";
    private final String IP = "IP";
    private final String reason = "Reason";

    protected String[] columnNames = {timestamp, IP, reason};
    protected LinkedList<LogEntry> logEntryList = new LinkedList<>();

    public SuspiciousIPsModel() {
    }

    @Override
    public int getRowCount() {
        return logEntryList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LogEntry log = logEntryList.get(rowIndex);
        String msg = log.getMessage();
        String[] split = msg.split("-");
        String _ip = split[0].trim();
        String _reason = split[1].trim();

        switch (columnIndex) {
            case 0 -> {
                return log.getTimestamp();
            }
            case 1 -> {
                return _ip;
            }
            case 2 -> {
                return _reason;
            }
        }

        return null;
    }

    @Override
    public void onDebugLogs(String[] records) {
    }

    @Override
    public void onInfoLogs(String[] records) {
        fireLogEntries(records);
    }

    @Override
    public void onWarnLogs(String[] records) {
    }

    @Override
    public void onErrorLogs(String[] records) {
    }

    private void fireLogEntries(String[] records) {
        logEntryList.clear();
        LogReader.readLogRecords(records, logEntryList);
        //confirm is for Suspicious IP
        if (!logEntryList.isEmpty()
                && STR_SUSPICIOUS_IP.equals(logEntryList.getFirst().getMarker())) {
            fireTableRowsInserted(0, records.length - 1);
        }
    }
}
