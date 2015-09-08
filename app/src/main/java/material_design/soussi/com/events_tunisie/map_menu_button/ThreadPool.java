package material_design.soussi.com.events_tunisie.map_menu_button;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by Soussi on 10/05/2015.
 */
public class ThreadPool {
    public static final String TAG = ThreadPool.class.getSimpleName();
    static ExecutorService threadPool;

    public static void initThreadPool(int max){ //
        if(max > 0){
            max = max < 3 ? 3 : max;
            threadPool = Executors.newFixedThreadPool(max);
        }else{
            threadPool = Executors.newCachedThreadPool();
        }

        Logger.d(TAG, "[ThreadPool]ThreadPool init success...max thread: " + max);

    }

    public static ExecutorService getInstances(){
        return threadPool;
    }

    public synchronized static<U, R> void go(Runtask<U, R> runtask){
        if(null == threadPool){
            Logger.e(TAG, "ThreadPool Application...");
            return;
        }
//        runtask.onBefore();
        threadPool.execute(runtask);
    }

    public synchronized static void go(Runnable runnable){
        if(null == threadPool){
            Logger.e(TAG, "ThreadPool Application...");
            return;
        }
        threadPool.execute(runnable);
    }



}
