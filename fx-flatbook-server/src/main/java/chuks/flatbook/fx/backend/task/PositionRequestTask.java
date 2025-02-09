/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.backend.task;

import chuks.flatbook.fx.backend.account.Broker;
import chuks.flatbook.fx.common.account.order.Position;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import quickfix.ConfigError;
import quickfix.SessionNotFound;
import chuks.flatbook.fx.backend.util.FixUtil;
import chuks.flatbook.fx.backend.util.TaskResult;

/**
 *
 * @author user
 */
public class PositionRequestTask extends Task {

    

    public PositionRequestTask(Broker account, String identifier) {
        super(account, identifier);
        logger = LoggerFactory.getLogger(PositionRequestTask.class.getName());
    }

    @Override
    public void onPositionReport(List<Position> positionlist, String error) {

        if (error == null) {
            future.complete(new TaskResult(true, "Position Report"));
            logger.debug("PositionReport");
        } else {
            future.complete(new TaskResult(false, error));
            logger.error("PositionReport error - "+error);
        }
    }

    @Override
    protected CompletableFuture<TaskResult> run() {
        try {
            future = FixUtil.sendPositionRequest(account);
        } catch (ConfigError | SessionNotFound ex) {
            future.complete(new TaskResult(false, "Position Request Failed"));
            logger.error(ex.getMessage(), ex);
        }

        return future;
    }

}
