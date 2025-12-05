<template>
  <div class="auth">
    <div class="card">
      <h2>登录</h2>
      <form @submit.prevent="onSubmit" class="form">
        <label>
          用户名
          <input v-model="form.username" required />
        </label>
        <label>
          密码
          <input v-model="form.password" type="password" required />
        </label>
        <button type="submit" class="primary">登录</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: '', password: '' })

async function onSubmit() {
  try {
    await userStore.login(form)
    router.push('/profile')
  } catch (e) {
    alert(e.message || '登录失败')
  }
}
</script>

<style scoped>
.auth { display:flex; justify-content:center; align-items:flex-start; padding:24px; }
.card { width:420px; background:#fff; border-radius:12px; box-shadow: 0 1px 3px rgba(0,0,0,.08), 0 8px 24px rgba(0,0,0,.06); padding:24px; }
.form { display:flex; flex-direction:column; gap:12px; }
label { color:#000; font-size:14px; display:flex; flex-direction:column; gap:6px; }
input { padding:10px 12px; border:1px solid #e2e8f0; border-radius:8px; outline:none; }
input:focus { border-color:#93c5fd; box-shadow:0 0 0 3px rgba(147,197,253,.35); }
button { padding:10px 12px; border-radius:8px; border:1px solid #d1d5db; cursor:pointer; }
button.primary { background:#0d6efd; border-color:#0d6efd; color:#000; }
button.primary:hover { background:#0b5ed7; }
</style>