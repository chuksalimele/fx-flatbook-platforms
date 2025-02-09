/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.trade.ui;

import java.text.DecimalFormat;

/**
 *
 * @author user
 */
public class SpreadRenderer extends BaseTableRenderer{

    DecimalFormat decimalFormat = new DecimalFormat("#");


    @Override
    protected void setValue(Object value) {
        double amt = (double)value;
        String strAmt = decimalFormat.format(amt);
        super.setValue(strAmt);
    }

}