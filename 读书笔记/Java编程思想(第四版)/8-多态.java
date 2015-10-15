

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

 		  如果某个方法是静态的,他的行为就不具备多态性.静态方法是与类,而并非与单个的对象.

 		7.构造器并不具有多态性(它们实际上是static方法,只不过该static声明是隐式的).

 		8.表明复杂对象调用构造器遵照下面的顺序:
 		  1>调用基类构造器.这个步骤会不断地反复递归下去,首先是构造这种层次结构的根,然后是下一层导出类,等等,知道最低层的导出类.
 		  2>按声明顺序调用成员的初始化方法。
 		  3>调用导出类构造器的主体.

 		9.继承与清理
 		  当覆盖被继承类的dispose()方法时,务必记住调用基类版本dispose()方法,否则,基类的清理动作就不会发生.
 		  万一某个子对象要依赖于其他对象,销毁的顺序应该和初始化顺序相反.对于字段,则意味着声明的顺序相反.

 		10.构造器内部的多态方法的行为

 		   编写构造器时有一条有效的准则:"用尽可能简单的方法使对象进入正常状态,如果可以的话,避免调用其他方法".在构造器内唯一能够安全调用的那些方法是基类中的final方法.

 		   class Glyph{
 		   	 	void draw(){
 		   	 		System.out.println("Glyph draw()");
 		   	 	}

 		   	 	Glyph(){
 		   	 		System.out.println("Glyph() before draw()");
 		   	 		draw();
 		   	 		System.out.println("Glyph after draw()");
 		   	 	}
 		   }

 		   class RoundGlyph extends Glyph{
 		   		private int radius = 1;
 		   		RoundGlyph(int r){
 		   			radius = r;
 		   			System.out.println("RoundGlyph.RoundGlyph(),radius="+radius);
 		   		}

 		   		void draw(){
 		   			System.out.println("RoundGlyph.draw(),radius="+radius);
 		   		}
 		   }

 		   public class PolyConstructors{
 		   		public static void main(String[] args){
 		   			new RoundGlyph(5);
 		   		}
 		   }
 		   //output:
 		   // Glyph() before draw()
 		   // RoundGlyph.draw(),radius=0
 		   // Glyph after draw()
 		   // RoundGlyph.RoundGlyph(),radius=5

 		11.在运行期间对类型进行检查的行为称作"运行时类型识别"(RTTI).




















