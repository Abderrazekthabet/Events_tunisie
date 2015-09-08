package material_design.soussi.com.events_tunisie.map_menu_button;
import android.os.Handler;
import android.os.Message;


import java.util.Collection;

import material_design.soussi.com.events_tunisie.map_menu_button.widget.CancelableTask;

/**
 * Created by Soussi on 10/05/2015.
 */
public abstract class Runtask<U, R> implements Runnable, CancelableTask {
    public Object[] objs;

    protected Runtask(Object... objs) {
        this.objs = objs;
    }

    private static final int TASK_BEFORE_UI = 0X001;
    private static final int TASK_UPDATE_UI = 0x002;
    private static final int TASK_RESULT = 0x003;

    private boolean isCanceled;

    protected Handler rHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case TASK_BEFORE_UI:
                    onBefore();
                    break;
                case TASK_UPDATE_UI:
                    onUpdateUiCallBack((U) msg.obj);
                    break;
                case TASK_RESULT:
                    onResult((R) msg.obj);
                    break;
            }

        }
    };


    @Override
    public void run() {
        if (isCanceled) {
            return;
        }

        Message msg = rHandler.obtainMessage();
        msg.arg1 = TASK_BEFORE_UI;
        rHandler.sendMessage(msg);

        if (isCanceled) {
            return;
        }
        R result = runInBackground();
        if (isCanceled) {
            return;
        }

        msg = rHandler.obtainMessage();
        msg.arg1 = TASK_RESULT;
        msg.obj = result;
        rHandler.sendMessage(msg);
    }

    public abstract R runInBackground();

    public void updateUi(U obj) {
        Message msg = rHandler.obtainMessage();
        msg.arg1 = TASK_UPDATE_UI;
        msg.obj = obj;
        rHandler.sendMessage(msg);
    }

    protected void onBefore() {
    }

    protected void onUpdateUiCallBack(U obj) {
    }

    protected void onResult(R result) {
    }

    public void cancel() {
        if (!isCanceled) {
            isCanceled = true;
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (mayInterruptIfRunning) {
            Thread.currentThread().interrupt();
        }
        cancel();
        return true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public void remove() {
        // ignore
    }

    @Override
    public void addListener(Collection<CancelableTask> cancelableTaskList) {
        // ignore
    }
}
