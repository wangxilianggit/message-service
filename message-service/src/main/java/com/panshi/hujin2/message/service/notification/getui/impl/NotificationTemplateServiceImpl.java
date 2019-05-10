package com.panshi.hujin2.message.service.notification.getui.impl;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.common.util.DozerUtils;
import com.panshi.hujin2.base.domain.page.Page;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.common.utils.TransactionIdBuilder;
import com.panshi.hujin2.message.dao.mapper.message.ApplicationsMapper;
import com.panshi.hujin2.message.dao.mapper.message.CountryConfigMapper;
import com.panshi.hujin2.message.dao.mapper.notification.AppPushBusinessTypeMapper;
import com.panshi.hujin2.message.dao.mapper.notification.AppPushTemplateCountryMapper;
import com.panshi.hujin2.message.dao.mapper.notification.AppPushTemplateMapper;
import com.panshi.hujin2.message.dao.model.*;
import com.panshi.hujin2.message.domain.dto.AppPushTemplateCountryDTO;
import com.panshi.hujin2.message.domain.dto.AppPushTemplateDTO;
import com.panshi.hujin2.message.domain.enums.ApplicationDefaultLanguageEnum;
import com.panshi.hujin2.message.domain.exception.NotificationException;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushTemplateQO;
import com.panshi.hujin2.message.domain.qo.getui.TemplateQO;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * create by shenjiankang on 2018/7/7 11:29
 *
 * 推送模板
 */
@Service
public class NotificationTemplateServiceImpl implements INotificationTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTemplateServiceImpl.class);

    @Autowired
    private AppPushBusinessTypeMapper appPushBusinessTypeMapper;

    @Autowired
    private CountryConfigMapper countryConfigMapper;

    @Autowired
    private AppPushTemplateCountryMapper templateCountryMapper;

    @Autowired
    private AppPushTemplateMapper appPushTemplateMapper;

    @Autowired
    private ApplicationsMapper applicationsMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    @Override
    public List<ApplicationsOutputBO> queryApplications() {
        List<Applications> appList = applicationsMapper.selectByExample(null);
        if(appList != null && appList.size()>0){
            List<ApplicationsOutputBO> outputBOS = DozerUtils.convertList(appList, ApplicationsOutputBO.class);
            return outputBOS;
        }
        return Collections.emptyList();
    }

    @Override
    public int addBusinessType(AppPushBusinessTypeInputBO inputBO, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,inputBO,inputBO.getAppId());
        AppPushBusinessType record = DozerUtils.convert(inputBO,AppPushBusinessType.class);
        return appPushBusinessTypeMapper.insertSelective(record);
    }

    @Override
    public int updateBusinessType(AppPushBusinessTypeUpdateBO updateBO, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,updateBO,updateBO.getId());
        AppPushBusinessType record = DozerUtils.convert(updateBO,AppPushBusinessType.class);
        return appPushBusinessTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<AppPushBusinessTypeOutputBO> queryBusinessType(AppPushBusinessTypeQO qo) {
        if(qo == null){
            qo = new AppPushBusinessTypeQO();
        }
        //计算总记录条数
        Integer count = appPushBusinessTypeMapper.countBusinessTypeByCondition(qo);
        Page page = qo.getPage();
        //设置总记录数
        page.setTotalNumber(count);
        List<AppPushBusinessType> businessTypes = appPushBusinessTypeMapper.queryBusinessTypeByCondition(qo);
        if(businessTypes!=null && businessTypes.size()>0){
            List<AppPushBusinessTypeOutputBO> outputBOList = DozerUtils.convertList(businessTypes,AppPushBusinessTypeOutputBO.class);
            return outputBOList;
        }
        return Collections.emptyList();
    }

    @Override
    public int addCountryConfig(CountryConfigInputBO inputBO, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context, inputBO);
            CountryConfig countryConfig = DozerUtils.convert(inputBO, CountryConfig.class);
            return countryConfigMapper.insertSelective(countryConfig);
        }catch (Exception e){
            if (e instanceof org.springframework.dao.DuplicateKeyException){
                //违反唯一主键
                NotificationExceptionUtils.throwExceptionDataExists(context);
            }
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateCountryConfig(CountryConfigUpdateBO updateBO, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context, updateBO, updateBO.getId());
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
    public int updateTemplateCountry(AppPushTemplateCountryUpdateBO updateBO, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context, updateBO,updateBO.getId());
        AppPushTemplateCountry record = DozerUtils.convert(updateBO,AppPushTemplateCountry.class);
        return templateCountryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional
    public int addPushTemplate(AppPushTemplateInputBO inputBO, Context context) {
        try {
            NotificationExceptionUtils.verifyObjectIsNull(context,inputBO);
            AppPushTemplate record = DozerUtils.convert(inputBO,AppPushTemplate.class);
            String templateCode = TransactionIdBuilder.getTransactionId();
            record.setTemplateCode(templateCode);
            int insertCount = appPushTemplateMapper.insertSelective(record);
            // 新增国际化模板
            List<AppPushTemplateCountryInputBO> countryInputBOS = inputBO.getTemplateCountryInputBOS();
            if(countryInputBOS!=null && countryInputBOS.size()>0){
                List<AppPushTemplateCountry> templateCountryList =
                        DozerUtils.convertList(countryInputBOS, AppPushTemplateCountry.class);

                //sql拼接 批量插入
                for(AppPushTemplateCountry templateCountry : templateCountryList){
                    if(templateCountry.getStatus() == null){
                        templateCountry.setStatus(true);
                    }
                    templateCountry.setTemplateCode(templateCode);
                }
                templateCountryMapper.insertBatch(templateCountryList);


                //sqlsession BATCH模式 批量插入（很多坑，暂时先用xml sql拼接的方式）
                //提交模式为不自动提交
//                SqlSession sqlSession =
//                        sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
//                AppPushTemplateCountryMapper mapper = sqlSession.getMapper(AppPushTemplateCountryMapper.class);
//                for (int i = 0; i <templateCountryList.size(); i++){
//                    AppPushTemplateCountry appPushTemplateCountry = templateCountryList.get(i);
//                    appPushTemplateCountry.setTemplateCode(templateCode);
//                    mapper.insertSelective(appPushTemplateCountry);
//                    sqlSession.commit();
//                }
//                sqlSession.close();

            }
            return insertCount;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }finally {
            //todo finally中关闭 sqlsession
        }
    }

    @Override
    @Transactional
    public void updatePushTemplate(AppPushTemplateUpdateBO updateBO, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,updateBO,updateBO.getId());
        String templateCode = updateBO.getTemplateCode();
        NotificationExceptionUtils.verifyStringIsBlank(context, templateCode);
        AppPushTemplate record = DozerUtils.convert(updateBO,AppPushTemplate.class);
        appPushTemplateMapper.updateByPrimaryKeySelective(record);

        List<AppPushTemplateCountryUpdateBO> templateCountryUpdateBOS = updateBO.getAppPushTemplateCountryUpdateBOS();
        if(templateCountryUpdateBOS!=null && templateCountryUpdateBOS.size()>0){
            templateCountryMapper.deleteByTemplateCode(templateCode);
            List<AppPushTemplateCountry> templateCountryList =
                    DozerUtils.convertList(templateCountryUpdateBOS, AppPushTemplateCountry.class);
            for(AppPushTemplateCountry templateCountry: templateCountryList){
                if (StringUtils.isBlank(templateCountry.getTemplateCode())){
                    templateCountry.setTemplateCode(templateCode);
                }
            }
            templateCountryMapper.insertBatch(templateCountryList);
        }
    }

    @Override
    public List<AppPushTemplateOutputBO> queryPushTemplate(AppPushTemplateQO qo) {
        if(qo == null){
            qo = new AppPushTemplateQO();
        }
        //计算总记录条数
        Integer count = appPushTemplateMapper.countTemplateByCondition(qo);
        Page page = qo.getPage();
        //设置总记录数
        page.setTotalNumber(count);
        List<AppPushTemplate> templates = appPushTemplateMapper.queryTemplateByCondition(qo);
        if(templates!=null && templates.size()>0){
            List<AppPushTemplateOutputBO> outputBOList = DozerUtils.convertList(templates,AppPushTemplateOutputBO.class);
            return outputBOList;
        }
        return Collections.emptyList();
    }

    @Override
    public List<AppPushTemplateOutputBO> queryTemplate(TemplateQO qo) {
        if(qo == null){
            qo = new TemplateQO();
        }
        Integer count = appPushTemplateMapper.countTemplate(qo);
        Page page = qo.getPage();
        page.setTotalNumber(count);
        List<AppPushTemplateDTO> dtos = appPushTemplateMapper.queryTemplate(qo);
        if(dtos!=null && dtos.size()>0){
            List<AppPushTemplateOutputBO> outputBOS = DozerUtils.convertList(dtos, AppPushTemplateOutputBO.class);

            Map<String, List<AppPushTemplateCountryOutputBO>> map = new HashMap<>();
            for(AppPushTemplateDTO templateDTO : dtos){
                String templateCode = templateDTO.getTemplateCode();
                List<AppPushTemplateCountryDTO> countryDTOS = templateDTO.getTemplateCountryDTOS();
                if(StringUtils.isNotBlank(templateCode)
                        && countryDTOS!=null
                        && countryDTOS.size()>0){
                    List<AppPushTemplateCountryOutputBO> countryOutputBOS =
                            DozerUtils.convertList(countryDTOS, AppPushTemplateCountryOutputBO.class);
                    map.put(templateCode,countryOutputBOS);
                }
            }

            for (AppPushTemplateOutputBO outputBO : outputBOS){
                String templateCode = outputBO.getTemplateCode();
                if(map.containsKey(templateCode)){
                    outputBO.setTemplateCountryOutputBOS(map.get(templateCode));
                }
            }
            return outputBOS;
        }

        return Collections.emptyList();
    }

    @Override
    public AppPushTemplateOutputBO getPushTemplate(ApplicationEnmu appEnmu, String templateCode, Context context) {
        try {
            NotificationExceptionUtils.verifyStringIsBlank(context, templateCode);
            AppPushTemplateOutputBO outputBO = null;

            //主模板推送启用，状态有效
            AppPushTemplateQO qo = new AppPushTemplateQO();
            qo.setAppId(appEnmu.getCode());
            qo.setTemplateCode(templateCode);
            qo.setPushStatus(true);
            qo.setStatus(true);
            List<AppPushTemplate> templates = appPushTemplateMapper.queryTemplateByCondition(qo);
            if (templates != null && templates.size() > 0) {
                AppPushTemplate template = templates.get(0);
                if (!template.getPushStatus() || !template.getStatus()) {
                    //无效的模板
                    throw new NotificationException(MessageFactory.getMsg("G19990104",context.getLocale()));
                }
                outputBO = DozerUtils.convert(template, AppPushTemplateOutputBO.class);

                //国际化语言版的短信内容状态有效
                AppPushTemplateCountryExample example = new AppPushTemplateCountryExample();
                AppPushTemplateCountryExample.Criteria criteria = example.createCriteria();
                criteria.andTemplateCodeEqualTo(templateCode);
                criteria.andCountryKeyEqualTo(String.valueOf(context.getLocale()));
                criteria.andStatusEqualTo(true);
                List<AppPushTemplateCountry> templateCountryList = templateCountryMapper.selectByExample(example);
                if (templateCountryList != null && templateCountryList.size() > 0) {
                    AppPushTemplateCountry templateCountry = templateCountryList.get(0);
                    String title = templateCountry.getTitle();
                    String text = templateCountry.getText();
                    String textStyle = templateCountry.getTextStyle();
                    if (!templateCountry.getStatus()) {
                        //无效的模板
                        throw new NotificationException(MessageFactory.getMsg("G19990104",context.getLocale()));
                    }
                    if (StringUtils.isBlank(title)
                            || StringUtils.isBlank(text)
                            || StringUtils.isBlank(textStyle)){
                        //拿默认模板
                        List<AppPushTemplateCountry> res = getPushTemplateByCondition(templateCode,
                                                            ApplicationEnmu.getByCode(appEnmu.getCode()).getI18n(),
                                                            true);
                        if(res!=null && res.size()>0){
                            AppPushTemplateCountry defaultTemplate = res.get(0);
                            title = defaultTemplate.getTitle();
                            text = defaultTemplate.getText();
                            textStyle = defaultTemplate.getTextStyle();
                        }
                        if(StringUtils.isBlank(title)
                                || StringUtils.isBlank(text)
                                || StringUtils.isBlank(textStyle)){
                            NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
                        }

                    }
                    outputBO.setTitle(title);
                    outputBO.setText(text);
                    outputBO.setTextStyle(textStyle);
                    return outputBO;
                }else {
                    //todo 补偿默认模板的逻辑  冗余，优化
                    //如果模板是有的，但是找不到对应的国际化语言模板，那就给一个默认的国际化模板
                    // (两种取默认模板的办法，1.一对多找出来，这样只查询一次数据库；2.分2次查询)

//                    criteria.andCountryKeyEqualTo(ApplicationEnmu.getByCode(appEnmu.getCode()).getI18n());
//                    List<AppPushTemplateCountry> templateCountryList1 = templateCountryMapper.selectByExample(example);

                    List<AppPushTemplateCountry> templateCountryList1 = getPushTemplateByCondition(templateCode,
                                                            ApplicationEnmu.getByCode(appEnmu.getCode()).getI18n(),
                                                            true);
                    //默认的模板就不判空了，如果默认模板都找不到，那就是设计有问题
                    if(templateCountryList1!=null && templateCountryList1.size()>0){
                        AppPushTemplateCountry templateCountry = templateCountryList1.get(0);
                        String title = templateCountry.getTitle();
                        String text = templateCountry.getText();
                        String textStyle = templateCountry.getTextStyle();
                        if(StringUtils.isBlank(title)
                                || StringUtils.isBlank(text)
                                || StringUtils.isBlank(textStyle)){
                            NotificationExceptionUtils.throwExceptionTemplateNotExist(context);
                        }
                        outputBO.setTitle(title);
                        outputBO.setText(text);
                        outputBO.setTextStyle(textStyle);
                        return outputBO;
                    }
                }
            }
            return outputBO;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    private List<AppPushTemplateCountry> getPushTemplateByCondition(String templateCode,
                                                                    String countryKey,
                                                                    boolean status){
        AppPushTemplateCountryExample example = new AppPushTemplateCountryExample();
        AppPushTemplateCountryExample.Criteria criteria = example.createCriteria();
        criteria.andTemplateCodeEqualTo(templateCode);
        criteria.andCountryKeyEqualTo(countryKey);
        criteria.andStatusEqualTo(status);
        List<AppPushTemplateCountry> templateCountryList = templateCountryMapper.selectByExample(example);
        return templateCountryList;
    }


    /**
     * @description:        根据app获取对应默认的国际化上下文
     * @param applicationEnmu
     * @Author shenjiankang
     * @Date 2018/7/31 16:35
     */
    private Context getDefaultContext(ApplicationEnmu applicationEnmu){
        ApplicationDefaultLanguageEnum defaultLanguageEnum =
                ApplicationDefaultLanguageEnum.getByCode(applicationEnmu.getCode());
        Context defaultContext = new Context();
        defaultContext.setLocale(new Locale(defaultLanguageEnum.getLanguage(),defaultLanguageEnum.getCountry()));
        return defaultContext;
    }

}
