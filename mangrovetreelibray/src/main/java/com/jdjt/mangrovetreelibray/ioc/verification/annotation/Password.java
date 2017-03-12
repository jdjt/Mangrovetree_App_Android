/*
 * Copyright (C) 2012 Mobs and Geeks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.jdjt.mangrovetreelibray.ioc.verification.annotation;


import com.jdjt.mangrovetreelibray.ioc.verification.Rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate a password field. Passwords are always required.
 *
 * @author mars
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    public int order();
    public String message()     default "请输入密码";
    public int messageResId()   default 0;
    public int minLength()      default 0;
    public int maxLength()      default Integer.MAX_VALUE;
    public String pattern()     default Rules.REGEX_NUM_WORD;//默认匹配字母数字_和-;//".";//密码匹配任何字符
    public int patternResId()   default 0;
}
