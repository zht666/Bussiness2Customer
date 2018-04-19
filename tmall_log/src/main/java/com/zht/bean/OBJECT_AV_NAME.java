package com.zht.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/*
 * 注解
 */
@AllArgsConstructor //全参构造
@NoArgsConstructor  //无惨构造
@Data  //getter setter 方法
@Accessors(chain=true)  //用来配置lombok如何产生和显示getters和setters的方法
public class OBJECT_AV_NAME {

	private String shxm_mch;
	private String shxzh_mch;

	
	
}
/*
 * val : 和 scala 中 val 同名, 可以在运行时确定类型;

@NonNull : 注解在参数上, 如果该类参数为 null , 就会报出异常,  throw new NullPointException(参数名)

@Cleanup : 注释在引用变量前, 自动回收资源 默认调用 close() 方法

@Getter/@Setter : 注解在类上, 为类提供读写属性

@Getter(lazy=true) :

@ToString : 注解在类上, 为类提供 toString() 方法

@EqualsAndHashCode : 注解在类上, 为类提供 equals() 和 hashCode() 方法

@NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor : 注解在类上, 为类提供无参,有指定必须参数, 全参构造函数

@Data : 注解在类上, 为类提供读写属性, 此外还提供了 equals()、hashCode()、toString() 方法

@Value :

@Builder : 注解在类上, 为类提供一个内部的 Builder

@SneakThrows :

@Synchronized : 注解在方法上, 为方法提供同步锁

@Log :

@Log4j : 注解在类上, 为类提供一个属性名为 log 的 log4j 的日志对象

@Slf4j : 注解在类上, 为类提供一个属性名为 log 的 log4j 的日志对象



@Cleanup("dispose") org.eclipse.swt.widgets.CoolBar bar = new CoolBar(parent, 0);
@Cleanup InputStream in = new FileInputStream(args[0]);
@Cleanup OutputStream out = new FileOutputStream(args[1]);
 */
