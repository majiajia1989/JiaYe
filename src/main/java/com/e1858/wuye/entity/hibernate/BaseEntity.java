package com.e1858.wuye.entity.hibernate;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 *<br><b>类描述:</b>
 *<pre>所示PO的父类</pre>
 *@see
 *@since
 */
@MappedSuperclass
public class BaseEntity implements Serializable
{
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
