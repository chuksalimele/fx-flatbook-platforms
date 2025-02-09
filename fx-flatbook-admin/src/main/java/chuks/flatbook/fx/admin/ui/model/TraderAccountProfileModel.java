/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.ui.model;

import chuks.flatbook.fx.admin.listener.TraderAccountListener;
import chuks.flatbook.fx.common.account.profile.TraderInfo;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class TraderAccountProfileModel extends AbstractTableModel implements TraderAccountListener {

    private String accountNumber = "ID";
    private String accountName = "Name";
    private String email = "Email";
    private String emailVerifiedTime = "Email Verified At";
    private String password; // Security-sensitive field
    private String registrationTime = "Reg. Time";
    private String approvalTime = "Approv. Time";
    private String approvedBy_AdminID = "Approv. By";
    private String isLoggedIn = "Log. In";
    private String isActive = "Active";
    private String isEnabled = "Enabled";

    protected String[] columnNames = {
        accountNumber,
        accountName,
        email,
        emailVerifiedTime,
        registrationTime,
        approvalTime,
        approvedBy_AdminID,
        isLoggedIn,
        isActive,
        isEnabled};
    protected LinkedList<TraderInfo> profileList = new LinkedList<>();

    @Override
    public int getRowCount() {
        return profileList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int indexOfColumn(String col) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(col)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TraderInfo profile = profileList.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return profile.getAccountNumber();
            }
            case 1 -> {
                return profile.getAccountName();
            }
            case 2 -> {
                return profile.getEmail();
            }
            case 3 -> {
                return profile.getEmailVerifiedTime();
            }
            case 4 -> {
                return profile.getRegistrationTime();
            }
            case 5 -> {
                return profile.getApprovalTime();
            }
            case 6 -> {
                return profile.getApprovedBy();
            }
            case 7 -> {
                return profile.getIsLoggedIn();
            }
            case 8 -> {
                return profile.isActive();
            }
            case 9 -> {
                return profile.isEnabled();
            }
        }

        return null;
    }

    int getProfileIndexByEmail(String trader_email) {
        for (int i = 0; i < profileList.size(); i++) {
            if (profileList.get(i).getEmail().equals(trader_email)) {
                return i;
            }
        }
        return -1;
    }

    int getProfileIndexByAccountNumber(int account_number) {
        for (int i = 0; i < profileList.size(); i++) {
            if (profileList.get(i).getAccountNumber() == account_number) {
                return i;
            }
        }
        return -1;
    }

    TraderInfo getProfileByEmail(String trader_email) {
        int index = getProfileIndexByEmail(trader_email);
        return index != -1 ? profileList.get(index) : null;
    }

    TraderInfo getProfileByAccountNumber(int account_number) {
        int index = getProfileIndexByAccountNumber(account_number);
        return index != -1 ? profileList.get(index) : null;
    }

    @Override
    public void onRegister(TraderInfo trader) {
        profileList.add(trader);
        fireTableRowsInserted(profileList.size()-1, profileList.size()-1);
    }

    @Override
    public void onEmailVerified(String trader_email, long verified_time) {
        int row = getProfileIndexByEmail(trader_email);
        if (row != -1) {
            profileList.get(row).setEmailVerifiedTime(verified_time);
            int col = indexOfColumn(emailVerifiedTime);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void onAccountOpened(String trader_email) {
    }

    @Override
    public void onAccountClosed(String trader_email) {

    }

    @Override
    public void onAccountDeactivated(String trader_email) {
        int row = getProfileIndexByEmail(trader_email);
        if (row != -1) {
            profileList.get(row).setActive(false);
            int col = indexOfColumn(isActive);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void onAccountActivated(String trader_email) {
        int row = getProfileIndexByEmail(trader_email);
        if (row != -1) {
            profileList.get(row).setActive(true);
            int col = indexOfColumn(isActive);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void onAccountEnabled(String trader_email) {
        int row = getProfileIndexByEmail(trader_email);
        if (row != -1) {
            profileList.get(row).setEnabled(true);
            int col = indexOfColumn(isEnabled);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void onAccountDisabled(String trader_email) {
        int row = getProfileIndexByEmail(trader_email);
        if (row != -1) {
            profileList.get(row).setEnabled(false);
            int col = indexOfColumn(isEnabled);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void onAccountApproved(String trader_email, long approval_time) {
        int row = getProfileIndexByEmail(trader_email);
        if (row != -1) {
            profileList.get(row).setApprovalTime(approval_time);
            int col = indexOfColumn(approvalTime);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void onAccountApproveFail(String trader_email) {        
    }

    @Override
    public void onPaginatedTraders(List<TraderInfo> traders, int overall_total) {
        profileList.clear();
        profileList.addAll(traders);
        fireTableRowsInserted(0, profileList.size()-1);
    }

}
