package com.hientran.sohebox.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.sco.SearchDateVO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.utils.SQLUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
@SuppressWarnings("serial")
public class GenericSpecs<T> {

	/**
	 * Search true/false
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildBooleanEqual(String fieldName, Boolean value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build date criteria: EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @param excludeTime
	 * @return
	 */

	@SuppressWarnings("hiding")
	private <T> Specification<T> buildDateEqual(String fieldName, Date value, boolean excludeTime) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Date minDate = value;
				Date maxDate = value;
				if (excludeTime) {
					minDate = MyDateUtils.getDateMin(value);
					maxDate = MyDateUtils.getDateMax(value);
				}

				Predicate predicate = criteriaBuilder.between(root.get(fieldName), minDate, maxDate);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build date criteria: GREATER or EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @param excludeTime
	 * @return
	 */
	private Specification<T> buildDateGe(String fieldName, Date value, boolean excludeTime) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Date checkDate = value;
				if (excludeTime) {
					checkDate = MyDateUtils.getDateMin(value);
				}

				Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), checkDate);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build date criteria: GREATER THAN
	 *
	 * @param fieldName
	 * @param value
	 * @param excludeTime
	 * @return
	 */
	private Specification<T> buildDateGt(String fieldName, Date value, boolean excludeTime) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Date checkDate = value;
				if (excludeTime) {
					checkDate = MyDateUtils.getDateMax(value);
				}

				Predicate predicate = criteriaBuilder.greaterThan(root.get(fieldName), checkDate);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build date criteria: IN
	 *
	 * @param fieldName
	 * @param values
	 * @param excludeTime
	 * @return
	 */
	private Specification<T> buildDateIn(String fieldName, Date[] values, boolean excludeTime) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get(fieldName).in(values);
			}
		};
	}

	/**
	 *
	 * Build date criteria: LESS THAN or EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @param excludeTime
	 * @return
	 */
	private Specification<T> buildDateLe(String fieldName, Date value, boolean excludeTime) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Date checkDate = value;
				if (excludeTime) {
					checkDate = MyDateUtils.getDateMax(value);
				}

				Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), checkDate);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build date criteria: LESS THAN
	 *
	 * @param fieldName
	 * @param value
	 * @param excludeTime
	 * @return
	 */
	private Specification<T> buildDateLt(String fieldName, Date value, boolean excludeTime) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Date checkDate = value;
				if (excludeTime) {
					checkDate = MyDateUtils.getDateMin(value);
				}

				Predicate predicate = criteriaBuilder.lessThan(root.get(fieldName), checkDate);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build number criteria: EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildNumberEqual(String fieldName, Double value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build number criteria: GREATER or EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildNumberGe(String fieldName, Double value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.ge(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build number criteria: GREATER THAN
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildNumberGt(String fieldName, Double value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.greaterThan(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build number criteria: IN
	 *
	 * @param fieldName
	 * @param values
	 * @return
	 */
	private Specification<T> buildNumberIn(String fieldName, Double[] values) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get(fieldName).in(values);
			}
		};
	}

	/**
	 *
	 * Build number criteria: LESS THAN or EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildNumberLe(String fieldName, Double value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.le(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build number criteria: LESS THAN
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildNumberLt(String fieldName, Double value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.lessThan(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build number criteria: NOT EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	private Specification<T> buildNumberNotEqual(String fieldName, Double value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.notEqual(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build search true/false
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	protected Specification<T> buildSearchBoolean(String fieldName, Boolean value) {
		// Declare result
		Specification<T> result = null;

		// Build search
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			result = buildBooleanEqual(fieldName, value);
		}

		// Return
		return result;
	}

	/**
	 *
	 * Build search DATE
	 *
	 * @param fieldName
	 * @param value
	 * @param excludeTime
	 * @return
	 */
	@SuppressWarnings("unused")
	protected Specification<T> buildSearchDate(String fieldName, SearchDateVO value, boolean excludeTime) {
		// Declare result
		Specification<T> result = null;

		// Build search
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			// Case equal
			if (value.getEq() != null) {
				result = buildDateEqual(fieldName, value.getEq(), excludeTime);

			} else if (value.getIn() != null && value.getIn().length > 0) {
				// Case in
				result = buildDateIn(fieldName, value.getIn(), excludeTime);

			} else {
				// Case difference
				if (value.getGe() != null) {
					if (result == null) {
						result = buildDateGe(fieldName, value.getGe(), excludeTime);
					} else {
						result = result.and(buildDateGe(fieldName, value.getGe(), excludeTime));
					}
				}

				if (value.getLe() != null) {
					if (result == null) {
						result = buildDateLe(fieldName, value.getLe(), excludeTime);
					} else {
						result = result.and(buildDateLe(fieldName, value.getLe(), excludeTime));
					}

				}

				if (value.getLt() != null) {
					if (result == null) {
						result = buildDateLt(fieldName, value.getLt(), excludeTime);
					} else {
						result = result.and(buildDateLt(fieldName, value.getLt(), excludeTime));
					}

				}
				if (value.getGt() != null) {
					if (result == null) {
						result = buildDateGt(fieldName, value.getGt(), excludeTime);
					} else {
						result = result.and(buildDateGt(fieldName, value.getGt(), excludeTime));
					}

				}
			}
		}

		// Return
		return result;

	}

	/**
	 *
	 * Build search NUMBER
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	protected Specification<T> buildSearchNumber(String fieldName, SearchNumberVO value) {
		// Declare result
		Specification<T> result = null;

		// Build search
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			// Case equal
			if (value.getEq() != null) {
				result = buildNumberEqual(fieldName, value.getEq());

			} else if (value.getNotEq() != null) {
				// Case not equal
				result = buildNumberNotEqual(fieldName, value.getNotEq());

			} else if (value.getIn() != null && value.getIn().length > 0) {
				// Case in
				result = buildNumberIn(fieldName, value.getIn());

			} else {
				// Case difference
				if (value.getGe() != null) {
					result = buildNumberGe(fieldName, value.getGe());
				} else if (value.getLe() != null) {
					result = buildNumberLe(fieldName, value.getLe());
				} else if (value.getLt() != null) {
					result = buildNumberLt(fieldName, value.getLt());
				} else if (value.getGt() != null) {
					result = buildNumberGt(fieldName, value.getGt());
				}
			}
		}

		// Return
		return result;
	}

	/**
	 *
	 * Build native SQL Search Number
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public String buildSearchNumberNative(String fieldName, SearchNumberVO value) {
		// Declare result
		String result = null;

		// Build search
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			// Case equal
			if (value.getEq() != null) {
				result = " AND " + fieldName + " = " + value.getEq();

			} else if (value.getIn() != null && value.getIn().length > 0) {
				// Case in
				result = " AND " + fieldName + " IN " + SQLUtils.convertToInNumberDoubleList(value.getIn());

			} else {
				// Case difference
				if (value.getGe() != null) {
					result = " AND " + fieldName + " >= " + value.getGe();
				} else if (value.getLe() != null) {
					result = " AND " + fieldName + " <= " + value.getLe();
				} else if (value.getLt() != null) {
					result = " AND " + fieldName + " < " + value.getLt();
				} else if (value.getGt() != null) {
					result = " AND " + fieldName + " > " + value.getGt();
				}
			}
		}

		// Return
		return result;
	}

	/**
	 *
	 * Build search TEXT
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	protected Specification<T> buildSearchText(String fieldName, SearchTextVO value) {
		// Declare result
		Specification<T> result = null;

		// Build search
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			if (value.getEq() != null) {
				result = buildTextEqual(fieldName, value.getEq());
			} else if (value.getLike() != null) {
				result = buildTextLike(fieldName, value.getLike(), value.getLikeMode());
			} else if (value.getIn() != null) {
				result = buildTextIn(fieldName, value.getIn());
			}
		}

		// Return
		return result;
	}

	/**
	 *
	 * Build native SQL Search Text
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public String buildSearchTextNative(String fieldName, SearchTextVO value) {
		// Declare result
		String result = null;

		// Build search
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			if (value.getEq() != null) {
				result = " AND " + fieldName + " = " + SQLUtils.normalizeSQLString(value.getEq());

			} else if (value.getLike() != null) {
				switch (value.getLikeMode()) {
				case DBConstants.LIKE_START:
					result = " AND " + fieldName + " LIKE " + SQLUtils.normalizeSQLLikeStart(value.getLike());
					break;
				case DBConstants.LIKE_END:
					result = " AND " + fieldName + " LIKE " + SQLUtils.normalizeSQLLikeEnd(value.getLike());
					break;
				default:
					result = " AND " + fieldName + " LIKE " + SQLUtils.normalizeSQLLikeAnyWhere(value.getLike());
					break;
				}

			} else if (value.getIn() != null) {
				result = " AND " + fieldName + " IN "
						+ SQLUtils.normalizeSQLString(new ArrayList<>(Arrays.asList(value.getIn())));
			}
		}

		// Return
		return result;
	}

	/**
	 *
	 * Build text criteria: EQUAL
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("hiding")
	private <T> Specification<T> buildTextEqual(String fieldName, String value) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);
				return predicate;
			}
		};
	}

	/**
	 *
	 * Build text criteria: IN
	 *
	 * @param fieldName
	 * @param values
	 * @return
	 */
	@SuppressWarnings("hiding")
	private <T> Specification<T> buildTextIn(String fieldName, String[] values) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get(fieldName).in(values);
			}
		};
	}

	/**
	 *
	 * Build text criteria: LIKE
	 *
	 * @param fieldName
	 * @param value
	 * @param likeMode
	 * @return
	 */
	@SuppressWarnings("hiding")
	private <T> Specification<T> buildTextLike(String fieldName, String value, int likeMode) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				String likeValue = null;
				switch (likeMode) {
				case DBConstants.LIKE_START:
					likeValue = DBConstants.WILDCARD + value;
					break;
				case DBConstants.LIKE_END:
					likeValue = value + DBConstants.WILDCARD;
					break;
				default:
					likeValue = DBConstants.WILDCARD + value + DBConstants.WILDCARD;
					break;
				}
				Predicate predicate = criteriaBuilder.like(root.get(fieldName), likeValue);
				return predicate;
			}
		};
	}
}