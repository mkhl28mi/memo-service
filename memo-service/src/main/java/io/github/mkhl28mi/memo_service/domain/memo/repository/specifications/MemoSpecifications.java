package io.github.mkhl28mi.memo_service.domain.memo.repository.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class MemoSpecifications {
	
	private MemoSpecifications() {
	}
	
	public static Specification<Memo> filterMemos(
            UUID departmentId,
            Integer memoNumber,
            UUID userId,
            String keyword,
            Integer year,
            Integer month,
            String label,
            UUID employeeId
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(cb.equal(root.get("department").get("id"), departmentId));

            if (memoNumber != null) {
                predicates.add(cb.equal(root.get("sequenceNumber"), memoNumber));
            } else {
            	addUserIdPredicate(root, cb, predicates, userId);
                
            	addKeywordPredicate(root, cb, predicates, keyword);
            	
            	addYearPredicate(root, cb, predicates, year);
                
            	addMonthPredicate(root, cb, predicates, month);

            	addLabelPredicate(root, cb, predicates, label);

            	addEmployeeIdPredicate(root, query, cb, predicates, employeeId);
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
	
	private static void addUserIdPredicate(Root<Memo> root, CriteriaBuilder cb, List<Predicate> predicates, UUID userId) {
        if (userId != null) {
            Join<Object, Object> assignee = root.join("assignee", JoinType.LEFT);
            Join<Object, Object> user = assignee.join("user", JoinType.LEFT);
            predicates.add(cb.equal(user.get("id"), userId));
        }
	}
	
	private static void addKeywordPredicate(Root<Memo> root, CriteriaBuilder cb, List<Predicate> predicates, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%"));
        }
	}
	
	private static void addYearPredicate(Root<Memo> root, CriteriaBuilder cb, List<Predicate> predicates, Integer year) {
        if (year != null) {
            predicates.add(cb.equal(cb.function("DATE_PART", Integer.class, cb.literal("YEAR"), root.get("createdAt")), year));
        }
	}
	
	private static void addMonthPredicate(Root<Memo> root, CriteriaBuilder cb, List<Predicate> predicates, Integer month) {
        if (month != null) {
            predicates.add(cb.equal(cb.function("DATE_PART", Integer.class, cb.literal("MONTH"), root.get("createdAt")), month));
        }
	}
	
	private static void addLabelPredicate(Root<Memo> root, CriteriaBuilder cb, List<Predicate> predicates, String label) {
        if (label != null && !label.isBlank()) {
            Join<Object, Object> memoLabels = root.join("memoLabels", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(memoLabels.get("name")), "%" + label.toLowerCase() + "%"));
        }
	}
	
	private static void addEmployeeIdPredicate(Root<Memo> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates, UUID employeeId) {
	    if (employeeId != null) {
	        Subquery<Long> subquery = query.subquery(Long.class);
	        Root<Memo> subRoot = subquery.correlate(root);
	        
	        Join<Object, Object> memoEmployees = subRoot.join("memoEmployees");
	        Join<Object, Object> employeeAssignment = memoEmployees.join("employeeAssignment");
	        Join<Object, Object> employee = employeeAssignment.join("employee");
	        
	        subquery.select(cb.literal(1L))
	                .where(
	                		cb.and(
	                				cb.equal(employee.get("id"), employeeId)),
	                				cb.equal(memoEmployees.get("role"), "RECIPIENT"));
	        
	        predicates.add(cb.exists(subquery));
	    }
	}
	
}
