package cn.bluesking.spider.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

/**
 * 通用的基础持久层操作接口。
 * 
 * @author 随心
 * @param <T> 对应数据库表的对象泛型
 * 2020年3月30日
 */
public interface BaseMapper<T> {

    /**
     * 根据 ID 删除数据库中的记录。
     * 
     * @param id [String] 待删除记录的 ID
     * @return [int] 删除操作成功与否，1 即为成功，0 为失败。
     */
    int deleteById(@Param("id") String id);

    /**
     * 将对象存储到数据库中。
     * 
     * @param entity [{@code T[]}] 待存储对象数组
     * @return [int] 删除操作成功与否，1 即为成功，0 为失败。
     */
    @SuppressWarnings("unchecked")
    int insert(T... entities);

    /**
     * 批量将对象存储到数据库中。
     * 
     * @param entities [{@code Collection<T>}] 待存储的对象集合
     * @return [int] 删除操作成功与否，1 即为成功，0 为失败。
     */
    int batchInsert(Collection<T> entities);

    /**
     * 根据 ID 查询数据库中的记录。
     * 
     * @param id [String] 待查询记录的 ID
     * @return [T] 返回查询到的对象
     */
    T selectById(@Param("id") String id);

}
