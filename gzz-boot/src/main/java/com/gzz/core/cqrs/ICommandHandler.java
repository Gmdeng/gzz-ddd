package com.gzz.core.cqrs;

/**
 * 命令处理器。
 *
 */
public interface ICommandHandler {

    void execute();
}
