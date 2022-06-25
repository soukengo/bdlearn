## 作业二：构建 SQL 满足如下要求

通过 set spark.sql.planChangeLog.level=WARN，查看：

1.构建一条 SQL，同时 apply 下面三条优化规则：

* CombineFilters
* CollapseProject
* BooleanSimplification

2.构建一条 SQL，同时 apply 下面五条优化规则：

* ConstantFolding
* PushDownPredicates
* ReplaceDistinctWithAggregate
* ReplaceExceptWithAntiJoin
* FoldablePropagation

### 作业实现

TODO 