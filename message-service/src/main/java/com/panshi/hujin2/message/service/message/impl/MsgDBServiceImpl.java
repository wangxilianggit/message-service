package com.panshi.hujin2.message.service.message.impl;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.util.DozerUtils;
import com.panshi.hujin2.base.domain.page.Page;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.TransactionIdBuilder;
import com.panshi.hujin2.message.dao.mapper.message.ApplicationsMapper;
import com.panshi.hujin2.message.dao.mapper.message.CountryConfigMapper;
import com.panshi.hujin2.message.dao.mapper.message.*;
import com.panshi.hujin2.message.dao.model.*;
import com.panshi.hujin2.message.domain.dto.MsgTemplateCountryDTO;
import com.panshi.hujin2.message.domain.dto.MsgTemplateDTO;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.MsgBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateCountryQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateQO;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by shenjiankang on 2018/6/22 16:33
 *
 * 消息类数据库操作服务
 */
@Service
public class MsgDBServiceImpl implements IMsgDBService {

    private static Logger LOGGER = LoggerFactory.getLogger(MsgDBServiceImpl.class);

    @Autowired
    private MessageSendRecordMapper messageSendRecordMapper;

    @Autowired
    private MsgResponseResultMapper msgResponseResultMapper;

    @Autowired
    private SubmailMsgSubhookMapper submailMsgSubhookMapper;

    @Autowired
    private MsgBusinessTypeMapper businessTypeMapper;

    @Autowired
    private MsgTemplateMapper templateMapper;

    @Autowired
    private MsgTemplateCountryMapper templateCountryMapper;

    @Autowired
    private CountryConfigMapper countryConfigMapper;

    @Autowired
    private InfobipMsgReportsMapper infobipMsgReportsMapper;

    @Autowired
    private ApplicationsMapper applicationsMapper;

    @Override
    public int addMsgSendRecord(MessageSendRecordInputBO inputBO,Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context,inputBO);
        MessageSendRecordDO record = DozerUtils.convert(inputBO, MessageSendRecordDO.class);
        return messageSendRecordMapper.insertSelective(record);
    }

    @Override
    public int addMsgResponseResult(MsgResponseResultInputBO inputBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context,inputBO,inputBO.getMobile());
        MsgResponseResult msgResponseResult = DozerUtils.convert(inputBO,MsgResponseResult.class);
        return msgResponseResultMapper.insertSelective(msgResponseResult);
    }

    @Override
    public int addSubmailMsgSubhook(SubmailMsgSubhookInputBO inputBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context,inputBO);
        SubmailMsgSubhook submailMsgSubhook = DozerUtils.convert(inputBO,SubmailMsgSubhook.class);
        return submailMsgSubhookMapper.insertSelective(submailMsgSubhook);
    }

    @Override
    public int addMsgBusinessType(MsgBusinessTypeInputBO inputBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context, inputBO);
        MsgBusinessType businessType = DozerUtils.convert(inputBO, MsgBusinessType.class);
        return businessTypeMapper.insertSelective(businessType);
    }

    @Override
    public int updateMsgBusinessType(MsgBusinessTypeUpdateBO updateBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context, updateBO, updateBO.getId());
        MsgBusinessType businessType = DozerUtils.convert(updateBO, MsgBusinessType.class);
        return businessTypeMapper.updateByPrimaryKeySelective(businessType);
    }

    @Override
    public List<MsgBusinessTypeOutputBO> queryMsgBusinessType(MsgBusinessTypeQO qo) {
        if(qo == null){
            qo = new MsgBusinessTypeQO();
        }
        //计算总记录数
        Integer count = businessTypeMapper.countMsgBusinessTypeByCondition(qo);
        Page page = qo.getPage();
        //设置总记录数
        page.setTotalNumber(count);
        List<MsgBusinessType> businessTypes = businessTypeMapper.queryMsgBusinessTypeByCondition(qo);
        if(businessTypes!=null && businessTypes.size()>0){
            List<MsgBusinessTypeOutputBO> outputBOList = DozerUtils.convertList(businessTypes,MsgBusinessTypeOutputBO.class);
            return outputBOList;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public int addMsgTemplate(MsgTemplateInputBO inputBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context,inputBO);
        MsgTemplate msgTemplate = DozerUtils.convert(inputBO, MsgTemplate.class);
        String templateCode = TransactionIdBuilder.getTransactionId();
        msgTemplate.setTemplateCode(templateCode);
        int insertCount = templateMapper.insertSelective(msgTemplate);

        List<MsgTemplateCountryInputBO> countryInputBOS = inputBO.getTemplateCountryInputBOS();
        if(countryInputBOS!=null && countryInputBOS.size()>0){
            List<MsgTemplateCountry> msgTemplateCountryList =
                    DozerUtils.convertList(countryInputBOS,MsgTemplateCountry.class);

            for(MsgTemplateCountry templateCountry : msgTemplateCountryList){
                if(templateCountry.getStatus() == null){
                    templateCountry.setStatus(true);
                }
                templateCountry.setTemplateCode(templateCode);
            }
            templateCountryMapper.insertBatch(msgTemplateCountryList);
        }
        return insertCount;
    }

    @Override
    @Transactional
    public void updateMsgTemplate(MsgTemplateUpdateBO updateBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context,updateBO,updateBO.getId());
        String templateCode = updateBO.getTemplateCode();
        ExceptionMessageUtils.verifyStringIsBlank(context, templateCode);
        MsgTemplate msgTemplate = DozerUtils.convert(updateBO, MsgTemplate.class);
        templateMapper.updateByPrimaryKeySelective(msgTemplate);

        List<MsgTemplateCountryUpdateBO> countryUpdateBOS = updateBO.getMsgTemplateCountryUpdateBOS();
        if(countryUpdateBOS!=null && countryUpdateBOS.size()>0){
            //todo  不是最佳， 应该找出不同的做修改，而不是全删全插
            templateCountryMapper.deleteByTemplateCode(templateCode);
            List<MsgTemplateCountry> templateCountrieList =
                    DozerUtils.convertList(countryUpdateBOS, MsgTemplateCountry.class);
            for (MsgTemplateCountry templateCountry:templateCountrieList){
                if(StringUtils.isBlank(templateCountry.getTemplateCode())){
                    templateCountry.setTemplateCode(templateCode);
                }

            }
            templateCountryMapper.insertBatch(templateCountrieList);
        }
    }

//    @Override
//    public List<MsgTemplateOutputBO> queryMsgTemplate(MsgTemplateQO qo) {
//        if(qo == null){
//            qo = new MsgTemplateQO();
//        }
//        //计算总记录条数
//        Integer count = templateMapper.countMsgTemplate(qo);
//
//        Page page = qo.getPage();
//        //设置总记录数
//        page.setTotalNumber(count);
//        List<MsgTemplate> templates = templateMapper.queryMsgTemplate(qo);
//        if(templates!=null && templates.size()>0){
//            List<MsgTemplateOutputBO> outputBOList = DozerUtils.convertList(templates, MsgTemplateOutputBO.class);
//
//            //todo  改成一对多的查询
//            for(MsgTemplateOutputBO templateOutputBO : outputBOList){
//                MsgTemplateCountryQO countryQO = new MsgTemplateCountryQO();
//                countryQO.setTemplateCode(templateOutputBO.getTemplateCode());
//                String msgText = qo.getMsgText();
//                if(msgText != null){
//                    countryQO.setMsgText(msgText);
//                }
//                List<MsgTemplateCountry> templateCountries = templateCountryMapper.queryMsgTemplateCountry(countryQO);
//                if(templateCountries!=null && templateCountries.size()>0){
//                    List<MsgTemplateCountryOutputBO> countryOutputBOS =
//                            DozerUtils.convertList(templateCountries,MsgTemplateCountryOutputBO.class);
//                    templateOutputBO.setTemplateCountryOutputBOS(countryOutputBOS);
//                }
//            }
//            return outputBOList;
//        }
//        return Collections.emptyList();
//    }

    @Override
    public List<MsgTemplateOutputBO> queryTemplate(MsgTemplateQO qo) {
        if(qo == null){
            qo = new MsgTemplateQO();
        }
        Integer count = templateMapper.countTemplate(qo);
        Page page = qo.getPage();
        page.setTotalNumber(count);
        List<MsgTemplateDTO> dtos = templateMapper.queryTemplate(qo);
        if(dtos!=null && dtos.size()>0){
            List<MsgTemplateOutputBO> outputBOList =
                    DozerUtils.convertList(dtos, MsgTemplateOutputBO.class);

            Map<String,List<MsgTemplateCountryOutputBO>> map = new HashMap<>();
            for (MsgTemplateDTO templateDTO : dtos){
                String templateCode = templateDTO.getTemplateCode();
                List<MsgTemplateCountryDTO> countryDTOS = templateDTO.getTemplateCountryDTOS();
                if(StringUtils.isNotBlank(templateCode)
                        && countryDTOS!=null
                        && countryDTOS.size()>0
                        ){
                    List<MsgTemplateCountryOutputBO> countryOutputBOS =
                            DozerUtils.convertList(countryDTOS, MsgTemplateCountryOutputBO.class);
                    map.put(templateCode, countryOutputBOS);
                }
            }
            for(MsgTemplateOutputBO outputBO : outputBOList){
                String templateCode = outputBO.getTemplateCode();
                if(map.containsKey(templateCode)){
                    outputBO.setTemplateCountryOutputBOS(map.get(templateCode));
                }

            }
            return outputBOList;
        }
        return Collections.emptyList();
    }


    //国际化短信内容
    @Override
    public int addMsgTemplateCountry(MsgTemplateCountryInputBO inputBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context, inputBO);
        MsgTemplateCountry templateCountry = DozerUtils.convert(inputBO,MsgTemplateCountry.class);
        return templateCountryMapper.insertSelective(templateCountry);
    }

    @Override
    public int updateMsgTemplateCountry(MsgTemplateCountryUpdateBO updateBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context, updateBO,updateBO.getId());
        MsgTemplateCountry templateCountry = DozerUtils.convert(updateBO, MsgTemplateCountry.class);
        return templateCountryMapper.updateByPrimaryKeySelective(templateCountry);
    }

    @Override
    public List<MsgTemplateCountryOutputBO> queryMsgTemplateCountry(MsgTemplateCountryQO qo) {
        if(qo == null){
            qo = new MsgTemplateCountryQO();
        }
        //记录总数
        Integer count = templateCountryMapper.countMsgTemplateCountry(qo);

        Page page = qo.getPage();
        page.setTotalNumber(count);
        List<MsgTemplateCountry> templateCountryList = templateCountryMapper.queryMsgTemplateCountry(qo);
        if(templateCountryList!=null && templateCountryList.size()>0){
            List<MsgTemplateCountryOutputBO> outputBOS =
                    DozerUtils.convertList(templateCountryList, MsgTemplateCountryOutputBO.class);
            return outputBOS;
        }
        return Collections.emptyList();
    }

    @Override
    public int addCountryConfig(CountryConfigInputBO inputBO, Context context) {
        try {
            ExceptionMessageUtils.verifyObjectIsNull(context, inputBO);
            CountryConfig countryConfig = DozerUtils.convert(inputBO, CountryConfig.class);
            return countryConfigMapper.insertSelective(countryConfig);
        }catch (Exception e){
            if (e instanceof org.springframework.dao.DuplicateKeyException){
                //违反唯一主键
                ExceptionMessageUtils.throwExceptionDataExists(context);
            }
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateCountryConfig(CountryConfigUpdateBO updateBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context, updateBO, updateBO.getId());
        CountryConfig countryConfig = DozerUtils.convert(updateBO, CountryConfig.class);
        return countryConfigMapper.updateByPrimaryKeySelective(countryConfig);
    }

    @Override
    public List<CountryConfigOutputBO> queryCountryConfig(CountryConfigQO qo) {
        if(qo == null)
            qo = new CountryConfigQO();
        //记录总数
        Integer count = countryConfigMapper.countCountryConfig(qo);
        Page page = qo.getPage();
        page.setTotalNumber(count);
        List<CountryConfig> countryConfigs = countryConfigMapper.queryCountryConfig(qo);
        if(countryConfigs!=null && countryConfigs.size()>0){
            List<CountryConfigOutputBO> outputBOS = DozerUtils.convertList(countryConfigs, CountryConfigOutputBO.class);
            return outputBOS;
        }
        return Collections.emptyList();
    }

    @Override
    public String getMsgTemplate(ApplicationEnmu appEnum, String templateCode, Context context) {
        ExceptionMessageUtils.verifyStringIsBlank(context, templateCode);
        String template = "";
        //获取短信模板
        MsgTemplateCountryQO qo = new MsgTemplateCountryQO();

        //从国际化上下文中获取语言和国家拼接CountryKey
//        String CountryKey = context.getLocale().getLanguage() + "_" + context.getLocale().getCountry();
        String countryKey = String.valueOf(context.getLocale());
        qo.setCountryKey(countryKey);
        qo.setTemplateCode(templateCode);
        List<MsgTemplateCountryOutputBO> templateCountryOutputBOS = queryMsgTemplateCountry(qo);
        if(templateCountryOutputBOS!=null && templateCountryOutputBOS.size()>0){
            MsgTemplateCountryOutputBO templateCountryOutputBO = templateCountryOutputBOS.get(0);
            if(templateCountryOutputBO.getStatus()){
                String msgText = templateCountryOutputBO.getMsgText();
                if(StringUtils.isBlank(msgText)){
                    //取默认模板
                    //todo 优化。。考虑用递归？要是模板没配置好，可能会无限递归
                    qo.setCountryKey(ApplicationEnmu.getByCode(appEnum.getCode()).getI18n());
                    List<MsgTemplateCountryOutputBO> templateCountryOutputBOS1 = queryMsgTemplateCountry(qo);
                    if(templateCountryOutputBOS1!=null && templateCountryOutputBOS1.size()>0){
                        MsgTemplateCountryOutputBO templateCountryOutputBO1 = templateCountryOutputBOS1.get(0);
                        if(templateCountryOutputBO1.getStatus()){
                            return templateCountryOutputBO1.getMsgText();
                        }else {
                            //模板已失效
                            ExceptionMessageUtils.throwExceptionTemplateLose(context);
                        }
                    }else {
                        //模板不存在
                        ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
                    }
                }
                template = msgText;
            }else{
                //模板已失效
                ExceptionMessageUtils.throwExceptionTemplateLose(context);
            }
        }else {
            //取默认模板
            qo.setCountryKey(ApplicationEnmu.getByCode(appEnum.getCode()).getI18n());
            List<MsgTemplateCountryOutputBO> templateCountryOutputBOS1 = queryMsgTemplateCountry(qo);
            if(templateCountryOutputBOS1!=null && templateCountryOutputBOS1.size()>0){
                MsgTemplateCountryOutputBO templateCountryOutputBO1 = templateCountryOutputBOS1.get(0);
                if(templateCountryOutputBO1.getStatus()){
                    return templateCountryOutputBO1.getMsgText();
                }else {
                    //模板已失效
                    ExceptionMessageUtils.throwExceptionTemplateLose(context);
                }
            }else {
                //模板不存在
                ExceptionMessageUtils.throwExceptionTemplateNotExist(context);
            }
        }
        return template;
    }

    @Override
    public int addInfobipMsgReports(InfobipMsgReportsInputBO inputBO, Context context) {
        ExceptionMessageUtils.verifyObjectIsNull(context, inputBO);
        InfobipMsgReports infobipMsgReports = DozerUtils.convert(inputBO,InfobipMsgReports.class);
        return infobipMsgReportsMapper.insertSelective(infobipMsgReports);
    }

    @Override
    public List<ApplicationsOutputBO> queryApplications() {
        //todo  如果以後app應用多了要改成分頁
        List<Applications> appList = applicationsMapper.selectByExample(null);
        if(appList != null && appList.size()>0){
            List<ApplicationsOutputBO> outputBOS = DozerUtils.convertList(appList, ApplicationsOutputBO.class);
            return outputBOS;
        }
        return Collections.emptyList();
    }


}
