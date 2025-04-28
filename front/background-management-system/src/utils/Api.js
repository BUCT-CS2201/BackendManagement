const api={
    // 后端地址
    devDomain: "http://127.0.0.1:8080",
    
    login: "/account/login", // 登录
    logout: "/account/logout", // 登出
    changePassword: "/account/changePassword", // 修改密码
    getCheckCode: "/account/getCheckCode", // 获取验证码
    submitCheckCode: "/account/submitCheckCode", // 提交短信验证码
    forgetAndChangePassword: "/account/forgetAndChangePassword", // 忘记密码并修改密码

    
}
export default api;