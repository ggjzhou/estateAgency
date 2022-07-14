<template>
  <div class="main">
    <!-- 用户操作组件 -->
    <div>
      <clientInfo></clientInfo>
    </div>
    <!-- 大标题 -->
    <div class="title">
      <h1>
        {{ selectHouseData.cellName }} &nbsp;靠梦时代 成熟小区，高品质楼盘
      </h1>
      <div class="sub">
        <p>地理位置很好，楼层好，视野宽阔，采光充足，配套实施齐全。</p>
      </div>
    </div>
    <!-- 地址导航 -->
    <div class="route">
      <!-- 首页 -->
      <span class="home"><a href="http://localhost:8080/Index">首页></a></span>
      <!-- 搜索页 -->
      <span class="search"><a href="http://localhost:8080/HouseType">搜索页></a></span>
      <!-- 当前页 -->
      <span class="detail"><a href="#">详情页</a></span>
    </div>
    <!-- 房屋信息 -->
    <div class="info">
      <!-- 图片展示区域 -->
      <div class="imgArea">
        <div class="block">
          <el-carousel trigger="click" height="400px">
            <el-carousel-item v-for="item in selectHouseData.img" :key="item.id">
              <!-- <div v-if="test(item)"> -->
              <img :src="item" alt="" />
              <!-- </div> -->
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <!-- 文字信息展示区域 -->
      <div class="textArea">
        <!-- 售价 -->
        <div class="sale">
          <div class="price">
            <span>{{ selectHouseData.housePrice }}</span> <span>万</span>
          </div>
          <div class="unitPrice">
            <span>
              {{
                  ((selectHouseData.housePrice * 10000) /
                    (selectHouseData.houseArea)).toFixed(2)
              }}元/㎡</span>
            <P>首付及贷款问题请资讯中介</P>
          </div>
        </div>
        <el-divider></el-divider>
        <!-- 描述信息 -->
        <div class="desc">
          <div class="layOutType">
            <span>{{ selectHouseData.layoutType }}</span>
          </div>
          <div class="orientation">
            <span>{{ selectHouseData.houseOrientation }}</span>
          </div>
          <div class="area">
            <span>{{ selectHouseData.houseArea }}平米</span>
          </div>
        </div>
        <el-divider></el-divider>
        <!-- 地址信息 -->
        <div class="add">
          <div class="cellName">
            <span class="fix">小区名称</span><span>{{ selectHouseData.cellName }}</span>
          </div>
          <div class="address">
            <span class="fix">所在区域</span><span>{{ selectHouseData.address }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <!-- 中介信息 -->
        <div class="agent" v-bind="empData">
          <!-- 头像 -->
          <div class="face">
            <img
              src="https://img.ljcdn.com/usercenter/images/uc_ehr_avatar/b781ad48-c258-4d18-9f24-2efd9cbfacf7.jpg.80x80.jpg"
              alt="" />
          </div>
          <!-- 联系 -->
          <div class="chat">
            <div class="name">
              <span>{{ empData.empName }}</span>
            </div>
            <div class="phone">
              <span>{{ empData.empPhone }}</span>
            </div>
            <div class="buyHouse">
              <el-button type="primary" @click="outerVisible = true">点击购房</el-button>
            </div>
            <el-dialog title="" :visible.sync="outerVisible">
              <el-descriptions title="房屋信息" direction="vertical" :column="2" border>
                <el-descriptions-item label="房屋名">{{ house.cellName }}</el-descriptions-item>
                <el-descriptions-item label="房屋地址">{{ house.address }}</el-descriptions-item>
                <el-descriptions-item label="房屋总价">{{ house.housePrice }}</el-descriptions-item>
                <el-descriptions-item label="房屋面积">{{ house.houseArea }} </el-descriptions-item>
                <el-descriptions-item label="房屋类型">{{ house.houseType }}</el-descriptions-item>
                <el-descriptions-item label="房屋布局">{{ house.layoutType }}</el-descriptions-item>
              </el-descriptions>

              <div slot="footer" class="dialog-footer">
                <el-button @click="buyHouse">提 交</el-button>
                <el-button @click="outerVisible = false">取 消</el-button>
              </div>
            </el-dialog>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import clientInfo from '@/components/common/clientInfo.vue';
import Cookies from "js-cookie"
export default {
  components: {
    clientInfo
  },
  created() {
    this.clientId = Cookies.get('clientId')
    this.selectHouseData =
      this.$session.get("selectHouseData"); //获取session
    delete this.selectHouseData.img.imgId
    this.getSelectHouseData();
    this.getEmpData();
  },
  data() {
    return {
      selectHouseData: {},
      // 房源详情数据
      // 中介数据
      empData: {
      },
      // 对话框显示
      outerVisible: false,
      // 当前登录客户编号
      clientId: '',
      // 图表信息
      house: {
        cellName: '',
        address: '',
        housePrice: '',
        houseArea: '',
        houseType: '',
        layoutType: '',
      },
      // 订单信息
      order: {
        "orderId": null,
        "clientId": 0,
        "empId": 0,
        "houseId": 0,
        "orderStatus": "待支付",
        "salePrice": 0,
        "buildTime": "2022-06-28T07:34:04.412Z",
      }

    };
  },
  methods: {
    getSelectHouseData() {
      console.log(this.selectHouseData);
      this.house.cellName = this.selectHouseData.cellName
      this.house.address = this.selectHouseData.address
      this.house.housePrice = this.selectHouseData.housePrice
      this.house.houseArea = this.selectHouseData.houseArea
      this.house.houseType = this.selectHouseData.houseType
      this.house.layoutType = this.selectHouseData.layoutType
      //   console.log(this.selectHouseData.address);
    },

    getEmpData() {
      this.$axios({
        url: "/emp/getEmpById",
        method: "get",
        params: {
          empId: this.selectHouseData.empId,
        },
        header: {
          "Content-Type": "application/json;charset=UTF-8",
        },
      }).then((res) => {
        // console.log(res.data.data);
        this.empData = res.data.data;
      })
        ;
    },
    // 添加订单，买房
    buyHouse() {
      // 生成当前时间
      let d = new Date()
      let year = d.getFullYear()
      let month = d.getMonth() + 1
      let day = d.getDate()
      let hour = d.getHours()
      let minute = d.getMinutes()
      let second = d.getSeconds()
      let time = `${year}-${month}-${day} ${hour}:${minute}:${second}`
      this.order.clientId = parseInt(this.clientId)
      this.order.empId = this.selectHouseData.empId
      this.order.houseId = this.selectHouseData.houseId
      this.order.salePrice = this.selectHouseData.housePrice
      this.order.buildTime = time
      console.log(this.order);
      this.outerVisible = false;
      this.$axios.post('/order/add/', this.order).then(res => {

        console.log(res);
        if (res.data.message == "添加成功") {
          this.$axios({
            url: "/house/changeHouseStatus",//请求的后台接口
            method: "get",//get请求方式
          }).then(response => {
            console.log(response)
          }).catch(error => {
            //请求失败
          });
          this.$message({
            showClose: true,
            message: "订单添加成功，请尽快支付",
            type: 'success'
          });
        } else {
          this.$message({
            showClose: true,
            message: "该房源已经售出,寻找其他好房吧",
            type: 'error'
          });
        }
      })
    }
  },
};
</script>
<style>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

.main {
  padding: 0 200px;
  /* text-align: center; */
}

.title h1 {
  font-size: 26px;
}

.title .sub p {
  margin-bottom: 30px;
  font-size: 14px;
  color: #aaa;
}

/* 地址导航 */
.route a {
  display: inline-block;
}

/* 设置主要区域flex布局 */
.info {
  display: flex;
  margin-top: 30px;
}

.imgArea .block {
  width: 710px;
  height: 400px;
}

.textArea {
  margin-left: 50px;
  width: 380px;
}

/* 设置售价区域flex布局 */
.sale {
  display: flex;
}

/* 设置字体 */
.sale .price :first-child {
  display: inline-block;
  font-family: Tahoma;
  font-size: 46px;
  line-height: 46px;
  color: #e4393c;
  font-weight: bold;
  letter-spacing: -1px;
  max-width: 180px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-right: 2px;
}

.sale .price :last-child {
  display: inline-block;
  font-size: 16px;
  color: #e4393c;
  height: 37px;
  vertical-align: 6px;
}

.sale .unitPrice {
  padding-left: 10px;
}

.sale .unitPrice span {
  font-weight: bold;
  color: #394043;
  white-space: nowrap;
}

.sale .unitPrice p {
  /* display: inline-block; */
  max-width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  vertical-align: middle;
}

/* 设置描述信息区域flex布局 */
.desc {
  display: flex;
  justify-content: space-between;
}

.desc span {
  font-size: 20px;
  font-weight: bold;
  color: #333333;
  overflow: hidden;
  text-overflow: ellipsis;
  -o-text-overflow: ellipsis;
  white-space: nowrap;
}

/* 地址区域 */
.add .fix {
  color: #aeb0b1;
  margin-right: 24px;
}

/* 设置中介信息flex布局 */
.agent {
  display: flex;
  align-items: center;
  margin-bottom: 0px;
}

.agent .face img {
  border-radius: 50%;
  margin-right: 10px;
}

/* .agent .chat :first-child {
  margin-bottom: 10px;
} */
.agent .chat div {
  margin-bottom: 10px;
}

.agent .chat .name span {
  vertical-align: middle;
  font-size: 18px;
  font-weight: 700;
  color: #101d37;
  margin-right: 8px;
  max-width: 140px;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  -o-text-overflow: ellipsis;
  text-overflow: ellipsis;
  line-height: 20px;
  cursor: pointer;
}
</style>