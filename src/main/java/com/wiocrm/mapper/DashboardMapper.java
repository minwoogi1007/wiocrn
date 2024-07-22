package com.wiocrm.mapper;


import com.wiocrm.model.DashboardData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {
    DashboardData findDataForCard1(String userId);


    List<DashboardData> findPointList(String custCode);

    DashboardData findAvgHourlyData(String custCode);


    List<DashboardData> getDailyTasks(String userId);

    Map<String, Object> getCompanyFeeSummary();
    List<Map<String, Object>> getCompanyFeeList();

    Map<String, Object> getEstimatedMonthlyFee();

    List<Map<String, Object>> getConsultantFeesList();
    List<Map<String, Object>> getWeeklyFees(String userId);


    List<Map<String, Object>> getDeviceTrafficData(String userId);
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
