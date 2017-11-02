package thread;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年11月2日 下午3:14:52
 */
public class MyTask implements Runnable {

	private String command ;
	
	public MyTask(String command) {
		this.command = command ;
	}
	
	public void sleep(Long times){
		try {
			Thread.sleep(times);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "Start,commond = " + command);
		sleep(5000L);
		System.out.println(Thread.currentThread().getName() +"end");

	}
	
	@Override
	public String toString() {
		return this.command ;
	}
	
}
