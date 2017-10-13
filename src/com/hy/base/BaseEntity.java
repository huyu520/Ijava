package com.hy.base;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by siyong.dxh on 2017/6/21.
 */
public class BaseEntity implements Serializable {

    public BaseEntity() {
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
