<template>
  <div class="main">
    <!-- 用户操作组件 -->
    <div>
      <clientInfo></clientInfo>
    </div>
    <div class="logo">
      <img src="../../assets/img/logo.png" alt="">
    </div>
    <div class="writing">
      连接每个家的故事
    </div>
    <div class="writingson">
      卖出房子累计85200套
    </div>
    <div class="center">
      <el-dropdown type="primary" @command="handleClick" :hide-on-click="false">
        <span class="el-dropdown-link">
          {{ select }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>

        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="房屋类型">房屋类型</el-dropdown-item>
          <el-dropdown-item command="房屋区域">房屋区域</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-input placeholder="请输入内容" v-model="input" clearable class="searchInput">
      </el-input>
      <el-button type="primary" id="search" icon="el-icon-search" @click="searchHouse()"></el-button>

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
      input: "",
      select: "房屋类型",
    };
  },
  methods: {
    searchHouse() {
      if (this.select == "房屋区域") {
        this.$router.push({ name: 'housearea', params: { mes: this.input } });
      } else if (this.select == "房屋类型") {
        this.$router.push({ name: 'housetype', params: { mes: this.input } });
      }
    },
    handleClick(command) {
      this.select = command;
    },
  },
};
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #338bcc;
  font-size: 26px;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.main {
  background-image: url("https://static.store-companion.ikea.cn/ikea-kitchen-h5/static/img/banner.4e65325c.jpg");
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.logo {
  width: 10%;
  /* background-color: aqua; */
}

.writing {
  color: #fff;
  text-shadow: 0px 0px 10px black;
  font-size: 75px;
  text-align: center;
  border-radius: 20px;
  width: 800px;
  height: 100px;
  position: absolute;
  left: 28%;
  top: 48%;
  transform: translate(-50%, -50%);
  margin-left: 25%;
}

.writingson {
  color: #fff;
  text-shadow: 0px 0px 10px black;
  font-size: 40px;
  text-align: center;
  border-radius: 20px;
  width: 800px;
  height: 100px;
  position: absolute;
  left: 28%;
  top: 62%;
  transform: translate(-50%, -50%);
  margin-left: 25%;
}


.search {
  padding: 20px;
  position: relative;
  margin: auto;
}

.searchInput {
  width: 626px;
  padding-left: 20px;
  border: 0;
  height: 55px;
  line-height: 55px;
  border-radius: 2px;
  font-size: 14px;
}

.center {
  margin-top: 25%;
  margin-left: 25%;

}

#search {
  background-color: #338bcc;
  border-radius: 0%;
}

.searchButton {
  margin: 0;
  padding: 0;
  outline: none;
  resize: none;
}
</style>