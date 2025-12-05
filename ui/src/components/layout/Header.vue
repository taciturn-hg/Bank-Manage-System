<template>
  <header class="header">
    <h1>银行管理系统</h1>
    <nav>
      <router-link to="/">首页</router-link>
      <!-- 仪表盘入口已移除 -->
      <router-link to="/accounts">账户列表</router-link>
      <router-link to="/profile">个人信息</router-link>
      <!-- 登录/注册或退出 -->
      <span class="auth">
        <template v-if="token">
          <span class="welcome">你好，{{ user?.username || '已登录' }}</span>
          <button class="link-btn" @click="logout">退出登录</button>
        </template>
        <template v-else>
          <router-link to="/login">登录</router-link>
          <router-link to="/register">注册</router-link>
        </template>
      </span>
    </nav>
  </header>
</template>

<script setup>
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const { user, token } = storeToRefs(userStore)
const router = useRouter()

const logout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.header { position:sticky; top:0; z-index:10; display:flex; align-items:center; justify-content:space-between; padding:12px 24px; background:linear-gradient(90deg,#0d6efd,#0b5ed7); color:#000; box-shadow:0 2px 10px rgba(0,0,0,.1); }
.header h1 { font-size:18px; margin:0; letter-spacing:.5px; }
nav { display:flex; align-items:center; gap:16px; }
nav a { color:#000; text-decoration:none; opacity:0.95; padding:6px 8px; border-radius:6px; }
nav a:hover { background:rgba(255,255,255,.12); }
nav a.router-link-active { background:rgba(255,255,255,.2); }
.auth { display:flex; align-items:center; gap:12px; margin-left:12px; }
.welcome { opacity:0.9; }
.link-btn { background:transparent; border:1px solid rgba(255,255,255,.35); color:#000; cursor:pointer; padding:6px 10px; border-radius:6px; }
.link-btn:hover { background:rgba(255,255,255,.12); }
</style>