<template>
  <div class="box">
    <!-- 用户操作组件 -->
    <div>
      <clientInfo></clientInfo>
    </div>
    <el-input class="searchInput" v-model="input" placeholder="请输入内容"></el-input>
    <el-button type="primary" id="search" icon="el-icon-search" @click="getHouseAreaData()"></el-button>
    <el-button type="primary" id="search" icon="el-icon-refresh" @click="siftHouse"></el-button>
    <div class="typeselect">
      <div>
        <span>价格: </span>
        <el-radio-group v-model="price">
          <el-radio :label="1">全部</el-radio>
          <el-radio :label="2">40万以下</el-radio>
          <el-radio :label="3">40万-60万</el-radio>
          <el-radio :label="4">60万-80万</el-radio>
          <el-radio :label="5">80万-100万</el-radio>
          <el-radio :label="6">100万-150万</el-radio>
          <el-radio :label="7">150万以上</el-radio>
        </el-radio-group>
      </div>
      <div>
        <span>面积: </span>
        <el-radio-group v-model="area">
          <el-radio :label="1">全部</el-radio>
          <el-radio :label="2">40㎡以下</el-radio>
          <el-radio :label="3">40㎡-60㎡ </el-radio>
          <el-radio :label="4">60㎡-80㎡ </el-radio>
          <el-radio :label="5">80㎡-100㎡</el-radio>
          <el-radio :label="6">100㎡-150㎡</el-radio>
          <el-radio :label="7">150㎡-200㎡</el-radio>
        </el-radio-group><br>
      </div>
      <div>
        <span>朝向: </span>
        <el-radio-group v-model="orient">
          <el-radio :label="1">全部</el-radio>
          <el-radio :label="2">南北</el-radio>
          <el-radio :label="3">南</el-radio>
          <el-radio :label="4">北</el-radio>
          <el-radio :label="5">东</el-radio>
          <el-radio :label="6">西</el-radio>
        </el-radio-group>
      </div>
    </div>
    <div class="commodity" v-for="item in houses" :key="item.id" @click="toHousedetail(item)">
      <div class="left">
        <el-image style="height: 100%" :src="item.img.img1" height="200px"></el-image>
      </div>
      <div class="right">
        <el-descriptions class="margin-top" :column="1" :size="size">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-school"></i>
              编号
            </template>
            {{ item.houseId }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-price-tag"></i>
              布局
            </template>
            {{ item.houseOrientation }} {{ item.layoutType }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              居住地
            </template>
            {{ item.city }} {{ item.cellName }} {{ item.houseArea + '㎡' }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              备注
            </template>
            <el-tag size="small">{{ item.houseType }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              联系地址
            </template>
            {{ item.address }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-thumb"></i>
              点击查看详情
            </template>
            <div class="housePrice"> {{ item.housePrice + '万' }}</div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script>
import clientInfo from '@/components/common/clientInfo.vue';
export default {
  components: {
    clientInfo
  },
  data() {
    return {
      mes: "",
      HouseAreaData: [],
      selectHouseData: {},
      src: "",
      size: "",
      img: [],
      price: 1,
      area: 1,
      orient: 1,
      houses: [],
      input: '',
    };
  },
  created() {
    this.mes = this.$route.params.mes;
    this.judgementInput();
    this.getHouseAreaData();
  },
  mounted() {
    this.houses = this.houses1
  },
  methods: {
    toHousedetail(index) {  //判断循环的时候点击的页面
      this.selectHouseData = index;
      this.$router.push({ name: 'housedetail', params: { selectHouseData: this.selectHouseData } });  //进行页面跳转传递参数
    },


    getHouseAreaData() {
      if (this.input != '' && this.input != undefined) {
        this.$axios({
          url: "/house/queryByCity/" + this.input,
          methods: "GET",
          header: {
            "Content-Type": "application/json;charset=UTF-8",
          },
        }).then((res) => {
          this.HouseAreaData = res.data.data;  //给HouseAreaData赋值
          this.houses = this.HouseAreaData;
        });
      } else {
        this.$axios({
          url: '/house/queryHouse',
          methods: "GET",
          header: {
            "Content-Type": "application/json;charset=UTF-8",
          },
        }).then((res) => {
          this.HouseAreaData = res.data.data;  //给HouseAreaData赋值
          this.houses = this.HouseAreaData;
        })
      }      //得到更具房屋类型筛选出来的房屋信息   
    },
    judgementInput() {
      if (this.mes != '') {
        this.input = this.mes;
      }
    },
    siftHouse() {
      // this.houses = this.HouseAreaData
      let houses = this.HouseAreaData
      if (this.price == 2) {
        houses = this.HouseAreaData.filter((h) => {
          return h.housePrice < 40
        })
      } else if (this.price == 3) {
        houses = this.HouseAreaData.filter((h) => {
          return h.housePrice >= 40 && h.housePrice < 60
        })
      } else if (this.price == 4) {
        houses = this.HouseAreaData.filter((h) => {
          return h.housePrice >= 60 && h.housePrice < 80
        })
      } else if (this.price == 5) {
        houses = this.HouseAreaData.filter((h) => {
          return h.housePrice >= 80 && h.housePrice < 100
        })
      } else if (this.price == 6) {
        houses = this.HouseAreaData.filter((h) => {
          return h.housePrice >= 100 && h.housePrice < 150
        })
      } else if (this.price == 7) {
        houses = this.HouseAreaData.filter((h) => {
          return h.housePrice >= 150
        })
      } else {

      }
      //
      if (this.area == 2) {
        houses = houses.filter((h) => {
          return h.houseArea < 40
        })
      } else if (this.area == 3) {
        houses = houses.filter((h) => {
          return h.houseArea >= 40 && h.houseArea < 60
        })
      } else if (this.area == 4) {
        houses = houses.filter((h) => {
          return h.houseArea >= 60 && h.houseArea < 80
        })
      } else if (this.area == 5) {
        houses = houses.filter((h) => {
          return h.houseArea >= 80 && h.houseArea < 100
        })
      } else if (this.area == 6) {
        houses = houses.filter((h) => {
          return h.houseArea >= 100 && h.houseArea < 150
        })
      } else if (this.area == 7) {
        houses = houses.filter((h) => {
          return h.houseArea >= 150
        })
      } else {

      }
      //
      if (this.orient == 2) {
        houses = houses.filter((h) => {
          return h.houseOrientation == "南 北"
        })
      } else if (this.orient == 3) {
        houses = houses.filter((h) => {
          return h.houseOrientation == "南"
        })
      } else if (this.orient == 4) {
        houses = houses.filter((h) => {
          return h.houseOrientation == "北"
        })
      } else if (this.orient == 5) {
        houses = houses.filter((h) => {
          return h.houseOrientation == "东"
        })
      } else if (this.orient == 6) {
        houses = houses.filter((h) => {
          return h.houseOrientation == "西"
        })
      } else { }
      this.houses = houses;
      console.log(this.houses);
    },
  },
};
</script>

<style scoped>
.housePrice {
  color: red;
  font-weight: bold;

}

.commodity {
  padding-left: 150px;
}

.typeselect {

  height: 100px;
  width: 80%;
  padding-left: 150px;
}

.searchInput {
  width: 626px;
  padding-left: 150px;
  border: 0;
  height: 55px;
  line-height: 55px;
  border-radius: 2px;
  font-size: 14px;
}

.left {
  float: left;
  width: 300px;
  height: 200px;
  background: #FC9;
  border-radius: 15px;
  padding: 10px;


}

.right {

  background: #FC9;
  border-radius: 15px;
  padding: 10px;
  margin-left: 310px;
  margin-top: 20px;
  height: 200px;
  width: 800px;
}

#search {
  background-color: #FC9;
  border-radius: 0%;
}

.searchButton {
  margin: 0;
  padding: 0;
  outline: none;
  resize: none;
}
</style>