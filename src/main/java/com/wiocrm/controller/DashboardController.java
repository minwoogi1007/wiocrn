package com.wiocrm.controller;
import com.wiocrm.model.DashboardData;
import com.wiocrm.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    //
    @GetMapping("/api/dashboard-data")
    public ResponseEntity<Map<String, Object>> getDashboardData(Principal principal,HttpServletRequest request) {
        // SecurityContext에서 인증 객체를 가져옵니다.
        String username = principal.getName();
       // dashboardService.printUserDetails();
        Map<String, Object> data = dashboardService.getDashboardData(username, request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard-daily-tasks")
    public ResponseEntity<Map<String, Object>> getDailyTasks(Principal principal,HttpServletRequest request) {
        String username = principal.getName();
        Map<String, Object> data = dashboardService.getDailyTasks(username,request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard/company-fee-summary")
    public ResponseEntity<Map<String, Object>> getCompanyFeeSummary() {
        Map<String, Object> summary = dashboardService.getCompanyFeeSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/api/dashboard/company-fee-list")
    public ResponseEntity<List<Map<String, Object>>> getCompanyFeeList() {
        List<Map<String, Object>> feeList = dashboardService.getCompanyFeeList();
        return ResponseEntity.ok(feeList);
    }

    @GetMapping("/api/dashboard/estimated-monthly-fee")
    public ResponseEntity<Map<String, Object>> getEstimatedMonthlyFee() {
        Map<String, Object> data = dashboardService.getEstimatedMonthlyFee();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard/consultant-fees")
    public ResponseEntity<List<Map<String, Object>>> getConsultantFeesList() {
        List<Map<String, Object>> data = dashboardService.getConsultantFeesList();
        return ResponseEntity.ok(data);
    }


    @GetMapping("/api/dashboard/weekly-fees")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyFees(HttpServletRequest request) {

        List<Map<String, Object>> data = dashboardService.getWeeklyFees(request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard/device-traffic")
    public ResponseEntity<List<Map<String, Object>>> getDeviceTrafficData(HttpServletRequest request) {
        List<Map<String, Object>> data = dashboardService.getDeviceTrafficData(request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard-callCount-data")
    public ResponseEntity<Map<String, Object>> getDashboardConCount(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getDashboardCallCount(username);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard-personCount-data")
    public ResponseEntity<Map<String, Object>> getDashboardPersonCount(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getDashboardPersonCount(username);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/dashboard-month-data")
    public ResponseEntity<Map<String, Object>> getDashboardMonth(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getDashboardMonth(username);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/empl/dashboard-employee")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public  ResponseEntity<Map<String, Object>> getEmployeeList() {


        Map<String, Object> data = dashboardService.getEmployeeList();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/empl/dashboard-custom")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public  ResponseEntity<Map<String, Object>> getCustomList() {


        Map<String, Object> data = dashboardService.getCustomList();
        return ResponseEntity.ok(data);
    }


    @GetMapping("/api/dashboard-daily-data")
    public ResponseEntity<Map<String, Object>> getDailyAve(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getDailyAve(username);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/api/dashboard-weekly-data")
    public ResponseEntity<Map<String, Object>> getWeeklySum(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getWeeklySum(username);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/api/dashboard-monthly-data")
    public ResponseEntity<Map<String, Object>> getMonthlySum(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getMonthlySum(username);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/api/dashboard-point-data")
    public ResponseEntity<Map<String, Object>> getDashBoardPoint(Principal principal) {

        String username = principal.getName();
        Map<String, Object> data = dashboardService.getDashBoardPoint(username);
        return ResponseEntity.ok(data);
    }
}