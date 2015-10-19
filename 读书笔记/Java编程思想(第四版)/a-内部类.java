

		1.非静态方法之外的任意位置创建某个内部类的对象, 具体地执行这个对象的类型:OuterClassName.InnerClassName.

		2.生成外部类对象的引用,可以使用外部类的名字后面紧跟原点和this.这样产生的引用自动地具有正确的类型,这一点在编译期就被知晓并受到检查,因此没有任何需要时开销.

		3.public class Outer{
			private class Inner{}
			public static void main(String[] args){
				Outer.Inner inner = new Outer().new Inner();
			}
		}

		4.局部内部类:
		  public class InnerTest{
		  	 private void test(boolean flag){
		  	 	if(flag){
		  	 		class Inner{
		  	 			private int i = 99;
		  	 			new Inner(){
		  	 				System.out.println(i);
		  	 			}
		  	 		}
		  	 		Inner inner = new Inner();
		  	 	} // Inner inner = new Inner(); Error!!Out of scope!!
		  	 }
		  }
		  output:99
		  //Inner类被嵌入在if语句的作用域内,这并不是说该类的创建是有条件的,它其实与别的类一起编译过了.

		5.匿名内部类:
		  public class P{
		  	public Contents contents(){
		  		return new Contents(){
		  			private int i = 11;
		  			public int value(return i);
		  		};
		  	}
		  	public static void main(String[] args){
		  		P p = new P();
		  		Contents c = p.contents();
		  	}
		  }

		6.优先使用类而不是接口.如果你的类中需要某个接口,你必须了解它.

		7.测试一个类可以这样:
		  public class TestBed{
		  	public void f(){System.out.println("Test succes");};
		  	public static class Tester{
		  		public static void main(String[] args){
		  			TestBed t = new TestBed();
		  			t.f();
		  		}
		  	}
		  }
		  //output:Test succes
		  // java TestBed$Tester 运行
		  // 在产品打包前简单地删除TestBed$Tester.class.


































