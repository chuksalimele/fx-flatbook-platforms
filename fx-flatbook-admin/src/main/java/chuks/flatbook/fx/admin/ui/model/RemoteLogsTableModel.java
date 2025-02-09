/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.ui.model;

import chuks.flatbook.fx.admin.listener.RemoteLogListener;
import chuks.flatbook.fx.common.util.log.LogEntry;
import chuks.flatbook.fx.common.util.log.LogLevel;
import chuks.flatbook.fx.common.util.log.LogReader;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class RemoteLogsTableModel extends AbstractTableModel implements RemoteLogListener {

    private final String timestamp = "Time";
    private final String thread = "Thread";
    private final String level = "Level";
    private final String marker = "Marker";
    private final String logger = "Logger";
    private final String message = "Message";
    private final String throwable = "Throwable";

    protected String[] columnNames = {timestamp, thread, level, marker, logger, message, throwable};
    protected LinkedList<LogEntry> logEntryList = new LinkedList<>();
    private final LogLevel logLevel;

    public RemoteLogsTableModel(LogLevel logLevel) {
        this.logLevel = logLevel;
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

        switch (columnIndex) {
            case 0 -> {
                return log.getTimestamp();
            }
            case 1 -> {
                return log.getThread();
            }
            case 2 -> {
                return log.getLevel();
            }
            case 3 -> {
                return log.getMarker();
            }
            case 4 -> {
                return log.getLogger();
            }
            case 5 -> {
                return log.getMessage();
            }
            case 6 -> {
                return log.getThrowable();
            }
        }

        return null;
    }

    @Override
    public void onDebugLogs(String[] records) {
        if (logLevel != LogLevel.DEBUG) {
            return;
        }
        fireLogEntries(records);
    }

    @Override
    public void onInfoLogs(String[] records) {
        if (logLevel != LogLevel.INFO) {
            return;
        }
        fireLogEntries(records);
    }

    @Override
    public void onWarnLogs(String[] records) {
        if (logLevel != LogLevel.WARN) {
            return;
        }
        fireLogEntries(records);
    }

    @Override
    public void onErrorLogs(String[] records) {
        if (logLevel != LogLevel.ERROR) {
            return;
        }
        fireLogEntries(records);
    }

    private void fireLogEntries(String[] records) {
        logEntryList.clear();
        LogReader.readLogRecords(records, logEntryList);
        fireTableRowsInserted(0, records.length-1);        
    }

}
