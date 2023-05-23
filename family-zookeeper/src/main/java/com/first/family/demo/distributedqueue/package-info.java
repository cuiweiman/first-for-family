/**
 * 分布式队列 Demo
 * 使用 znode 路径 /queue 作为分布式队列根节点，其下节点表示队列中的元素。
 * /queue 为顺序持久化节点，名称后缀表示元素在队列中的位置。其中后缀数字越小，队列位置越靠前。
 * {@link #offer()} 在 /queue 路径下创建一个顺序 znode，顺序性 znode 意味着在所有 znode 中最大后缀的数字 加 1，可以表示队尾元素。
 * {@link #element()} 获取 /queue 路径下所有子节点，并根据后缀数字进行从小到大排序，再返回后缀数字最小的节点。
 * {@link #remove()} 获取 /queue 路径下所有子节点，并根据后缀数字进行从小到大排序，再返回后缀数字最小的节点，并从队列中删除。
 *
 *
 */
package com.first.family.demo.distributedqueue;