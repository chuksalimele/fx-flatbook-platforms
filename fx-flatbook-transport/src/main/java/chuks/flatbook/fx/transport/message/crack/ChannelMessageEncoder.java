/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport.message.crack;

import chuks.flatbook.fx.transport.message.ChannelMessage;
import chuks.flatbook.fx.transport.message.VarType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author user
 */
public class ChannelMessageEncoder extends MessageToMessageEncoder<ChannelMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChannelMessage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();

        //write one byte which represent message type
        buffer.writeByte(msg.getType().getValue());
        
        //write length of request-response-identifier
        String identifier = msg.getIdentifier();
        
        if(identifier != null && !identifier.isEmpty()){
            buffer.writeInt(identifier.length());
            buffer.writeBytes(identifier.getBytes());
        }else{
            buffer.writeInt(0);
        }
        
        
        buffer = encode0(buffer, msg.getArguments());
        out.add(buffer);
    }

    void writeArguPrefix(ByteBuf buffer, int variable_type, int byte_length, int array_length) {
        buffer.writeInt(variable_type);//write the vaiable type - whether int, float e.t.c
        buffer.writeInt(byte_length);//write number of bytes used to store the variable type
        buffer.writeInt(array_length); //write whether it is array or not. -1 means is not an array otherwise is an array
    }

    void writeByte(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.BYTE, Byte.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeByte((byte) argument);
        } else {//is an array
            byte[] arr = (byte[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeByte(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeChar(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.CHAR, Character.BYTES, array_length);

        if (array_length == -1) {//not an array           
            buffer.writeChar((char) argument);
        } else {//is an array
            char[] arr = (char[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeChar(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeBoolean(ByteBuf buffer, Object argument, int argument_index, int array_length) {
        //in the case of Boolean we will short as integer but mark it as Boolean
        writeArguPrefix(buffer, VarType.BOOLEAN, Integer.BYTES, array_length);

        if (array_length == -1) {//not an array
            int bool = ((boolean) argument)? 1 : 0; //Convert to interger or Zero or One
            //NOTE: for boolean we will store as integer because 
            //theER is no writeBoolean method on the buffer object
            buffer.writeInt(bool);//Yes in the case of boolean
        } else {//is an array
            boolean[] arr = (boolean[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeBoolean(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeShort(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.SHORT, Short.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeShort((short) argument);
        } else {//is an array
            short[] arr = (short[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeShort(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeInt(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.INT, Integer.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeInt((int) argument);
        } else {//is an array
            int[] arr = (int[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeInt(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeLong(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.LONG, Long.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeLong((long) argument);
        } else {//is an array
            long[] arr = (long[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeLong(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeFloat(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.FLOAT, Float.BYTES, array_length);

        if (array_length == -1) {//not an array                    
            buffer.writeFloat((float) argument);
        } else {//is an array
            float[] arr = (float[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeFloat(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeDouble(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.DOUBLE, Double.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeDouble((double) argument);
        } else {//is an array
            double[] arr = (double[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeDouble(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }

    }

    void writeString(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        if (array_length == -1) {//not an array
            if (argument == null) {
                argument = "";
            }
            int str_byte_len = ((String) argument).getBytes(StandardCharsets.UTF_8).length;
            writeArguPrefix(buffer, VarType.STRING, str_byte_len, array_length);
            buffer.writeBytes(((String) argument).getBytes());
        } else {//is an array
            writeArguPrefix(buffer, VarType.STRING, 0, array_length);
            String[] arr = (String[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeString(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }

    }

    private ByteBuf encode0(ByteBuf buffer, Object... varargs) {

        //first write the number of arguments
        buffer.writeInt(varargs.length);

        for (int i = 0; i < varargs.length; i++) {

            if (varargs[i] instanceof Byte) {
                writeByte(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Character) {
                writeChar(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Boolean) {
                writeBoolean(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Short) {
                writeShort(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Integer) {
                writeInt(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Long) {
                writeLong(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Float) {
                writeFloat(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Double) {
                writeDouble(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof String) {
                writeString(buffer, varargs[i], i, -1);
            }

            if (varargs[i] instanceof byte[]) {
                writeByte(buffer, varargs[i], i, ((byte[]) varargs[i]).length);
            } else if (varargs[i] instanceof char[]) {
                writeChar(buffer, varargs[i], i, ((char[]) varargs[i]).length);
            } else if (varargs[i] instanceof boolean[]) {
                writeBoolean(buffer, varargs[i], i, ((boolean[]) varargs[i]).length);
            } else if (varargs[i] instanceof short[]) {
                writeShort(buffer, varargs[i], i, ((short[]) varargs[i]).length);
            } else if (varargs[i] instanceof int[]) {
                writeInt(buffer, varargs[i], i, ((int[]) varargs[i]).length);
            } else if (varargs[i] instanceof long[]) {
                writeLong(buffer, varargs[i], i, ((long[]) varargs[i]).length);
            } else if (varargs[i] instanceof float[]) {
                writeFloat(buffer, varargs[i], i, ((float[]) varargs[i]).length);
            } else if (varargs[i] instanceof double[]) {
                writeDouble(buffer, varargs[i], i, ((double[]) varargs[i]).length);
            } else if (varargs[i] instanceof String[]) {
                writeString(buffer, varargs[i], i, ((String[]) varargs[i]).length);
            }

        }

        return buffer;
    }
}
