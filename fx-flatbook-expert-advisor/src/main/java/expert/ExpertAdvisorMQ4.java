/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package expert;

import expert.contract.IExpertAdvisor;
import expert.contract.IExpertService;
import expert.reflect.Inject;

/**
 *
 * @author user
 */
public abstract class ExpertAdvisorMQ4 implements IExpertAdvisor{

    @Inject
    private IExpertService expertService;
    
    public int _Digits = -1;

        
    @Override
    public String __PATH__(){
        return expertService.__PATH__();
    } 
    
    @Override
    public String __FILE__ (){
        return expertService.__FILE__();
    }                
         
    
    @Override
    public abstract int OnInit();

    @Override
    public abstract void OnDeinit(int reason);

    @Override
    public abstract void OnTick();

    @Override
    public abstract void OnTimer();

    @Override
    public abstract void OnTrade();

    @Override
    public abstract void OnTradeTransaction();

    @Override
    public boolean IsExpertEnabled() {
        return expertService.IsExpertEnabled();
    }

    @Override
    public boolean IsDllsAllowed() {
        return expertService.IsDllsAllowed();
    }

    @Override
    public boolean IsTradeAllowed() {
        return expertService.IsTradeAllowed();
    }

    @Override
    public double MathAbs(double num) {
        return expertService.MathAbs(num);
    }

    @Override
    public double AccountFreeMarginCheck(String symbol, int type, double lot_size) {
        return expertService.AccountFreeMarginCheck(symbol, type, lot_size);
    }

    @Override
    public double AccountMargin() {
        return expertService.AccountMargin();
    }

    @Override
    public double AccountFreeMargin() {
        return expertService.AccountFreeMargin();
    }

    @Override
    public double AccountEquity() {
        return expertService.AccountEquity();
    }

    @Override
    public double AccountProfit() {
        return expertService.AccountProfit();
    }

    @Override
    public double AccountStopoutLevel() {
        return expertService.AccountStopoutLevel();
    }

    @Override
    public int AccountLeverage() {
        return expertService.AccountLeverage();
    }

    @Override
    public double AccountBalance() {
        return expertService.AccountBalance();
    }

    @Override
    public double AccountCredit() {
        return expertService.AccountCredit();
    }

    @Override
    public boolean IsConnected() {
        return expertService.IsConnected();
    }

    @Override
    public long TimeCurrent() {
        return expertService.TimeCurrent();
    }

    @Override
    public int PositionsTotal() {
        return expertService.PositionsTotal();
    }

    @Override
    public double NormalizeDouble(double value, int digits) {
        return expertService.NormalizeDouble(value, digits);
    }

    @Override
    public double Point() {
        return expertService.Point();
    }

    @Override
    public int OrdersTotal() {
        return expertService.OrdersTotal();
    }

    @Override
    public int OrdersHistoryTotal() {
        return expertService.OrdersHistoryTotal();
    }

    @Override
    public double OrderOpenPrice() {
        return expertService.OrderOpenPrice();
    }

    @Override
    public double OrderClosePrice() {
        return expertService.OrderClosePrice();
    }

    @Override
    public long OrderOpenTime() {
        return expertService.OrderOpenTime();
    }

    @Override
    public long OrderCloseTime() {
        return expertService.OrderCloseTime();
    }

    @Override
    public String OrderSymbol() {
        return expertService.OrderSymbol();
    }

    @Override
    public double OrderLots() {
        return expertService.OrderLots();
    }

    @Override
    public double OrderSwap() {
        return expertService.OrderSwap();
    }

    @Override
    public double OrderType() {
        return expertService.OrderType();
    }

    @Override
    public long OrderTicket() {
        return expertService.OrderTicket();
    }

    @Override
    public double OrderCommission() {
        return expertService.OrderCommission();
    }

    @Override
    public double OrderTakeProfit() {
        return expertService.OrderTakeProfit();
    }

    @Override
    public double OrderStopLoss() {
        return expertService.OrderStopLoss();
    }

    @Override
    public double OrderProfit() {
        return expertService.OrderProfit();
    }

    @Override
    public double AccountInfoDouble(int code) {
        return expertService.AccountInfoDouble(code);
    }

    @Override
    public int SymbolInfoInteger(String symbol, int code) {
        return expertService.SymbolInfoInteger(symbol, code);
    }

    @Override
    public double SymbolInfoDouble(String symbol, int code) {
        return expertService.SymbolInfoDouble(symbol, code);
    }

    @Override
    public String Symbol() {
        return expertService.Symbol();
    }

    @Override
    public boolean OrderSelect(long index, int select_type, int mode) {
        return expertService.OrderSelect(index, select_type, mode);
    }

    @Override
    public boolean OrderSelect(long ticket, int select_by) {
        return expertService.OrderSelect(ticket, select_by);
    }

    @Override
    public boolean OrderDelete(long ticket) {
        return expertService.OrderDelete(ticket);
    }

    @Override
    public long OrderSend(String symbol, int order_type, double lot_size, double entry_price, int slippage, double stoploss, double target, String comment, int magic_number, long expiry) {
        return expertService.OrderSend(symbol, order_type, lot_size, entry_price, slippage, stoploss, target, comment, magic_number, expiry);
    }

    @Override
    public boolean OrderModify(long ticket, double open_price, double stoploss, double target, long expiry) {
        return expertService.OrderModify(ticket, open_price, stoploss, target, expiry);
    }

    @Override
    public int GetLastError() {
        return expertService.GetLastError();
    }

    @Override
    public void ResetLastError() {
        expertService.ResetLastError();
    }

    @Override
    public String ErrorDescription(int error_code) {
        return expertService.ErrorDescription(error_code);
    }

    @Override
    public double MarketInfo(String symbol, int mode) {
        return expertService.MarketInfo(symbol, mode);
    }

    @Override
    public void Print(Object... args) {
        expertService.Print(args);
    }

    @Override
    public void PrintFormat(String str, Object... args) {
        expertService.PrintFormat(str, args);
    }

    @Override
    public String StringFormat(String str, Object... args) {
        return expertService.StringFormat(str, args);
    }

    @Override
    public void Comment(Object... args) {
        expertService.Comment(args);
    }

    @Override
    public int StringLen(String str) {
        return expertService.StringLen(str);
    }

    @Override
    public String StringSubstr(String path, int i, int i0) {
        return expertService.StringSubstr(path, i, i0);
    }

    @Override
    public String StringSubstr(String path, int i) {
        return expertService.StringSubstr(path, i);
    }

    @Override
    public boolean IsDemo() {
        return expertService.IsDemo();
    }

    @Override
    public String AccountCompany() {
        return expertService.AccountCompany();
    }

    @Override
    public int AccountNumber() {
        return expertService.AccountNumber();
    }

    @Override
    public String AccountName() {
        return expertService.AccountName();
    }

    @Override
    public String AccountCurrency() {
        return expertService.AccountCurrency();
    }

    @Override
    public String TerminalPath() {
        return expertService.TerminalPath();
    }

    @Override
    public boolean EventSetMillisecondTimer(int millsec) {
        return expertService.EventSetMillisecondTimer(millsec);
    }

    @Override
    public void EventKillTimer() {
        expertService.EventKillTimer();
    }

    @Override
    public void ExpertRemove() {
        expertService.ExpertRemove();
    }

    @Override
    public void Alert(String msg) {
        expertService.Alert(msg);
    }

    @Override
    public void PlaySound(String alertwav) {
        expertService.PlaySound(alertwav);
    }

    @Override
    public void MessageBox(String msg) {
        expertService.MessageBox(msg);
    }

    @Override
    public long TimeLocal() {
        return expertService.TimeLocal();
    }

    @Override
    public long GetTickCount() {
        return expertService.GetTickCount();
    }

    @Override
    public String DoubleToStr(double value, int decimal) {
        return expertService.DoubleToStr(value, decimal);
    }

    @Override
    public void SendNotification(String str) {
        expertService.SendNotification(str);
    }

    @Override
    public long Time(int shift) {
        return expertService.Time(shift);
    }

    @Override
    public int Volume(int shift) {
        return expertService.Volume(shift);
    }

    @Override
    public double Open(int shift) {
        return expertService.Open(shift);
    }

    @Override
    public double High(int shift) {
        return expertService.High(shift);
    }

    @Override
    public double Low(int shift) {
        return expertService.Low(shift);
    }

    @Override
    public double Close(int shift) {
        return expertService.Close(shift);
    }

    @Override
    public long iTime(String symbol, int timeframe, int shift) {
        return expertService.iTime(symbol, timeframe, shift);
    }

    @Override
    public int iVolume(String symbol, int timeframe, int shift) {
        return expertService.iVolume(symbol, timeframe, shift);
    }

    @Override
    public double iOpen(String symbol, int timeframe, int shift) {
        return expertService.iOpen(symbol, timeframe, shift);
    }

    @Override
    public double iHigh(String symbol, int timeframe, int shift) {
        return expertService.iHigh(symbol, timeframe, shift);
    }

    @Override
    public double iLow(String symbol, int timeframe, int shift) {
        return expertService.iLow(symbol, timeframe, shift);
    }

    @Override
    public double iClose(String symbol, int timeframe, int shift) {
        return expertService.iClose(symbol, timeframe, shift);
    }

    @Override
    public int iHighest(String symbol, int timeframe, int type, int count) {
        return expertService.iHighest(symbol, timeframe, type, count);
    }

    @Override
    public int iHighest(String symbol, int timeframe, int type, int count, int start) {
        return expertService.iHighest(symbol, timeframe, type, count, start);
    }

    @Override
    public int iLowest(String symbol, int timeframe, int type, int count) {
        return expertService.iLowest(symbol, timeframe, type, count);
    }

    @Override
    public int iLowest(String symbol, int timeframe, int type, int count, int start) {
        return expertService.iLowest(symbol, timeframe, type, count, start);
    }

    @Override
    public int Period() {
        return expertService.Period();
    }

    @Override
    public boolean IsStopped() {
        return expertService.IsStopped();
    }

    @Override
    public void MessageBox(String msg, String title, int code) {
        expertService.MessageBox(msg, title, code);
    }

    @Override
    public String LongToString(long value) {
        return expertService.LongToString(value);
    }

    @Override
    public String LongToString(long value, int str_len) {
        return expertService.LongToString(value, str_len);
    }

    @Override
    public String IntegerToString(int value) {
        return expertService.IntegerToString(value);
    }

    @Override
    public String IntegerToString(int value, int str_len) {
        return expertService.IntegerToString(value, str_len);
    }

    @Override
    public String DoubleToString(double value) {
        return expertService.DoubleToString(value);
    }

    @Override
    public String DoubleToString(double value, int decimal) {
        return expertService.DoubleToString(value, decimal);
    }

    @Override
    public int StringToInteger(String value) {
        return expertService.StringToInteger(value);
    }

    @Override
    public double StringToDouble(String value) {
        return expertService.StringToDouble(value);
    }

    @Override
    public char StringGetChar(String str, int index) {
        return expertService.StringGetChar(str, index);
    }

    @Override
    public String[] StringSplit(String str, char ch) {
        return expertService.StringSplit(str, ch);
    }

    @Override
    public String StringReplace(String str, String search, String replacement) {
        return expertService.StringReplace(str, search, replacement);
    }

    @Override
    public int StringFind(String str, String search, int from_index) {
        return expertService.StringFind(str, search, from_index);
    }

    @Override
    public int StringFind(String str, String search) {
        return expertService.StringFind(str, search);
    }

    @Override
    public String StringToUpper(String str) {
        return expertService.StringToUpper(str);
    }

    @Override
    public String CharArrayToString(char[] arr) {
        return expertService.CharArrayToString(arr);
    }

    @Override
    public void TerminalClose(int reason) {
        expertService.TerminalClose(reason);
    }

    @Override
    public int AccountInfoInteger(int code) {
        return expertService.AccountInfoInteger(code);
    }

    @Override
    public int TerminalInfoInteger(int code) {
        return expertService.TerminalInfoInteger(code);
    }

    @Override
    public void Sleep(int delay) {
        expertService.Sleep(delay);
    }

    @Override
    public void RefreshRates() {
        expertService.RefreshRates();
    }

    @Override
    public boolean OrderClose(long ticket, double lots, double price, int slippage) {
        return expertService.OrderClose(ticket, lots, price, slippage);
    }

    @Override
    public int OrderMagicNumber() {
        return expertService.OrderMagicNumber();
    }

    @Override
    public int ArraySize(String[] arr) {
        return expertService.ArraySize(arr);
    }

    @Override
    public int ArraySize(long[] arr) {
        return expertService.ArraySize(arr);
    }

    @Override
    public int ArraySize(int[] arr) {
        return expertService.ArraySize(arr);
    }

    @Override
    public int ArraySize(double[] arr) {
        return expertService.ArraySize(arr);
    }

    @Override
    public String[] ArrayResize(String[] arr, int new_size) {
        return expertService.ArrayResize(arr, new_size);
    }

    @Override
    public long[] ArrayResize(long[] arr, int new_size) {
        return expertService.ArrayResize(arr, new_size);
    }

    @Override
    public int[] ArrayResize(int[] arr, int new_size) {
        return expertService.ArrayResize(arr, new_size);
    }

    @Override
    public double[] ArrayResize(double[] arr, int new_size) {
        return expertService.ArrayResize(arr, new_size);
    }

    @Override
    public char[] ArrayResize(char[] arr, int new_size) {
        return expertService.ArrayResize(arr, new_size);
    }

    @Override
    public double[] ArrayCopy(double[] to, double[] from) {
        return expertService.ArrayCopy(to, from);
    }

    @Override
    public long[] ArrayCopy(long[] to, long[] from) {
        return expertService.ArrayCopy(to, from);
    }

    @Override
    public int[] ArrayCopy(int[] to, int[] from) {
        return expertService.ArrayCopy(to, from);
    }
    

}
