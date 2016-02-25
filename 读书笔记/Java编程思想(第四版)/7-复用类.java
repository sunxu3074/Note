

		1.初始化基类

		  构建过程是从基类"向外"扩散的.

		2. 
		  class First{
		  	First(int i){
		  		System.out.println(i);
		  	}
		  }

		  class Second extends First{
		  	Second(int i){
		  		super(i); // 如果没有super,编译器将会"抱怨"无法找到First()形式的构造器
		  		System.out.println(i);
		  	}
		  }
		  
		  // 如果不调用super(i) && First类中有默认的构造器 ; 
		  // 则会先调用基类的默认构造器
		  // 再执行这个类的构造器

		3.组合与继承

		  一个最清晰的判断办法就是问一问自己是否需要从新类向基类进行上转型.如果必须向上转型,则继承是必要的,但如果不需要,则应当好好考虑自己是否需要继承.

		4.final数据
		  可以在编译时执行计算式.
		  在Java中,这类常量必须是基本数据类型,并且以关键字final表示.
		  final int a1[] = {1,2,3,4,};// 引用是永恒不变的,数组也是对象.

		  带有恒定初始值(即,编译器常量)的final static基本类型全用大写字母命名.

		5.空白final
		  允许,但在使用前依然要求被赋值.

		6.final方法
		  只有在想要明确禁止覆盖时,才将方法设置为final的.

		7.
		  public class Bettle extends Insect {
		  	public static void main(String[] args){
		  		
		  	}
		  }
		
		  public class Insect {
		  	static {
		  	    System.out.println("Insect static");
		  	}
		  }
		  
		  // output
		  Insect static 
		  
		  当试图访问Bettle.main()时,加载器开始启动并找到Bettle类的编译代码(在名为Bettle.class的文件之中).在对它进行加载
		  的过程中,编译器注意到它有一个基类(这是由关键字extends得知的),于是它继续进行加载.不管是否打算产生一个新的对象,
		  这都要发生.

















