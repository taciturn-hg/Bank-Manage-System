<template>
  <div class="page">
    <div class="card">
      <h2>账户查询</h2>
      <form @submit.prevent="fetchAccount" class="form">
        <label>
          账户号：
          <input v-model="accountNumber" placeholder="请输入账户号" required />
        </label>
        <div class="actions">
          <button type="submit" :disabled="loading" class="primary">查询账户详情</button>
          <button type="button" @click="fetchBalance" :disabled="loading || !accountNumber">查询余额</button>
        </div>
      </form>
      <p v-if="error" class="error">{{ error }}</p>
    </div>

    <div v-if="account" class="card">
      <h3>账户信息</h3>
      <div class="grid">
        <p><strong>账户号：</strong>{{ account.accountNumber }}</p>
        <p><strong>类型：</strong>{{ account.type }}</p>
        <p><strong>用户ID：</strong>{{ account.userId }}</p>
      </div>
    </div>

    <div v-if="balance !== null" class="card">
      <h3>账户余额</h3>
      <p class="balance">{{ balance }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { getAccount, getBalance } from '../api/account'

const accountNumber = ref('')
const account = ref(null)
const balance = ref(null)
const error = ref('')
const loading = ref(false)

const fetchAccount = async () => {
  error.value = ''
  balance.value = null
  loading.value = true
  try {
    const data = await getAccount(accountNumber.value)
    account.value = data
  } catch (e) {
    error.value = e?.message || '查询账户失败'
  } finally {
    loading.value = false
  }
}

const fetchBalance = async () => {
  error.value = ''
  loading.value = true
  try {
    const data = await getBalance(accountNumber.value)
    balance.value = data
  } catch (e) {
    error.value = e?.message || '查询余额失败'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page { padding: 16px; display:grid; gap:16px; }
.card { background:#fff; border-radius:12px; box-shadow:0 1px 3px rgba(0,0,0,.08), 0 8px 24px rgba(0,0,0,.06); padding:18px; }
.form { display:grid; grid-template-columns: 1fr auto; gap:12px 16px; align-items:end; }
.form > label { color:#000; font-size:14px; display:flex; flex-direction:column; gap:6px; }
.actions { display:flex; gap:8px; }
input { padding:10px 12px; border:1px solid #e2e8f0; border-radius:8px; outline:none; }
input:focus { border-color:#93c5fd; box-shadow:0 0 0 3px rgba(147,197,253,.35); }
button { padding:10px 12px; border-radius:8px; border:1px solid #d1d5db; cursor:pointer; }
button.primary { background:#0d6efd; border-color:#0d6efd; color:#000; }
button.primary:hover { background:#0b5ed7; }
.error { color:#000; }
.grid { display:grid; grid-template-columns: repeat(3, 1fr); gap:8px 12px; }
.balance { font-size:20px; font-weight:700; color:#000; }
</style>