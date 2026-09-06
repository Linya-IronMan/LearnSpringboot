package com.study.spring;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. 读取配置
 * 2. 扫描包下所有带了@Controller、@Service、@Component等注解的类
 * 3. 注册，记录一下所有的Bean信息
 * 4. 实例化Bean
 */
public class BeanFactory {

    // Bean实例池子
    private final ConcurrentHashMap<String, Object> INSTANCES = new ConcurrentHashMap<>();

    // Bean的类信息
    private final ConcurrentHashMap<String, Class<?>> BEANS = new ConcurrentHashMap<>();

    /**
     * 注册Bean
     */
    public void regisBean(String beanName, Class<?> clazz) {
        BEANS.put(clazz.getName(), clazz);
    }

    /**
     * 获取Bean
     *
     * @param beanName Bean名称
     */
    public Object getBean(String beanName) throws Exception {
        // 1. 从池子里面获取，获取到了直接返回，实现单例
        Object bean = INSTANCES.get(beanName);
        if (bean != null) {
            return bean;
        }

        // 2. 池子中没有，则创建实例
        Class<?> clazz = BEANS.get(beanName);
        if (clazz == null) {
            throw new RuntimeException("Bean " + beanName + " 未定义");
        }

        // 3. 通过反射创建Bean实例，这里忽略构造参数的考虑
        bean = clazz.getDeclaredConstructor().newInstance();

        // 4. 处理DI
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Autowired和Resource注解自动注入
            if (field.isAnnotationPresent(Autowired.class)
                    || field.isAnnotationPresent(Resource.class)) {
                field.setAccessible(true);
                // 递归调用getBean获取实例，给字段赋值
                Object dependency = getBean(field.getName());
                field.set(bean, dependency);
            }
        }

        // 方法的自动调用
        // todo了这个就

        // 5. 放入Bean实例的池子，下次可以直接获取
        INSTANCES.put(beanName, bean);
        return bean;
    }
}
