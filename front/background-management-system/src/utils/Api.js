const api={
    // 后端地址
    devDomain: "http://127.0.0.1:8080",
    // 账户模块
    login: "/account/login", // 登录
    logout: "/account/logout", // 登出
    changePassword: "/account/changePassword", // 修改密码
    getCheckCode: "/account/getCheckCode", // 获取验证码
    submitCheckCode: "/account/submitCheckCode", // 提交短信验证码
    forgetAndChangePassword: "/account/forgetAndChangePassword", // 忘记密码并修改密码
    // 用户模块
    loadUserList: "/user/loadUserList", // 加载用户列表
    addOrUpdateUser: "/user/addOrUpdateUser", // 添加或修改用户
    deleteUser: "/user/deleteUser", // 删除用户

    // 日志模块 ✅
    loadLogList: "/log/loadLogList", // 查询日志列表
    deleteLog:"/log/deleteLog", // 删除日志
    addLog:"/log/addLog", // 增加日志
    updateUserCommentStatus: "/user/updateUserCommentStatus", // 修改用户评论状态
    // 公告模块
    loadNoticeList: "/notice/loadNoticeList", // 加载公告列表
    addOrUpdateNotice: "/notice/addOrUpdateNotice", // 添加公告
    deleteNotice: "/notice/deleteNotice", // 删除公告
    // 博物馆模块
    loadMuseumList: "/museum/loadMuseumList", // 加载博物馆列表
    addOrUpdateMuseum: "/museum/addOrUpdateMuseum", // 添加或修改博物馆
    deleteMuseum: "/museum/deleteMuseum", // 删除博物馆
    // 评论模块
    loadCommentList: "/comment/loadCommentList", // 加载评论列表
    updateCommentStatus: "/comment/updateCommentStatus", // 更新评论状态
}
export default api;