package cn.bluesking.spider.mobile.lagou;


public class TestDemo {
	public static void main(String[] args) throws InterruptedException {
		Thread a = new Thread() {
			int i = 0;
			public void run() {
				System.out.println("i = " + i);
				for(int i = 0; i < 10; i ++) {
					if(i == 3) {
						System.err.println("线程抛出异常！");
						throw new RuntimeException();
					}
				}
			};
		};
		a.start();
		Thread.sleep(5000);
		System.out.println(a.isAlive());
		System.out.println(a.isInterrupted());
		a.start();
//		Thread b = new Thread(a);
//		b.start();
	}
}
