/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.backend.task;

import chuks.flatbook.fx.backend.util.TaskResult;
import chuks.flatbook.fx.backend.account.type.OrderNettingAccount;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author user
 */
abstract class SequentialTask extends Task {
    
    private Task[] tasks;

    public SequentialTask(OrderNettingAccount account, String identifier) {
        super(account, identifier);
    }

    public SequentialTask(Task... tasks) {
        super(null, null);
        logger = LoggerFactory.getLogger(SequentialTask.class.getName());
        this.tasks = tasks;
    }

    public void setSequence(Task... tasks) {
        this.tasks = tasks;
    }

    abstract protected CompletableFuture<TaskResult> finallyRun();

    @Override
    public CompletableFuture<TaskResult> run() {

        CompletableFuture<TaskResult> future = null;
        TaskResult result;
        try {
            for (Task task : tasks) {

                result = task.run().get();

                if (!result.isSuccess()) {
                    return future;
                }

            }
        } catch (InterruptedException | ExecutionException ex) {
            logger.error(ex.getMessage(), ex);
            if (future == null) {
                future = new CompletableFuture();
            }
            future.complete(new TaskResult(false, "An error occurred while processing task"));
            return future;
        }

        return finallyRun();
    }
}
