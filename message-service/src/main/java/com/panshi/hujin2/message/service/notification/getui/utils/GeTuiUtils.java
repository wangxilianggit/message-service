package com.panshi.hujin2.message.service.notification.getui.utils;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.getui.GeTuiAppConfigEnum;
import com.panshi.hujin2.message.service.notification.getui.bo.GeTuiPushConfigInfoBO;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;

/**
 * create by shenjiankang on 2018/10/8 17:42
 */
public class GeTuiUtils {

    public static GeTuiPushConfigInfoBO getGeTuiPushConfigInfo(ApplicationEnmu appEnum, Context context){
        GeTuiPushConfigInfoBO infoBO = new GeTuiPushConfigInfoBO();
        switch (appEnum){
            case PAN_GUAN_JIA:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_MONEYMANAGER_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_MONEYMANAGER_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_MONEYMANAGER_MASTER_SECRET.getValue());
                break;
            case WU_YOU_DAI:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_MONEYRUSH_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_MONEYRUSH_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_MONEYRUSH_MASTER_SECRET.getValue());
                break;
            case BR_Eloan:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_ELOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_ELOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_ELOAN_MASTER_SECRET.getValue());
                break;
            case BR_MY_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_MYLOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_MYLOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_MYLOAN_MASTER_SECRET.getValue());
                break;
            case BR_FLASH_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_FLASHLOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_FLASHLOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_FLASHLOAN_MASTER_SECRET.getValue());
                break;
            case BR_SIMPLE_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_SIMPLELOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_SIMPLELOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_SIMPLELOAN_MASTER_SECRET.getValue());
                break;
            case BR_WOWLOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_WOWLOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_WOWLOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_WOWLOAN_MASTER_SECRET.getValue());
                break;
            case BR_FREELOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.BR_FREELOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.BR_FREELOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.BR_FREELOAN_MASTER_SECRET.getValue());
                break;
            case MX_I_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_ILOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_ILOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_ILOAN_MASTER_SECRET.getValue());
                break;
            case MX_MONEYR:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_MONEYR_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_MONEYR_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_MONEYR_MASTER_SECRET.getValue());
                break;
            case MX_M_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_MLOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_MONEYR_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_MLOAN_MASTER_SECRET.getValue());
                break;
            case MX_Y_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_YLOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_YLOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_YLOAN_MASTER_SECRET.getValue());
                break;
            case MX_U_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_ULOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_ULOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_ULOAN_MASTER_SECRET.getValue());
                break;
            case MX_HI_LOAN:
                infoBO.setAppId(GeTuiAppConfigEnum.MX_HILOAN_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.MX_HILOAN_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.MX_HILOAN_MASTER_SECRET.getValue());
                break;
            case VI_CASH_DOG:
                infoBO.setAppId(GeTuiAppConfigEnum.VI_CASH_DOG_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.VI_CASH_DOG_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.VI_CASH_DOG_MASTER_SECRET.getValue());
                break;
            case INA_CASH_KANGAROO:
                infoBO.setAppId(GeTuiAppConfigEnum.INA_CASH_KANGAROO_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.INA_CASH_KANGAROO_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.INA_CASH_KANGAROO_MASTER_SECRET.getValue());
                break;
            case INA_CASH_KANGAROO_2:
                infoBO.setAppId(GeTuiAppConfigEnum.INA_CASH_KANGAROO_2_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.INA_CASH_KANGAROO_2_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.INA_CASH_KANGAROO_2_MASTER_SECRET.getValue());
                break;
            case INA_CASH_KANGAROO_DEXTER:
                infoBO.setAppId(GeTuiAppConfigEnum.INA_CASH_KANGAROO_DEXTER_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.INA_CASH_KANGAROO_DEXTER_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.INA_CASH_KANGAROO_DEXTER_MASTER_SECRET.getValue());
                break;
            case VI_CASH_DOG_NEVERSOLDOUT:
                infoBO.setAppId(GeTuiAppConfigEnum.VI_CASH_DOG_NEVERSOLDOUT_APP_ID.getValue());
                infoBO.setAppKey(GeTuiAppConfigEnum.VI_CASH_DOG_NEVERSOLDOUT_APP_KEY.getValue());
                infoBO.setMasterSecret(GeTuiAppConfigEnum.VI_CASH_DOG_NEVERSOLDOUT_MASTER_SECRET.getValue());
                break;
            default:
                //参数错误异常
                System.err.println(" ======获取个推推送key错误======= ");
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
        }
        return infoBO;
    }
}
