/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.admin.account.contract;

import chuks.flatbook.fx.common.util.log.LogLevel;

/**
 *
 * @author user
 */
public interface AdminOperation extends TraderOperation{

    void getAccountList(int page_index, int page_size, int admin_id);
    void getAdminList(int page_index, int page_size, int admin_id);
    void getUnapprovedAccountList(int page_index, int page_size, int admin_id);
    void getDisabledAccountList(int page_index, int page_size, int admin_id);
    void getDeactivatedAccountList(int page_index, int page_size, int admin_id);
    void getClosedAccountList(int page_index, int page_size, int admin_id);
    void approveAccount(String email, int admin_id);
    void enableAccount(String email, int admin_id);
    void disableAccount(String email, int admin_id);
    void activateAccount(String email, int admin_id);    
    void deactivateAccount(String email, int admin_id);    
    void closeAccount(String email, int admin_id);
    void whitelistIPs(String[] IPs, int admin_id);
    void blacklistIPs(String[] IPs, int admin_id);
    void getWhitelistIPs(int admin_id);
    void getBlacklistIPs(int admin_id);
    void getLogs(LogLevel log_level, int admin_id);
    void getLogs(LogLevel log_level, long start_time, int admin_id);
    void getLogs(LogLevel log_level, long start_time, long end_time, int admin_id);
    void setMaxConnectionPerIP(int max, int admin_id);
    void setMaxRequestPerSecondPerIP(int max, int admin_id);
    void getMaxConnectionPerIP(int admin_id);
    void getMaxRequestPerSecondPerIP(int admin_id);    
}
