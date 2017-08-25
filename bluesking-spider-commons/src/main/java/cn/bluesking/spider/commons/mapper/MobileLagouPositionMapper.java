package cn.bluesking.spider.commons.mapper;

import cn.bluesking.spider.commons.entity.MobileLagouPosition;
import cn.bluesking.spider.commons.entity.MobileLagouPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MobileLagouPositionMapper {

	/**
	 * 统计符合自定义条件的数据个数
	 *
	 * @param example [MobileLagouPositionExample]自定义条件
	 * @return [int]符合条件的数据个数
	 */
	int countByExample(MobileLagouPositionExample example);

	/**
	 * 自定义条件删除数据
	 *
	 * @param example [MobileLagouPositionExample]自定义条件
	 * @return [int]受影响行数
	 */
	int deleteByExample(MobileLagouPositionExample example);

	/**
	 * 根据主键删除单条数据
	 *
	 * @param positionId [Integer](主键属性)职位编号
	 * @return [int]受影响行数
	 */
	int deleteByPrimaryKey(@Param("positionId") Integer positionId);

	/**
	 * 插入单条数据
	 *
	 * @param record [MobileLagouPosition]待插入数据
	 * @return [int]受影响行数
	 */
	int insert(MobileLagouPosition record);

	/**
	 * 有选择的插入单条数据，只插入不为空的属性
	 *
	 * @param record [MobileLagouPosition]待插入数据
	 * @return [int]受影响行数
	 */
	int insertSelective(MobileLagouPosition record);

	/**
	 * 查询符合自定义条件的数据
	 *
	 * @param example [MobileLagouPositionExample]自定义条件
	 * @return [List<MobileLagouPosition>]符合自定义条件的数据
	 */
	List<MobileLagouPosition> selectByExample(MobileLagouPositionExample example);

	/**
	 * 根据主键查询单条数据
	 *
	 * @param positionId [Integer](主键属性)职位编号
	 * @return [MobileLagouPosition]查询结果
	 */
	MobileLagouPosition selectByPrimaryKey(@Param("positionId") Integer positionId);

	/**
	 * 自定义条件的有选择性的修改某些属性，只修改不为空的属性
	 *
	 * @param record [MobileLagouPosition]数据对象，用于指定修改属性
	 * @param example [MobileLagouPositionExample]自定义条件
	 * @return [int]受影响行数
	 */
	int updateByExampleSelective(@Param("record") MobileLagouPosition record, @Param("example") MobileLagouPositionExample example);

	/**
	 * 自定义条件修改所有属性
	 *
	 * @param record [MobileLagouPosition]数据对象，用于指定修改属性
	 * @param example [MobileLagouPositionExample]自定义条件
	 * @return [int]受影响行数
	 */
	int updateByExample(@Param("record") MobileLagouPosition record, @Param("example") MobileLagouPositionExample example);

	/**
	 * 通过主键有选择性的修改某该属性
	 *
	 * @param record [MobileLagouPosition]数据对象，用于指定修改属性，数据中应包含主键
	 * @return [int]受影响行数
	 */
	int updateByPrimaryKeySelective(MobileLagouPosition record);

	/**
	 * 通过主键修改所有属性
	 *
	 * @param record [MobileLagouPosition]数据对象，用于指定修改属性，数据中应包含主键
	 * @return [int]受影响行数
	 */
	int updateByPrimaryKey(MobileLagouPosition record);

}