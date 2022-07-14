package com.example.groupproject.service;


import com.example.groupproject.entity.Order;
import com.example.groupproject.utils.Statistic;
import com.example.groupproject.utils.Turnover;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;

public interface StatisticService {

    Integer queryByPrice(Double minPrice, Double maxPrice);

    Integer queryCount();

    //房源统计
    List<Statistic> houseStatistic();

    Integer querySeller();

    Integer queryBuyer();

    Integer queryClientCount();

    //客源统计
    List clientStatistic();

    Integer queryDoneOrder();

    //营业额统计
    List turnoverStatistic(Integer quarter);

    Turnover lineChartDate();

    Integer queryOrderCount(Date startTime,Date endTime);

    List getOrderTotal();
}
