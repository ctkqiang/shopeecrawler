package cn.ctkqiang.shopeecrawler.Controller;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 临时标记注解
 * 用于标记临时代码、方法或类，表示这些内容在将来可能会被修改或删除
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface Temporary {
    /**
     * 临时代码的描述信息
     */
    String value() default "";

    /**
     * 预计移除或修改的日期
     */
    String removeDate() default "";

    /**
     * 临时代码的原因
     */
    String reason() default "";

    /**
     * 负责人
     */
    String owner() default "";
}
