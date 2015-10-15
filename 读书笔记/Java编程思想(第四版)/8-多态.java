

		1.在面向对象的程序设计语言中,多态是继数据抽象和继承之后的第三种基本特征.多态的作用是消除类型之间的耦合关系.

		2.将一个方法调用同一个方法主体关联起来被称作绑定.Java中除了static方法和final方法(private方法属于final方法)之外,其他所有的方法都是后期绑定.

		3.final方法的作用除了防止其他人覆盖该方法,更重要的一点是有效地"关闭"动态绑定.但对程序的整体性能不会有什么改观,不要出于试图提高性能的目的来使用final.

 		4.多态是一项让程序员"将改变的事物与未变的事物分离开来"的重要技术.

 		5.缺陷:"覆盖"私有方法

 		  public class Super{
 		  	private void f{
 		  		System.out.println("Super");
 		  	}
 		  	public static void main(String[] args){
 		  		Sub sub = new Super();
 		  		sub.f();
 		  		//output:
 		  		// Super
 		  		//只有非private方法才可以被覆盖
 		  	}
 		  }

 		  class Sub extends Super{
 		  	public void f{
 		  		System.out.println("Sub");
 		  	}
 		  }

 		6.缺陷:域与静态方法
 		  只有普通的方法调用可以是多态的.如果你直接访问某个域,这个访问就将在编译期进行解析.

 		  如果某个方法是静态的,他的行为就不具备多态性.静态方法是与类,而并非与单个的对象相关联的.




















