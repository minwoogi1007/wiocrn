package com.wiocrm.mapper;


import com.wiocrm.model.DashboardData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {
    DashboardData findDataForCard1(String custCode);

    DashboardData findDataForCard2(String custCode);

    List<DashboardData> findPointList(String custCode);

    DashboardData dashConSum(String custCode);

    List<DashboardData> getDashboardCallCount(String custCode);

    List<DashboardData> getDashboardPersonCount(String custCode);

    DashboardData getDashboardMonth(String custCode);

    List<DashboardData> getEmployeeList();
    List<DashboardData> getCustomList();
    List<DashboardData> getDailyAve(String custCode);

    List<DashboardData> getWeeklySum(String custCode);
    List<DashboardData> getMonthlySum(String custCode);

    DashboardData getPoint(String custCode);
    List<DashboardData> getPointList(String custCode);



}
