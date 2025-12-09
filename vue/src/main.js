import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { createPinia } from 'pinia'
import router from './router'
import { useUserStore } from './store/user'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)

// 应用初始化：如果存在本地 token，尝试拉取当前用户信息
const userStore = useUserStore()
userStore.initFromToken()

app.use(router)
app.mount('#app')
