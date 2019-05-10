package com.panshi.hujin2.message.controller.utils;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;


/**
 * create by shenjiankang on 2018/10/9 11:52
 * <p>
 * 巴西马甲包：0 eloan  1 myLoan
 */
public class VestUtils {

    public static ApplicationEnmu getAppEnumByVest(Integer vest) {
        ApplicationEnmu applicationEnmu = null;
        if (vest != null) {
            VestEnum vestEnum = VestEnum.getDefaultByCode(vest);
            if (VestEnum.VEST_0.equals(vestEnum)) {
                //eLoan 0
                applicationEnmu = ApplicationEnmu.BR_Eloan;
            } else if (VestEnum.VEST_1.equals(vestEnum)) {
                //myLoan 1
                applicationEnmu = ApplicationEnmu.BR_MY_LOAN;
            } else if (VestEnum.VEST_2.equals(vestEnum)) {
                //FlashLoan
                applicationEnmu = ApplicationEnmu.BR_FLASH_LOAN;
            } else if (VestEnum.VEST_3.equals(vestEnum)) {
                //SimpleLoan
                applicationEnmu = ApplicationEnmu.BR_SIMPLE_LOAN;
            } else if (VestEnum.VEST_4.equals(vestEnum)) {
                //WOWLoan
                applicationEnmu = ApplicationEnmu.BR_WOWLOAN;
            } else if (VestEnum.VEST_5.equals(vestEnum)) {
                //FreeLoan
                applicationEnmu = ApplicationEnmu.BR_FREELOAN;
            }
        }
        return applicationEnmu;
    }


}
