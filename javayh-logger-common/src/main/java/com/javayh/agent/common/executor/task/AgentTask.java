package com.javayh.agent.common.executor.task;

/**
 * <p>
 * 线程工作任务
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
public class AgentTask extends Thread {

    private final Runnable runnable;

    public AgentTask(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        runnable.run();
    }
}
