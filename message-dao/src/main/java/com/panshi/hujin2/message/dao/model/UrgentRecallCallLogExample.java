package com.panshi.hujin2.message.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UrgentRecallCallLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UrgentRecallCallLogExample() {
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

        public Criteria andExtendIdIsNull() {
            addCriterion("extend_id is null");
            return (Criteria) this;
        }

        public Criteria andExtendIdIsNotNull() {
            addCriterion("extend_id is not null");
            return (Criteria) this;
        }

        public Criteria andExtendIdEqualTo(String value) {
            addCriterion("extend_id =", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdNotEqualTo(String value) {
            addCriterion("extend_id <>", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdGreaterThan(String value) {
            addCriterion("extend_id >", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdGreaterThanOrEqualTo(String value) {
            addCriterion("extend_id >=", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdLessThan(String value) {
            addCriterion("extend_id <", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdLessThanOrEqualTo(String value) {
            addCriterion("extend_id <=", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdLike(String value) {
            addCriterion("extend_id like", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdNotLike(String value) {
            addCriterion("extend_id not like", value, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdIn(List<String> values) {
            addCriterion("extend_id in", values, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdNotIn(List<String> values) {
            addCriterion("extend_id not in", values, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdBetween(String value1, String value2) {
            addCriterion("extend_id between", value1, value2, "extendId");
            return (Criteria) this;
        }

        public Criteria andExtendIdNotBetween(String value1, String value2) {
            addCriterion("extend_id not between", value1, value2, "extendId");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeIsNull() {
            addCriterion("fee_time is null");
            return (Criteria) this;
        }

        public Criteria andFeeTimeIsNotNull() {
            addCriterion("fee_time is not null");
            return (Criteria) this;
        }

        public Criteria andFeeTimeEqualTo(Integer value) {
            addCriterion("fee_time =", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeNotEqualTo(Integer value) {
            addCriterion("fee_time <>", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeGreaterThan(Integer value) {
            addCriterion("fee_time >", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee_time >=", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeLessThan(Integer value) {
            addCriterion("fee_time <", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("fee_time <=", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeIn(List<Integer> values) {
            addCriterion("fee_time in", values, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeNotIn(List<Integer> values) {
            addCriterion("fee_time not in", values, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeBetween(Integer value1, Integer value2) {
            addCriterion("fee_time between", value1, value2, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("fee_time not between", value1, value2, "feeTime");
            return (Criteria) this;
        }

        public Criteria andEndDirectionIsNull() {
            addCriterion("end_direction is null");
            return (Criteria) this;
        }

        public Criteria andEndDirectionIsNotNull() {
            addCriterion("end_direction is not null");
            return (Criteria) this;
        }

        public Criteria andEndDirectionEqualTo(Integer value) {
            addCriterion("end_direction =", value, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionNotEqualTo(Integer value) {
            addCriterion("end_direction <>", value, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionGreaterThan(Integer value) {
            addCriterion("end_direction >", value, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_direction >=", value, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionLessThan(Integer value) {
            addCriterion("end_direction <", value, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionLessThanOrEqualTo(Integer value) {
            addCriterion("end_direction <=", value, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionIn(List<Integer> values) {
            addCriterion("end_direction in", values, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionNotIn(List<Integer> values) {
            addCriterion("end_direction not in", values, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionBetween(Integer value1, Integer value2) {
            addCriterion("end_direction between", value1, value2, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndDirectionNotBetween(Integer value1, Integer value2) {
            addCriterion("end_direction not between", value1, value2, "endDirection");
            return (Criteria) this;
        }

        public Criteria andEndReasonIsNull() {
            addCriterion("end_reason is null");
            return (Criteria) this;
        }

        public Criteria andEndReasonIsNotNull() {
            addCriterion("end_reason is not null");
            return (Criteria) this;
        }

        public Criteria andEndReasonEqualTo(Integer value) {
            addCriterion("end_reason =", value, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonNotEqualTo(Integer value) {
            addCriterion("end_reason <>", value, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonGreaterThan(Integer value) {
            addCriterion("end_reason >", value, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_reason >=", value, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonLessThan(Integer value) {
            addCriterion("end_reason <", value, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonLessThanOrEqualTo(Integer value) {
            addCriterion("end_reason <=", value, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonIn(List<Integer> values) {
            addCriterion("end_reason in", values, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonNotIn(List<Integer> values) {
            addCriterion("end_reason not in", values, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonBetween(Integer value1, Integer value2) {
            addCriterion("end_reason between", value1, value2, "endReason");
            return (Criteria) this;
        }

        public Criteria andEndReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("end_reason not between", value1, value2, "endReason");
            return (Criteria) this;
        }

        public Criteria andRecodingurlIsNull() {
            addCriterion("recodingUrl is null");
            return (Criteria) this;
        }

        public Criteria andRecodingurlIsNotNull() {
            addCriterion("recodingUrl is not null");
            return (Criteria) this;
        }

        public Criteria andRecodingurlEqualTo(String value) {
            addCriterion("recodingUrl =", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlNotEqualTo(String value) {
            addCriterion("recodingUrl <>", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlGreaterThan(String value) {
            addCriterion("recodingUrl >", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlGreaterThanOrEqualTo(String value) {
            addCriterion("recodingUrl >=", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlLessThan(String value) {
            addCriterion("recodingUrl <", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlLessThanOrEqualTo(String value) {
            addCriterion("recodingUrl <=", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlLike(String value) {
            addCriterion("recodingUrl like", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlNotLike(String value) {
            addCriterion("recodingUrl not like", value, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlIn(List<String> values) {
            addCriterion("recodingUrl in", values, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlNotIn(List<String> values) {
            addCriterion("recodingUrl not in", values, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlBetween(String value1, String value2) {
            addCriterion("recodingUrl between", value1, value2, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andRecodingurlNotBetween(String value1, String value2) {
            addCriterion("recodingUrl not between", value1, value2, "recodingurl");
            return (Criteria) this;
        }

        public Criteria andCallerIsNull() {
            addCriterion("caller is null");
            return (Criteria) this;
        }

        public Criteria andCallerIsNotNull() {
            addCriterion("caller is not null");
            return (Criteria) this;
        }

        public Criteria andCallerEqualTo(String value) {
            addCriterion("caller =", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotEqualTo(String value) {
            addCriterion("caller <>", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerGreaterThan(String value) {
            addCriterion("caller >", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerGreaterThanOrEqualTo(String value) {
            addCriterion("caller >=", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerLessThan(String value) {
            addCriterion("caller <", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerLessThanOrEqualTo(String value) {
            addCriterion("caller <=", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerLike(String value) {
            addCriterion("caller like", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotLike(String value) {
            addCriterion("caller not like", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerIn(List<String> values) {
            addCriterion("caller in", values, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotIn(List<String> values) {
            addCriterion("caller not in", values, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerBetween(String value1, String value2) {
            addCriterion("caller between", value1, value2, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotBetween(String value1, String value2) {
            addCriterion("caller not between", value1, value2, "caller");
            return (Criteria) this;
        }

        public Criteria andCalleeIsNull() {
            addCriterion("callee is null");
            return (Criteria) this;
        }

        public Criteria andCalleeIsNotNull() {
            addCriterion("callee is not null");
            return (Criteria) this;
        }

        public Criteria andCalleeEqualTo(String value) {
            addCriterion("callee =", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotEqualTo(String value) {
            addCriterion("callee <>", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeGreaterThan(String value) {
            addCriterion("callee >", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeGreaterThanOrEqualTo(String value) {
            addCriterion("callee >=", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeLessThan(String value) {
            addCriterion("callee <", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeLessThanOrEqualTo(String value) {
            addCriterion("callee <=", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeLike(String value) {
            addCriterion("callee like", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotLike(String value) {
            addCriterion("callee not like", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeIn(List<String> values) {
            addCriterion("callee in", values, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotIn(List<String> values) {
            addCriterion("callee not in", values, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeBetween(String value1, String value2) {
            addCriterion("callee between", value1, value2, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotBetween(String value1, String value2) {
            addCriterion("callee not between", value1, value2, "callee");
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

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
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