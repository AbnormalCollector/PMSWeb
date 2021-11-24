package com.sy.util;

import java.lang.annotation.*;
/**
 * 配合DispathCherServlet使用
 * 把注解卸载类和方法上，如：
 *@RequestMapping("/file")
 * public class FileServlet {}
 * @RequestMapping("/add.do")
 *     public void fileAdd() throws Exception {}
 * */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
