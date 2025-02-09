/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.backend.account;

import chuks.flatbook.fx.backend.account.contract.Identifier;
import chuks.flatbook.fx.common.account.profile.TraderInfo;
import chuks.flatbook.fx.common.account.order.ManagedOrder;
import chuks.flatbook.fx.common.account.order.SymbolInfo;
import chuks.flatbook.fx.transport.message.MessageType;
import chuks.flatbook.fx.backend.listener.ConnectionListener;
import chuks.flatbook.fx.backend.listener.OrderActionListener;
import chuks.flatbook.fx.backend.listener.SymbolUpdateListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import chuks.flatbook.fx.backend.listener.AccountListener;
import chuks.flatbook.fx.common.account.profile.AdminInfo;
import chuks.flatbook.fx.common.util.OnceAccessStore;
import chuks.flatbook.fx.common.util.log.LogLevel;
import static chuks.flatbook.fx.common.util.log.LogLevel.DEBUG;
import static chuks.flatbook.fx.common.util.log.LogLevel.INFO;
import chuks.flatbook.fx.transport.message.MessageFactory;

/**
 *
 * @author user
 */
public class Client implements Identifier, OrderActionListener, SymbolUpdateListener, ConnectionListener, AccountListener {

    private String[] selectedSymbols = new String[0];//for prices subscription
    private ChannelHandlerContext ctx;
    Identifier idf;
    final public static int NO_ACCOUNT_NUMBER = -1;
    int userType = -1;//important
    OnceAccessStore onceAccesStore = new OnceAccessStore();

    public Client(int account_number, int user_type, ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.userType = user_type;
        this.idf = new Identifier() {
            private final int accountNumber = account_number;

            @Override
            public int getAccountNumber() {
                return accountNumber;
            }
        };
    }

    @Override
    public int getAccountNumber() {
        return this.idf.getAccountNumber();
    }

    public void setSelectedSymbols(String[] symbols) {
        selectedSymbols = symbols;
    }

    public ChannelHandlerContext getContext() {
        return this.ctx;
    }

    void handleOrderEvent(MessageType event, String req_identifier, ManagedOrder order) {
        if (order.getAccountNumber() == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(MessageFactory
                    .create(event, req_identifier)
                    .assign(order.stringify()));
        }
    }

    void handleOrderlistEvent(MessageType event, String req_identifier, List<ManagedOrder> orders) {

        List<String> subOrdersStr = new LinkedList();
        for (int i = 0; i < orders.size(); i++) {
            ManagedOrder order = orders.get(i);
            if (order.getAccountNumber() == this.idf.getAccountNumber()) {
                subOrdersStr.add(order.stringify());
            }
        }
        ctx.writeAndFlush(
                MessageFactory
                        .create(event, req_identifier)
                        .assign(subOrdersStr.toArray()));

    }

    @Override
    public Identifier onLogIn(int account_number) {
        if (account_number == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(MessageFactory
                    .create(MessageType.LOGGED_IN)
                    .assign(account_number));
        }
        return idf;
    }

    @Override
    public Identifier onLogInFail(int account_number, String reason) {
        if (account_number == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(MessageFactory
                    .create(MessageType.LOGIN_FAILED)
                    .assign(account_number, reason));

        }
        return idf;
    }

    @Override
    public Identifier onLogOut(int account_number) {
        if (account_number == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(MessageFactory
                    .create(MessageType.LOGGED_OUT)
                    .assign(account_number));
        }
        return idf;
    }

    @Override
    public Identifier onLogOutFail(int account_number, String reason) {
        if (account_number == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(
                    MessageFactory
                            .create(MessageType.LOGOUT_FAILED)
                            .assign(account_number, reason));
        }
        return idf;
    }

    @Override
    public Identifier onNewMarketOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.NEW_MARKET_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onClosedMarketOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.CLOSED_MARKET_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onModifiedMarketOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.MODIFIED_MARKET_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onTriggeredPendingOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.TRIGGERED_PENDING_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onNewPendingOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.NEW_PENDING_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onDeletedPendingOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.DELETED_PENDING_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onModifiedPendingOrder(String req_identifier, ManagedOrder order) {
        handleOrderEvent(MessageType.MODIFIED_PENDING_ORDER, req_identifier, order);
        return idf;
    }

    @Override
    public Identifier onOrderRemoteError(String req_identifier, ManagedOrder order, String errMsg) {
        if (order.getAccountNumber() == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(MessageFactory
                    .create(MessageType.ORDER_REMOTE_ERROR, req_identifier)
                    .assign(order.stringify(), errMsg));
        }
        return idf;
    }

    @Override
    public Identifier onOrderNotAvailable(String req_identifier, int account_number, String errMsg) {
        if (account_number == this.idf.getAccountNumber()) {
            ctx.writeAndFlush(MessageFactory
                    .create(MessageType.ORDER_NOT_AVAILABLE, req_identifier)
                    .assign(errMsg));
        }

        return idf;
    }

    @Override
    public Identifier onAddAllOpenOrders(String req_identifier, int account_number, List<ManagedOrder> orders) {
        if (account_number == this.idf.getAccountNumber()) {
            handleOrderlistEvent(MessageType.ADD_ALL_OPEN_ORDERS, req_identifier, orders);
        }
        return idf;
    }

    @Override
    public Identifier onAddAllPendingOrders(String req_identifier, int account_number, List<ManagedOrder> orders) {
        if (account_number == this.idf.getAccountNumber()) {
            handleOrderlistEvent(MessageType.ADD_ALL_PENDING_ORDERS, req_identifier, orders);
        }
        return idf;
    }

    @Override
    public Identifier onAddAllHistoryOrders(int account_number, List<ManagedOrder> orders, String req_identifier) {
        if (account_number == this.idf.getAccountNumber()) {
            handleOrderlistEvent(MessageType.ADD_ALL_HISTORY_ORDERS, req_identifier, orders);
        }

        return idf;
    }

    @Override
    public Identifier onSwapChange(SymbolInfo symbolInfo) {
        for (String selectedSymbol : selectedSymbols) {
            if (selectedSymbol.equals(symbolInfo.getName())) {
                ctx.writeAndFlush(MessageFactory
                        .create(MessageType.SWAP_CHANGE)
                        .assign(symbolInfo.stringify()));
                break;
            }
        }

        return idf;
    }

    @Override
    public Identifier onPriceChange(SymbolInfo symbolInfo) {
        for (String selectedSymbol : selectedSymbols) {
            if (selectedSymbol.equals(symbolInfo.getName())) {
                ctx.writeAndFlush(
                        MessageFactory
                                .create(MessageType.PRICE_CHANGE)
                                .assign(symbolInfo.stringify()));
                break;
            }
        }

        return idf;
    }

    @Override
    public Identifier onFullSymbolList(int account_number, Set<String> symbol_list) {
        if (account_number != this.idf.getAccountNumber()) {
            return idf;
        }
        ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.FULL_SYMBOL_LIST)
                        .assign((Object)symbol_list.toArray(String[]::new)));
        return idf;
    }

    @Override
    public Identifier onSelectedSymbolInfoList(int account_number, List<SymbolInfo> symbol_info_list) {
        if (account_number != this.idf.getAccountNumber()) {
            return idf;
        }
        String[] info_str_arr = new String[symbol_info_list.size()];
        int i = 0;
        for (SymbolInfo symbol_info : symbol_info_list) {
            info_str_arr[i] = symbol_info.stringify();
            i++;
        }
        ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.SELECTED_SYMBOL_INFO_LIST)
                        .assign((Object) info_str_arr));
        return idf;
    }

    @Override
    public Identifier onConnectionProgress(String status) {
        return idf;
    }

    @Override
    public Identifier onTradeSessionLogOn() {
        return idf;
    }

    @Override
    public Identifier onQuoteSessionLogOn() {
        return idf;
    }

    @Override
    public Identifier onTradeSessionLogOut() {
        return idf;
    }

    @Override
    public Identifier onPriceSessionLogOut() {
        return idf;
    }

    @Override
    public Identifier onAccountOpen(int account_number) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Identifier onSignUpInitiated(String email) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.SIGN_UP_INITIATED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onSignUpFail(String reason) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.SIGN_UP_FAILED)
                .assign(reason));
        return idf;
    }

    @Override
    public Identifier onAccountActivated(String email) {

        ctx.writeAndFlush(MessageFactory
                .create(MessageType.ACCOUNT_ACTIVATED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onAccountDeactivated(String email) {

        ctx.writeAndFlush(MessageFactory
                .create(MessageType.ACCOUNT_DEACTIVATED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onAccountDisabled(String email) {

        ctx.writeAndFlush(MessageFactory
                .create(MessageType.ACCOUNT_DISABLED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onAccountEnabled(String email) {

        ctx.writeAndFlush(MessageFactory
                .create(MessageType.ACCOUNT_ENABLED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onAccountApproved(String email) {

        ctx.writeAndFlush(MessageFactory
                .create(MessageType.ACCOUNT_APPROVED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onAccountClosed(String email) {

        ctx.writeAndFlush(MessageFactory
                .create(MessageType.ACCOUNT_CLOSED)
                .assign(email));
        return idf;
    }

    @Override
    public Identifier onPasswordChanged(int account_number, char[] new_password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void onPaginatedAccountList(List<TraderInfo> list, int overall_total) {
        String[] arr = new String[list.size()];
        int i = 0;
        for (TraderInfo profile : list) {
            arr[i] = profile.stringify(false);
            i++;
        }
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.PAGINATED_ACCOUNT_LIST)
                .assign(arr, overall_total));
    }

    public void onPaginatedDeactivatedAccountList(List<TraderInfo> list, int overall_total) {
        String[] arr = new String[list.size()];
        int i = 0;
        for (TraderInfo profile : list) {
            arr[i] = profile.stringify(false);
            i++;
        }
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.PAGINATED_DEACTIVATED_ACCOUNT_LIST)
                .assign(arr, overall_total));
    }

    public void onPaginatedDisabledAccountList(List<TraderInfo> list, int overall_total) {
        String[] arr = new String[list.size()];
        int i = 0;
        for (TraderInfo profile : list) {
            arr[i] = profile.stringify(false);
            i++;
        }
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.PAGINATED_DISABLED_ACCOUNT_LIST)
                .assign(arr, overall_total));
    }

    public void onPaginatedUnapprovedAccountList(List<TraderInfo> list, int overall_total) {
        String[] arr = new String[list.size()];
        int i = 0;
        for (TraderInfo profile : list) {
            arr[i] = profile.stringify(false);
            i++;
        }
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.PAGINATED_UNAPPROVED_ACCOUNT_LIST)
                .assign(arr, overall_total));
    }

    public void onPaginatedClosedAccountList(List<TraderInfo> list, int overall_total) {
        String[] arr = new String[list.size()];
        int i = 0;
        for (TraderInfo profile : list) {
            arr[i] = profile.stringify(false);
            i++;
        }
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.PAGINATED_CLOSED_ACCOUNT_LIST)
                .assign(arr, overall_total));
    }

    public void onWhitelistedIPs(String[] ip_arr) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.WHITELISTED_IPS)
                .assign((Object) ip_arr));
    }

    public void onBlacklistedIPs(String[] ip_arr) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.BLACKLISTED_IPS)
                .assign((Object) ip_arr));
    }

    public void onMaxConnectionPerIP(int max) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.MAX_CONNECTION_PER_IP)
                .assign(max));
    }

    public void onMaxRequestPerSecondPerIP(int max) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.MAX_REQUEST_PER_SECOND_PER_IP)
                .assign(max));
    }

    public void onRequestFailed(String reason) {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.REQUEST_FAILED)
                .assign(reason));
    }

    public void onPaginatedAdminList(List<AdminInfo> list, int overall_total) {
        String[] arr = new String[list.size()];
        int i = 0;
        for (AdminInfo profile : list) {
            arr[i] = profile.stringify(false);
            i++;
        }
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.PAGINATED_ADMIN_LIST)
                .assign(arr, overall_total));
    }

    public void onLogs(LogLevel level, List<String> logs) {
        MessageType msgType;
        switch (level) {
            case INFO ->
                msgType = MessageType.INFO_LOGS;
            case DEBUG ->
                msgType = MessageType.DEBUG_LOGS;
            case WARN ->
                msgType = MessageType.WARN_LOGS;
            case ERROR ->
                msgType = MessageType.ERROR_LOGS;
            case TRACE ->
                msgType = MessageType.TRACE_LOGS;
            case REJECTED_IPS ->
                msgType = MessageType.REJECTED_IPS_LOGS;
            case SUSPICIOUS_IPS ->
                msgType = MessageType.SUSPICIOUS_IPS_LOGS;
            default -> {
                return;
            }
        }

        ctx.writeAndFlush(MessageFactory
                .create(msgType)
                .assign((Object)logs.toArray(String[]::new)));
    }

}
