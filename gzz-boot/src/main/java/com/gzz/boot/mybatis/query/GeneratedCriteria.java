package com.gzz.boot.mybatis.query;

import java.util.ArrayList;
import java.util.List;

public class GeneratedCriteria {
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

    public GeneratedCriteria andIsNull() {
        addCriterion("id is null");
        return this;
    }

    public GeneratedCriteria andIsNotNull() {
        addCriterion("id is not null");
        return this;
    }

    public GeneratedCriteria andEqualTo(Long value) {
        addCriterion("id =", value, "id");
        return this;
    }

    public GeneratedCriteria andNotEqualTo(Long value) {
        addCriterion("id <>", value, "id");
        return this;
    }

    public GeneratedCriteria andGreaterThan(Long value) {
        addCriterion("id >", value, "id");
        return this;
    }

    public GeneratedCriteria andGreaterThanOrEqualTo(Long value) {
        addCriterion("id >=", value, "id");
        return this;
    }

    public GeneratedCriteria andLessThan(Long value) {
        addCriterion("id <", value, "id");
        return this;
    }

    public GeneratedCriteria andIdLessThanOrEqualTo(Long value) {
        addCriterion("id <=", value, "id");
        return this;
    }

    public GeneratedCriteria andIn(List<Long> values) {
        addCriterion("id in", values, "id");
        return this;
    }

    public GeneratedCriteria andNotIn(List<Long> values) {
        addCriterion("id not in", values, "id");
        return this;
    }

    public GeneratedCriteria andBetween(Long value1, Long value2) {
        addCriterion("id between", value1, value2, "id");
        return this;
    }

    public GeneratedCriteria andNotBetween(Long value1, Long value2) {
        addCriterion("id not between", value1, value2, "id");
        return this;
    }
}
