/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport.message.crack;

import chuks.flatbook.fx.transport.message.ChannelMessage;
import chuks.flatbook.fx.transport.message.MessageFactory;
import chuks.flatbook.fx.transport.message.MessageType;
import chuks.flatbook.fx.transport.message.VarType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;
import java.util.List;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class ChannelMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //read the message type
        byte typeValue = in.readByte();
        MessageType type = MessageType.fromValue(typeValue);

        //read the length of resquest-response-identifier
        int len_identifier = in.readInt();
        
        //read the resquest-response-identifier
        String str_identifier = null;
        if(len_identifier>0){
            byte[] identifier_bytes = new byte[len_identifier];
            in.readBytes(identifier_bytes);
            str_identifier = new String(identifier_bytes);
        }
        
        //read the number of arguments     
        int argument_length = in.readInt();
        Object[] arguments = new Object[argument_length];
        decode0(in, arguments);
        ChannelMessage msg = MessageFactory.create(type, str_identifier).assign(arguments);
        out.add(msg);
    }

    private void decode0(ByteBuf buffer, Object[] arguments) {

        for (int i = 0; i < arguments.length; i++) {
            //read the variable type
            int variable_type = buffer.readInt();

            //read number of byte used to store the variable type
            int byte_length = buffer.readInt();

            //check if is array by read array length. -1 means it is not an array
            int array_length = buffer.readInt();

            switch (variable_type) {
                case VarType.BYTE ->
                    arguments[i] = readByte(buffer, byte_length, array_length);
                case VarType.CHAR ->
                    arguments[i] = readChar(buffer, byte_length, array_length);
                case VarType.BOOLEAN ->
                    arguments[i] = readBoolean(buffer, byte_length, array_length);
                case VarType.SHORT ->
                    arguments[i] = readShort(buffer, byte_length, array_length);
                case VarType.INT ->
                    arguments[i] = readInt(buffer, byte_length, array_length);
                case VarType.LONG ->
                    arguments[i] = readLong(buffer, byte_length, array_length);
                case VarType.FLOAT ->
                    arguments[i] = readFloat(buffer, byte_length, array_length);
                case VarType.DOUBLE ->
                    arguments[i] = readDouble(buffer, byte_length, array_length);
                case VarType.STRING ->
                    arguments[i] = readString(buffer, byte_length, array_length);
                default -> {
                }
            }

        }

    }

    private Object readByte(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readByte();
        } else {// is an array

            byte[] arr = new byte[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (byte) readByte(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readChar(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readChar();
        } else {// is an array

            char[] arr = new char[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (char) readChar(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readBoolean(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            //NOTE: in the case of boolean we will read as integer
            int var = buffer.readInt();
            return var == 1; //else zero since we expect 1 or 0            
        } else {// is an array

            boolean[] arr = new boolean[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (boolean) readBoolean(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readShort(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readShort();
        } else {// is an array

            short[] arr = new short[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (short) readShort(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readInt(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readInt();
        } else {// is an array

            int[] arr = new int[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (int) readInt(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readLong(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readLong();
        } else {// is an array

            long[] arr = new long[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (long) readLong(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readFloat(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readFloat();
        } else {// is an array

            float[] arr = new float[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (float) readFloat(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readDouble(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readDouble();
        } else {// is an array

            double[] arr = new double[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (double) readDouble(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readString(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            //for the case of string we will get the byte from 
            //the current readable positon with the specified length
            //and then skip the lenght for next read

            ByteBuf buff = buffer.readBytes(byte_length);

            return buff.toString(StandardCharsets.UTF_8);

        } else {// is an array

            String[] arr = new String[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (String) readString(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

}
