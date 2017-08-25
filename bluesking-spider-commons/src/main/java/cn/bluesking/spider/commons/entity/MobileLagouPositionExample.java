package cn.bluesking.spider.commons.entity;

import java.util.ArrayList;
import java.util.List;

public class MobileLagouPositionExample {

	/**
	 * 排序从句
	 */
	protected String orderByClause;

	/**
	 * 是否使用distinct查询（去重查询）
	 */
	protected boolean distinct;

	/**
	 * example中包含多个criteria，
	 * criteria包含多个Criterion条件，条件中的内容是以and的形式加到where上的，
	 * criteria与criteria之间是以or的形式添加到where中的。
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * 构造方法，初始化criteria条件列表。
	 */
	public MobileLagouPositionExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
 		return oredCriteria;
	}

	/**
	 * or形式在条件语句criteria列表中拼接一个新的criteria。
	 * @param criteria [Criteria]自定义的条件
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * or形式在条件语句criteria列表中创建一个新的criteria，并返回。
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * 创建一个条件语句criteria，
	 * 如果criteria列表中没有元素，则返回根节点。
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * 创建一个条件语句criteria（内部方法）。
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * 清空criteria列表中。
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	 /**
	 * 每个crteria中都有一个criterion列表，子条件列表，用and语句相互连接。
	 */
	public static class Criterion {

		 /**
		 * 条件关键字，例如：>=、<=等等
		 */
		private String condition;

		/**
		 * 条件语句会用到的值1
		 */
		private Object value;

		/**
		 * 条件语句会用到的值2
		 */
		private Object secondValue;

		/**
		 * 条件关键字没有值，例如：not null、 null
		 */
		private boolean noValue;

		/**
		 * 条件关键字有且只有一个值，例如：>= 1、<= 1
		 */
		private boolean singleValue;

		/**
		 * 条件关键字有且只有两个值，例如：between 1 and 2
		 */
		private boolean betweenValue;

		/**
		 * 条件关键字有且只有一个列表的值，例如：in(1、2、3、4)
		 */
		private boolean listValue;

		/**
		 * 该条件针对的字段名
		 */
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		/**
		 * 无参数条件构造方法
		 * @param condition [String]条件关键字
		 */
		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		/**
		 * 单值参数或列表参数构造方法
		 * @param condition [String]条件关键字
		 * @param value [Object]条件语句会用到的值1
		 * @param typeHeader [String]该条件针对的字段名
		 */
		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		/**
		 * 单值参数或列表参数构造方法（不指定所属字段）
		 * @param condition [String]条件关键字
		 * @param value [Object]条件语句会用到的值1
		 */
		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		/**
		 * 双参数构造方法
		 * @param condition [String]条件关键字
		 * @param value [Object]条件语句会用到的值1
		 * @param secondValue [Object]条件语句会用到的值2
		 * @param typeHeader [String]该条件针对的字段名
		 */
		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		/**
		 * 双参数构造方法（不指定所属字段）
		 * @param condition [String]条件关键字
		 * @param value [Object]条件语句会用到的值1
		 * @param secondValue [Object]条件语句会用到的值2
		 */
		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
	 * criteria条件类，父类GeneratedCriteria是动态生成的。
	 */
	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

	/**
	 * 动态生成的Criteria父类
	 */
	protected abstract static class GeneratedCriteria {

		/**
		 * 条件列表集合
		 */
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		/**
		 * 条件列表集合中是否有值
		 * @return [boolean]true : 有、 false : 没有
		 */
		public boolean isValid() {
			return criteria.size() > 0;
		}

		/**
		 * 获取条件列表集合
		 * @return [List<Criterion>]条件列表集合
		 */
		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		/**
		 * 获取条件列表集合
		 * @return [List<Criterion>]条件列表集合
		 */
		public List<Criterion> getCriteria() {
			return criteria;
		}

		/**
		 * 无参数添加条件
 		 * @param condition [String]条件语句
		 * @exception [RuntimeException]输入条件语句不能为null
		 */
		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		/**
		 * 单参数添加条件
 		 * @param condition [String]条件语句
 		 * @param value [Object]条件语句会用到的值1
 		 * @param property [String]该条件针对的属性名
		 * @exception [RuntimeException]输入条件语句不能为null
		 */
		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		/**
		 * 双参数添加条件
 		 * @param condition [String]条件语句
 		 * @param value [Object]条件语句会用到的值1
 		 * @param value2 [Object]条件语句会用到的值2
 		 * @param property [String]该条件针对的属性名
		 * @exception [RuntimeException]输入条件语句不能为null
		 */
		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}
		
		/**
		 * position_id字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdIsNull() {
			addCriterion("position_id is null");
			return (Criteria) this;
		}

		/**
		 * position_id字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdIsNotNull() {
			addCriterion("position_id is not null");
			return (Criteria) this;
		}

		/**
		 * position_id等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdEqualTo(Integer value) {
			addCriterion("position_id =", value, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id不等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdNotEqualTo(Integer value) {
			addCriterion("position_id <>", value, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id大于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdGreaterThan(Integer value) {
			addCriterion("position_id >", value, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id大于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("position_id >=", value, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id小于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdLessThan(Integer value) {
			addCriterion("position_id <", value, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id小于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdLessThanOrEqualTo(Integer value) {
			addCriterion("position_id <=", value, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdIn(List<Integer> values) {
			addCriterion("position_id in", values, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id不在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdNotIn(List<Integer> values) {
			addCriterion("position_id not in", values, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdBetween(Integer value1, Integer value2) {
			addCriterion("position_id between", value1, value2, "positionId");
			return (Criteria) this;
		}

		/**
		 * position_id不在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionIdNotBetween(Integer value1, Integer value2) {
			addCriterion("position_id not between", value1, value2, "positionId");
			return (Criteria) this;
		}
		/**
		 * position_name字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameIsNull() {
			addCriterion("position_name is null");
			return (Criteria) this;
		}

		/**
		 * position_name字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameIsNotNull() {
			addCriterion("position_name is not null");
			return (Criteria) this;
		}

		/**
		 * position_name等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameEqualTo(String value) {
			addCriterion("position_name =", value, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameNotEqualTo(String value) {
			addCriterion("position_name <>", value, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameGreaterThan(String value) {
			addCriterion("position_name >", value, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameGreaterThanOrEqualTo(String value) {
			addCriterion("position_name >=", value, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameLessThan(String value) {
			addCriterion("position_name <", value, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameLessThanOrEqualTo(String value) {
			addCriterion("position_name <=", value, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameIn(List<String> values) {
			addCriterion("position_name in", values, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameNotIn(List<String> values) {
			addCriterion("position_name not in", values, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameBetween(String value1, String value2) {
			addCriterion("position_name between", value1, value2, "positionName");
			return (Criteria) this;
		}

		/**
		 * position_name不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionNameNotBetween(String value1, String value2) {
			addCriterion("position_name not between", value1, value2, "positionName");
			return (Criteria) this;
		}
		/**
		 * city字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityIsNull() {
			addCriterion("city is null");
			return (Criteria) this;
		}

		/**
		 * city字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityIsNotNull() {
			addCriterion("city is not null");
			return (Criteria) this;
		}

		/**
		 * city等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityEqualTo(String value) {
			addCriterion("city =", value, "city");
			return (Criteria) this;
		}

		/**
		 * city不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityNotEqualTo(String value) {
			addCriterion("city <>", value, "city");
			return (Criteria) this;
		}

		/**
		 * city大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityGreaterThan(String value) {
			addCriterion("city >", value, "city");
			return (Criteria) this;
		}

		/**
		 * city大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityGreaterThanOrEqualTo(String value) {
			addCriterion("city >=", value, "city");
			return (Criteria) this;
		}

		/**
		 * city小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityLessThan(String value) {
			addCriterion("city <", value, "city");
			return (Criteria) this;
		}

		/**
		 * city小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityLessThanOrEqualTo(String value) {
			addCriterion("city <=", value, "city");
			return (Criteria) this;
		}

		/**
		 * city在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityIn(List<String> values) {
			addCriterion("city in", values, "city");
			return (Criteria) this;
		}

		/**
		 * city不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityNotIn(List<String> values) {
			addCriterion("city not in", values, "city");
			return (Criteria) this;
		}

		/**
		 * city在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityBetween(String value1, String value2) {
			addCriterion("city between", value1, value2, "city");
			return (Criteria) this;
		}

		/**
		 * city不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCityNotBetween(String value1, String value2) {
			addCriterion("city not between", value1, value2, "city");
			return (Criteria) this;
		}
		/**
		 * salary字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryIsNull() {
			addCriterion("salary is null");
			return (Criteria) this;
		}

		/**
		 * salary字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryIsNotNull() {
			addCriterion("salary is not null");
			return (Criteria) this;
		}

		/**
		 * salary等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryEqualTo(String value) {
			addCriterion("salary =", value, "salary");
			return (Criteria) this;
		}

		/**
		 * salary不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryNotEqualTo(String value) {
			addCriterion("salary <>", value, "salary");
			return (Criteria) this;
		}

		/**
		 * salary大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryGreaterThan(String value) {
			addCriterion("salary >", value, "salary");
			return (Criteria) this;
		}

		/**
		 * salary大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryGreaterThanOrEqualTo(String value) {
			addCriterion("salary >=", value, "salary");
			return (Criteria) this;
		}

		/**
		 * salary小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryLessThan(String value) {
			addCriterion("salary <", value, "salary");
			return (Criteria) this;
		}

		/**
		 * salary小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryLessThanOrEqualTo(String value) {
			addCriterion("salary <=", value, "salary");
			return (Criteria) this;
		}

		/**
		 * salary在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryIn(List<String> values) {
			addCriterion("salary in", values, "salary");
			return (Criteria) this;
		}

		/**
		 * salary不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryNotIn(List<String> values) {
			addCriterion("salary not in", values, "salary");
			return (Criteria) this;
		}

		/**
		 * salary在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryBetween(String value1, String value2) {
			addCriterion("salary between", value1, value2, "salary");
			return (Criteria) this;
		}

		/**
		 * salary不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andSalaryNotBetween(String value1, String value2) {
			addCriterion("salary not between", value1, value2, "salary");
			return (Criteria) this;
		}
		/**
		 * min_salary字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryIsNull() {
			addCriterion("min_salary is null");
			return (Criteria) this;
		}

		/**
		 * min_salary字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryIsNotNull() {
			addCriterion("min_salary is not null");
			return (Criteria) this;
		}

		/**
		 * min_salary等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryEqualTo(Integer value) {
			addCriterion("min_salary =", value, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary不等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryNotEqualTo(Integer value) {
			addCriterion("min_salary <>", value, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary大于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryGreaterThan(Integer value) {
			addCriterion("min_salary >", value, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary大于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryGreaterThanOrEqualTo(Integer value) {
			addCriterion("min_salary >=", value, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary小于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryLessThan(Integer value) {
			addCriterion("min_salary <", value, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary小于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryLessThanOrEqualTo(Integer value) {
			addCriterion("min_salary <=", value, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryIn(List<Integer> values) {
			addCriterion("min_salary in", values, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary不在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryNotIn(List<Integer> values) {
			addCriterion("min_salary not in", values, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryBetween(Integer value1, Integer value2) {
			addCriterion("min_salary between", value1, value2, "minSalary");
			return (Criteria) this;
		}

		/**
		 * min_salary不在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMinSalaryNotBetween(Integer value1, Integer value2) {
			addCriterion("min_salary not between", value1, value2, "minSalary");
			return (Criteria) this;
		}
		/**
		 * max_salary字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryIsNull() {
			addCriterion("max_salary is null");
			return (Criteria) this;
		}

		/**
		 * max_salary字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryIsNotNull() {
			addCriterion("max_salary is not null");
			return (Criteria) this;
		}

		/**
		 * max_salary等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryEqualTo(Integer value) {
			addCriterion("max_salary =", value, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary不等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryNotEqualTo(Integer value) {
			addCriterion("max_salary <>", value, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary大于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryGreaterThan(Integer value) {
			addCriterion("max_salary >", value, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary大于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryGreaterThanOrEqualTo(Integer value) {
			addCriterion("max_salary >=", value, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary小于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryLessThan(Integer value) {
			addCriterion("max_salary <", value, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary小于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryLessThanOrEqualTo(Integer value) {
			addCriterion("max_salary <=", value, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryIn(List<Integer> values) {
			addCriterion("max_salary in", values, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary不在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryNotIn(List<Integer> values) {
			addCriterion("max_salary not in", values, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryBetween(Integer value1, Integer value2) {
			addCriterion("max_salary between", value1, value2, "maxSalary");
			return (Criteria) this;
		}

		/**
		 * max_salary不在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andMaxSalaryNotBetween(Integer value1, Integer value2) {
			addCriterion("max_salary not between", value1, value2, "maxSalary");
			return (Criteria) this;
		}
		/**
		 * companyId字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdIsNull() {
			addCriterion("companyId is null");
			return (Criteria) this;
		}

		/**
		 * companyId字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdIsNotNull() {
			addCriterion("companyId is not null");
			return (Criteria) this;
		}

		/**
		 * companyId等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdEqualTo(Integer value) {
			addCriterion("companyId =", value, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId不等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdNotEqualTo(Integer value) {
			addCriterion("companyId <>", value, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId大于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdGreaterThan(Integer value) {
			addCriterion("companyId >", value, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId大于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("companyId >=", value, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId小于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdLessThan(Integer value) {
			addCriterion("companyId <", value, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId小于等于传入值
		 * @param value [Integer]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdLessThanOrEqualTo(Integer value) {
			addCriterion("companyId <=", value, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdIn(List<Integer> values) {
			addCriterion("companyId in", values, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId不在传入值之中
		 * @param value [List<Integer>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdNotIn(List<Integer> values) {
			addCriterion("companyId not in", values, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdBetween(Integer value1, Integer value2) {
			addCriterion("companyId between", value1, value2, "companyId");
			return (Criteria) this;
		}

		/**
		 * companyId不在传入值之间
		 * @param value1 [Integer]传入值1
		 * @param value2 [Integer]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyIdNotBetween(Integer value1, Integer value2) {
			addCriterion("companyId not between", value1, value2, "companyId");
			return (Criteria) this;
		}
		/**
		 * company_name字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameIsNull() {
			addCriterion("company_name is null");
			return (Criteria) this;
		}

		/**
		 * company_name字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameIsNotNull() {
			addCriterion("company_name is not null");
			return (Criteria) this;
		}

		/**
		 * company_name等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameEqualTo(String value) {
			addCriterion("company_name =", value, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameNotEqualTo(String value) {
			addCriterion("company_name <>", value, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameGreaterThan(String value) {
			addCriterion("company_name >", value, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
			addCriterion("company_name >=", value, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameLessThan(String value) {
			addCriterion("company_name <", value, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameLessThanOrEqualTo(String value) {
			addCriterion("company_name <=", value, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameIn(List<String> values) {
			addCriterion("company_name in", values, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameNotIn(List<String> values) {
			addCriterion("company_name not in", values, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameBetween(String value1, String value2) {
			addCriterion("company_name between", value1, value2, "companyName");
			return (Criteria) this;
		}

		/**
		 * company_name不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyNameNotBetween(String value1, String value2) {
			addCriterion("company_name not between", value1, value2, "companyName");
			return (Criteria) this;
		}
		/**
		 * company_full_name字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameIsNull() {
			addCriterion("company_full_name is null");
			return (Criteria) this;
		}

		/**
		 * company_full_name字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameIsNotNull() {
			addCriterion("company_full_name is not null");
			return (Criteria) this;
		}

		/**
		 * company_full_name等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameEqualTo(String value) {
			addCriterion("company_full_name =", value, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameNotEqualTo(String value) {
			addCriterion("company_full_name <>", value, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameGreaterThan(String value) {
			addCriterion("company_full_name >", value, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameGreaterThanOrEqualTo(String value) {
			addCriterion("company_full_name >=", value, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameLessThan(String value) {
			addCriterion("company_full_name <", value, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameLessThanOrEqualTo(String value) {
			addCriterion("company_full_name <=", value, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameIn(List<String> values) {
			addCriterion("company_full_name in", values, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameNotIn(List<String> values) {
			addCriterion("company_full_name not in", values, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameBetween(String value1, String value2) {
			addCriterion("company_full_name between", value1, value2, "companyFullName");
			return (Criteria) this;
		}

		/**
		 * company_full_name不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andCompanyFullNameNotBetween(String value1, String value2) {
			addCriterion("company_full_name not between", value1, value2, "companyFullName");
			return (Criteria) this;
		}
		/**
		 * position_advantage字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageIsNull() {
			addCriterion("position_advantage is null");
			return (Criteria) this;
		}

		/**
		 * position_advantage字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageIsNotNull() {
			addCriterion("position_advantage is not null");
			return (Criteria) this;
		}

		/**
		 * position_advantage等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageEqualTo(String value) {
			addCriterion("position_advantage =", value, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageNotEqualTo(String value) {
			addCriterion("position_advantage <>", value, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageGreaterThan(String value) {
			addCriterion("position_advantage >", value, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageGreaterThanOrEqualTo(String value) {
			addCriterion("position_advantage >=", value, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageLessThan(String value) {
			addCriterion("position_advantage <", value, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageLessThanOrEqualTo(String value) {
			addCriterion("position_advantage <=", value, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageIn(List<String> values) {
			addCriterion("position_advantage in", values, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageNotIn(List<String> values) {
			addCriterion("position_advantage not in", values, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageBetween(String value1, String value2) {
			addCriterion("position_advantage between", value1, value2, "positionAdvantage");
			return (Criteria) this;
		}

		/**
		 * position_advantage不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAdvantageNotBetween(String value1, String value2) {
			addCriterion("position_advantage not between", value1, value2, "positionAdvantage");
			return (Criteria) this;
		}
		/**
		 * district字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictIsNull() {
			addCriterion("district is null");
			return (Criteria) this;
		}

		/**
		 * district字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictIsNotNull() {
			addCriterion("district is not null");
			return (Criteria) this;
		}

		/**
		 * district等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictEqualTo(String value) {
			addCriterion("district =", value, "district");
			return (Criteria) this;
		}

		/**
		 * district不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictNotEqualTo(String value) {
			addCriterion("district <>", value, "district");
			return (Criteria) this;
		}

		/**
		 * district大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictGreaterThan(String value) {
			addCriterion("district >", value, "district");
			return (Criteria) this;
		}

		/**
		 * district大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictGreaterThanOrEqualTo(String value) {
			addCriterion("district >=", value, "district");
			return (Criteria) this;
		}

		/**
		 * district小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictLessThan(String value) {
			addCriterion("district <", value, "district");
			return (Criteria) this;
		}

		/**
		 * district小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictLessThanOrEqualTo(String value) {
			addCriterion("district <=", value, "district");
			return (Criteria) this;
		}

		/**
		 * district在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictIn(List<String> values) {
			addCriterion("district in", values, "district");
			return (Criteria) this;
		}

		/**
		 * district不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictNotIn(List<String> values) {
			addCriterion("district not in", values, "district");
			return (Criteria) this;
		}

		/**
		 * district在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictBetween(String value1, String value2) {
			addCriterion("district between", value1, value2, "district");
			return (Criteria) this;
		}

		/**
		 * district不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andDistrictNotBetween(String value1, String value2) {
			addCriterion("district not between", value1, value2, "district");
			return (Criteria) this;
		}
		/**
		 * biz_area字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaIsNull() {
			addCriterion("biz_area is null");
			return (Criteria) this;
		}

		/**
		 * biz_area字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaIsNotNull() {
			addCriterion("biz_area is not null");
			return (Criteria) this;
		}

		/**
		 * biz_area等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaEqualTo(String value) {
			addCriterion("biz_area =", value, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaNotEqualTo(String value) {
			addCriterion("biz_area <>", value, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaGreaterThan(String value) {
			addCriterion("biz_area >", value, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaGreaterThanOrEqualTo(String value) {
			addCriterion("biz_area >=", value, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaLessThan(String value) {
			addCriterion("biz_area <", value, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaLessThanOrEqualTo(String value) {
			addCriterion("biz_area <=", value, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaIn(List<String> values) {
			addCriterion("biz_area in", values, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaNotIn(List<String> values) {
			addCriterion("biz_area not in", values, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaBetween(String value1, String value2) {
			addCriterion("biz_area between", value1, value2, "bizArea");
			return (Criteria) this;
		}

		/**
		 * biz_area不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andBizAreaNotBetween(String value1, String value2) {
			addCriterion("biz_area not between", value1, value2, "bizArea");
			return (Criteria) this;
		}
		/**
		 * lng字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngIsNull() {
			addCriterion("lng is null");
			return (Criteria) this;
		}

		/**
		 * lng字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngIsNotNull() {
			addCriterion("lng is not null");
			return (Criteria) this;
		}

		/**
		 * lng等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngEqualTo(String value) {
			addCriterion("lng =", value, "lng");
			return (Criteria) this;
		}

		/**
		 * lng不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngNotEqualTo(String value) {
			addCriterion("lng <>", value, "lng");
			return (Criteria) this;
		}

		/**
		 * lng大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngGreaterThan(String value) {
			addCriterion("lng >", value, "lng");
			return (Criteria) this;
		}

		/**
		 * lng大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngGreaterThanOrEqualTo(String value) {
			addCriterion("lng >=", value, "lng");
			return (Criteria) this;
		}

		/**
		 * lng小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngLessThan(String value) {
			addCriterion("lng <", value, "lng");
			return (Criteria) this;
		}

		/**
		 * lng小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngLessThanOrEqualTo(String value) {
			addCriterion("lng <=", value, "lng");
			return (Criteria) this;
		}

		/**
		 * lng在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngIn(List<String> values) {
			addCriterion("lng in", values, "lng");
			return (Criteria) this;
		}

		/**
		 * lng不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngNotIn(List<String> values) {
			addCriterion("lng not in", values, "lng");
			return (Criteria) this;
		}

		/**
		 * lng在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngBetween(String value1, String value2) {
			addCriterion("lng between", value1, value2, "lng");
			return (Criteria) this;
		}

		/**
		 * lng不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLngNotBetween(String value1, String value2) {
			addCriterion("lng not between", value1, value2, "lng");
			return (Criteria) this;
		}
		/**
		 * lat字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatIsNull() {
			addCriterion("lat is null");
			return (Criteria) this;
		}

		/**
		 * lat字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatIsNotNull() {
			addCriterion("lat is not null");
			return (Criteria) this;
		}

		/**
		 * lat等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatEqualTo(String value) {
			addCriterion("lat =", value, "lat");
			return (Criteria) this;
		}

		/**
		 * lat不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatNotEqualTo(String value) {
			addCriterion("lat <>", value, "lat");
			return (Criteria) this;
		}

		/**
		 * lat大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatGreaterThan(String value) {
			addCriterion("lat >", value, "lat");
			return (Criteria) this;
		}

		/**
		 * lat大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatGreaterThanOrEqualTo(String value) {
			addCriterion("lat >=", value, "lat");
			return (Criteria) this;
		}

		/**
		 * lat小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatLessThan(String value) {
			addCriterion("lat <", value, "lat");
			return (Criteria) this;
		}

		/**
		 * lat小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatLessThanOrEqualTo(String value) {
			addCriterion("lat <=", value, "lat");
			return (Criteria) this;
		}

		/**
		 * lat在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatIn(List<String> values) {
			addCriterion("lat in", values, "lat");
			return (Criteria) this;
		}

		/**
		 * lat不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatNotIn(List<String> values) {
			addCriterion("lat not in", values, "lat");
			return (Criteria) this;
		}

		/**
		 * lat在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatBetween(String value1, String value2) {
			addCriterion("lat between", value1, value2, "lat");
			return (Criteria) this;
		}

		/**
		 * lat不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andLatNotBetween(String value1, String value2) {
			addCriterion("lat not between", value1, value2, "lat");
			return (Criteria) this;
		}
		/**
		 * education字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationIsNull() {
			addCriterion("education is null");
			return (Criteria) this;
		}

		/**
		 * education字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationIsNotNull() {
			addCriterion("education is not null");
			return (Criteria) this;
		}

		/**
		 * education等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationEqualTo(String value) {
			addCriterion("education =", value, "education");
			return (Criteria) this;
		}

		/**
		 * education不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationNotEqualTo(String value) {
			addCriterion("education <>", value, "education");
			return (Criteria) this;
		}

		/**
		 * education大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationGreaterThan(String value) {
			addCriterion("education >", value, "education");
			return (Criteria) this;
		}

		/**
		 * education大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationGreaterThanOrEqualTo(String value) {
			addCriterion("education >=", value, "education");
			return (Criteria) this;
		}

		/**
		 * education小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationLessThan(String value) {
			addCriterion("education <", value, "education");
			return (Criteria) this;
		}

		/**
		 * education小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationLessThanOrEqualTo(String value) {
			addCriterion("education <=", value, "education");
			return (Criteria) this;
		}

		/**
		 * education在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationIn(List<String> values) {
			addCriterion("education in", values, "education");
			return (Criteria) this;
		}

		/**
		 * education不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationNotIn(List<String> values) {
			addCriterion("education not in", values, "education");
			return (Criteria) this;
		}

		/**
		 * education在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationBetween(String value1, String value2) {
			addCriterion("education between", value1, value2, "education");
			return (Criteria) this;
		}

		/**
		 * education不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andEducationNotBetween(String value1, String value2) {
			addCriterion("education not between", value1, value2, "education");
			return (Criteria) this;
		}
		/**
		 * work_year字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearIsNull() {
			addCriterion("work_year is null");
			return (Criteria) this;
		}

		/**
		 * work_year字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearIsNotNull() {
			addCriterion("work_year is not null");
			return (Criteria) this;
		}

		/**
		 * work_year等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearEqualTo(String value) {
			addCriterion("work_year =", value, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearNotEqualTo(String value) {
			addCriterion("work_year <>", value, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearGreaterThan(String value) {
			addCriterion("work_year >", value, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearGreaterThanOrEqualTo(String value) {
			addCriterion("work_year >=", value, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearLessThan(String value) {
			addCriterion("work_year <", value, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearLessThanOrEqualTo(String value) {
			addCriterion("work_year <=", value, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearIn(List<String> values) {
			addCriterion("work_year in", values, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearNotIn(List<String> values) {
			addCriterion("work_year not in", values, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearBetween(String value1, String value2) {
			addCriterion("work_year between", value1, value2, "workYear");
			return (Criteria) this;
		}

		/**
		 * work_year不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andWorkYearNotBetween(String value1, String value2) {
			addCriterion("work_year not between", value1, value2, "workYear");
			return (Criteria) this;
		}
		/**
		 * position_description字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionIsNull() {
			addCriterion("position_description is null");
			return (Criteria) this;
		}

		/**
		 * position_description字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionIsNotNull() {
			addCriterion("position_description is not null");
			return (Criteria) this;
		}

		/**
		 * position_description等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionEqualTo(String value) {
			addCriterion("position_description =", value, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionNotEqualTo(String value) {
			addCriterion("position_description <>", value, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionGreaterThan(String value) {
			addCriterion("position_description >", value, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("position_description >=", value, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionLessThan(String value) {
			addCriterion("position_description <", value, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionLessThanOrEqualTo(String value) {
			addCriterion("position_description <=", value, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionIn(List<String> values) {
			addCriterion("position_description in", values, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionNotIn(List<String> values) {
			addCriterion("position_description not in", values, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionBetween(String value1, String value2) {
			addCriterion("position_description between", value1, value2, "positionDescription");
			return (Criteria) this;
		}

		/**
		 * position_description不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionDescriptionNotBetween(String value1, String value2) {
			addCriterion("position_description not between", value1, value2, "positionDescription");
			return (Criteria) this;
		}
		/**
		 * position_address字段为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressIsNull() {
			addCriterion("position_address is null");
			return (Criteria) this;
		}

		/**
		 * position_address字段为不为null
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressIsNotNull() {
			addCriterion("position_address is not null");
			return (Criteria) this;
		}

		/**
		 * position_address等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressEqualTo(String value) {
			addCriterion("position_address =", value, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address不等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressNotEqualTo(String value) {
			addCriterion("position_address <>", value, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address大于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressGreaterThan(String value) {
			addCriterion("position_address >", value, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address大于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressGreaterThanOrEqualTo(String value) {
			addCriterion("position_address >=", value, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address小于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressLessThan(String value) {
			addCriterion("position_address <", value, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address小于等于传入值
		 * @param value [String]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressLessThanOrEqualTo(String value) {
			addCriterion("position_address <=", value, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressIn(List<String> values) {
			addCriterion("position_address in", values, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address不在传入值之中
		 * @param value [List<String>]传入值
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressNotIn(List<String> values) {
			addCriterion("position_address not in", values, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressBetween(String value1, String value2) {
			addCriterion("position_address between", value1, value2, "positionAddress");
			return (Criteria) this;
		}

		/**
		 * position_address不在传入值之间
		 * @param value1 [String]传入值1
		 * @param value2 [String]传入值2
		 * @return [Criteria]更新后的criteria
		 */
		public Criteria andPositionAddressNotBetween(String value1, String value2) {
			addCriterion("position_address not between", value1, value2, "positionAddress");
			return (Criteria) this;
		}

	}
}