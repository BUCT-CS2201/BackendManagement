import {defineStore} from 'pinia';

export const useUserInfoStore = defineStore('user',{
    state: ()=>({
        userInfo: {},
    }),
    actions: {
        setUserInfo(userInfo){
            this.userInfo = userInfo;
        },
        getUserInfo(){
            return this.userInfo;
        },
    },
})