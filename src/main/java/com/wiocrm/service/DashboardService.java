package com.wiocrm.service;

import com.wiocrm.config.CustomUserDetails;
import com.wiocrm.mapper.DashboardMapper;
import com.wiocrm.mapper.Tcnt01EmpMapper;
import com.wiocrm.mapper.Temp01Mapper;
import com.wiocrm.model.DashboardData;
import com.wiocrm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DashboardService {

    private final DashboardMapper dashboardMapper;
    private final Tcnt01EmpMapper tcnt01EmpMapper;
    private final Temp01Mapper temp01Mapper;
    @Autowired
    public DashboardService(Tcnt01EmpMapper tcnt01EmpMapper, Temp01Mapper temp01Mapper, DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
        this.tcnt01EmpMapper = tcnt01EmpMapper;
        this.temp01Mapper = temp01Mapper;
    }

    public Map<String, Object> getTcntEmp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 사용자의 CustomUserDetails 객체에서 custCode 추출
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String tempUserGrade ="";
        String custGrade = "";

        Map<String, Object> data = new HashMap<>();

        if(userDetails.getTempUserInfo()!= null){
            tempUserGrade = userDetails.getTempUserInfo().getPosition();
            data.put("dataForA", "A 등급 사용자에 대한 데이터");
        }else{
             custGrade = userDetails.getTcntUserInfo().getCust_grade();
        }

        if ("A".equals(custGrade)) {
            data.put("dataForA", "A 등급 사용자에 대한 데이터");
        }        // 내부 직원 정보 접근
        else if ("B".equals(custGrade)) {
            // 여기에 B 등급 사용자를 위한 데이터 준비 로직 추가
            data.put("dataForB", "B 등급 사용자에 대한 데이터");
        }

        return data;
    }
    public Map<String, Object> getDashboardData(String username,HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 사용자의 CustomUserDetails 객체에서 custCode 추출
       // CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        HttpSession session = request.getSession();

        Map<String, Object> data = new HashMap<>();
        User user = (User) session.getAttribute("user");
    // 사용자 유형에 따른 처리
        DashboardData card1Data = dashboardMapper.findDataForCard1(user.getUserId());//일,월  처리건
        List<DashboardData> pointList = dashboardMapper.findPointList(user.getUserId()); //이번달 수수료
        DashboardData avgHourlyData = dashboardMapper.findAvgHourlyData(user.getUserId()); //시간당 평균 처리건

        data.put("card-data-1", card1Data);
        data.put("pointlist-data", pointList);
        data.put("avg-hourly-data", avgHourlyData);

        return data;
    }

    public Map<String, Object> getDailyTasks(String username,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<String, Object> data = new HashMap<>();
        List<DashboardData> dailyTasks = dashboardMapper.getDailyTasks(user.getUserId());
        data.put("dailyTasks", dailyTasks);
        return data;
    }

    public Map<String, Object> getCompanyFeeSummary() {
        return dashboardMapper.getCompanyFeeSummary();
    }
    public List<Map<String, Object>> getCompanyFeeList() {
        return dashboardMapper.getCompanyFeeList();
    }
    public Map<String, Object> getEstimatedMonthlyFee() {
        return dashboardMapper.getEstimatedMonthlyFee();
    }


    public List<Map<String, Object>> getConsultantFeesList() {
        return dashboardMapper.getConsultantFeesList();
    }

    //로그인 유저별 코드 (거래처 는 업체 코드)
    private String getCurrentUserCustCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ""; // CustomUserDetails가 아니면 빈 문자열 반환
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 거래처 직원 정보 접근
        if (userDetails.getTcntUserInfo() != null) {
            //System.out.println("CustCode from Tcnt01Emp: " + userDetails.getTcntUserInfo().getCustCode());
            //System.out.println("getUserId from Tcnt01Emp: "+userDetails.getTcntUserInfo().getUserId());
            //System.out.println("getCust_grade from Tcnt01Emp: "+userDetails.getTcntUserInfo().getCust_grade());
            //System.out.println("getCust_gubn from Tcnt01Emp: "+userDetails.getTcntUserInfo().getCust_gubn());
            return userDetails.getTcntUserInfo().getCustCode();
        }

        // 내부 직원 정보 접근
        if (userDetails.getTempUserInfo() != null) {
            // 내부 직원에 대한 처리가 필요한 경우 여기에 로직 추가
            System.out.println("Accessing Temp01 UserInfo");
            // 예: return userDetails.getTempUserInfo().getSomeOtherInfo();
        }

        return "";
    }
    public Map<String, Object> getDashboardCallCount(String username) {
        String custCode = getCurrentUserCustCode();
        Map<String, Object> data = new HashMap<>();
        data.put("dashStatCount-data", dashboardMapper.getDashboardCallCount(custCode));
        return data;
    }

    public Map<String, Object> getDashboardPersonCount(String username) {
        String custCode = getCurrentUserCustCode();
        Map<String, Object> data = new HashMap<>();
        data.put("dashStatCount-data", dashboardMapper.getDashboardPersonCount(custCode));
        return data;
    }

    public Map<String, Object> getDashboardMonth(String username) {
        String custCode = getCurrentUserCustCode();
        Map<String, Object> data = new HashMap<>();
        data.put("dashMonth-data", dashboardMapper.getDashboardMonth(custCode));
        return data;
    }
    public Map<String, Object> getEmployeeList() {
        checkUserRole();
        Map<String, Object> data = new HashMap<>();

        data.put("employeeList", dashboardMapper.getEmployeeList());
        return data;
    }
    public Map<String, Object> getCustomList() {
        checkUserRole();
        Map<String, Object> data = new HashMap<>();

        data.put("customList", dashboardMapper.getCustomList());
        return data;
    }

    public Map<String, Object> getDailyAve(String username) {
        String custCode = getCurrentUserCustCode();
        Map<String, Object> data = new HashMap<>();
        data.put("dailyAve", dashboardMapper.getDailyAve(custCode));
        return data;
    }
    public Map<String, Object> getWeeklySum(String username) {
        String custCode = getCurrentUserCustCode();
        Map<String, Object> data = new HashMap<>();
        data.put("dailyAve", dashboardMapper.getWeeklySum(custCode));
        return data;
    }
    public Map<String, Object> getMonthlySum(String username) {
        String custCode = getCurrentUserCustCode();
        Map<String, Object> data = new HashMap<>();

        data.put("dailyAve", dashboardMapper.getMonthlySum(custCode));
        return data;
    }
    public void checkUserRole() {
        // 현재 인증된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("사용자가 로그인하지 않았습니다.");
            return;
        }

        // 사용자의 권한 정보를 추출하고, ROLE_EMPLOYEE 또는 ROLE_USER 인지 확인
        boolean isEmployee = false;
        boolean isUser = false;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_EMPLOYEE")) {
                isEmployee = true;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
            }
        }

        // 권한에 따라 다른 로직 수행
        if (isEmployee) {
            System.out.println("사용자는 직원(EMPLOYEE)입니다.");
        } else if (isUser) {
            System.out.println("사용자는 거래처(USER)입니다.");
        } else {
            System.out.println("사용자는 알려진 권한이 없습니다.");
        }
    }


    public Map<String, Object> getDashBoardPoint(String username) {

        Map<String, Object> data = new HashMap<>();
        // 데이터베이스 조회
        String custCode = getCurrentUserCustCode();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        DashboardData point = null;
        List<DashboardData> pointList = null;
        //System.out.println("userDetails.getTcntUserInfo()====" + userDetails.getTcntUserInfo());
        //System.out.println("userDetails.getTempUserInfo()====" + userDetails.getTempUserInfo());
        // 사용자 유형에 따른 처리
        // 거래처 사용자인 경우


        // 내부 직원 사용자인 경우
        // 내부 직원 사용자에 대한 처리 로직 추가
        point = dashboardMapper.getPoint(custCode);
        pointList = dashboardMapper.getPointList(custCode);

        data.put("point", point);
        data.put("pointList", pointList);

        return data;
    }
}
