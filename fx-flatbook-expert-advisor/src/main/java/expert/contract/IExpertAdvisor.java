/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package expert.contract;

/**
 *
 * @author user
 */
public interface IExpertAdvisor {

    // Account Properties
    public int ACCOUNT_LOGIN = 0; // Account login
    public int ACCOUNT_TRADE_ALLOWED = 1; // Trade allowed
    public int ACCOUNT_TRADE_EXPERT = 2; // Expert advisors allowed to trade
    public int ACCOUNT_MARGIN = 3; // Margin mode (0-5)
    public int ACCOUNT_MARGIN_SO_SO = 4; // Margin StopOut level (%)
    public int ACCOUNT_BALANCE = 5; // Account balance
    public int ACCOUNT_CREDIT = 6; // Account credit
    public int ACCOUNT_LEVERAGE = 7; // Account leverage (e.g., 100:1)

    // Time Periods
    public int PERIOD_M1 = 1; // 1-minute period
    public int PERIOD_M5 = 5; // 5-minute period
    public int PERIOD_M15 = 15; // 15-minute period
    public int PERIOD_M30 = 30; // 30-minute period
    public int PERIOD_H1 = 60; // 1-hour period
    public int PERIOD_H4 = 240; // 4-hour period
    public int PERIOD_D1 = 1440; // Daily period
    public int PERIOD_W1 = 10080; // Weekly period
    public int PERIOD_MN1 = 43200; // Monthly period

    // Terminal Status
    public int TERMINAL_DISCONNECTED = 0; // Terminal disconnected
    public int TERMINAL_CONNECTED = 1; // Terminal connected

    // Reason Codes
    public int REASON_PROGRAM = 0;//Expert Advisor terminated its operation by calling the ExpertRemove() function
    public int REASON_REMOVE = 1;//Program has been deleted from the chart
    public int REASON_CHARTCHANGE = 2; // Chart change
    public int REASON_RECOMPILE = 3; // Recompile
    public int REASON_CHARTCLOSE = 4; // Chart close
    public int REASON_PARAMETERS = 5; // Parameters change
    public int REASON_ACCOUNT = 6; // Account change
    public int REASON_TEMPLATE = 7; // Template change
    public int REASON_INITFAILED = 8; // Initialization failed
    public int REASON_CLOSE = 9; // Close

    // Message Box Icons
    public int MB_ICONERROR = 0x10; // Error icon
    public int MB_ICONEXCLAMATION = 0x30; // Exclamation icon
    public int MB_ICONWARNING = 0x40; // Warning icon
    public int MB_ICONINFORMATION = 0x80; // Information icon

    // Symbol Properties
    public int SYMBOL_DIGITS = 1; // Number of digits after decimal
    public int SYMBOL_VOLUME_MIN = 3; // Minimum trade volume
    public int SYMBOL_VOLUME_MAX = 4; // Maximum trade volume
    public int SYMBOL_TRADE_TICK_SIZE = 5; // Tick size
    public int SYMBOL_TRADE_TICK_VALUE = 6; // Tick value
    public int SYMBOL_SWAP_LONG = 7; // Swap long value
    public int SYMBOL_SWAP_SHORT = 8; // Swap short value
    public int SYMBOL_SPREAD = 9; // Spread value
    public int SYMBOL_ASK = 10;
    public int SYMBOL_BID = 11;
    public int SYMBOL_TRADE_MODE = 12;
    public int SYMBOL_TRADE_MODE_DISABLED = 13;
    public int SYMBOL_TRADE_MODE_LONGONLY = 14;
    public int SYMBOL_TRADE_MODE_SHORTONLY = 15;
    public int SYMBOL_TRADE_MODE_CLOSEONLY = 16;
    public int SYMBOL_TRADE_MODE_FULL = 17;
    public int SYMBOL_TRADE_CONTRACT_SIZE = 18; // Contract size

    // Order Types
    public int OP_BUY = 0; // Buy order
    public int OP_SELL = 1; // Sell order
    public int OP_BUYSTOP = 2; // Buy stop order
    public int OP_SELLSTOP = 3; // Sell stop order
    public int OP_BUYLIMIT = 4; // Buy limit order
    public int OP_SELLLIMIT = 5; // Sell limit order

    // Selection Modes
    public int SELECT_BY_POS = 0; // Selection by position
    public int SELECT_BY_TICKET = 1; // Selection by ticket

    // Modes
    public int MODE_TRADES = 0; // Trades mode
    public int MODE_DIGITS = 1; // Digits mode
    public int MODE_TICKVALUE = 2; // Tick value mode
    public int MODE_SPREAD = 3; // Spread mode
    public int MODE_SWAPLONG = 4; // Swap long mode
    public int MODE_SWAPSHORT = 5; // Swap short mode
    public int MODE_ASK = 6; // Ask price mode
    public int MODE_BID = 7; // Bid price mode
    public int MODE_HIGH = 8;
    public int MODE_LOW = 9;
    public int MODE_OPEN = 10;
    public int MODE_CLOSE = 11;
    public int MODE_VOLUME = 12;
    public int MODE_TIME = 13;
    public int MODE_TRADEALLOWED = 14; // Trade allowed mode
    public int MODE_LOTSIZE = 15;
    public int MODE_HISTORY = 16;
    public int MODE_TICKSIZE = 17;

    public int ERR_SYMBOL_NOT_FOUND = 7000;//Symbol not found - Java
    public int ERR_ORDER_NOT_FOUND = 7001; //Order not found - Java
    public int ERR_UNKNOWN_ORDER_TYPE = 7002;//Unknown order type - Java
    public int ERR_ORDER_NOT_SELECTED = 7003;//order not selected - Java

    public int ERR_UNKNOWN_SYMBOL = 4106; // Unknown symbol - mql4
    public int ERR_REQUOTE = 138; // Requote - mql4 - DO NOT CHANGE THE CODE VALUE

    // Initialization
    public int INIT_FAILED = -1; // Initialization failed
    public int INIT_SUCCEEDED = 0; // Initialization succeeded

    // Other constants
    public int INT_MIN = Integer.MIN_VALUE;
    public int INT_MAX = Integer.MAX_VALUE;

    String __PATH__();

    String __FILE__();

    int OnInit();

    void OnDeinit(int reason);

    void OnTick();

    void OnTimer();

    void OnTrade();

    void OnTradeTransaction();

    boolean IsExpertEnabled();

    boolean IsDllsAllowed();

    boolean IsTradeAllowed();

    double MathAbs(double num);

    double AccountFreeMarginCheck(String symbol, int type, double lot_size);

    double AccountMargin();

    double AccountFreeMargin();

    double AccountEquity();

    double AccountProfit();

    double AccountStopoutLevel();

    int AccountLeverage();

    double AccountBalance();

    double AccountCredit();

    boolean IsConnected();

    long TimeCurrent();

    int PositionsTotal();

    double NormalizeDouble(double value, int digits);

    double Point();

    int OrdersTotal();

    int OrdersHistoryTotal();

    double OrderOpenPrice();

    double OrderClosePrice();

    long OrderOpenTime();

    long OrderCloseTime();

    String OrderSymbol();

    double OrderLots();

    double OrderSwap();

    double OrderType();

    long OrderTicket();

    double OrderCommission();

    double OrderTakeProfit();//target price

    double OrderStopLoss();//stoploss price

    double OrderProfit();//profit in currency

    double AccountInfoDouble(int code);

    int SymbolInfoInteger(String symbol, int code);

    double SymbolInfoDouble(String symbol, int code);

    String Symbol();

    /**
     *
     * @param index - can also mean ticket depending on if select_type is
     * SELECT_BY_TICKET
     * @param select_type
     * @param mode
     * @return
     */
    boolean OrderSelect(long index, int select_type, int mode);

    boolean OrderSelect(long ticket, int select_by);

    boolean OrderDelete(long ticket);

    long OrderSend(String symbol, int order_type, double lot_size, double entry_price, int slippage, double stoploss, double target,
            String comment, int magic_number, long expiry);

    boolean OrderModify(long ticket, double open_price, double stoploss, double target, long expiry);

    int GetLastError();

    void ResetLastError();

    String ErrorDescription(int error_code);

    double MarketInfo(String symbol, int mode);

    void Print(Object... args);

    void PrintFormat(String str, Object... args);

    String StringFormat(String str, Object... args);

    void Comment(Object... args);

    int StringLen(String str);

    String StringSubstr(String path, int i, int i0);

    String StringSubstr(String path, int i);

    boolean IsDemo();

    String AccountCompany();

    int AccountNumber();

    String AccountName();

    String AccountCurrency();

    String TerminalPath();

    boolean EventSetMillisecondTimer(int millsec);

    void EventKillTimer();

    void ExpertRemove();

    void Alert(String msg);

    void PlaySound(String alertwav);

    void MessageBox(String msg);

    long TimeLocal();

    long GetTickCount();

    String DoubleToStr(double value, int decimal);

    void SendNotification(String str);

    long Time(int shift);

    int Volume(int shift);

    double Open(int shift);

    double High(int shift);

    double Low(int shift);

    double Close(int shift);

    long iTime(String symbol, int timeframe, int shift);

    int iVolume(String symbol, int timeframe, int shift);

    double iOpen(String symbol, int timeframe, int shift);

    double iHigh(String symbol, int timeframe, int shift);

    double iLow(String symbol, int timeframe, int shift);

    double iClose(String symbol, int timeframe, int shift);

    int iHighest(String symbol, int timeframe, int type, int count);

    int iHighest(String symbol, int timeframe, int type, int count, int start);

    int iLowest(String symbol, int timeframe, int type, int count);

    int iLowest(String symbol, int timeframe, int type, int count, int start);

    int Period();

    boolean IsStopped();

    void MessageBox(String msg, String title, int code);

    String LongToString(long value);

    String LongToString(long value, int str_len);

    String IntegerToString(int value);

    String IntegerToString(int value, int str_len);

    String DoubleToString(double value);

    String DoubleToString(double value, int decimal);

    int StringToInteger(String value);

    double StringToDouble(String value);

    char StringGetChar(String str, int index);

    String[] StringSplit(String str, char ch);

    String StringReplace(String str, String search, String replacement);

    int StringFind(String str, String search, int from_index);

    int StringFind(String str, String search);

    String StringToUpper(String str);

    String CharArrayToString(char[] arr);

    void TerminalClose(int reason);

    int AccountInfoInteger(int code);

    int TerminalInfoInteger(int code);

    void Sleep(int delay);

    void RefreshRates();

    boolean OrderClose(long ticket, double lots, double price, int slippage);

    int OrderMagicNumber();

    int ArraySize(String[] arr);

    int ArraySize(long[] arr);

    int ArraySize(int[] arr);

    int ArraySize(double[] arr);
    
    String[] ArrayResize(String[] arr, int new_size);

    long[] ArrayResize(long[] arr, int new_size);

    int[] ArrayResize(int[] arr, int new_size);

    double[] ArrayResize(double[] arr, int new_size);

    char[] ArrayResize(char[] arr, int new_size);

    double[] ArrayCopy(double[] to, double[] from);

    long[] ArrayCopy(long[] to, long[] from);

    int[] ArrayCopy(int[] to, int[] from);

    public static void main(String... args) {
        Object n = 8.8D;
        Object m = "chuks";

        System.out.println(n.toString());
        System.out.println(m);
        System.out.println(n + "me");
    }
}
