package com.demo.springUtil.repository;


import com.demo.springUtil.domain.SysLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SysLogRepository extends BaseRepository<SysLog, Long> {
	@Modifying
	@Query("UPDATE SysLog t SET t.deleted=1 WHERE t.id in (?1)")
	void deleteByIdIn_logic(List<Long> ids);

	@Modifying
	@Query("DELETE FROM SysLog t WHERE t.addTime < ?1")
	void deleteOneMonth(Date time);

	@Override
    @Modifying
	@Query("DELETE FROM SysLog")
	void deleteAll();
}
