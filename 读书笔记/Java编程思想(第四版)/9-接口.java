

		1.包含抽象方法的类叫做抽象类.如果一个类包含一个或多个抽象方法,该类必须被限定为抽象的.(否则,编译器将会报错)

		2.创建一个interface, 可在在前加public关键字.如果不加则是默认包访问权限,这样它就只能在一个包中使用.接口也可以有域,但是这些域都是隐式地static和final的.

		3.interface SuperInterface{
			int NUMBER　= 1; // static & final
			void add();  // Automatically public
		}

		4.我们应该使用接口还是抽象类?如果要创建不带任何方法定义和成员变量的基类,那么就应该选择接口而不是抽象类.事实上,如果知道某事物应该成为一个基类,那么第一选择应该是使它成为一个接口.
		  恰当的规则应该是优先选择类而不是接口.//TODO理解 ....

		5.interface I1{void f();}
		  interface I2{int f()}
		  interface I3 extends I1,I3{}// 编译器就会报错.重载方法仅通过返回值是不能区分的.

		6.接口中的域不是接口的一部分,它们的值被存储在该接口的静态存储区域中.




















