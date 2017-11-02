package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年11月2日 下午3:19:50
 */
public class SimpleThreadPool {
	
	private static final int nums = 5 ;
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(nums);
		for(int i = 0 ; i < 10 ; i++){
			Runnable worker = new MyTask(i+"");
			service.execute(worker);
		}
		service.shutdown();
		while(!service.isTerminated()){
		}
		System.err.println("finished all thread!");
		
	}
}
