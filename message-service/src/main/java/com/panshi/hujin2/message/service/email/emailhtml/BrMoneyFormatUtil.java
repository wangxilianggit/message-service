package com.panshi.hujin2.message.service.email.emailhtml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author ycg 2018/12/1 16:52
 */
public class BrMoneyFormatUtil {
    public static String formatMoney(Double money) {
        if (money == null){
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(dfs);
        return decimalFormat.format(money);
    }
}
