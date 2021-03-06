

		1.在Java中,"初始化"和"创建"捆绑在一起,两者不能分离.

		2.方法重载:

		  相同的参数,但顺序不同,因此得以区分.// 但没有什么意义.

		  返回值不能区分.

		3.无论是垃圾回收还是终结,都不保证一定会发生.如果Java虚拟机(JVM)并未面临内存耗尽的情形,它是不会浪费时间去执行垃圾回收以恢复内存的.

		  System.gc() 用于强制进行终结动作.即使不这么做,通过重复地执行程序(假设程序将非配大量的存储空间而导致垃圾回收动作的执行),最终也能找出错误的对象.

		  当垃圾回收器工作时,将一面回收空间,一面使堆中的对象紧凑排列,这样堆指针就可以很容易移动到更靠近传送带的开始处,也就尽量避免了页面错误.通过垃圾回收期对对象重新排列,实现了一种高速的、有无线空间可供分配的堆模型.

		  Java采用一种自适应的垃圾回收技术.
		  停止-复制 (stop-and-copy) 
		  标记-清扫 (mark-and-sweep)

		4.构造器初始化

		  变量定义的先后顺序决定了初始化的顺序.及时变量定义散布于方法之间,它们仍旧会在任何方法(包括构造器)被调用之前得到初始化.

		  静态初始化只有在必要时刻才会进行.静态对象不会被再次初始化.

		  int x ;
		  static {
		  	x = 47;
		  }// 这段代码仅执行一次:当首次生成这个类的一个对象时,或者首次访问属于那个类的静态数据成员时(即使从未生成过那个类的对象).

		  public class Mugs{
		  	int x ;
		  	{
		  		x = 47;
		  	}// 使得你可以保证无论调用了哪个显式构造器,某些操作都会发生.实例初始化句子是在两个构造器之前执行的.
		  }

		5.int[] a1; // 更推荐这种方式
		  int a1[];

		  数组的创建确实是在运行时刻进行的.
		  Array.toString()将产生一维数组的可打印版本.

		  int a1 = [1,2,3,4,];// 最后这个逗号是可选的(这一特性使维护长列表变得更容易)

		  建立一个类的对象,调用toString()方法,默认行为就是打印类的名字和对象的地址.

		6.可变参数列表
		  将0个参数传递给可变参数列表是可行的.

		7.枚举类型
		  具名值,由于枚举类型的实例是常量,因此按照命名惯例都用大写字母表示(如果一个名字有多个单词,用下划线将它们隔开).

		  创建enum时,编译器会自动添加一些有用的特性.For example:
		  创建toString()方法.
		  创建ordinal()方法,用来表示某个特定enum常量的声明顺序.
		  static values()方法,用来按照enum常量顺序,产生由这些常量值构成的数组.

















