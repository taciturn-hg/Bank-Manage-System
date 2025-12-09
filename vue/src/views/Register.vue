<template>
  <div class="auth">
    <div class="card">
      <h2>注册</h2>
      <form @submit.prevent="onSubmit" class="form">
        <label>用户名<input v-model="form.username" required /></label>
        <label>密码<input v-model="form.password" type="password" required /></label>
        <label>姓名<input v-model="form.name" required /></label>
        <label>身份证<input v-model="form.idCard" required /></label>
        <label>手机号<input v-model="form.phone" required /></label>
        <label>邮箱<input v-model="form.email" type="email" required /></label>
        <button type="submit" class="primary">注册</button>
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
const form = reactive({ username: '', password: '', name: '', idCard: '', phone: '', email: '' })

async function onSubmit() {
  try {
    await userStore.register(form)
    alert('注册成功，请登录')
    router.push('/login')
  } catch (e) {
    alert(e.message || '注册失败')
  }
}
</script>

<style scoped>
.auth { display:flex; justify-content:center; align-items:flex-start; padding:24px; }
.card { width:520px; background:#fff; border-radius:12px; box-shadow: 0 1px 3px rgba(0,0,0,.08), 0 8px 24px rgba(0,0,0,.06); padding:24px; }
.form { display:grid; grid-template-columns: 1fr 1fr; gap:12px 16px; }
.form > label { color:#000; font-size:14px; display:flex; flex-direction:column; gap:6px; }
.form > label:nth-last-child(1) { grid-column: 1 / -1; }
input { padding:10px 12px; border:1px solid #e2e8f0; border-radius:8px; outline:none; }
input:focus { border-color:#93c5fd; box-shadow:0 0 0 3px rgba(147,197,253,.35); }
button { padding:10px 12px; border-radius:8px; border:1px solid #d1d5db; cursor:pointer; }
button.primary { background:#0d6efd; border-color:#0d6efd; color:#000; }
button.primary:hover { background:#0b5ed7; }
</style>