package com.gzz.core.cqrs;

/**
 *  命令总线
 */
public class CommandBus {
    private ICommandHandler commandHandler;
    public CommandBus(){

    }
    public CommandBus(ICommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    /**
     * 调度分派
     */
    public void dispatch(){
        commandHandler.execute();
    }
}
