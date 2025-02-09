/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.main;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import chuks.flatbook.fx.admin.transport.Admin;
import chuks.flatbook.fx.admin.transport.AdminManager;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author user
 */
public class AdminSimulator extends AdminManager{

    private static Admin admin;
    private static AdminSimulator adminAccount;
    
        
    public static void main(String... args) throws Exception{        

        adminAccount = new AdminSimulator();
        admin = new Admin(adminAccount, "localhost", 7000);
        admin.start();

    }
}
