package com.example.groupproject.controller;

import com.example.groupproject.entity.House;
import com.example.groupproject.handler.RestJson;
import com.example.groupproject.service.HouseService;
import com.example.groupproject.utils.PageBeans;
import com.example.groupproject.utils.Result;
import com.example.groupproject.utils.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 房源前端控制器
 * @author: 张振彬
 * @create: 2022-06-13
 * @version: 1.0
 */
@RestController
@Api(tags = "房源管理控制器")
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    //获取分页数据
    @GetMapping("/getAllByPage")
    @ApiOperation("房源分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageStart", value = "起始页", dataType = "String"),

            @ApiImplicitParam(name = "pageSize", value = "页面大小", dataType = "String")
    })
    public Result getAllByPage(String pageStart, String pageSize){
        PageBeans<House> pageBeans= this.houseService.queryByPage(new House(),Integer.valueOf(pageStart),Integer.valueOf(pageSize));
        return new Result(ResultCodeEnum.SUCCESS,pageBeans);
    }

    //id查询房源
    @GetMapping("/queryById/{houseId}")
    @ApiOperation("id查询房源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseId", value = "房源id", dataType = "String"),
    })
    public Result queryById(@PathVariable String houseId) {
        House house= this.houseService.queryById(Integer.valueOf(houseId));
        return new Result(ResultCodeEnum.SUCCESS,house);
    }

    //id查图片
    @GetMapping("/getImgByHouseId/{houseId}")
    @ApiOperation("id查询房源图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseId", value = "房源id", dataType = "String"),
    })
    public Result getImgByHouseId(@PathVariable String houseId) {
        House house= this.houseService.getImgByHouseId(Integer.valueOf(houseId));
        return new Result(ResultCodeEnum.SUCCESS,house);
    }

    //查询房源
    @GetMapping("/queryHouse")
    @ApiOperation("查询在售房源")
    public Result queryHouse() {
        List<House> list = this.houseService.queryAll();
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //查询房源
    @GetMapping("/getHouse")
    @ApiOperation("查询所有房源")
    public Result getHouse() {
        List<House> list = this.houseService.getHouse();
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //多条件查询房源
    @PostMapping("/queryCondition")
    @ApiOperation("多条件查询房源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "house", value = "房源对象", dataType = "House"),
    })
    public Result queryHouse(@RequestBody House house) {
        List<House> list = this.houseService.queryCondition(house);
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //添加房源
    @PostMapping("/add")
    @ApiOperation("添加房源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "house", value = "房源对象", dataType = "House"),
    })
    public Result addHouse(@RequestBody House house) {
        return this.houseService.add(house);

    }


    // 删除房源
    @GetMapping("/delete/{houseId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseId", value = "房源编号", dataType = "String"),
    })
    @ApiOperation("删除房源")
    public Result deleteHouse(@PathVariable String houseId) {
        return this.houseService.delete(Integer.valueOf(houseId));

    }

    //修改房源
    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "house", value = "房源对象", dataType = "House"),
    })
    @ApiOperation("修改房源信息")
    public Result updateHouse(@RequestBody House house) {
        return this.houseService.update(house);

    }

    //类型查询
    @GetMapping("/queryByType/{houseType}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseType", value = "房源类型", dataType = "String"),
    })
    @ApiOperation("类型索引")
    public Result queryHouseByType(@PathVariable String houseType) {
        List<House> list = this.houseService.queryByType(houseType);
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //房源面积区间查询
    @GetMapping("/queryByArea/{minHouseArea}&&{maxHouseArea}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "minHouseArea", value = "最小面积", dataType = "String"),
            @ApiImplicitParam(name = "maxHouseArea", value = "最大面积", dataType = "String"),
    })
    @ApiOperation("面积区间索引")
    public Result queryByArea(@PathVariable String minHouseArea, @PathVariable String maxHouseArea) {
        List<House> list = this.houseService.queryByArea(Integer.valueOf(minHouseArea), Integer.valueOf(maxHouseArea));
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //房源价格区间查询
    @GetMapping("/queryByPrice/{minPrice}&&{maxPrice}")
    @ApiOperation("价格区间索引")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "minPrice", value = "最低价格", dataType = "String"),
            @ApiImplicitParam(name = "maxPrice", value = "最高价格", dataType = "String"),
    })
    public Result queryByPrice(@PathVariable String minPrice, @PathVariable String maxPrice) {
        List<House> list = this.houseService.queryByPrice(Double.valueOf(minPrice), Double.valueOf(maxPrice));
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //城市查询
    @GetMapping("/queryByCity/{city}")
    @ApiOperation("城市查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", value = "城市", dataType = "String"),
    })
    public Result queryByCity(@PathVariable String city) {
        List<House> list = this.houseService.queryByCity(city);
        return new Result(ResultCodeEnum.SUCCESS,list);
    }

    //分页查询
    @GetMapping("/queryPage")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "house", value = "房源对象", dataType = "House"),

            @ApiImplicitParam(name = "pageStart", value = "起始页", dataType = "Integer"),

            @ApiImplicitParam(name = "pageSize", value = "页面大小", dataType = "Integer")
    })
    public Result  queryByPage(@RequestBody House house, @PathVariable Integer pageStart, @PathVariable Integer pageSize) {
        PageBeans<House> pageBeans= this.houseService.queryByPage(house, pageStart, pageSize);
        return new Result(ResultCodeEnum.SUCCESS,pageBeans);

    }

    //修改房源状态
    @GetMapping("/changeHouseStatus")
    @ApiOperation("修改房源状态")
    public Result  changeHouseStatus() {
        List<House> list= this.houseService.changeHouseStatus();
        return new Result(ResultCodeEnum.SUCCESS,list);

    }

    //状态查询
    @GetMapping("/queryByStatus/{houseStatus}")
    @ApiOperation("状态查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseStatus", value = "房源状态", dataType = "String"),
    })
    public Result queryByStatus(@PathVariable String houseStatus) {
        List<House> list = this.houseService.queryByStatus(houseStatus);
        return new Result(ResultCodeEnum.SUCCESS,list);
    }
    //id修改状态
    @GetMapping("/changeStatusById/{houseId}")
    @ApiOperation("通过id修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseStatus", value = "房源状态", dataType = "String"),
    })
    public Result changeStatusById(@PathVariable String houseId) {
        return this.houseService.changeStatusById(Integer.valueOf(houseId));
    }
}
