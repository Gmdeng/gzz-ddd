package com.gzz.core.validation;

import java.util.Map;

/**
 * 校验结果
 */
public class ValidationResult {
    // 校验结果是否有错
    private boolean hasError = false;
    // 校验错误信息
    private Map<String, String> errors;

    public boolean isHasError() {
        return hasError;
    }

//    public void setHasError(boolean hasError) {
//        this.hasError = hasError;
//    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
        this.hasError = true;
    }
}