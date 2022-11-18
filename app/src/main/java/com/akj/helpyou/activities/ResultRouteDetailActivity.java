package com.akj.helpyou.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.akj.helpyou.R;
import com.akj.helpyou.activities.FindRoad.Time;
import com.akj.helpyou.activities.Odsay.DataKeyword;
import com.akj.helpyou.activities.Odsay.Dataset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResultRouteDetailActivity extends AppCompatActivity {
    public static int[] resi = new int[20];
    public static int resj = 0;
    public static int resjj = 0;
    public static String start;
    public static String[][] test = new String[5][20];
    public static int[][] traffic = new int[5][20];
    public static int[][] totalTime = new int[5][1];
    public static String[][] totalFee = new String[5][1];
    public static String[][] trafficNum = new String[5][20];
    public static String[][] startName = new String[5][20];
    public static Time time = new Time();


    //도보시
    public static String[][] distance = new String[5][20]; // 걷는 거리
    public static int[][] walkSectionTime = new int[5][20]; // 도보 이동 시간

    //버스
    public static int[][] busSectionTime = new int[5][20]; // 버스 이동 시간
    public static int[][] busArrivalTime = new int[5][20];; // 버스 실시간 도착 시간
    public static String[][] endName = new String[5][20]; // 버스 하차 정류장 이름
    public static String[][] busID = new String[5][20]; // 버스 정류장 고유 ID
    public static int[][] busStationCount = new int[5][20];; // 정차 정류장 수
    public static String[][] busLow = new String[5][20]; // 저상버스 유무

    //지하철
    public static int[][] subwaySectionTime = new int[5][20]; // 지하철 이동 시간
    public static int[][] subwayStationCount = new int[5][20];; // 정차 정류장 수
    public static int[][] subwayWaycode = new int[5][20];; // 1:상행 2:하행
    public static String[][] startSubwayTel = new String[5][20]; // 출발역 전화번호
    public static String[][] endSubwayTel = new String[5][20]; // 도착역 전화번호
    public static int[][] count;
    // 지하철or 버스 경유 정차명
    public static String[][][] passName = new String[5][20][100];  // [j][i][z] j/i는 그전에 썻던 그대로 위치값 z는 경유 정차 갯수
    // ex) [j][i][0]~[j][i][k]는 [j][i]가 버스일때 해당 버스 출발지부터 도착지까지의 경유 정류장이름이 들어가있음
    // 쓸 때 버스랑 지하철일 경우(if문)만 for문으로 z=0; z<100; z++하고 if(passName[j][i][z] !=null) 이면 passName[j][i][z] 호출 <<하면됄듯

//    public static String startRoute;
//    public static String endRoute;

    public static int i;
    public static void resdata2 (ArrayList<DataKeyword> data, int j, int i, int k, int trafficType, String[][][] Pass, int[][] pass){
        Dataset dataset = new Dataset(data, j, i, k, trafficType);
        count[j][i] = pass[j][i];
        passName = Pass;
        for(int z=0; z<100; z++){
            if(passName[j][i][z] != null){
                Log.d("aassdd" ,"passName" +passName[j][i][z]);
            }
        }
        Log.d("aassdd", "j / i : " +j + " " + i);
        if(i==0) {
            totalFee[j][0] = dataset.gettotalFee(j,i);
            totalTime[j][0] = dataset.gettotalTime(j,i);
            Log.d("hkhk","totalFee : " + totalFee[j][0]);
            Log.d("hkhk","totalTime : " + totalTime[j][0]);
        }
        traffic[j][i] = trafficType;

        resj = j;
        resi[j] = i;
        Log.d("ddd","dd" + resi[j] + " " + resj);
        if(traffic[j][i] == 3) {  // 도보값만 저장
            //test[resj][resi] = dataset.getDistance(resj, resi);
            // 검색출발지
            distance[j][i] = String.valueOf(dataset.getDistance(j,i));
            walkSectionTime[j][i] = dataset.getWalkSectionTime(j,i);

        }
        if(traffic[j][i] == 2) {  // 버스값만 저장
            // 이동수단 번호
            startName[j][i] = dataset.getStartBusName(j,i);
            trafficNum[j][i] = dataset.getBusNo(j,i);
            Log.d("kkk", "startName : " + startName[j][i] +" resj : " + j);
            Log.d("kkk", "trafficNum : " + trafficNum[j][i] + " resi[j] : " + i);
            busSectionTime[j][i] = dataset.getBusSectionTime(j,i);
            busArrivalTime[j][i] = dataset.getBusArrivalTime(j,i);
            endName[j][i] = dataset.getEndBusName(j,i);
            busID[j][i] = dataset.getBusID(j,i);
            busStationCount[j][i] = dataset.getBusStationCount(j,i);
            busLow[j][i] = dataset.getBusID(j,i);
        }
        if(traffic[j][i] == 1) {  // 지하철 값만 저장
            //이동수단 번호
            Log.d("kkk", "startName : " + startName[j][i] +" resj : " + resj);
            Log.d("kkk", "trafficNum : " + trafficNum[j][i] + " resi[j] : " + resi[j]);
            startName[j][i] = dataset.getStartSubwayName(j,i);
            trafficNum[j][i] = dataset.getSubwayNo(j,i);

            subwaySectionTime[j][i] = dataset.getSubwaySectionTime(j,i);
            endName[j][i] = dataset.getEndSubwayName(j,i);
            subwayStationCount[j][i] = dataset.getSubwayStationCount(j,i);
            startSubwayTel[j][i] = dataset.getStartSubwayTel(j,i);
            endSubwayTel[j][i] = dataset.getEndSubwayTel(j,i);
            subwayWaycode[j][i] = dataset.getSubwayWaycode(j,i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_route_detail);

        RecyclerView rvInfd = findViewById(R.id.recyclerView_inf_d);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResultRouteDetailActivity.this);
        InfDAdapter infDAdapter = new InfDAdapter(buildInfDList());
        rvInfd.setAdapter(infDAdapter);
        rvInfd.setLayoutManager(layoutManager);
        //resj에 고정 포지션값 받아오기
        //출발지 도착지에 값 입력
        //지도연결
        //슬라이드바에 상세 정보 표시
        //도보 버스 지하철 이런식으로
        //리사이클러 뷰로 하나만 필요
    }

    private List<InfD> buildInfDList() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        List<InfD> infDList = new ArrayList<>();
        String startRoute = intent.getStringExtra("startRoute");
        String endRoute = intent.getStringExtra("endRoute");

        for ( i=0; i<resi[position]+1; i++) {
            if(traffic[position][i] == 3) {  // 도보값만 저장
                // 검색출발지
                if(i == 0){
                    endName[position][i] = startName[position][i+1];
                    InfD infd = new InfD("도보  ",startRoute+"  ",endName[position][i]+"  ",walkSectionTime[position][i] + "분  ","           ","", buildInfD2List());
                    infDList.add(infd);
                }
                else if(i==resi[position]){
                    startName[position][i] = endName[position][i-1];
                    InfD infd = new InfD("도보  ",startName[position][i]+"  ",endRoute+"  ",walkSectionTime[position][i] + "분  ","           ","", buildInfD2List());
                    infDList.add(infd);
                }
                else{
                    startName[position][i] = endName[position][i-1];
                    InfD infd = new InfD("도보  ",startName[position][i]+"  ",endName[position][i]+"  ",walkSectionTime[position][i] + "분  ","dd "," ", buildInfD2List());
                    infDList.add(infd);
                }
            }
            if(traffic[position][i] == 2) {  // 버스값만 저장
                // 이동수단 번호
                InfD infd = new InfD("버스  ",startName[position][i]+"  ",endName[position][i]+"  ",busSectionTime[position][i] + "분  ","dd           ","상세보기 ", buildInfD2List());
                infDList.add(infd);
            }
            if(traffic[position][i] == 1) {  // 지하철 값만 저장
                //이동수단 번호
                InfD infd = new InfD("지하철  ",startName[position][i]+"  ",endName[position][i]+"  ",subwaySectionTime[position][i] + "분  ","출발역 : "+startSubwayTel[position][i]+"   \n도착역 : "+endSubwayTel[position][i],"      상세보기 ", buildInfD2List());
                infDList.add(infd);
            }
        }
        return infDList;
    }
    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
    private List<InfD2> buildInfD2List() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        List<InfD2> infD2List = new ArrayList<>();
        if (traffic[position][i] != 3) {
            for (int z = 0; z < passName.length; z++) {

                    InfD2 infD2 = new InfD2(passName[position][i][z]);
                    infD2List.add(infD2);

            }
        }
        return infD2List;
    }
}