/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.transport.message;

/**
 *
 * @author user
 */
public class ChannelMessage{

    protected int messeageID;
    protected MessageType type;
    protected Object[] arguments;
    private String resquestResponseIdentifier;

    
    ChannelMessage(MessageType type){
        this.type = type;
    }        
    
    ChannelMessage(MessageType type, String resquestResponseIdentifier){
        this.type = type;
        this.resquestResponseIdentifier = resquestResponseIdentifier;
    }        
    
    public ChannelMessage assign(Object... arguments){
          this.arguments = arguments;
          return this;
    }
    
    public String getIdentifier(){
        return resquestResponseIdentifier;
    }
    
    public MessageType getType() {
        return type;
    }
    
    private boolean outOfRange(int index){
        return index < 0 || index > arguments.length -1;
    }
    
    public int argumentsCount(){
        return arguments.length;
    }
    
    public boolean isByte(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof Byte;
    }
    
    public boolean isChar(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof Character;
    }
    
    public boolean isInt(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof Integer;
    }
    
    public boolean isLong(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof Long;
    }
        
    public boolean isFloat(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof Float;
    }
    
    public boolean isDouble(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof Double;
    }
    
    public boolean isString(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof String;
    }
    
    
    public boolean isByteArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof byte[];
    }
    
    public boolean isCharArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof char[];
    }
    
    public boolean isBooleanArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof boolean[];
    }
        
    public boolean isShortArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof short[];
    }
        
    public boolean isIntArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof int[];
    }
        
    public boolean isLongArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof long[];
    }
    
    public boolean isFloatArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof float[];
    }
    
    public boolean isDoubleArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof double[];
    }
    
    public boolean isStringArray(int argument_index){
        return !outOfRange(argument_index) && arguments[argument_index] instanceof String[];
    }
        
    public byte getByte(int argument_index) {
        return (byte) arguments[argument_index];
    }
    
    
    public char getChar(int argument_index) {
        return (char) arguments[argument_index];
    }

    
    public boolean getBoolean(int argument_index) {
        return (boolean) arguments[argument_index];
    }
    
    public short getShort(int argument_index) {
        return (short) arguments[argument_index];
    }
    
    public int getInt(int argument_index) {
        return (int) arguments[argument_index];
    }
    
    public long getLong(int argument_index) {
        return (long) arguments[argument_index];
    }
    
    public float getFloat(int argument_index) {
        return (float) arguments[argument_index];
    }
    
    public double getDouble(int argument_index) {
        return (double) arguments[argument_index];
    }
    
    public String getString(int argument_index) {
        return (String) arguments[argument_index];
    }
        
    public byte[] getByteArray(int argument_index) {
        return (byte[]) arguments[argument_index];
    }    

    
    public char[] getCharArray(int argument_index) {
        return (char[]) arguments[argument_index];
    }

    
    public boolean[] getBooleanArray(int argument_index) {
        return (boolean[]) arguments[argument_index];
    }
    
    public short[] getShortArray(int argument_index) {
        return (short[]) arguments[argument_index];
    }
    
    public int[] getIntArray(int argument_index) {
        return (int[]) arguments[argument_index];
    }
    
    public long[] getLongArray(int argument_index) {
        return (long[]) arguments[argument_index];
    }
    
    public float[] getFloatArray(int argument_index) {
        return (float[]) arguments[argument_index];
    }
    
    public double[] getDoubleArray(int argument_index) {
        return (double[]) arguments[argument_index];
    }

    
    public String[] getStringArray(int argument_index) {
        return (String[]) arguments[argument_index];
    }

    public Object[] getArguments() {
        return arguments;
    }

}
