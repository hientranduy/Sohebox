package com.hientran.sohebox.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.sco.EnglishSCO;
import com.hientran.sohebox.specification.EnglishSpecs;
import com.hientran.sohebox.specification.EnglishSpecs.EnglishTblEnum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public interface EnglishRepository
		extends JpaRepository<EnglishTbl, Long>, JpaSpecificationExecutor<EnglishTbl>, BaseRepository {

	EnglishSpecs specs = new EnglishSpecs();

	/**
	 *
	 * Get all data
	 *
	 * @return
	 */
	public default Page<EnglishTbl> findAll(EnglishSCO sco) {

		// Declare result
		Page<EnglishTbl> result = null;

		// Create data filter
		Specification<EnglishTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<EnglishTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

	/**
	 *
	 * Get all data that have low learn
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default List<Object[]> findLowLearn(EnglishSCO sco, EntityManager entityManager) {
		// Declare result
		List<Object[]> result = null;

		// Prepare native SQL
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT en.id             ");
		sql.append("      , en.key_word       ");
		sql.append("      , en.image_name     ");
		sql.append("      , en.explanation_en ");
		sql.append("      , en.explanation_vn ");
		sql.append("      , en.voice_uk_file  ");
		sql.append("      , en.voice_us_file  ");
		sql.append("      , er.record_times   ");
		sql.append("      , cat.type_code as category ");
		sql.append("      , gra.type_code as grade    ");
		sql.append(" FROM english_tbl en      ");
		sql.append(" LEFT JOIN english_learn_record_tbl er ON er.english_id = en.id AND er.user_id = ")
				.append(sco.getUserId().getEq());
		sql.append(" LEFT JOIN english_type_tbl cat ON cat.id = en.category_id");
		sql.append(" LEFT JOIN english_type_tbl gra ON gra.id = en.vus_grade_id");
		sql.append(" WHERE 1 = 1              ");

		// Add condition keyword if have
		if (sco.getKeyWord() != null) {
			sql.append(specs.buildSearchTextNative("en." + EnglishTblEnum.key_word.name(), sco.getKeyWord()));
		}

		// Add condition category if have
		if (sco.getCategoryId() != null) {
			sql.append(specs.buildSearchNumberNative("en." + EnglishTblEnum.category_id.name(), sco.getCategoryId()));
		}

		// Add condition level if have
		if (sco.getWordLevelId() != null) {
			sql.append(
					specs.buildSearchNumberNative("en." + EnglishTblEnum.word_level_id.name(), sco.getWordLevelId()));
		}

		// Add condition grade/learn day if have
		if (sco.getVusGradeId() != null) {
			if (sco.getLearnDayId() == null) {
				sql.append(
						specs.buildSearchNumberNative("en." + EnglishTblEnum.vus_grade_id.name(), sco.getVusGradeId()));
			} else {
				sql.append(" AND ( en.vus_grade_id < " + sco.getVusGradeId().getLe());
				sql.append(" OR ( en.vus_grade_id = " + sco.getVusGradeId().getLe() + " AND en.learn_day_id <= "
						+ sco.getLearnDayId().getLe() + "))");
			}

		}

		sql.append(" ORDER BY er.record_times ASC ");
		sql.append(" LIMIT ").append(sco.getMaxRecordPerPage());

		// Execute SQL
		Query query = entityManager.createNativeQuery(sql.toString());
		result = query.getResultList();

		// Return
		return result;
	}

	/**
	 *
	 * Get top learn
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default List<Object[]> findTopLearn(Long limitNumber, EntityManager entityManager) {
		// Declare result
		List<Object[]> result = null;

		// Prepare native SQL
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT user_id                       ");
		sql.append("      , max(learned_date) as learndate");
		sql.append(" FROM english_learn_report_tbl        ");
		sql.append(" GROUP BY user_id                     ");
		sql.append(" ORDER BY learndate desc              ");
		sql.append(" LIMIT ").append(limitNumber);

		// Execute SQL
		Query query = entityManager.createNativeQuery(sql.toString());
		result = query.getResultList();

		// Return
		return result;
	}
}
