###  Spring源码   

注入方式: 

byName byType 只是一个注入模型(找法不一样)   bytype 和构造方法两种方式 

```java
@Autowired //先通过类型 在通过name去找
```



只是一种写法 底层也是通过name type去找的

需求: spring当中一个对象被spring实例化之后就执行某些逻辑  

```java
1 @PostConstruct 
2 public class AnotherExampleBean implements InitializingBean {

    public void afterPropertiesSet() {
        // do some initialization work
    }
} 
3 //xml配置的方法 
    <bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/> 
        
  public class ExampleBean {

    public void init() {
        // do some initialization work
    }
}
```





我想当spring容器初始化完成之后里面执行逻辑 

###### 动态代理的简单实现(模拟MyBitis)

先来一个简单的接口 

```java
public interface UserDao {
   @Select("select * from xx ")
   public void qyery();
}
```

代理类 : 

```java
public class MyBeanFactory {
   public static Object getMapper(Class clazz) {
      /**
       * (ClassLoader loader, 动态加载一个类
       * Class<?>[] interfaces, 指定放回什么借口
       * InvocationHandler h  代理类实现接口后的具体的逻辑 实现类之后实现的类
       */
      Class[] classes = new Class[]{clazz};
      Object o = Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader(), classes, new MyInvocationHandler());
      return o;
   }
}
```

具体的实现 (我们的代理类实现其实是在这里面去实现的) 

```java
public class MyInvocationHandler implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		System.out.println("conn db");
		Select annotation = method.getAnnotation(Select.class);
		String sql = annotation.value()[0];
		System.out.println(sql);
		Class<?> returnType = method.getReturnType();
		return returnType;
	}
}
```

######  模拟Spring动态代理的具体实现 

1 首先我们上一个接口 我们的目的就是让不同的实现类,通过我们手动创建的代理对象去执行里面相对应的简单方法

```java
public interface ZLService {

   public void update(String s);
} 
//默认的实现 
public class ZLServiceImpl implements ZLService {
	@Override
	public void update(String s) {
		System.out.println("zl  logic" + s);
	}
} 
```

2接下来我们模拟一个动态代理的类,用它生成默认的java文件 并且编译成class文件 在通过加载器加载到类内存空间去 

```java
public class MyProxy {
	//先产生类文件
	public static Object getInstance(Object target) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Class clazz = target.getClass().getInterfaces()[0];
		String infName = clazz.getSimpleName();
		String content = "";
		String line = "\n";//换行
		String tab = "\t";//tab
		String packageContent = "package com.baidu;" + line;
		String importContent = "import " + clazz.getName() + ";" + line;
		String clazzFirstLineContent = "public class $ProxyHgj implements " + infName + "{" + line;
		String filedContent = tab + "private " + infName + " target;" + line;

		String constructorContent = tab + "public $ProxyHgj (" + infName + " target){" + line
				+ tab + tab + "this.target =target;"
				+ line + tab + "}" + line;


		String methodContent = "";
		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			//String
			String returnTypeName = method.getReturnType().getSimpleName();
			//query
			String methodName = method.getName();
			// [String.class===class]
			Class args[] = method.getParameterTypes();
			String argsContent = "";
			String paramsContent = "";
			int flag = 0;
			for (Class arg : args) {
				//String
				String temp = arg.getSimpleName();
				//String
				//String p0
				argsContent += temp + " p" + flag + ",";
				//p0
				paramsContent += "p" + flag + ",";
				flag++;
			}
			if (argsContent.length() > 0) {
				argsContent = argsContent.substring(0, argsContent.lastIndexOf(",") - 1);
				paramsContent = paramsContent.substring(0, paramsContent.lastIndexOf(",") - 1);
			}
			//public String query(String p0){
			//		System.out.println("log");
			//		target.query(p0);
			//	}

			methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ") {" + line
					+ tab + tab + "System.out.println(\"log\");" + line
					+ tab + tab + "target." + methodName + "(" + paramsContent + ");" + line
					+ tab + "}" + line;

		}

		content += packageContent + importContent + clazzFirstLineContent + filedContent + constructorContent + methodContent + "}";


		File file = new File("D:\\com\\baidu\\$ProxyHgj.java");

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.flush();
			fw.close();
		} catch (Exception e) {

		}

		/**
		 * 编译成class文件
		 */
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable units = fileMgr.getJavaFileObjects(file);

		JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
		t.call();
		//Class.forName()
		//fileMgr.close();
		//类是指定了包 名的所以不需要指定特殊的地址
		URL[] urls = new URL[]{new URL("file:D:\\\\")};
		URLClassLoader urlClassLoader = new URLClassLoader(urls);
		//
		Class cls = urlClassLoader.loadClass("com.baidu.$ProxyHgj");
		Constructor constructor = cls.getConstructor(clazz);
		Object o = constructor.newInstance(target);

		return o;

	}


}
```

3最后我们通过自己写的代理类去调用它的方法试一下是否能正常运行

```java
ZLService proxy = (ZLService) MyProxy.getInstance(new ZLServiceImpl());
			proxy.update("XXXX");
```

//具体的代理类去一期的动态代理 



######  BeanDefinition源码分析

bean spring容器 

一完整的生命
scan new --put map--bean
scan ---> 解析 parse-->验证validate(info) -->initBean 实例化初始化(生命周期)   

1扫描 

2解析  parse--beanDefinition obiect-- 存到 map<"xx",o> 

3验证validate  mape"xx", o1>  如果我们在validate期间 把这个o1改成别的bean 

4Life----(生命周期) 遍历map  得到beanDefinition 实例化o1 

单例池  BeanDefinition 是什么? 是对我们的普通的bean 用BeanDefinition 来定义Spring当中的Bean供给我们在程序或者Spring当中去使用  单例池和 bd池 都是存在map当中  **合并merge?**

每个 BeanDefinition的子类 的作用    

在实例化的时候为什么只是实例化了一个AnnotatedBeanDefinitionReader?因为需要根据它来实例化配置类也就是Appconfig.class 读取配置类BeanDefinitionRegistryPostProcessor--还没有完成扫描之前
没明白spring是怎么保证他自己的后置处理器先执行? spring提供的内置的beanFactoryPostProcessor ---- >ConfigurationClassPostProcessor
BeanFactoryPostProcessor bean工厂的后置外理器 干预bean工厂的工作流程 bean1厂 (DefaultListableBeanFactory)

**扫描包,怎么完成的扫描?**

**Spring提供jar包去生成类的索引**

**模拟mybitis的扫描**





Bean工厂的后置处理器--bean的后置处理器

初始化的处理器  实例化的处理器



46 理解了注册的时候得部分源码 类里面的执行逻辑以及为什么要这样执行 

**策略模式的代码逻辑块的理解也就是两个Bean工厂的执行时机** 



类图 :https://www.processon.com/view/link/5c15e10ae4b0ed122da86303

```java
org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List<org.springframework.beans.factory.config.BeanFactoryPostProcessor>)
```

@MapperScan的处理时机  新版与旧版的区别  

旧版 Spring扫描完成后立即执行 

因为新版的是实现了bdrpp这个接口的,它的执行时间被内定了 

org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry) 这个方法

Spring的三个扩展点: 

BeanDefinitionRegistry ,BeanFactoryPostProcessor ,ImportBeanDefinitionRegistrar 

执行时机 某个扩展点在哪里实现的,分别是怎么实现的 

1. 1 bean工厂的后置处理器 --->  BeanDefinitionRegistryPostProcessor  
   1首先执行程序员通过api提供----> ac.addBeanFactoryPostProcessor();
   2然后执行spring内置的 程序员提供有特点---> PriorityOrdered
   3程序员提供 设有任何特点X impl BeanDefinitionRegistryPostProcessor --- >postProcessBeanDefinitionRegist()

2. BeanFactoryPostProcessor
  1执行的是实现了 子类的 --->BeanDefinitionRegistryPostProcessor的BeanFactoryPostProcessor ----------->postProcessBeanfactory 

  ```java
  org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry
  ```

  2执行直接实现了BeanFactoryPostProcessorostProcessBeanFactory-分特点

  

bean的后置处理器 14个

![image-20201209151847197](https://i.loli.net/2020/12/09/FNrC7Q2UiWwXAep.png)

![image-20201209151939112](https://i.loli.net/2020/12/09/jnFxBd5qVUmR1sg.png)

所谓的代理就是产生一个新的类

每次创建bean的时候就把正在创建的bean存到一个集合当中 

@Bean的方式与  会把创建的Bean存在一个map中 

org.springframework.beans.factory.support.SimpleInstantiationStrategy#currentlyInvokedFactoryMethod    

会调用父类,而父类里面是创建e方法所有 ->当前正在创建的bean的方法是f()方法但是正在执行确实e()方法 

**代理模式 重点记住的是 代理方法的父类就是我们当前写的类**

也就是在内存中,我们的代理类一般是下面这样的 

```java
public $CglibA extend A{
    xxxx
}
```

所以当调用方法的时候一般指的是,是调用代理方法的父类

Spring在创建@Bean的时候时候回去判断当前类是不是全注解类,如果是的话就去使用cglib代理

保证单例的思路就是代理,如果你这个当前方法内调用其他的方法 会进行比对,也就是当前创建的方法和当前执行的方法是不是同一个,如果一样就会调用父类去创建bean 不一样的话就会调用自己的逻辑去创建bean

同时保存一个方法到线程里面 同时保存一个当前创建bean的方法是哪个 

一个存在set里面一个存在当前线程中

普通的@Conpont 有区别

所有的bd都扫描出来放到map当中  

```java
org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors  
    --->org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(java.util.Collection<? extends org.springframework.beans.factory.config.BeanFactoryPostProcessor>, org.springframework.beans.factory.config.ConfigurableListableBeanFactory) 
    --- > 判断要不要参数代理org.springframework.context.annotation.ConfigurationClassPostProcessor#enhanceConfigurationClasses 
```

也就是我们之前bdmap中把原来的appconfig的类,替换成我们的代理对象 proxy对象

 **Spring当中的循环引用**

​		1正在创建 

**Spring怎么体现当中默认支持循环依赖 -->关闭循环依赖**

**Spring在那一步完成的属性注入?** --->**SpringBean 的生命周期**

属性注入的时候 -spring bean的生命周期

1. 推断物造方法

1. 实例化一个对象
2. 属性注入
3. 生命周期回调初始化方法
4.  aop代理
   bean 

**重点 :接口干嘛的? 2 怎么使用的? 以及Spring重要的几个类的作用** 	    

org.springframework.beans.factory.ObjectFactory 半成品的bd 提前暴露工厂



```java
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#populateBean --属性注入 注解解析
	CommonAnnotationBeanFactoryProcess  --->处理@Resources
	AutowiredAnnotationTypes -->处理    @Autowired
```





基本了解    查看Spring代码的风格 

​	找到bean和mapList 的方法  

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

######  BeanDefinition.java 类了解   的作用 干嘛的?怎么去使用 ?

```
作用: 
 * BeanPostProcessor是Spring框架的提供的一个扩展类点（不止一个）
 * 通过实现BeanPostProcessor接口，程序员就可插手bean实例化的过程,从而减轻了beanFactory的负担
 * 值得说明的是这个接口可以设置多个，会形成一个列表，然后依次执行
 * (但是spring默认的怎么办？set)
 * 比如AOP就是在bean实例后期间将切面逻辑织入bean实例中的
 * AOP也正是通过BeanPostProcessor和IOC容器建立起了联系
```

```java
@Component
public class TestIndexProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")) {
			System.out.println("IndexDao实例化之前");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")) {
			System.out.println("IndexDao实例化之后");
		}
		return bean;
	}
} 
@Component
public class IndexDao {
	public IndexDao() {
		System.out.println("initIndexDao构造!!");
	}

	@PostConstruct
	public void init() {
		System.out.println("init");
	}

	public void  query(){
		System.out.println("query");
	}


}
测试类 
public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);
		applicationContext.refresh();
		IndexDao bean = applicationContext.getBean(IndexDao.class);
		bean.query();
}    
```

打印台输出 : 

```text
initIndexDao构造!!
IndexDao实例化之前
init
IndexDao实例化之后
query 
```

我们可以在看一下BeanPostProcessor这个类的作用 

首先看一个现象: 

配置类:

```java

@Configuration
public class Appconfig {

} 
//里面放一个Dao 
public class IndexDao implements Dao {

	@Override
	public void query() {
		System.out.println("Indao1 query()");
	}

} 
然后通过我们的main方法启动 去获取这个IndexDao 结果是什么? 一定是获取不到,为什么会获取不到呢 ?很显然因为因为我们没有把注入进去,假设一个需求为要在query方法之前,然后IndexDao实例话之前做一些操作  通过注解的方式  
 那我们就先自定义一个注解 
    
@Import(MyImport.class)
public @interface EanbleHgj {
} 
然后当然我们的这个MyImport.class 这个类是我们呢自定义的 
public class MyImport implements ImportSelector {
	//这个类里面是什么都不干的
    @Override 
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{MyAwareProcessor.class.getName()};
    }
}   

/**
 * 后置处理器 在放到BeanDefinitionMap之前执行
 */
public class MyAwareProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")) {
			System.out.println("MyAwareProcessor 动态代理");
			bean = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Dao.class}, new MyInvocationHandler(bean));

		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}

}
public class MyInvocationHandler implements InvocationHandler {
	Object targer;

	public MyInvocationHandler(Object targer) {
		this.targer = targer;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("wo shi dai li fang fa!!!");
		return method.invoke(targer, args);
	}
} 
然后我们去把自定义的@EnableHgj添加到AppConfig里面 
    同时启动main方法看他是什么样的?
AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);
//		applicationContext.addBeanFactoryPostProcessor(new MyBeanFactoryProcess());
		applicationContext.refresh();
		Dao bean =(Dao) applicationContext.getBean("indexDao");
		bean.query();
    
当我们执行后的结果:
MyAwareProcessor 动态代理
wo shi dai li fang fa!!!
Indao1 query()
```

**为什么要用上面那个类的呢?因为有的时候我们需要Spring的动态加载 Indexdao  也就是说我们可以开关闭某些功能 AOP就是这么做的(对一个功能的动态开启与关闭)**





我们可以看到通过实现了这个类,我们就可以参与bean的创建的前后做一些操作  也就可以看出这里为什么添加一个默认的后置处理器 

<img  src="https://i.loli.net/2020/12/07/EI2reDdvk3VBhaw.gif">

```java
org.springframework.context.support.AbstractApplicationContext#prepareBeanFactory
    里面的方法 
    	//为什么让我们去处理添加一个后置处理器?因为我们可以参与bean工厂实例化的过程
		//问题 接口干嘛的? 2 怎么使用的?
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
```

ApplicationContextAwareProcessor 这个类是用来干嘛的 ? 它的父类就是我们上面所说的beanPostProcess		

单例当中注入了一个原型的对象怎么获取?   我们可以通过如下方式获取 

**问题怎么获取的? 因为Spring把他添加进去了**

```java
@Component
public class IndexDao implements ApplicationContextAware {
	/**
	 * 获取到原型的bean 
	 */
	private ApplicationContext applicationContext;

	public IndexDao() {
		System.out.println("initIndexDao构造!!");
	}

	@PostConstruct
	public void init() {
		System.out.println("init");
	}

	public void query() {
		System.out.println("query"); 
        //添加获取Bean
		applicationContext.getBean("index");
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
```

那Spring什么时候把它赋值进去的呢 ? 

我们可以试着改变Spring的源码: 

```java
org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces 
if (bean instanceof ApplicationContextAware) { 
    //自己改变源码如果是我们自己的Dao则不添加进去
   if (!bean.getClass().getSimpleName().equals("IndexDao")) {
      ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
   }
}
```

结果: 

nitIndexDao构造!!
IndexDao实例化之前
init
IndexDao实例化之后

```org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker postProcessAfterInitialization
信息: Bean 'indexDao' of type [com.dao.IndexDao] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
query
Exception in thread "main" java.lang.NullPointerException
	at com.luban.dao.IndexDao.query(IndexDao.java:28)
	at com.luban.test.Test.main(Test.java:22) 
```

我们看到因为我们判断了没把这个Dao添加进去所以 当我们调用applicationContext.getBean("index");的时候会报空指针 

所以由此我们知道了org.springframework.context.support.ApplicationContextAwareProcessor#ApplicationContextAwareProcessor类的作用就是依次把我们Bean添加起到,添加到一个大的map中

######  Spring图整体了解

1 起步:   

<img src="https://i.loli.net/2020/12/07/4chbiyfNzrVlkZE.gif">

涉及到的代码:  

 ```java
org.springframework.context.annotation.AnnotationConfigApplicationContext#AnnotationConfigApplicationContext() --> 默认调用父类的构造方法 
org.springframework.context.support.GenericApplicationContext#GenericApplicationContext(org.springframework.beans.factory.support.DefaultListableBeanFactory) 
    1, 实例化一个DefaultListtableBeanFactory 
    2、ClassPathBeanDefinitionScanner，能够扫描我们bd,能够扫描一个类，并且转换成bd
 ```

DefaultListtableBeanFactory 这个类是干嘛的呢?

里面存储这一系类的参数 比如说  :

org.springframework.beans.factory.support.DefaultListableBeanFactory#dependencyComparator 

记录着排序的属性 

org.springframework.beans.factory.support.DefaultListableBeanFactory#beanDefinitionMap

记录着BeanDefinition

org.springframework.beans.factory.support.DefaultListableBeanFactory#beanDefinitionNames 

记录着 beanDefinitionMap 里面的key是一个List 

然后是调用着自己的构造方法

```java
/**
	 * 初始化一个bean的读取和扫描器
	 * 何谓读取器和扫描器参考上面的属性注释
	 * 默认构造函数，如果直接调用这个默认构造方法，需要在稍后通过调用其register()
	 * 去注册配置类（javaconfig），并调用refresh()方法刷新容器，
	 * 触发容器对注解Bean的载入、解析和注册过程
	 * 这种使用过程我在ioc应用的第二节课讲@profile的时候讲过
	 * Create a new AnnotationConfigApplicationContext that needs to be populated
	 * through {@link #register} calls and then manually {@linkplain #refresh refreshed}.
	 */
	public AnnotationConfigApplicationContext() {
		/**
		 * 父类的构造方法
		 * 创建一个读取注解的Bean定义读取器
		 * 什么是bean定义？BeanDefinition
		 */
		this.reader = new AnnotatedBeanDefinitionReader(this);


		//可以用来扫描包或者类，继而转换成bd
		//但是实际上我们扫描包工作不是scanner这个对象来完成的
		//是spring自己new的一个ClassPathBeanDefinitionScanner
		//这里的scanner仅仅是为了程序员能够在外部调用AnnotationConfigApplicationContext对象的scan方法
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}
```

reader 是什么? 

AnnotatedBeanDefinitionReader顾名思义是一个bd读取器给一个类给他，他帮你转换成为bd但是这个对象只能读取加了注解的类 

```java
	/**
	 *  这里的BeanDefinitionRegistry registry是通过在AnnotationConfigApplicationContext
	 *  的构造方法中传进来的this
	 *  由此说明AnnotationConfigApplicationContext是一个BeanDefinitionRegistry类型的类
	 *  何以证明我们可以看到AnnotationConfigApplicationContext的类关系：
	 *  GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry
	 *  看到他实现了BeanDefinitionRegistry证明上面的说法，那么BeanDefinitionRegistry的作用是什么呢？
	 *  BeanDefinitionRegistry 顾名思义就是BeanDefinition的注册器
	 *  那么何为BeanDefinition呢？参考BeanDefinition的源码的注释
	 * @param registry
	 */
	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, getOrCreateEnvironment(registry));
	}

```

上面看完父类的构造方法接着看子类的构造方法干了什么事情 ? 

<img src="C:\Users\HGJ\Desktop\upload\spring2.gif">

方法调用链

```java
org.springframework.context.annotation.AnnotationConfigApplicationContext#AnnotationConfigApplicationContext()
		委托AnnotationConfigUtils
		org.springframework.context.annotation.AnnotatedBeanDefinitionReader#AnnotatedBeanDefinitionReader()
			
			org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors()  
    DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
		if (beanFactory != null) {
			if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
				//AnnotationAwareOrderComparator主要能解析@Order注解和@Priority
				beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
			}
			if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
				//ContextAnnotationAutowireCandidateResolver提供处理延迟加载的功能
				beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
			}
		}

		Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<>(8);
		//BeanDefinitio的注册，这里很重要，需要理解注册每个bean的类型
		if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			//需要注意的是ConfigurationClassPostProcessor的类型是BeanDefinitionRegistryPostProcessor
			//而 BeanDefinitionRegistryPostProcessor 最终实现BeanFactoryPostProcessor这个接口
			RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
		}
```

认识每个类的作用以及是干什么的? 

但是Spring自己提供的怎么办呢? 

动态开启与关闭  

full 与lite的区别 

@Configuration

@Import  返回出来的类 什么时候装换为bd 并且注册进去? 

```java
4 种类的情况:  四种类是干什么的?
普通类 -->在@ComponentScan 里面的类   org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass 里面处理. 扫描完成后就注册到bdMap当中  
    
importSelector  -->  先放到 org.springframework.context.annotation.ConfigurationClassParser#configurationClasses 里面去然后在 org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitions 方法中去注册 
    
register  -->  先放到 org.springframework.context.annotation.ConfigurationClass#importBeanDefinitionRegistrars 里面  
   在org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsFromRegistrars 里面注册

import普通类 -- > doProcessConfigurationClass 里面 
  
```

加了@Configuration 进这个代理 对象方法不再返回new这个对象了  因为是改变了这个方法了 代理类的idnexDao1

 代理对象的话什么事情都不干 

先看一个现象 :   先建一个IndexDao

```java
public class IndexDao2 {
	/**
	 * 获取到原型的bean 问题怎么获取的? 因为Spring把他添加进去了
	 */

	public IndexDao2() {
		System.out.println("initIndexDao2构造!!");
	}

	public void init() {
		System.out.println("init");
	}

}

```

```java
@ComponentScan({"com.hgj"})
//@Configuration
//@Import(MyImport.class)
//@EanbleHgj
//@Import(MyImport.class)
public class Appconfig {
	@Bean
	public IndexDao2 indexDao1() {
		return new IndexDao2();
	}
	@Bean
	public IndexDao indexDao() {
		indexDao1();
//		indexDao1();
		return new IndexDao();
	}
}
```

```java
public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);
		applicationContext.refresh();
		Appconfig appConfig = (Appconfig) applicationContext.getBean("appconfig");
		System.out.println(appConfig);
	}
```

输出什么呢?  输出了两遍:

​		initIndexDao2构造!!
​		initIndexDao2构造!! 

但是当我们把appconfig里面的@ComponentScan({"com.hgj"}) 去掉 并且替换成 @Configuration 

结果只输出了一遍这是为什么?

因为 如果加了Configuration  就会进行cglib 代理,进行代理的话就会进行方法过滤 在getBean的时候就会去获取bean,而不是new 一个新的对象  

cglib对Spring进行基本的拦截 :  

```java
public class MyTestMethodCallBack implements MethodInterceptor {
	/** 描述: 对Cjlib的方法拦截器 进行拦截
	 * @param o           代理对象
	 * @param method      目标的方法
	 * @param objects     参数
	 * @param methodProxy 代理方法
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("MyTestMethodCallBack 方法拦截");
		return methodProxy.invokeSuper(o, objects);
	}
}
```

主方法 

```java
public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);
		applicationContext.refresh();
	//参考地址org.springframework.context.annotation.ConfigurationClassEnhancer#newEnhancer	
    Enhancer enhancer = new Enhancer();
		//增强父类，地球人都知道cglib是基于继承来的
		enhancer.setSuperclass(IndexDao.class);
		enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
		enhancer.setCallback(new MyTestMethodCallBack());
		IndexDao indexDao = (IndexDao) enhancer.create();
		indexDao.query();
	}
```

输出

```java
MyTestMethodCallBack 方法拦截
Indao1 query()
```

这样我们就可以在 idnexDao的query方法之前执行其他方法 





与不加的原因



两份注入方式  

######  产生的代理类与我们自己的类有什么区别 ? CGLIB代理之类的知识   



```java
org/springframework/context/annotation/ConfigurationClassEnhancer.java:378		
//一个非常牛逼的判断
			//判断到底是new 还是get
			//判断执行的	方法和调用方法是不是同一个方法
			if (isCurrentlyInvokedFactoryMethod(beanMethod)) {
				// The factory is calling the bean method in order to instantiate and register the bean
				// (i.e. via a getBean() call) -> invoke the super implementation of the method to actually
				// create the bean instance.
				if (logger.isWarnEnabled() &&
						BeanFactoryPostProcessor.class.isAssignableFrom(beanMethod.getReturnType())) {
					logger.warn(String.format("@Bean method %s.%s is non-static and returns an object " +
									"assignable to Spring's BeanFactoryPostProcessor interface. This will " +
									"result in a failure to process annotations such as @Autowired, " +
									"@Resource and @PostConstruct within the method's declaring " +
									"@Configuration class. Add the 'static' modifier to this method to avoid " +
									"these container lifecycle issues; see @Bean javadoc for complete details.",
							beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName()));
				}
                //0先进来的UserService是进这个类
                //如果是新的就去调用父类的方法 CGlib源码里面就是这样的
				return cglibMethodProxy.invokeSuper(enhancedConfigInstance, beanMethodArgs);
			} 
			//第二次OrderService调用userService的时候进的是这个类
			//否则就调用代理
			return resolveBeanReference(beanMethod, beanMethodArgs, beanFactory, beanName);
		}
```



调用方法和正在执行的方法 (代理方法和正在执行的方法)  

<img src="https://i.loli.net/2020/12/07/c8eumHIULvCG2Yz.png">

BeanFactoryPostProcessor -->是我们可以手动添加进去 

```java
annotationConfigApplicationContext.addBeanFactoryPostProcessor();
```



