import {ElMessage} from 'element-plus'

const showMessage = (message,type,callback)=>{
    ElMessage({
        type: type,
        message: message,
        showClose: true,
        duration: 2000
    })
}

const message = {
    error: (message,callback)=>{
        showMessage(message,"error",callback);
    },
    success: (message,callback)=>{
        showMessage(message,"success",callback);
    },
    warning: (message,callback)=>{
        showMessage(message,"warning",callback);
    },
}

export default message;