var CustomForm = {
  /**
   * 父页面调用此方法给表单赋值
   * @param data
   */
  loadFormInfo: function(data) {
    const { formJson, editData, editable } = data;
    debugger;
    const jsonData = formJson?JSON.parse(formJson):null;
    if(!vueObj.$el){
      vueObj = new Vue({
        el: '#app',
        data: {
          jsonData: jsonData,
          editData: editData||{},
        },
        editable: editable||false,
        methods: {
          handleSubmit () {
            CustomForm.submit();
          },
          getFieldsByFormJson(fields, items){
            items.forEach(item => {
              debugger;
              fields.push(item.model);
              if(item.list){
                this.getFieldsByFormJson(fields, item.list)
              }

            })
          }
        },
        mounted() {
          vueObj.$nextTick(() => {
            // 获取所有的字段
            let disabledFields = [];

            this.getFieldsByFormJson(disabledFields, jsonData.list)

            if(!editable){
              // 设置所有字段成禁用状态
              this.$refs.generateform.disabled(disabledFields, true)
            }
          });
        }
      });
    }else{
      // vueObj.jsonData = jsonData;
    }
  },

  // 获取表单数据
  geFormData: function(isValidate ){
    return vueObj.$refs.generateform.getData(isValidate);
  },

  // 设置表单数据
  setFormData: function(formData, isEdit) {
    debugger;
    vueObj.editData = formData;
    vueObj.edit = isEdit;
    setTimeout(()=>{
      vueObj.$refs.generateform.$refs.generateForm._props.disabled = !isEdit
    }, 100);
  }
};


