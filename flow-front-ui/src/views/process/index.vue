<template>
  <PageWrapper class="!mt-4 process-list-container w-full" contentClass="!m-0">
    <template #title>
      <PartitionOutlined />&nbsp;&nbsp;流程中心
    </template>
    <template #extra>
      <div v-if="currentTabKey !== 'launch'">
        <a-button type="primary" @click="toLaunch"> 发起流程 </a-button>
      </div>
    </template>

    <Tabs :tabBarGutter="40" :activeKey="currentTabKey" @change="handleChangeProcessTabs">
      <TabPane key="todo"  >
        <template #tab>
          <Badge :count="todoCount" :offset="[15, -2]" size="default">
            <span >待办</span>
          </Badge>
        </template>
        <Todo />
      </TabPane>

      <TabPane key="have-down" tab="已办" >
        <HaveDown />
      </TabPane>

      <TabPane key="launched" tab="已发" >
        <Launched />
      </TabPane>

      <TabPane key="launch" tab="发起" >
        <Launch />
      </TabPane>
    </Tabs>
  </PageWrapper>
</template>
<script lang="ts">
import { defineComponent, ref, unref } from 'vue';
import { PageWrapper } from '@/components/Page';
import { Tabs, Tag, Popover } from 'ant-design-vue';
import { Badge } from 'ant-design-vue';
import { PartitionOutlined } from '@ant-design/icons-vue';

import ProcessHeader from '@/views/process/components/ProcessHeader.vue';
import LaunchButton from '@/views/process/components/LaunchButton.vue';
import {getAppingTaskCont} from "@/api/process/process";
import { useRouter } from 'vue-router';
import Todo from './todo/index.vue';
import Launched from './launched/index.vue';
import HaveDown from './have-down/index.vue';
import Launch from './launch/index.vue';

const TabPane = Tabs.TabPane;

export default defineComponent({
  components: {
    PartitionOutlined,
    Todo,
    Launched,
    HaveDown,
    Launch,
    ProcessHeader,
    LaunchButton,
    PageWrapper,
    Tabs,
    TabPane,
    Badge,
  },
  setup() {
    const todoCount = ref(0);
    const currentTabKey = ref("todo");
    const { currentRoute } = useRouter();
    const { params:{ currentKey } } = unref(currentRoute);
    currentTabKey.value = currentKey;

    function handleChangeProcessTabs(e){
      currentTabKey.value = e;
      history.replaceState('', '', '#/process/' + e)
    }

    getAppingTaskCont({}).then(res=>{
      todoCount.value = res;
    });

    function toLaunch() {
      currentTabKey.value = "launch";
      history.replaceState('', '', '#/process/launch')
    }

    return {
      toLaunch,
      currentTabKey,
      todoCount,
      handleChangeProcessTabs,
    };
  },
});
</script>
<style lang="less">
.process-list-container{
  .vben-basic-table-form-container{
    padding: 0!important;
  }
  .ant-tabs-nav{
    padding-left: 20px;
    background: white;
  }
}
.process{
  .vben-basic-table-form-container{
    .ant-form{
      margin-bottom:0;
    }
  }
}
</style>

