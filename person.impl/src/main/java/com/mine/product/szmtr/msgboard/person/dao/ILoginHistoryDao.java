package com.mine.product.szmtr.msgboard.person.dao;

import com.mine.product.szmtr.msgboard.person.model.LoginHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @title: ILoginHistoryDao
 * @description:
 * @auther: yeaho_lee
 * @version: 1.0
 * @create 2019/9/10 14:54
 */
public interface ILoginHistoryDao extends JpaRepository<LoginHistoryModel, String> {
    @Query(nativeQuery = true, value="select * from (select * from (select * from LoginHistoryModel where userName =?1 and ifUse =?5 and CREATETIMESTAMP >= TO_DATE(?2,'YYYY-MM-DD HH24:MI:SS') and CREATETIMESTAMP <= TO_DATE(?3,'YYYY-MM-DD HH24:MI:SS') order by CREATETIMESTAMP desc) where ROWNUM <=5) where status=?4 ")
    List<LoginHistoryModel> getLoginHistoryModelByQuery(String userName, String beginDate, String endDate, int status, int ifUse);
    @Query(nativeQuery = true, value="select * from LoginHistoryModel where userName =?1 and CREATETIMESTAMP >= TO_DATE(?2,'YYYY-MM-DD')")
    List<LoginHistoryModel> getAllByUserNameAndCreateTimestamp(String userName, String date);
}
