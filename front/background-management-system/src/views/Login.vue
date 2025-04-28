<template>
    <div id="login">
        <el-container style="height: 100%; width: 100%">
            <el-header style="height: 10%; width: 100%;">
                <el-row :gutter="20">
                    <el-col :span="18" style="text-align: left;">
                        <el-row>
                            <el-image src="/logo.png" />
                            <el-text id="title" size="large">海外藏中国文物知识管理与服务平台-后台管理子系统</el-text>
                        </el-row>
                    </el-col>
                    <el-col :span="6">
                        <div style="width: 100%;height: 100%;margin-top: 50px">
                            {{nowTime}}
                        </div>
                    </el-col>
                </el-row>
            </el-header>
            <el-main style="height: 80%; width: 100%;">
                <div id="loginBlock">
                    <div id="formBlock">
                        <el-form :model="form" label-width="auto" :rules="rules" ref="formRef">
                            <el-form-item>
                                <el-text>登录入口</el-text>
                            </el-form-item>
                            <el-form-item prop="phoneNumber">
                                <el-image src="/login_username.png" />
                                <el-input v-model="form.phoneNumber" placeholder="请输入手机号" />
                            </el-form-item>
                            <el-form-item prop="password">
                                <el-image src="/login_password.png" />
                                <el-input v-model="form.password" placeholder="请输入密码" show-password />
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="login" size="large">登录</el-button>
                            </el-form-item>
                            <el-form-item>
                                <el-link @click="getCheckCodeFormVisible=true" style="margin-left: 230px;">忘记密码</el-link>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </el-main>
            <el-footer style="height: 5%; width: 100%;"></el-footer>
        </el-container>
    </div>
    <!-- 短信验证码 -->
    <el-dialog v-model="getCheckCodeFormVisible" title="忘记密码" width="30%">
        <el-form :model="getCheckCodeForm" label-width="100px" :rules="getCheckCodeFormRules" ref="getCheckCodeFormRef">
            <el-form-item label="电话" prop="getCheckCodePhoneNumber">
                <el-input v-model="getCheckCodeForm.getCheckCodePhoneNumber"/>
                <el-button type="primary" size="small" @click="getCheckCode">获取验证码</el-button>
            </el-form-item>
            <el-form-item label="验证码">
                <el-input v-model="getCheckCodeForm.checkCode"/>
            </el-form-item>
        </el-form>
        <template #footer>
            <span>
                <el-button @click="getCheckCodeFormVisible=false">取消</el-button>
                <el-button type="primary" @click="submitCheckCode" style="color:white ;background-color: #324069;">确定</el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 修改密码 -->
    <el-dialog v-model="changePasswordFormVisible" title="修改密码" width="30%">
        <el-form :model="changePasswordForm" label-width="100px" :rules="changePasswordFormRules" ref="changePasswordFormRef">
            <el-form-item label="新密码" prop="newPassword">
                <el-input v-model="changePasswordForm.newPassword" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
                <el-input v-model="changePasswordForm.confirmPassword" type="password"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span>
                <el-button @click="changePasswordFormVisible=false">取消</el-button>
                <el-button type="primary" @click="changePassword">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
    import { ref, reactive,onMounted, onBeforeUnmount,getCurrentInstance} from 'vue'
    import { useRouter } from 'vue-router';
    import {formateDate} from '../utils/Utils.js'
    import {useUserInfoStore} from '../stores/userInfoStore.js'
    import md5 from 'js-md5';
    // 显示实时时间
    const nowTime = ref('')
    // 表单
    const form = reactive({
        phoneNumber: '',
        password: '',
    })
    // vue实例
    const { proxy } = getCurrentInstance();
    // 用户信息存储对象
    const userInfoStore = useUserInfoStore();
    // 路由
    const router = useRouter();
    // 表单对象
    const formRef = ref(null);
    // 表单提交校验
    const rules ={
        phoneNumber: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ]
    }
    // 发送短信验证码对话框
    const getCheckCodeFormVisible = ref(false);
    // 验证码表单引用
    const getCheckCodeFormRef = ref(null);
    // 短信验证码表单提交校验
    const getCheckCodeFormRules ={
        getCheckCodePhoneNumber: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
    }
    // 短信验证码表单
    const getCheckCodeForm = reactive({
        getCheckCodePhoneNumber: '',
        checkCode: ''  
    })
    // 短信验证码时间
    let countDown = 0;


    // 修改密码表单可视
    const changePasswordFormVisible = ref(false);
    // 修改密码表单引用
    const changePasswordFormRef = ref(null);
    // 修改密码表单提交校验
    const changePasswordFormRules = {
        newPassword: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ]
  };
  // 修改密码表单
  const changePasswordForm = reactive({
    phoneNumber: '',
    newPassword: '',
    confirmPassword: ''
  })


    /**
     * 登录
     */
    const login = async ()=>{
        // 提交前先校验
        let result = null;
        await formRef.value.validate(async valid => {
            if(!valid){
                proxy.Message.error("请检查输入信息是否正确");
            }else{
                result = await proxy.Request.request({
                    url: proxy.Api.login,
                    params: {
                        phoneNumber: form.phoneNumber,
                        // md5加密
                        password: md5(form.password),
                    }
                })
            }
        });
        if(!result || !result.data){
            return;
        }
        // 保存token
        localStorage.setItem('token',result.data.token);
        // 保存用户信息
        userInfoStore.setUserInfo(result.data);
        // 跳转
        router.push("/manage");
    }
    /**
     * 获取短信验证码
     */
    const getCheckCode = async ()=>{
        if(countDown>0){
            proxy.Message.error("请等待"+countDown+"秒后重试");
            return;
        }
        let getCheckCodeResult = null;
        // 提交前先校验
        await getCheckCodeFormRef.value.validate(async valid => {
            if(!valid){
                proxy.Message.error("请检查输入信息是否正确");
            }else{
                getCheckCodeResult = await proxy.Request.request({
                    url: proxy.Api.getCheckCode,
                    params: {
                        phoneNumber: getCheckCodeForm.getCheckCodePhoneNumber,
                    }
                })
            }
        });
        if(!getCheckCodeResult){
            return;
        }
        proxy.Message.success("验证码已发送，请于30s内输入");
        countDown = 30;
        let countTimer = setInterval(()=>{
            countDown--;
            if(countDown<=0){
                clearInterval(countTimer);
                countTimer = null;
            }
        },1000)
    }
    /**
     * 提交短信验证码
     */
    const submitCheckCode = async ()=>{
        let submitCheckCodeResult = null;
        // 提交前先校验
        await getCheckCodeFormRef.value.validate(async valid => {
            if(!valid){
                proxy.Message.error("请检查输入信息是否正确");
            }else{
                submitCheckCodeResult = await proxy.Request.request({
                    url: proxy.Api.submitCheckCode,
                    params: {
                        phoneNumber: getCheckCodeForm.getCheckCodePhoneNumber,
                        checkCode: getCheckCodeForm.checkCode,
                    }
                })
            }
        });
        if(!submitCheckCodeResult){
            return;
        }
        proxy.Message.success("验证码正确");
        getCheckCodeFormVisible.value = false;
        // 修改密码
        changePasswordForm.phoneNumber = getCheckCodeForm.getCheckCodePhoneNumber;
        changePasswordFormVisible.value = true;
    }
    /**
     * 修改密码
     */
const changePassword = async () => {
    if(changePasswordForm.newPassword != changePasswordForm.confirmPassword){
            proxy.Message.error('两次输入的密码不一致');
            return;
        }
    let changePasswordResult = null;
    // 提交前先校验
    await changePasswordFormRef.value.validate(async valid => {
        if (!valid) {
            proxy.Message.error("请检查输入信息是否正确");
        } else {
            changePasswordResult = await proxy.Request.request({
                url: proxy.Api.forgetAndChangePassword,
                params: {
                    phoneNumber: changePasswordForm.phoneNumber,
                    newPassword: md5(changePasswordForm.newPassword),
                }
            })
        }
    })
    if(!changePasswordResult){
        return;
    }
    proxy.Message.success("修改成功");
    changePasswordFormVisible.value = false;
}

    /**
     * 设置定时器
     */
    let timer = 0;
    onMounted(() => {
        timer = setInterval(() => {
            nowTime.value = formateDate()
        }, 1000)
    })
    /**
     * 取消定时器
     */
    onBeforeUnmount(() => {
        clearInterval(timer) //清除定时器
        timer = 0
    })

</script>

<style scoped>
    #login{
        height: 95vh;
        width: 100%;
    }
    #title{
        font-size: 30px;
        font-weight: bold;
        color: #324069;
        margin-left: 30px;
    }
    .el-header{
        background-color: white;
        color: #333;
        text-align: center;
        line-height: normal;
    }
    .el-main{
        color: #333;
        text-align: center;
        line-height: 700px;
        background-image: url("/public/login_background.png");
        background-size: 100% 100%;
        background-position: center;
    }
    #loginBlock{
        background-color: white;
        width: 400px;
        height: 350px;
        position: absolute;
        right: 100px;
        top: 300px;
    }
    #formBlock{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 300px;
    }
    .el-form{
        margin-top: 20px;
        width: 300px;
        .el-text{
            font-size: 22px;
            font-weight: bold;
            color: #324069;
            width: 400px;
        }
        .el-form-item{
            margin-bottom: 20px;
        }
        .el-image{
            width: 40px;
            height: 40px;
        }
        .el-input{
            width: 260px;
            height: 40px;
        }
        .el-button{
            width: 400px;
            height: 45px;
            color: white;
            background-color: #324069;
            font-size: 15px;
        }
    }
</style>