/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.ui.model;

import chuks.flatbook.fx.admin.listener.AdminAccountListener;
import chuks.flatbook.fx.common.account.profile.AdminInfo;
import chuks.flatbook.fx.common.util.log.LogEntry;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class AdminProfileModel  extends AbstractTableModel implements AdminAccountListener {
    private String accountNumber = "ID";
    private String accountName = "Name";
    private String email = "Email";
    private String password; // Security-sensitive field
    private String registrationTime = "Reg. Time";
    private String approvalTime = "Approv. Time";
    private String approvedBy_AdminID = "Approv. By";
    private String isLoggedIn = "Log. In";
    protected String[] columnNames = { accountNumber, accountName, email, registrationTime, approvalTime, approvedBy_AdminID,isLoggedIn};
    protected LinkedList<AdminInfo> profileList = new LinkedList<>();

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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AdminInfo profile = profileList.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return profile.getAdminID();
            }
            case 1 -> {
                return profile.getAdminName();
            }
            case 2 -> {
                return profile.getEmail();
            }
            case 3 -> {
                return profile.getRegistrationTime();
            }
            case 4 -> {
                return profile.getApprovalTime();
            }
            case 5 -> {
                return profile.getApprovedBy();
            }
            case 6 -> {
                return profile.getIsLoggedIn();
            }
        }

        return null;        
    }

    @Override
    public void onLoggedIn(int admin_id) {
    }

    @Override
    public void onLogInFailed(String reason) {
    }

    @Override
    public void onLoggedOut() {
    }

    @Override
    public void onLogOutFailed(String reason) {
    }

    @Override
    public void onAccountOpen(int admin_id) {
    }

    @Override
    public void onSignUpFailed(String reason) {
    }

    @Override
    public void onAdminDisabled() {
    }

    @Override
    public void onAdminEnabled() {
    }

    @Override
    public void onAdminApproved() {    
    }

    @Override
    public void onAdminClosed() {
    }

    @Override
    public void onAdminRequestFailed(String errMsg) {        
    }

    @Override
    public void onAdminPasswordChanged(char[] new_password) {
    }

    @Override
    public void onPaginatedAdmins(List<AdminInfo> admins, int overall_total) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
