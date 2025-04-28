<template>
    <div id="manage">
        <el-container style="width: 100%;height: 100%;">
            <el-header style="width: 100%;height: 8%">
                <div id="title">
                    <el-row @click="goToHome" style="cursor: pointer;height: 100%;">
                        <el-image src="/manage_logo.png" style="width: 55px;height: 90%;" />
                        <div style="margin-left: 15px; line-height: 50px;">
                            海外藏中国文物知识管理与服务平台-后台管理子系统
                        </div>
                        <div id="user">
                            <el-dropdown trigger="click">
                                <el-image src="/manage_user.jpg"
                                    style="width: 30px; height: 30px; border-radius: 50%;" />
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item @click="passwordChangeFormVisible = true">
                                            <el-icon style="margin-left: -10px;margin-right: 10px;">
                                                <Edit />
                                            </el-icon>
                                            修改密码
                                        </el-dropdown-item>
                                        <el-dropdown-item @click="logout(null, true)" :divided="true">
                                            <el-icon style="margin-left: -10px;margin-right: 10px;">
                                                <HomeFilled />
                                            </el-icon>
                                            退出
                                        </el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </div>
                    </el-row>

                </div>
            </el-header>
            <el-container class="asideAndMain">
                <ManageAside />
                <el-main>
                    <el-scrollbar>
                        <RouterView />
                    </el-scrollbar>
                </el-main>
            </el-container>
        </el-container>
    </div>
    <el-dialog v-model="passwordChangeFormVisible" title="修改密码" width="30%">
        <el-form :model="passwordChangeForm" label-width="100px" :rules="rules" ref="passwordChangeFormRef">
            <el-form-item label="旧密码" prop="oldPassword">
                <el-input v-model="passwordChangeForm.oldPassword" type="password"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input v-model="passwordChangeForm.newPassword" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
                <el-input v-model="passwordChangeForm.confirmPassword" type="password"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span>
                <el-button @click="cancelChangePassword">取消</el-button>
                <el-button type="primary" @click="changePasswordCheck">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
  import { useRouter } from 'vue-router';
  import { ref,getCurrentInstance,reactive } from 'vue';
  import { ElPopover, ElDropdownMenu, ElDropdownItem } from 'element-plus';
  import { useUserInfoStore } from '@/stores/userInfoStore';
  import md5 from 'js-md5';
  import ManageAside from '@/views/ManageAside.vue'

  const router = useRouter();
  const {proxy} = getCurrentInstance();
  const userInfoStore = useUserInfoStore();
  // 密码修改对话框
  const passwordChangeFormVisible = ref(false);
  // 密码修改表单
  const passwordChangeForm = reactive({
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
  });

  // 密码修改校验
  const passwordChangeFormRef = ref(null);

  const rules = {
        oldPassword: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        newPassword: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ]
  }


  /**
   * 跳转到首页
   */
  const goToHome = ()=>{
      router.push('/manage');
  }
  
  /**
   * 退出登录
   */
    const logout = async (message = '退出成功',needConfirm=false) => {
        if(needConfirm){
            proxy.Confirm({
                message: '确定退出登录',
                okfun: ()=>{logout()},
            });
            return;
        }
        let result = await proxy.Request.request({
            url: proxy.Api.logout,
            params: {
                userId: userInfoStore.getUserInfo().userId,
            }
        })
        if(!result){
            return;
        }
        userInfoStore.setUserInfo({});
        localStorage.clear();
        router.push('/login');
        proxy.Message.success(message);
    };
    /**
     * 修改密码确认
     */
    const changePasswordCheck =()=>{
        passwordChangeFormRef.value.validate(valid => {
            if(!valid){
                proxy.Message.error("请检查输入信息是否正确");
            }else{
                // 确认框
                proxy.Confirm({
                    message: '确定修改密码',
                    okfun: changePassword,
                });
            }
        });
    }
    /**
     * 修改密码
     */
    const changePassword = async ()=>{
        if(passwordChangeForm.newPassword != passwordChangeForm.confirmPassword){
            proxy.Message.error('两次输入的密码不一致');
            return;
        }
        let result = await proxy.Request.request({
            url: proxy.Api.changePassword,
            params: {
                userId: userInfoStore.getUserInfo().userId,
                oldPassword: md5(passwordChangeForm.oldPassword),
                newPassword: md5(passwordChangeForm.newPassword),
            }
        })
        if(!result){
            return;
        }
        passwordChangeFormVisible.value = false; 
        // 退出登录
        logout('修改密码成功，请重新登录');
    }
    /**
     * 取消修改密码
     */
    const cancelChangePassword = ()=>{
        passwordChangeForm.oldPassword = '';
        passwordChangeForm.newPassword = '';
        passwordChangeForm.confirmPassword = '';
        passwordChangeFormVisible.value = false;
    }
    
</script>


<style scoped>
  #manage{
      height: 95vh;
      width: 100%;
  }
  .el-header{
      background-color: #0483d4;
      color: #ffffff;
      line-height: 60px;
  }
  #title{
      font-size: 26px;
      font-weight: bold;
      margin-left: 20px;
      margin-top: 10px;
  }
  #user {
      margin-left: 680px;
      margin-top: 15px;
      cursor: pointer;
  }
  .asideAndMain{
    background-color: rgb(243.9, 244.2, 244.8);
  }

  .el-dropdown-menu {
      background-color: #339adc;
      width: 100px;
  }
  /deep/ .el-dropdown-menu__item:not(.is-disabled) {
      color: white;
  }

  /deep/ .el-dropdown-menu__item:not(.is-disabled):focus {
      background-color: #75bbe5;
      color: white;
  }
</style>
  