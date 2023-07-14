package com.hientran.sohebox.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * SQL Utils
 *
 */
public class SQLUtils {

	private static final String SPLASH = "\\";

	private static final String UNDERSCORED = "_";

	private static final String PERCENT = "%";

	private static final String QUOTE = "'";

	private static final String OPEN_PARANTHESE = "(";

	private static final String CLOSE_PARANTHESE = ")";

	public static final int LIKE_ANY_WHERE = 0;

	public static final int LIKE_START = 1;

	public static final int LIKE_END = 2;

	/**
	 * Convert an object to big decimal
	 *
	 * @param object
	 * @return
	 */
	public static BigDecimal convertToBigDecimal(Object object) {
		BigDecimal result = BigDecimal.ZERO;
		if (object != null) {
			result = BigDecimal.valueOf(Double.valueOf(object.toString()));
		}
		return result;
	}

	/**
	 * Explanation of processing
	 *
	 * @param srList
	 * @return
	 */
	public static Object convertToInBigNumberList(List<BigInteger> ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (CollectionUtils.isNotEmpty(ids)) {
			for (BigInteger data : ids) {
				sql.append(data);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Convert list data as integer to applied in sql statement.
	 *
	 * @param codes data list
	 * @return SQL expression
	 *
	 */
	public static String convertToInIntegerList(List<String> codes) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (CollectionUtils.isNotEmpty(codes)) {
			for (String data : codes) {
				sql.append(data);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Explanation of processing
	 *
	 * @param ids
	 * @return a string under SQL format.
	 */
	public static String convertToInNumberDoubleList(Double[] ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (null != ids) {
			for (Double data : ids) {
				sql.append(data);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Explanation of processing
	 *
	 * @param ids
	 * @return a string under SQL format.
	 */
	public static String convertToInNumberList(List<Long> ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Long data : ids) {
				sql.append(data);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	public static String convertToInStringArray(Collection<String> codes) {
		String[] pattern = new String[1];
		return convertToInStringArray(codes.toArray(pattern));

	}

	/**
	 * Explanation of processing
	 *
	 * @param ids
	 * @return a string under SQL format.
	 */
	public static String convertToInStringArray(String[] codes) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (null != codes) {
			for (String data : codes) {
				sql.append(normalizeSQLString(data));
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Convert list data as string to applied in sql statement.
	 *
	 * @param codes data list
	 * @return SQL expression
	 *
	 */
	public static String convertToInStringList(List<String> codes) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (CollectionUtils.isNotEmpty(codes)) {
			for (String data : codes) {
				sql.append(normalizeSQLString(data));
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		} else {
			sql.append("''");
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Convert int[] to string
	 *
	 * @param ids
	 * @return
	 */
	public static String convertToIntNumberArray(int[] ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		for (int data : ids) {
			sql.append(data);
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Explanation of processing
	 *
	 * @param ids
	 * @return a string under SQL format.
	 */
	public static String convertToIntNumberList(List<Integer> ids) {
		StringBuilder sql = new StringBuilder();
		sql.append(OPEN_PARANTHESE);
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Integer data : ids) {
				sql.append(data);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(CLOSE_PARANTHESE);
		return sql.toString();
	}

	/**
	 * Convert object to String
	 *
	 * @param object Java Object
	 * @return String
	 */
	public static String convertToString(Object object) {
		String result = "";
		if (object != null) {
			result = (String) object;
		}
		return result;
	}

	/**
	 * <P>
	 * Replace some specials characters in sql statement (\ _ *).
	 * </P>
	 *
	 * @param sqlString SQL statement
	 * @return String, SQL satement after replacing
	 */
	public static String escapeSpecialCharsInLike(String sqlString) {
		String sql = "";
		StringBuilder result = new StringBuilder();

		// Replace special character \ _ *
		sql = org.apache.commons.lang.StringUtils.replace(sqlString, SPLASH, SPLASH + SPLASH);
		// Replace special character _
		sql = org.apache.commons.lang.StringUtils.replace(sql, UNDERSCORED, SPLASH + UNDERSCORED);
		// Replace special character %
		sql = org.apache.commons.lang.StringUtils.replace(sql, PERCENT, SPLASH + PERCENT);
		// Replace special character '
		sql = org.apache.commons.lang.StringUtils.replace(sql, QUOTE, QUOTE + QUOTE);

		result.append(sql);

		return result.toString();
	}

	/**
	 * Escape special character quote '
	 *
	 * @param sqlString sql statement
	 * @return sql normalized
	 */
	public static String escapeString(final String sqlString) {
		// Replace special character '
		StringBuilder result = new StringBuilder();
		String temp;
		temp = org.apache.commons.lang.StringUtils.replace(sqlString, QUOTE, QUOTE + QUOTE);
		result.append(org.apache.commons.lang.StringUtils.replace(temp, SPLASH, SPLASH + SPLASH));
		return result.toString();

	}

	/**
	 *
	 * Normalize search like any where condition.
	 *
	 * @param sqlString SQL
	 * @return new SQL String
	 */
	public static String normalizeSQLLikeAnyWhere(final String sqlString) {

		StringBuilder result = new StringBuilder();
		result.append(QUOTE);
		result.append(PERCENT);
		result.append(escapeSpecialCharsInLike(sqlString));
		result.append(PERCENT);
		result.append(QUOTE);

		return result.toString();

	}

	/**
	 *
	 * Normalize search like end
	 *
	 * @param sqlString SQL
	 * @return new SQL String
	 */
	public static String normalizeSQLLikeEnd(final String sqlString) {

		StringBuilder result = new StringBuilder();
		result.append(QUOTE);
		result.append(escapeSpecialCharsInLike(sqlString));
		result.append(PERCENT);
		result.append(QUOTE);

		return result.toString();

	}

	/**
	 *
	 * Normalize search like start
	 *
	 * @param sqlString SQL
	 * @return new SQL String
	 */
	public static String normalizeSQLLikeStart(final String sqlString) {

		StringBuilder result = new StringBuilder();
		result.append(QUOTE);
		result.append(PERCENT);
		result.append(escapeSpecialCharsInLike(sqlString));
		result.append(QUOTE);

		return result.toString();

	}

	/**
	 *
	 * Put a list of sql string into double quote
	 *
	 * @param objectList SQL statement
	 * @return new list of SQL string to be into double quote.
	 */
	public static List<String> normalizeSQLString(final List<String> objectList) {
		List<String> results = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(objectList)) {
			StringBuilder result = null;
			for (String obj : objectList) {
				result = new StringBuilder();
				result.append(QUOTE);
				result.append(escapeString(obj));
				result.append(QUOTE);
				// Add in list
				results.add(result.toString());
			}
		}
		return results;
	}

	/**
	 *
	 * put sql string into double quote
	 *
	 * @param sqlString SQL statement
	 * @return new SQL string
	 */
	public static String normalizeSQLString(final String sqlString) {

		StringBuilder result = new StringBuilder();
		result.append(QUOTE);
		result.append(escapeString(sqlString));

		result.append(QUOTE);

		return result.toString();

	}

	/**
	 * Default constructor to hide me.
	 *
	 */
	private SQLUtils() {

	}
}
