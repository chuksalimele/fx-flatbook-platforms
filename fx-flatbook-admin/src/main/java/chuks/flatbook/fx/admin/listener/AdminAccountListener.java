/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.admin.listener;

import chuks.flatbook.fx.common.account.profile.AdminInfo;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdminAccountListener {
        
    /**
     * Admin logged in
     * 
     * @param admin_id
     */  
    void onLoggedIn(int admin_id);
    
    /**
     * Admin login failed
     * 
     * @param reason
     */      
    void onLogInFailed(String reason);
    
    /**
     * Admin logout
     * 
     */      
    void onLoggedOut();
    
    /**
     * Admin logout failed
     * 
     * @param reason
     */      
    void onLogOutFailed(String reason);
    
    /**
     * Admin account open
     * 
     * @param admin_id
     */      
    void onAccountOpen(int admin_id);
    
    /**
     * Admin sign up failed
     * 
     * @param reason
     */      
    void onSignUpFailed(String reason);
    
    /**
     * Admin account disabled
     * 
     */      
    void onAdminDisabled();
    
    /**
     * Admin account enabled
     * 
     */      
    void onAdminEnabled();

    /**
     * Admin account approved
     * 
     */      
    void onAdminApproved();
    
    /**
     * Admin account closed
     * 
     */       
    void onAdminClosed();
    
    /**
     * Admin request failed
     * 
     * @param errMsg
     */    
    void onAdminRequestFailed(String errMsg);
    
    /**
     * Admin password changed
     * 
     * @param new_password 
     */
    void onAdminPasswordChanged(char[] new_password);
    
    void onPaginatedAdmins(List<AdminInfo> admins, int overall_total);
}
