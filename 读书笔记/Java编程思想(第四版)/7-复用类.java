

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



















