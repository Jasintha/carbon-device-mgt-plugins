/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.device.mgt.mobile.windows.api.services.syncml.beans;

/**
 * Bean for device updateLockOperation screen passcode policy.
 */
public class PasscodePolicy extends BasicOperation {

    private int maxFailedAttempts;
    private int minLength;
    private int pinHistory;
    private int minComplexChars;
    private int maxPINAgeInDays;
    private boolean requireAlphanumeric;
    private boolean allowSimple;
    private boolean enablePassword;
    private int maxInactiveTime;

    public int getMaxInactiveTime() {
        return maxInactiveTime;
    }

    public void setMaxInactiveTime(int maxInactiveTime) {
        this.maxInactiveTime = maxInactiveTime;
    }

    public boolean isEnablePassword() {
        return enablePassword;
    }

    public void setEnablePassword(boolean enablePassword) {
        this.enablePassword = enablePassword;
    }

    public int getMaxFailedAttempts() {
        return maxFailedAttempts;
    }

    public void setMaxFailedAttempts(int maxFailedAttempts) {
        this.maxFailedAttempts = maxFailedAttempts;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getPinHistory() {
        return pinHistory;
    }

    public void setPinHistory(int pinHistory) {
        this.pinHistory = pinHistory;
    }

    public int getMinComplexChars() {
        return minComplexChars;
    }

    public void setMinComplexChars(int minComplexChars) {
        this.minComplexChars = minComplexChars;
    }

    public int getMaxPINAgeInDays() {
        return maxPINAgeInDays;
    }

    public void setMaxPINAgeInDays(int maxPINAgeInDays) {
        this.maxPINAgeInDays = maxPINAgeInDays;
    }

    public boolean isRequireAlphanumeric() {
        return requireAlphanumeric;
    }

    public void setRequireAlphanumeric(boolean requireAlphanumeric) {
        this.requireAlphanumeric = requireAlphanumeric;
    }

    public boolean isAllowSimple() {
        return allowSimple;
    }

    public void setAllowSimple(boolean allowSimple) {
        this.allowSimple = allowSimple;
    }
}
