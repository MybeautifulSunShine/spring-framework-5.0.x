###  Spring源码  第一课

**重点 :接口干嘛的? 2 怎么使用的? 以及Spring重要的几个类的作用** 	    

代码入口   

基本了解    查看Spring代码的风格 

​	找到bean和mapList 的方法  

​	BeanDefinition.java 类了解

​	Java当中万物皆对象-->Spring就相当于java里面的类读取器

​	Java当中用 Class 描述 而, spring当中 是用bean来表示的(也就是BeanDefinition) 也就是描述这个类的东西 

​	read 扫描器   和scan扫描器 相当于registe方法   

```java
public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(); 
    	//注册 
		applicationContext.register(Appconfig.class);
		//刷新
    	applicationContext.refresh();
		//获取 调用 
    	IndexDao bean = applicationContext.getBean(IndexDao.class);
		bean.query();
} 
AnnotationConfigApplicationContext ->  
    /**
	 * 这个构造方法需要传入一个被javaconfig注解了的配置类
	 * 然后会把这个被注解了javaconfig的类通过注解读取器读取后继而解析
	 * Create a new AnnotationConfigApplicationContext, deriving bean definitions
	 * from the given annotated classes and automatically refreshing the context.
	 * @param annotatedClasses one or more annotated classes,
	 * e.g. {@link Configuration @Configuration} classes
	 */
	public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
		//annotatedClasses  appconfig.class
		//重点: 这里由于他有父类，故而会先调用父类的构造方法，然后才会调用自己的构造方法
		//在自己构造方法中初始一个读取器和扫描器
		this();
		register(annotatedClasses);
		refresh();
	} 
```

Spring提供的扩展点,怎么样让代理对象 

对应着代码理解 : 

​		