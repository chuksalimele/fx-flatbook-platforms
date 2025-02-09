/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package chuks.flatbook.fx.admin.main;

import chuks.flatbook.fx.admin.listener.AdminAccountListener;
import chuks.flatbook.fx.admin.trade.ui.Toast;
import com.formdev.flatlaf.FlatLightLaf;
import chuks.flatbook.fx.admin.transport.Admin;
import chuks.flatbook.fx.admin.transport.AdminManager;
import chuks.flatbook.fx.admin.ui.model.AdminProfileModel;
import chuks.flatbook.fx.admin.ui.model.BlacklistedIPsModel;
import chuks.flatbook.fx.admin.ui.model.RejectedIPsModel;
import chuks.flatbook.fx.admin.ui.model.RemoteLogsTableModel;
import chuks.flatbook.fx.admin.ui.model.SuspiciousIPsModel;
import chuks.flatbook.fx.admin.ui.model.TraderAccountProfileModel;
import chuks.flatbook.fx.admin.ui.model.WhitelistedIPsModel;
import chuks.flatbook.fx.common.account.profile.AdminInfo;
import chuks.flatbook.fx.common.listener.ConnectionListener;
import chuks.flatbook.fx.common.util.SecurePasswordUtils;
import chuks.flatbook.fx.common.util.log.LogLevel;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author user
 */
public class AdminGUI extends javax.swing.JFrame {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdminGUI.class.getName());
    static AdminGUI adminGUI;
    static private AdminManager adminAccount;
    static private Admin admin;
    static private int adminID = -1;
    static boolean isConnected;
    private static final AdminAccountListener accountListerner = new AdminAccountListener() {

        void displayConnectionStatus(String status) {
            lblConnectionStatusDisplay.setText(status);
            lblConnectionStatusDisplay.setToolTipText(status);
            cmdConnectionRetry.setVisible(!admin.isConnected() && admin.connectionAttempts() > 1);
        }

        @Override
        public void onLoggedIn(int admin_id) {
            isConnected = true;
            displayConnectionStatus("Logged in");
            dlgLogin.setVisible(false);
            mnuLogin.setEnabled(false);

        }

        @Override
        public void onLoggedOut() {
            isConnected = false;
            displayConnectionStatus("Logged out");
            mnuLogin.setEnabled(true);
        }

        @Override
        public void onLogInFailed(String errMsg) {
            Toast.show(errMsg, 10000, adminGUI);
        }

        @Override
        public void onLogOutFailed(String errMsg) {
            Toast.show(errMsg, 10000, adminGUI);
        }

        @Override
        public void onAccountOpen(int account_number) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onSignUpFailed(String reason) {
            Toast.show(reason, 10000, adminGUI);
        }

        @Override
        public void onAdminDisabled() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onAdminEnabled() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onAdminApproved() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onAdminClosed() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onAdminPasswordChanged(char[] new_password) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onAdminRequestFailed(String errMsg) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void onPaginatedAdmins(List<AdminInfo> admins, int overall_total) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    };

    private static final ConnectionListener connectionListerner = new ConnectionListener() {

        void displayConnectionStatus(String status) {
            lblConnectionStatusDisplay.setText(status);
            lblConnectionStatusDisplay.setToolTipText(status);
            cmdConnectionRetry.setVisible(!admin.isConnected() && admin.connectionAttempts() > 1);
        }

        @Override
        public void onConnectionProgress(String status) {
            displayConnectionStatus(status);
        }

        @Override
        public void onConnected() {
            isConnected = true;
            displayConnectionStatus("Connected");
        }

        @Override
        public void onDisconnected(String errMsg) {
            isConnected = false;
            displayConnectionStatus(errMsg);
            mnuLogin.setEnabled(true);
        }

    };

    /**
     * Creates new form AdminGUI
     */
    public AdminGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        navButtonsGroup = new javax.swing.ButtonGroup();
        dlgLogin = new javax.swing.JDialog();
        jLabel21 = new javax.swing.JLabel();
        txtAccountNumberDlgLogin = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        pwdPasswordDlgLogin = new javax.swing.JPasswordField();
        cmdLoginDlgLogin = new javax.swing.JButton();
        cmdIDontHaveAnAccountDlgLogin = new javax.swing.JButton();
        chkRememberMeDlgLogin = new javax.swing.JCheckBox();
        cmdForgotPasswordDlgLogin = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();
        toolBarFooter = new javax.swing.JToolBar();
        Status = new javax.swing.JLabel();
        lblConnectionStatusDisplay = new javax.swing.JLabel();
        cmdConnectionRetry = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        splMainSplitPane = new javax.swing.JSplitPane();
        pnlMainLeftPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cmdTraders = new javax.swing.JToggleButton();
        cmdAdmins = new javax.swing.JToggleButton();
        cmdSeverConfiguration = new javax.swing.JToggleButton();
        cmdServerLogs = new javax.swing.JToggleButton();
        cmdSettings = new javax.swing.JToggleButton();
        pnlMainRightPane = new javax.swing.JPanel();
        pnlTraders = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        cboFilterAccounts = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        lblUnapprovedAccountsCount = new javax.swing.JLabel();
        lblDisabledAccountsCount = new javax.swing.JLabel();
        lblInactiveAccountsCount = new javax.swing.JLabel();
        lblClosedAccountsCount = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        pnlAdministrators = new javax.swing.JLayeredPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        pnlServerConfig = new javax.swing.JLayeredPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtWhitelistIPIs = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        txtWhitelistIPIs2 = new javax.swing.JTextArea();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner4 = new javax.swing.JSpinner();
        cmdUpdateMaxConnectionPerIP = new javax.swing.JButton();
        cmdUpdateMaxRequestPerSecondPerIP = new javax.swing.JButton();
        pnlServerLogs = new javax.swing.JLayeredPane();
        logTabPane = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        dtpFromLogTime = new com.github.lgooddatepicker.components.DateTimePicker();
        dtpToLogTime = new com.github.lgooddatepicker.components.DateTimePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        pnlSettings = new javax.swing.JLayeredPane();
        mnuMainTopMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuLogin = new javax.swing.JMenuItem();
        mnuLogout = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem4.setText("jMenuItem4");

        dlgLogin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgLogin.setTitle("Login");
        dlgLogin.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel21.setText("Account Number");

        jLabel22.setText("Password");

        cmdLoginDlgLogin.setText("Login");
        cmdLoginDlgLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoginDlgLoginActionPerformed(evt);
            }
        });

        cmdIDontHaveAnAccountDlgLogin.setText("I don't have an account");

        chkRememberMeDlgLogin.setText("Remember me");
        chkRememberMeDlgLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRememberMeDlgLoginActionPerformed(evt);
            }
        });

        cmdForgotPasswordDlgLogin.setText("Forgot Password");

        javax.swing.GroupLayout dlgLoginLayout = new javax.swing.GroupLayout(dlgLogin.getContentPane());
        dlgLogin.getContentPane().setLayout(dlgLoginLayout);
        dlgLoginLayout.setHorizontalGroup(
            dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgLoginLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmdForgotPasswordDlgLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(chkRememberMeDlgLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdIDontHaveAnAccountDlgLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(cmdLoginDlgLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAccountNumberDlgLogin)
                    .addComponent(pwdPasswordDlgLogin))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        dlgLoginLayout.setVerticalGroup(
            dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgLoginLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtAccountNumberDlgLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(pwdPasswordDlgLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(chkRememberMeDlgLogin)
                .addGap(30, 30, 30)
                .addComponent(cmdLoginDlgLogin)
                .addGap(18, 18, 18)
                .addComponent(cmdIDontHaveAnAccountDlgLogin)
                .addGap(18, 18, 18)
                .addComponent(cmdForgotPasswordDlgLogin)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AdministratorTM");
        setBounds(new java.awt.Rectangle(0, 0, 600, 400));

        pnlContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        pnlContent.setLayout(new java.awt.BorderLayout(10, 0));

        toolBarFooter.setRollover(true);
        toolBarFooter.setPreferredSize(new java.awt.Dimension(4, 30));

        Status.setText("Status:  ");
        Status.setToolTipText("");
        toolBarFooter.add(Status);
        toolBarFooter.add(lblConnectionStatusDisplay);

        cmdConnectionRetry.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        cmdConnectionRetry.setForeground(java.awt.SystemColor.textHighlight);
        cmdConnectionRetry.setText("Retry");
        cmdConnectionRetry.setFocusable(false);
        cmdConnectionRetry.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdConnectionRetry.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdConnectionRetry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdConnectionRetryActionPerformed(evt);
            }
        });
        toolBarFooter.add(cmdConnectionRetry);
        toolBarFooter.add(jSeparator3);

        pnlContent.add(toolBarFooter, java.awt.BorderLayout.SOUTH);

        splMainSplitPane.setDividerLocation(220);

        navButtonsGroup.add(cmdTraders);
        cmdTraders.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmdTraders.setText("Traders");
        cmdTraders.setPreferredSize(new java.awt.Dimension(180, 50));
        cmdTraders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTradersActionPerformed(evt);
            }
        });

        navButtonsGroup.add(cmdAdmins);
        cmdAdmins.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmdAdmins.setText("Administrtors");
        cmdAdmins.setPreferredSize(new java.awt.Dimension(180, 50));
        cmdAdmins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAdminsActionPerformed(evt);
            }
        });

        navButtonsGroup.add(cmdSeverConfiguration);
        cmdSeverConfiguration.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmdSeverConfiguration.setText("Server Configuration");
        cmdSeverConfiguration.setPreferredSize(new java.awt.Dimension(180, 50));
        cmdSeverConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSeverConfigurationActionPerformed(evt);
            }
        });

        navButtonsGroup.add(cmdServerLogs);
        cmdServerLogs.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmdServerLogs.setText("Server Logs");
        cmdServerLogs.setPreferredSize(new java.awt.Dimension(180, 50));
        cmdServerLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdServerLogsActionPerformed(evt);
            }
        });

        navButtonsGroup.add(cmdSettings);
        cmdSettings.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmdSettings.setText("Settings");
        cmdSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmdSettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmdTraders, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addComponent(cmdAdmins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdSeverConfiguration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdServerLogs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cmdTraders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdAdmins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdSeverConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdServerLogs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlMainLeftPanelLayout = new javax.swing.GroupLayout(pnlMainLeftPanel);
        pnlMainLeftPanel.setLayout(pnlMainLeftPanelLayout);
        pnlMainLeftPanelLayout.setHorizontalGroup(
            pnlMainLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLeftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        pnlMainLeftPanelLayout.setVerticalGroup(
            pnlMainLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLeftPanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        splMainSplitPane.setLeftComponent(pnlMainLeftPanel);

        pnlMainRightPane.setLayout(new java.awt.CardLayout());

        pnlTraders.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Traders", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        jScrollPane6.setBorder(null);

        jTable6.setModel(new TraderAccountProfileModel());
        jScrollPane6.setViewportView(jTable6);

        jButton3.setText("<<");

        jButton4.setText("<");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setText("23");

        jLabel4.setText("of");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("50");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton5.setText(">>");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText(">");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6)
                        .addComponent(jButton5))))
        );

        cboFilterAccounts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "UNAPPROVED", "DISABLED", "INACTIVE", "CLOSED" }));

        jLabel6.setText("Filter Accounts:");

        lblUnapprovedAccountsCount.setText("0 Unapproved accounts");

        lblDisabledAccountsCount.setText("0 Disabled accounts");

        lblInactiveAccountsCount.setText("0 Inactive acounts");

        lblClosedAccountsCount.setText("0 Closed accounts");

        jButton12.setText("Update Status");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                            .addComponent(lblUnapprovedAccountsCount, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblDisabledAccountsCount, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                    .addComponent(lblInactiveAccountsCount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblClosedAccountsCount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboFilterAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton12, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUnapprovedAccountsCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDisabledAccountsCount)
                    .addComponent(jButton12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInactiveAccountsCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFilterAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(lblClosedAccountsCount))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pnlTraders.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pnlTradersLayout = new javax.swing.GroupLayout(pnlTraders);
        pnlTraders.setLayout(pnlTradersLayout);
        pnlTradersLayout.setHorizontalGroup(
            pnlTradersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTradersLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 47, Short.MAX_VALUE))
        );
        pnlTradersLayout.setVerticalGroup(
            pnlTradersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTradersLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlMainRightPane.add(pnlTraders, "TradersCard");

        pnlAdministrators.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Administrators", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        jTable12.setModel(new AdminProfileModel());
        jScrollPane14.setViewportView(jTable12);

        jButton8.setText("<<");

        jButton9.setText("<");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel9.setText("23");

        jLabel10.setText("of");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("50");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton10.setText(">>");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText(">");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton8)
                        .addComponent(jButton9)
                        .addComponent(jLabel9))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton11)
                        .addComponent(jButton10))))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pnlAdministrators.setLayer(jPanel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pnlAdministratorsLayout = new javax.swing.GroupLayout(pnlAdministrators);
        pnlAdministrators.setLayout(pnlAdministratorsLayout);
        pnlAdministratorsLayout.setHorizontalGroup(
            pnlAdministratorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdministratorsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        pnlAdministratorsLayout.setVerticalGroup(
            pnlAdministratorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdministratorsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlMainRightPane.add(pnlAdministrators, "AdministratorsCard");

        pnlServerConfig.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server Configuration", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        jTable4.setModel(new WhitelistedIPsModel());
        jScrollPane4.setViewportView(jTable4);

        txtWhitelistIPIs.setColumns(20);
        txtWhitelistIPIs.setRows(5);
        jScrollPane5.setViewportView(txtWhitelistIPIs);

        jLabel1.setText("Enter a list of IPs separate by space, comma or new line");

        jButton1.setText("Whitelist");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Whitelisted IPs", jPanel3);

        jLabel12.setText("Enter a list of IPs separate by space, comma or new line");

        jButton13.setText("Blacklist");
        jButton13.setBackground(new java.awt.Color(51, 51, 51));
        jButton13.setForeground(new java.awt.Color(255, 255, 255));

        txtWhitelistIPIs2.setColumns(20);
        txtWhitelistIPIs2.setRows(5);
        jScrollPane15.setViewportView(txtWhitelistIPIs2);

        jTable13.setModel(new BlacklistedIPsModel());
        jScrollPane16.setViewportView(jTable13);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane15)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Blacklisted IPs", jPanel5);

        jTable10.setModel(new SuspiciousIPsModel());
        jScrollPane11.setViewportView(jTable10);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 157, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Suspicious IPs", jPanel6);

        jTable11.setModel(new RejectedIPsModel());
        jScrollPane12.setViewportView(jTable11);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );

        jTabbedPane3.addTab("Rejected IPs", jPanel7);

        jLabel7.setText("Max. Connection Per IP");

        jLabel8.setText("Max. Request Per Second Per IP");

        cmdUpdateMaxConnectionPerIP.setText("Update");

        cmdUpdateMaxRequestPerSecondPerIP.setText("Updae");

        pnlServerConfig.setLayer(jTabbedPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerConfig.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerConfig.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerConfig.setLayer(jSpinner3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerConfig.setLayer(jSpinner4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerConfig.setLayer(cmdUpdateMaxConnectionPerIP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerConfig.setLayer(cmdUpdateMaxRequestPerSecondPerIP, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pnlServerConfigLayout = new javax.swing.GroupLayout(pnlServerConfig);
        pnlServerConfig.setLayout(pnlServerConfigLayout);
        pnlServerConfigLayout.setHorizontalGroup(
            pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServerConfigLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlServerConfigLayout.createSequentialGroup()
                        .addGroup(pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinner4)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdUpdateMaxConnectionPerIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdUpdateMaxRequestPerSecondPerIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlServerConfigLayout.setVerticalGroup(
            pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServerConfigLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmdUpdateMaxConnectionPerIP)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlServerConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmdUpdateMaxRequestPerSecondPerIP)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMainRightPane.add(pnlServerConfig, "ServerConfigurationCard");

        pnlServerLogs.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server Logs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        logTabPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                logTabPaneStateChanged(evt);
            }
        });

        jTable3.setModel(new RemoteLogsTableModel(LogLevel.INFO));
        jScrollPane3.setViewportView(jTable3);

        logTabPane.addTab("Info", jScrollPane3);

        jTable7.setModel(new RemoteLogsTableModel(LogLevel.WARN));
        jScrollPane7.setViewportView(jTable7);

        logTabPane.addTab("Warn", jScrollPane7);

        jTable8.setModel(new RemoteLogsTableModel(LogLevel.DEBUG));
        jScrollPane8.setViewportView(jTable8);

        logTabPane.addTab("Debug", jScrollPane8);

        jTable9.setModel(new RemoteLogsTableModel(LogLevel.ERROR));
        jScrollPane9.setViewportView(jTable9);

        logTabPane.addTab("Error", jScrollPane9);

        dtpFromLogTime.addDateTimeChangeListener(new DateTimeChangeListener(){
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                handleDtpFromLogTimeDateTimeChange(event);
            }
        });

        dtpToLogTime.addDateTimeChangeListener(new DateTimeChangeListener(){
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                handleDtpToLogTimeDateTimeChange(event);
            }
        });

        jLabel2.setText("From:");

        jLabel13.setText("To:");

        pnlServerLogs.setLayer(logTabPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerLogs.setLayer(dtpFromLogTime, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerLogs.setLayer(dtpToLogTime, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerLogs.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        pnlServerLogs.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pnlServerLogsLayout = new javax.swing.GroupLayout(pnlServerLogs);
        pnlServerLogs.setLayout(pnlServerLogsLayout);
        pnlServerLogsLayout.setHorizontalGroup(
            pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServerLogsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logTabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlServerLogsLayout.createSequentialGroup()
                        .addGroup(pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServerLogsLayout.createSequentialGroup()
                                .addComponent(dtpFromLogTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(180, 180, 180))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServerLogsLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(371, 371, 371)))
                        .addGroup(pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtpToLogTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pnlServerLogsLayout.setVerticalGroup(
            pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServerLogsLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlServerLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dtpFromLogTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtpToLogTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logTabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        pnlMainRightPane.add(pnlServerLogs, "ServerLogsCard");

        pnlSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        javax.swing.GroupLayout pnlSettingsLayout = new javax.swing.GroupLayout(pnlSettings);
        pnlSettings.setLayout(pnlSettingsLayout);
        pnlSettingsLayout.setHorizontalGroup(
            pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 702, Short.MAX_VALUE)
        );
        pnlSettingsLayout.setVerticalGroup(
            pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );

        pnlMainRightPane.add(pnlSettings, "SettingsCard");

        splMainSplitPane.setRightComponent(pnlMainRightPane);

        pnlContent.add(splMainSplitPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlContent, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText("File");

        mnuLogin.setText("Login");
        mnuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLoginActionPerformed(evt);
            }
        });
        jMenu1.add(mnuLogin);

        mnuLogout.setText("Logout");
        mnuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(mnuLogout);

        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnuExit);

        mnuMainTopMenu.add(jMenu1);

        jMenu2.setText("View");
        mnuMainTopMenu.add(jMenu2);

        jMenu3.setText("Help");
        mnuMainTopMenu.add(jMenu3);

        setJMenuBar(mnuMainTopMenu);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdServerLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdServerLogsActionPerformed
        CardLayout cl = (CardLayout) pnlMainRightPane.getLayout();
        cl.show(pnlMainRightPane, "ServerLogsCard");
    }//GEN-LAST:event_cmdServerLogsActionPerformed

    private void cmdSeverConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSeverConfigurationActionPerformed

        getLogs();
        CardLayout cl = (CardLayout) pnlMainRightPane.getLayout();
        cl.show(pnlMainRightPane, "ServerConfigurationCard");
    }//GEN-LAST:event_cmdSeverConfigurationActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void cmdTradersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTradersActionPerformed

        try {
            adminAccount.getAccountList(0, 10, adminID);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        CardLayout cl = (CardLayout) pnlMainRightPane.getLayout();
        cl.show(pnlMainRightPane, "TradersCard");

    }//GEN-LAST:event_cmdTradersActionPerformed

    private void cmdAdminsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAdminsActionPerformed

        try {
            adminAccount.getAdminList(0, 10, adminID);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        CardLayout cl = (CardLayout) pnlMainRightPane.getLayout();
        cl.show(pnlMainRightPane, "AdministratorsCard");
    }//GEN-LAST:event_cmdAdminsActionPerformed

    private void cmdSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSettingsActionPerformed
        CardLayout cl = (CardLayout) pnlMainRightPane.getLayout();
        cl.show(pnlMainRightPane, "SettingsCard");
    }//GEN-LAST:event_cmdSettingsActionPerformed

    private void logTabPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_logTabPaneStateChanged
        getLogs();
    }//GEN-LAST:event_logTabPaneStateChanged

    private void mnuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLoginActionPerformed
        dlgLogin.setSize(new Dimension(500, 400));
        dlgLogin.setLocationRelativeTo(this);

        dlgLogin.setVisible(true);
    }//GEN-LAST:event_mnuLoginActionPerformed

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuLogoutActionPerformed

    private void cmdLoginDlgLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoginDlgLoginActionPerformed
        String srtAccNum = txtAccountNumberDlgLogin.getText();
        int account_number = 0;
        try {
            account_number = Integer.parseInt(srtAccNum);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(dlgLogin,
                    "Acount number contains only digits",
                    "Invalid",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] hashPassword;
        try {
            hashPassword = SecurePasswordUtils.hashPassword(pwdPasswordDlgLogin.getPassword());
            adminAccount.login(account_number, hashPassword);
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(dlgLogin,
                    "Could not prepare data to server",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(ex.getMessage(), ex);
        }
    }//GEN-LAST:event_cmdLoginDlgLoginActionPerformed

    private void chkRememberMeDlgLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRememberMeDlgLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkRememberMeDlgLoginActionPerformed

    private void cmdConnectionRetryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdConnectionRetryActionPerformed
        admin.connectToServer();
    }//GEN-LAST:event_cmdConnectionRetryActionPerformed

    private void getLogs() {

        LocalDateTime fromTime = dtpFromLogTime.getDateTimeStrict();
        LocalDateTime toTime = dtpToLogTime.getDateTimeStrict();

        if (fromTime == null || toTime == null || fromTime.isAfter(toTime)) {
            return;
        }

        long intFromTime = fromTime.toEpochSecond(ZoneOffset.UTC);
        long intToTime = toTime.toEpochSecond(ZoneOffset.UTC);
        LogLevel level;
        switch (logTabPane.getSelectedIndex()) {
            case 0 ->
                level = LogLevel.INFO;
            case 1 ->
                level = LogLevel.WARN;
            case 3 ->
                level = LogLevel.DEBUG;
            case 4 ->
                level = LogLevel.ERROR;
            default -> {
                return;
            }
        }
        adminAccount.getLogs(level, intFromTime, intToTime, adminID);

    }

    private void handleDtpFromLogTimeDateTimeChange(DateTimeChangeEvent evt) {

        System.out.println("From - " + evt.getNewDateTimeStrict());
    }

    private void handleDtpToLogTimeDateTimeChange(DateTimeChangeEvent evt) {

        System.out.println("To - " + evt.getNewDateTimeStrict());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    adminGUI = new AdminGUI();
                    adminAccount = new AdminManager();

                    adminAccount.addConnectionListener(connectionListerner);
                    adminAccount.addAdminAccountListener(accountListerner);

                    (admin = new Admin(adminAccount,
                            "localhost",
                            7000)).start();

                    adminGUI.setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status;
    private javax.swing.JComboBox<String> cboFilterAccounts;
    private javax.swing.JCheckBox chkRememberMeDlgLogin;
    private javax.swing.JToggleButton cmdAdmins;
    private static javax.swing.JButton cmdConnectionRetry;
    private javax.swing.JButton cmdForgotPasswordDlgLogin;
    private javax.swing.JButton cmdIDontHaveAnAccountDlgLogin;
    private javax.swing.JButton cmdLoginDlgLogin;
    private javax.swing.JToggleButton cmdServerLogs;
    private javax.swing.JToggleButton cmdSettings;
    private javax.swing.JToggleButton cmdSeverConfiguration;
    private javax.swing.JToggleButton cmdTraders;
    private javax.swing.JButton cmdUpdateMaxConnectionPerIP;
    private javax.swing.JButton cmdUpdateMaxRequestPerSecondPerIP;
    private static javax.swing.JDialog dlgLogin;
    private com.github.lgooddatepicker.components.DateTimePicker dtpFromLogTime;
    private com.github.lgooddatepicker.components.DateTimePicker dtpToLogTime;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JLabel lblClosedAccountsCount;
    private static javax.swing.JLabel lblConnectionStatusDisplay;
    private javax.swing.JLabel lblDisabledAccountsCount;
    private javax.swing.JLabel lblInactiveAccountsCount;
    private javax.swing.JLabel lblUnapprovedAccountsCount;
    private javax.swing.JTabbedPane logTabPane;
    private javax.swing.JMenuItem mnuExit;
    private static javax.swing.JMenuItem mnuLogin;
    private javax.swing.JMenuItem mnuLogout;
    private javax.swing.JMenuBar mnuMainTopMenu;
    private javax.swing.ButtonGroup navButtonsGroup;
    private javax.swing.JLayeredPane pnlAdministrators;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlMainLeftPanel;
    private javax.swing.JPanel pnlMainRightPane;
    private javax.swing.JLayeredPane pnlServerConfig;
    private javax.swing.JLayeredPane pnlServerLogs;
    private javax.swing.JLayeredPane pnlSettings;
    private javax.swing.JLayeredPane pnlTraders;
    private javax.swing.JPasswordField pwdPasswordDlgLogin;
    private javax.swing.JSplitPane splMainSplitPane;
    private javax.swing.JToolBar toolBarFooter;
    private javax.swing.JTextField txtAccountNumberDlgLogin;
    private javax.swing.JTextArea txtWhitelistIPIs;
    private javax.swing.JTextArea txtWhitelistIPIs2;
    // End of variables declaration//GEN-END:variables

}
