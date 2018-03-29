package cn.bluesking.spider.commons.mapper;

import cn.bluesking.spider.commons.entity.ProxyInfo;
import cn.bluesking.spider.commons.entity.ProxyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProxyInfoMapper {

	/**
	 * 统计符合自定义条件的数据个数
	 *
	 * @param example [ProxyInfoExample]自定义条件
	 * @return [int]符合条件的数据个数
	 */
	int countByExample(ProxyInfoExample example);

	/**
	 * 自定义条件删除数据
	 *
	 * @param example [ProxyInfoExample]自定义条件
	 * @return [int]受影响行数
	 */
	int deleteByExample(ProxyInfoExample example);

	/**
	 * 根据主键删除单条数据
	 *
	 * @param proxyId [Integer](主键属性)代理编号
	 * @return [int]受影响行数
	 */
	int deleteByPrimaryKey(@Param("proxyId") Integer proxyId);

	/**
	 * 插入单条数据
	 *
	 * @param record [ProxyInfo]待插入数据
	 * @return [int]受影响行数
	 */
	int insert(ProxyInfo record);

	/**
	 * 有选择的插入单条数据，只插入不为空的属性
	 *
	 * @param record [ProxyInfo]待插入数据
	 * @return [int]受影响行数
	 */
	int insertSelective(ProxyInfo record);

	/**
	 * 查询符合自定义条件的数据
	 *
	 * @param example [ProxyInfoExample]自定义条件
	 * @return [List<ProxyInfo>]符合自定义条件的数据
	 */
	List<ProxyInfo> selectByExample(ProxyInfoExample example);

	/**
	 * 根据主键查询单条数据
	 *
	 * @param proxyId [Integer](主键属性)代理编号
	 * @return [ProxyInfo]查询结果
	 */
	ProxyInfo selectByPrimaryKey(@Param("proxyId") Integer proxyId);

	/**
	 * 自定义条件的有选择性的修改某些属性，只修改不为空的属性
	 *
	 * @param record [ProxyInfo]数据对象，用于指定修改属性
	 * @param example [ProxyInfoExample]自定义条件
	 * @return [int]受影响行数
	 */
	int updateByExampleSelective(@Param("record") ProxyInfo record, @Param("example") ProxyInfoExample example);

	/**
	 * 自定义条件修改所有属性
	 *
	 * @param record [ProxyInfo]数据对象，用于指定修改属性
	 * @param example [ProxyInfoExample]自定义条件
	 * @return [int]受影响行数
	 */
	int updateByExample(@Param("record") ProxyInfo record, @Param("example") ProxyInfoExample example);

	/**
	 * 通过主键有选择性的修改某该属性
	 *
	 * @param record [ProxyInfo]数据对象，用于指定修改属性，数据中应包含主键
	 * @return [int]受影响行数
	 */
	int updateByPrimaryKeySelective(ProxyInfo record);

	/**
	 * 通过主键修改所有属性
	 *
	 * @param record [ProxyInfo]数据对象，用于指定修改属性，数据中应包含主键
	 * @return [int]受影响行数
	 */
	int updateByPrimaryKey(ProxyInfo record);

}