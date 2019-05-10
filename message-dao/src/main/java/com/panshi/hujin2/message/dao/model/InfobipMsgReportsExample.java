package com.panshi.hujin2.message.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfobipMsgReportsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InfobipMsgReportsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBulkIdIsNull() {
            addCriterion("bulk_id is null");
            return (Criteria) this;
        }

        public Criteria andBulkIdIsNotNull() {
            addCriterion("bulk_id is not null");
            return (Criteria) this;
        }

        public Criteria andBulkIdEqualTo(String value) {
            addCriterion("bulk_id =", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdNotEqualTo(String value) {
            addCriterion("bulk_id <>", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdGreaterThan(String value) {
            addCriterion("bulk_id >", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdGreaterThanOrEqualTo(String value) {
            addCriterion("bulk_id >=", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdLessThan(String value) {
            addCriterion("bulk_id <", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdLessThanOrEqualTo(String value) {
            addCriterion("bulk_id <=", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdLike(String value) {
            addCriterion("bulk_id like", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdNotLike(String value) {
            addCriterion("bulk_id not like", value, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdIn(List<String> values) {
            addCriterion("bulk_id in", values, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdNotIn(List<String> values) {
            addCriterion("bulk_id not in", values, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdBetween(String value1, String value2) {
            addCriterion("bulk_id between", value1, value2, "bulkId");
            return (Criteria) this;
        }

        public Criteria andBulkIdNotBetween(String value1, String value2) {
            addCriterion("bulk_id not between", value1, value2, "bulkId");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNull() {
            addCriterion("phone_number is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNotNull() {
            addCriterion("phone_number is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberEqualTo(String value) {
            addCriterion("phone_number =", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotEqualTo(String value) {
            addCriterion("phone_number <>", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThan(String value) {
            addCriterion("phone_number >", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThanOrEqualTo(String value) {
            addCriterion("phone_number >=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThan(String value) {
            addCriterion("phone_number <", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThanOrEqualTo(String value) {
            addCriterion("phone_number <=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLike(String value) {
            addCriterion("phone_number like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotLike(String value) {
            addCriterion("phone_number not like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIn(List<String> values) {
            addCriterion("phone_number in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotIn(List<String> values) {
            addCriterion("phone_number not in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberBetween(String value1, String value2) {
            addCriterion("phone_number between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotBetween(String value1, String value2) {
            addCriterion("phone_number not between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNull() {
            addCriterion("message_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNotNull() {
            addCriterion("message_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageIdEqualTo(String value) {
            addCriterion("message_id =", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotEqualTo(String value) {
            addCriterion("message_id <>", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThan(String value) {
            addCriterion("message_id >", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThanOrEqualTo(String value) {
            addCriterion("message_id >=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThan(String value) {
            addCriterion("message_id <", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThanOrEqualTo(String value) {
            addCriterion("message_id <=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLike(String value) {
            addCriterion("message_id like", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotLike(String value) {
            addCriterion("message_id not like", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdIn(List<String> values) {
            addCriterion("message_id in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotIn(List<String> values) {
            addCriterion("message_id not in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdBetween(String value1, String value2) {
            addCriterion("message_id between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotBetween(String value1, String value2) {
            addCriterion("message_id not between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andSentAtIsNull() {
            addCriterion("sent_at is null");
            return (Criteria) this;
        }

        public Criteria andSentAtIsNotNull() {
            addCriterion("sent_at is not null");
            return (Criteria) this;
        }

        public Criteria andSentAtEqualTo(String value) {
            addCriterion("sent_at =", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtNotEqualTo(String value) {
            addCriterion("sent_at <>", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtGreaterThan(String value) {
            addCriterion("sent_at >", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtGreaterThanOrEqualTo(String value) {
            addCriterion("sent_at >=", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtLessThan(String value) {
            addCriterion("sent_at <", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtLessThanOrEqualTo(String value) {
            addCriterion("sent_at <=", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtLike(String value) {
            addCriterion("sent_at like", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtNotLike(String value) {
            addCriterion("sent_at not like", value, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtIn(List<String> values) {
            addCriterion("sent_at in", values, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtNotIn(List<String> values) {
            addCriterion("sent_at not in", values, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtBetween(String value1, String value2) {
            addCriterion("sent_at between", value1, value2, "sentAt");
            return (Criteria) this;
        }

        public Criteria andSentAtNotBetween(String value1, String value2) {
            addCriterion("sent_at not between", value1, value2, "sentAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtIsNull() {
            addCriterion("done_at is null");
            return (Criteria) this;
        }

        public Criteria andDoneAtIsNotNull() {
            addCriterion("done_at is not null");
            return (Criteria) this;
        }

        public Criteria andDoneAtEqualTo(String value) {
            addCriterion("done_at =", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotEqualTo(String value) {
            addCriterion("done_at <>", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtGreaterThan(String value) {
            addCriterion("done_at >", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtGreaterThanOrEqualTo(String value) {
            addCriterion("done_at >=", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtLessThan(String value) {
            addCriterion("done_at <", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtLessThanOrEqualTo(String value) {
            addCriterion("done_at <=", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtLike(String value) {
            addCriterion("done_at like", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotLike(String value) {
            addCriterion("done_at not like", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtIn(List<String> values) {
            addCriterion("done_at in", values, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotIn(List<String> values) {
            addCriterion("done_at not in", values, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtBetween(String value1, String value2) {
            addCriterion("done_at between", value1, value2, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotBetween(String value1, String value2) {
            addCriterion("done_at not between", value1, value2, "doneAt");
            return (Criteria) this;
        }

        public Criteria andSmsCountIsNull() {
            addCriterion("sms_count is null");
            return (Criteria) this;
        }

        public Criteria andSmsCountIsNotNull() {
            addCriterion("sms_count is not null");
            return (Criteria) this;
        }

        public Criteria andSmsCountEqualTo(Integer value) {
            addCriterion("sms_count =", value, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountNotEqualTo(Integer value) {
            addCriterion("sms_count <>", value, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountGreaterThan(Integer value) {
            addCriterion("sms_count >", value, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sms_count >=", value, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountLessThan(Integer value) {
            addCriterion("sms_count <", value, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountLessThanOrEqualTo(Integer value) {
            addCriterion("sms_count <=", value, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountIn(List<Integer> values) {
            addCriterion("sms_count in", values, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountNotIn(List<Integer> values) {
            addCriterion("sms_count not in", values, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountBetween(Integer value1, Integer value2) {
            addCriterion("sms_count between", value1, value2, "smsCount");
            return (Criteria) this;
        }

        public Criteria andSmsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sms_count not between", value1, value2, "smsCount");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageIsNull() {
            addCriterion("price_per_message is null");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageIsNotNull() {
            addCriterion("price_per_message is not null");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageEqualTo(String value) {
            addCriterion("price_per_message =", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageNotEqualTo(String value) {
            addCriterion("price_per_message <>", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageGreaterThan(String value) {
            addCriterion("price_per_message >", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageGreaterThanOrEqualTo(String value) {
            addCriterion("price_per_message >=", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageLessThan(String value) {
            addCriterion("price_per_message <", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageLessThanOrEqualTo(String value) {
            addCriterion("price_per_message <=", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageLike(String value) {
            addCriterion("price_per_message like", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageNotLike(String value) {
            addCriterion("price_per_message not like", value, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageIn(List<String> values) {
            addCriterion("price_per_message in", values, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageNotIn(List<String> values) {
            addCriterion("price_per_message not in", values, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageBetween(String value1, String value2) {
            addCriterion("price_per_message between", value1, value2, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andPricePerMessageNotBetween(String value1, String value2) {
            addCriterion("price_per_message not between", value1, value2, "pricePerMessage");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("currency like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("currency not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCallbackDataIsNull() {
            addCriterion("callback_data is null");
            return (Criteria) this;
        }

        public Criteria andCallbackDataIsNotNull() {
            addCriterion("callback_data is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackDataEqualTo(String value) {
            addCriterion("callback_data =", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataNotEqualTo(String value) {
            addCriterion("callback_data <>", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataGreaterThan(String value) {
            addCriterion("callback_data >", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataGreaterThanOrEqualTo(String value) {
            addCriterion("callback_data >=", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataLessThan(String value) {
            addCriterion("callback_data <", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataLessThanOrEqualTo(String value) {
            addCriterion("callback_data <=", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataLike(String value) {
            addCriterion("callback_data like", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataNotLike(String value) {
            addCriterion("callback_data not like", value, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataIn(List<String> values) {
            addCriterion("callback_data in", values, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataNotIn(List<String> values) {
            addCriterion("callback_data not in", values, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataBetween(String value1, String value2) {
            addCriterion("callback_data between", value1, value2, "callbackData");
            return (Criteria) this;
        }

        public Criteria andCallbackDataNotBetween(String value1, String value2) {
            addCriterion("callback_data not between", value1, value2, "callbackData");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdIsNull() {
            addCriterion("status_group_id is null");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdIsNotNull() {
            addCriterion("status_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdEqualTo(Integer value) {
            addCriterion("status_group_id =", value, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdNotEqualTo(Integer value) {
            addCriterion("status_group_id <>", value, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdGreaterThan(Integer value) {
            addCriterion("status_group_id >", value, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_group_id >=", value, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdLessThan(Integer value) {
            addCriterion("status_group_id <", value, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("status_group_id <=", value, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdIn(List<Integer> values) {
            addCriterion("status_group_id in", values, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdNotIn(List<Integer> values) {
            addCriterion("status_group_id not in", values, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("status_group_id between", value1, value2, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("status_group_id not between", value1, value2, "statusGroupId");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameIsNull() {
            addCriterion("status_group_name is null");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameIsNotNull() {
            addCriterion("status_group_name is not null");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameEqualTo(String value) {
            addCriterion("status_group_name =", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameNotEqualTo(String value) {
            addCriterion("status_group_name <>", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameGreaterThan(String value) {
            addCriterion("status_group_name >", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("status_group_name >=", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameLessThan(String value) {
            addCriterion("status_group_name <", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameLessThanOrEqualTo(String value) {
            addCriterion("status_group_name <=", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameLike(String value) {
            addCriterion("status_group_name like", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameNotLike(String value) {
            addCriterion("status_group_name not like", value, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameIn(List<String> values) {
            addCriterion("status_group_name in", values, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameNotIn(List<String> values) {
            addCriterion("status_group_name not in", values, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameBetween(String value1, String value2) {
            addCriterion("status_group_name between", value1, value2, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusGroupNameNotBetween(String value1, String value2) {
            addCriterion("status_group_name not between", value1, value2, "statusGroupName");
            return (Criteria) this;
        }

        public Criteria andStatusIdIsNull() {
            addCriterion("status_id is null");
            return (Criteria) this;
        }

        public Criteria andStatusIdIsNotNull() {
            addCriterion("status_id is not null");
            return (Criteria) this;
        }

        public Criteria andStatusIdEqualTo(Integer value) {
            addCriterion("status_id =", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotEqualTo(Integer value) {
            addCriterion("status_id <>", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdGreaterThan(Integer value) {
            addCriterion("status_id >", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_id >=", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLessThan(Integer value) {
            addCriterion("status_id <", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLessThanOrEqualTo(Integer value) {
            addCriterion("status_id <=", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdIn(List<Integer> values) {
            addCriterion("status_id in", values, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotIn(List<Integer> values) {
            addCriterion("status_id not in", values, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdBetween(Integer value1, Integer value2) {
            addCriterion("status_id between", value1, value2, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotBetween(Integer value1, Integer value2) {
            addCriterion("status_id not between", value1, value2, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusNameIsNull() {
            addCriterion("status_name is null");
            return (Criteria) this;
        }

        public Criteria andStatusNameIsNotNull() {
            addCriterion("status_name is not null");
            return (Criteria) this;
        }

        public Criteria andStatusNameEqualTo(String value) {
            addCriterion("status_name =", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotEqualTo(String value) {
            addCriterion("status_name <>", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameGreaterThan(String value) {
            addCriterion("status_name >", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameGreaterThanOrEqualTo(String value) {
            addCriterion("status_name >=", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameLessThan(String value) {
            addCriterion("status_name <", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameLessThanOrEqualTo(String value) {
            addCriterion("status_name <=", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameLike(String value) {
            addCriterion("status_name like", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotLike(String value) {
            addCriterion("status_name not like", value, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameIn(List<String> values) {
            addCriterion("status_name in", values, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotIn(List<String> values) {
            addCriterion("status_name not in", values, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameBetween(String value1, String value2) {
            addCriterion("status_name between", value1, value2, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusNameNotBetween(String value1, String value2) {
            addCriterion("status_name not between", value1, value2, "statusName");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionIsNull() {
            addCriterion("status_description is null");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionIsNotNull() {
            addCriterion("status_description is not null");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionEqualTo(String value) {
            addCriterion("status_description =", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionNotEqualTo(String value) {
            addCriterion("status_description <>", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionGreaterThan(String value) {
            addCriterion("status_description >", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("status_description >=", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionLessThan(String value) {
            addCriterion("status_description <", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionLessThanOrEqualTo(String value) {
            addCriterion("status_description <=", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionLike(String value) {
            addCriterion("status_description like", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionNotLike(String value) {
            addCriterion("status_description not like", value, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionIn(List<String> values) {
            addCriterion("status_description in", values, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionNotIn(List<String> values) {
            addCriterion("status_description not in", values, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionBetween(String value1, String value2) {
            addCriterion("status_description between", value1, value2, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andStatusDescriptionNotBetween(String value1, String value2) {
            addCriterion("status_description not between", value1, value2, "statusDescription");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdIsNull() {
            addCriterion("error_group_id is null");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdIsNotNull() {
            addCriterion("error_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdEqualTo(Integer value) {
            addCriterion("error_group_id =", value, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdNotEqualTo(Integer value) {
            addCriterion("error_group_id <>", value, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdGreaterThan(Integer value) {
            addCriterion("error_group_id >", value, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("error_group_id >=", value, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdLessThan(Integer value) {
            addCriterion("error_group_id <", value, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("error_group_id <=", value, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdIn(List<Integer> values) {
            addCriterion("error_group_id in", values, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdNotIn(List<Integer> values) {
            addCriterion("error_group_id not in", values, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("error_group_id between", value1, value2, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("error_group_id not between", value1, value2, "errorGroupId");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameIsNull() {
            addCriterion("error_group_name is null");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameIsNotNull() {
            addCriterion("error_group_name is not null");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameEqualTo(String value) {
            addCriterion("error_group_name =", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameNotEqualTo(String value) {
            addCriterion("error_group_name <>", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameGreaterThan(String value) {
            addCriterion("error_group_name >", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("error_group_name >=", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameLessThan(String value) {
            addCriterion("error_group_name <", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameLessThanOrEqualTo(String value) {
            addCriterion("error_group_name <=", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameLike(String value) {
            addCriterion("error_group_name like", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameNotLike(String value) {
            addCriterion("error_group_name not like", value, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameIn(List<String> values) {
            addCriterion("error_group_name in", values, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameNotIn(List<String> values) {
            addCriterion("error_group_name not in", values, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameBetween(String value1, String value2) {
            addCriterion("error_group_name between", value1, value2, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorGroupNameNotBetween(String value1, String value2) {
            addCriterion("error_group_name not between", value1, value2, "errorGroupName");
            return (Criteria) this;
        }

        public Criteria andErrorIdIsNull() {
            addCriterion("error_id is null");
            return (Criteria) this;
        }

        public Criteria andErrorIdIsNotNull() {
            addCriterion("error_id is not null");
            return (Criteria) this;
        }

        public Criteria andErrorIdEqualTo(Integer value) {
            addCriterion("error_id =", value, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdNotEqualTo(Integer value) {
            addCriterion("error_id <>", value, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdGreaterThan(Integer value) {
            addCriterion("error_id >", value, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("error_id >=", value, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdLessThan(Integer value) {
            addCriterion("error_id <", value, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdLessThanOrEqualTo(Integer value) {
            addCriterion("error_id <=", value, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdIn(List<Integer> values) {
            addCriterion("error_id in", values, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdNotIn(List<Integer> values) {
            addCriterion("error_id not in", values, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdBetween(Integer value1, Integer value2) {
            addCriterion("error_id between", value1, value2, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("error_id not between", value1, value2, "errorId");
            return (Criteria) this;
        }

        public Criteria andErrorNameIsNull() {
            addCriterion("error_name is null");
            return (Criteria) this;
        }

        public Criteria andErrorNameIsNotNull() {
            addCriterion("error_name is not null");
            return (Criteria) this;
        }

        public Criteria andErrorNameEqualTo(String value) {
            addCriterion("error_name =", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameNotEqualTo(String value) {
            addCriterion("error_name <>", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameGreaterThan(String value) {
            addCriterion("error_name >", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameGreaterThanOrEqualTo(String value) {
            addCriterion("error_name >=", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameLessThan(String value) {
            addCriterion("error_name <", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameLessThanOrEqualTo(String value) {
            addCriterion("error_name <=", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameLike(String value) {
            addCriterion("error_name like", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameNotLike(String value) {
            addCriterion("error_name not like", value, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameIn(List<String> values) {
            addCriterion("error_name in", values, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameNotIn(List<String> values) {
            addCriterion("error_name not in", values, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameBetween(String value1, String value2) {
            addCriterion("error_name between", value1, value2, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorNameNotBetween(String value1, String value2) {
            addCriterion("error_name not between", value1, value2, "errorName");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionIsNull() {
            addCriterion("error_description is null");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionIsNotNull() {
            addCriterion("error_description is not null");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionEqualTo(String value) {
            addCriterion("error_description =", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionNotEqualTo(String value) {
            addCriterion("error_description <>", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionGreaterThan(String value) {
            addCriterion("error_description >", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("error_description >=", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionLessThan(String value) {
            addCriterion("error_description <", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionLessThanOrEqualTo(String value) {
            addCriterion("error_description <=", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionLike(String value) {
            addCriterion("error_description like", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionNotLike(String value) {
            addCriterion("error_description not like", value, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionIn(List<String> values) {
            addCriterion("error_description in", values, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionNotIn(List<String> values) {
            addCriterion("error_description not in", values, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionBetween(String value1, String value2) {
            addCriterion("error_description between", value1, value2, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorDescriptionNotBetween(String value1, String value2) {
            addCriterion("error_description not between", value1, value2, "errorDescription");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentIsNull() {
            addCriterion("error_permanent is null");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentIsNotNull() {
            addCriterion("error_permanent is not null");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentEqualTo(Boolean value) {
            addCriterion("error_permanent =", value, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentNotEqualTo(Boolean value) {
            addCriterion("error_permanent <>", value, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentGreaterThan(Boolean value) {
            addCriterion("error_permanent >", value, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("error_permanent >=", value, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentLessThan(Boolean value) {
            addCriterion("error_permanent <", value, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentLessThanOrEqualTo(Boolean value) {
            addCriterion("error_permanent <=", value, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentIn(List<Boolean> values) {
            addCriterion("error_permanent in", values, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentNotIn(List<Boolean> values) {
            addCriterion("error_permanent not in", values, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentBetween(Boolean value1, Boolean value2) {
            addCriterion("error_permanent between", value1, value2, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andErrorPermanentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("error_permanent not between", value1, value2, "errorPermanent");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}