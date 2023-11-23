package com.first;

import com.first.mappers.db1.Db1Mapper;
import com.first.mappers.db2.Db2Mapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/31 19:21
 */
@RestController
public class DbController {
    private Db1Mapper db1Mapper;
    private Db2Mapper db2Mapper;

    public DbController(Db1Mapper db1Mapper, Db2Mapper db2Mapper) {
        this.db1Mapper = db1Mapper;
        this.db2Mapper = db2Mapper;
    }

    /**
     * Transactional(rollbackFor = Exception.class) 注解已经不能解决 分布式事务
     * <p>
     * 两阶段提交的回滚日志
     * <pre class="code">
     * INFO TransactionManagerHolder     : TransactionManager Singleton io.seata.tm.DefaultTransactionManager@7fa978c
     * INFO DefaultGlobalTransaction  : Begin new global transaction [0.0.1.1:8091:5720001735914418177]
     * INFO AbstractResourceManager      : branch register success, xid:0.0.1.1:8091:5720001735914418177, branchId:5720001735914418179, lockKeys:null
     * INFO DefaultGlobalTransaction  : transaction 0.0.1.1:8091:5720001735914418177 will be rollback
     * INFO RmBranchRollbackProcessor    : rm handle branch rollback process:BranchRollbackRequest{xid='0.0.1.1:8091:5720001735914418177', branchId=5720001735914418179, branchType=XA, resourceId='jdbc:mysql://127.0.0.1:3306/coursedb_0', applicationData='null'}
     * INFO AbstractRMHandler            : Branch Rollbacking: 0.0.1.1:8091:5720001735914418177 5720001735914418179 jdbc:mysql://127.0.0.1:3306/coursedb_0
     * INFO ResourceManagerXA   : 0.0.1.1:8091:5720001735914418177-5720001735914418179 was rollbacked
     * INFO AbstractRMHandler            : Branch Rollbacked result: PhaseTwo_Rollbacked
     * INFO DefaultGlobalTransaction  : transaction end, xid = 0.0.1.1:8091:5720001735914418177
     * INFO DefaultGlobalTransaction  : [0.0.1.1:8091:5720001735914418177] rollback status: Rollbacked
     * </pre>
     *
     * @return result
     */
    @GetMapping("/test")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String test() {
        db1Mapper.aaaReduce();
        System.out.printf("xid = %s, branchType = %s, timeout= %s %n",
                RootContext.getXID(), RootContext.getBranchType(), RootContext.getTimeout());
        int i = 1 / 0;
        db2Mapper.bbbAdd();
        return "success";
    }

}
