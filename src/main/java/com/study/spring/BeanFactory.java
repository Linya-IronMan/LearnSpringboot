package com.study.spring;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/// # 简化版 Bean 工厂
///
/// NOTE: `BeanFactory` 是为了演示 IoC（控制反转）和 DI（依赖注入）基本原理而编写的
/// 简化容器，不是 Spring 提供的 `org.springframework.beans.factory.BeanFactory`。
/// 当前 Spring Boot 应用也没有将它注册为 Spring Bean，因此应用实际运行时仍由
/// Spring 的 `ApplicationContext` 创建和管理组件。
///
/// 这个类演示了 Bean 容器最核心的几个步骤：
///
/// 1. 通过 `regisBean(...)` 登记 Bean 的类信息；
/// 2. 通过 `getBean(...)` 按名称查找 Bean；
/// 3. 首次获取时使用无参构造方法创建实例；
/// 4. 通过反射为带有 `@Autowired` 或 `@Resource` 的字段注入依赖；
/// 5. 缓存创建完成的实例，后续返回同一个对象。
///
/// ## 当前限制
///
/// - 不会自动扫描 `@Controller`、`@Service` 或 `@Component`；
/// - 只支持无参构造方法和字段注入；
/// - 依赖查找使用字段名称，不支持按类型、`@Qualifier` 或 `@Primary` 匹配；
/// - 实例在依赖注入完成后才进入缓存，因此不能处理循环依赖；
/// - `ConcurrentHashMap` 只保证集合操作的线程安全，当前“查找后创建”流程并不能
///   保证并发环境中只创建一次实例；
/// - 不支持初始化回调、销毁回调以及 AOP 代理等完整 Bean 生命周期能力。
public class BeanFactory {

    // 已完成创建和依赖注入的 Bean 实例缓存。
    private final ConcurrentHashMap<String, Object> INSTANCES = new ConcurrentHashMap<>();

    // Bean 名称与待实例化类型之间的注册信息。
    private final ConcurrentHashMap<String, Class<?>> BEANS = new ConcurrentHashMap<>();

    /// # 注册 Bean 类型
    ///
    /// 将类型信息保存到 Bean 定义集合中，供 `getBean(...)` 后续创建实例。
    ///
    /// 当前实现使用 `clazz.getName()`，也就是类的全限定名作为注册键；传入的
    /// `beanName` 参数尚未参与注册。这意味着调用 `getBean(...)` 时必须传入类的
    /// 全限定名，才能取得这里注册的类型。
    ///
    /// @param beanName 预期使用的 Bean 名称；当前实现暂未使用该参数
    /// @param clazz Bean 的具体类型，必须提供可访问的无参构造方法
    public void regisBean(String beanName, Class<?> clazz) {
        BEANS.put(clazz.getName(), clazz);
    }

    /// # 获取或创建 Bean
    ///
    /// 如果实例缓存中已经存在指定 Bean，则直接返回缓存对象；否则查找对应的类型，
    /// 使用反射创建实例，并递归解析其字段依赖。创建完成的对象会加入实例缓存，模拟
    /// Spring 默认的单例作用域。
    ///
    /// 注入依赖时使用字段名称作为 Bean 名称。例如字段名是 `testService`，容器就会
    /// 调用 `getBean("testService")`。由于 `regisBean(...)` 当前使用类的全限定名作为
    /// 注册键，这两种名称可能不一致并导致查找失败。
    ///
    /// @param beanName 用于查询实例缓存和 Bean 定义的名称
    /// @return 已存在或新创建的 Bean 实例
    /// @throws RuntimeException 找不到对应的 Bean 定义时抛出
    /// @throws Exception 反射创建实例或设置依赖字段失败时抛出
    public Object getBean(String beanName) throws Exception {
        // 1. 优先返回缓存实例，使同一名称通常获得同一个对象。
        Object bean = INSTANCES.get(beanName);
        if (bean != null) {
            return bean;
        }

        // 2. 缓存未命中时，查找此前注册的 Bean 类型。
        Class<?> clazz = BEANS.get(beanName);
        if (clazz == null) {
            throw new RuntimeException("Bean " + beanName + " 未定义");
        }

        // 3. 调用无参构造方法创建对象，暂不支持构造器参数注入。
        bean = clazz.getDeclaredConstructor().newInstance();

        // 4. 检查当前类声明的字段，并处理带注入注解的依赖。
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 这里只识别注解，不实现 Spring 对这些注解的完整解析规则。
            if (field.isAnnotationPresent(Autowired.class)
                    || field.isAnnotationPresent(Resource.class)) {
                // 私有字段默认不可由外部反射写入，需要先取消访问检查。
                field.setAccessible(true);

                // 以字段名查找依赖；依赖不存在或形成循环时，创建过程会失败。
                Object dependency = getBean(field.getName());
                field.set(bean, dependency);
            }
        }

        // 此处可以继续扩展初始化方法、BeanPostProcessor 等生命周期能力。

        // 5. 依赖注入完成后缓存实例，供后续查询复用。
        INSTANCES.put(beanName, bean);
        return bean;
    }
}
