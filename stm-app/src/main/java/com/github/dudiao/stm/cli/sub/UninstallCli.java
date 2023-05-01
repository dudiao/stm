package com.github.dudiao.stm.cli.sub;

import cn.hutool.core.io.FileUtil;
import com.github.dudiao.stm.cli.StmSubCli;
import com.github.dudiao.stm.persistence.ToolDO;
import com.github.dudiao.stm.persistence.ToolsPersistence;
import com.github.dudiao.stm.plugin.StmException;
import com.github.dudiao.stm.tools.StmUtils;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import picocli.CommandLine;

/**
 * @author songyinyin
 * @since 2023/4/30 20:00
 */
@Slf4j
@Component
@CommandLine.Command(name = "uninstall", description = "卸载应用")
public class UninstallCli implements StmSubCli {

    @CommandLine.Parameters(index = "0", description = "应用名称")
    private String name;

    @Inject
    private ToolsPersistence toolsPersistence;

    @Override
    public Integer execute() {
        ToolDO toolDO = toolsPersistence.get(name);
        if (toolDO == null) {
            throw new StmException("应用不存在");
        }
        String appPath = StmUtils.getAppPath(toolDO);
        toolsPersistence.remove(name);
        FileUtil.del(appPath);
        log.info("删除文件:{}", appPath);
        log.info("应用[{}]卸载成功", name);
        return null;
    }
}
