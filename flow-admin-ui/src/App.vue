<template>
  <ConfigProvider :locale="getAntdLocale" :theme="themeConfig">
    <AppProvider>
      <RouterView />
    </AppProvider>
  </ConfigProvider>
</template>

<script lang="ts" setup>
  import { AppProvider } from '@/components/Application';
  import { useTitle } from '@/hooks/web/useTitle';
  import { useLocale } from '@/locales/useLocale';
  import { ConfigProvider } from 'ant-design-vue';

  import { useDarkModeTheme } from '@/hooks/setting/useDarkModeTheme';
  import 'dayjs/locale/zh-cn';
  import {computed, h} from 'vue';
  import { useMessage } from '@/hooks/web/useMessage';

  const { notification } = useMessage();

  // support Multi-language那天net
  const { getAntdLocale } = useLocale();

  const { isDark, darkTheme } = useDarkModeTheme();

  const themeConfig = computed(() =>
    Object.assign(
      {
        token: {
          colorPrimary: '#1677ff',
          colorSuccess: '#55D187',
          colorWarning: '#EFBD47',
          colorError: '#ED6F6F',
          colorInfo: '#0960bd',
        },
      },
      isDark.value ? darkTheme : {},
    ),
  );
  // Listening to page changes and dynamically changing site titles
  useTitle();

  const genItems = [
    h("p", {}, [
      h('span', '(推荐) 企业级商用版 流程引擎 现已发售，采用 Flowable Vue3 + bpmnJS '),
      h('a', {href: "http://www.tulangkj.com:8888", target: '_blank'}, '商用版预览。')
    ]),
    h("p",
      [
        h("span", '如需了解or购买企业级商用版可 '),
        h("a", {href: "http://www.tulangkj.com/contact", target: '_blank'}, '联系我们')
      ]),
    h("p", [
      h('a', {href: "http://www.tulangkj.com", target: '_blank'}, '官网'),
      h('a', {href: "http://tulangkj.com/bpmn/docs/", target: '_blank', style: {marginLeft: '20px'}}, 'BPMN文档')]),
  ];

  function showNotificationTip(){
    notification.info({
      message: 'Tip',
      description:   h("div", null, genItems),
      placement: "bottomLeft",
      duration: null,
      onClose: ()=>{
        setTimeout(()=>{
          showNotificationTip();
        }, 10000)
      },
      getContainer:() => document.body
    });
  }

  setTimeout(() => {
    // showNotificationTip();
  }, 300)
</script>
