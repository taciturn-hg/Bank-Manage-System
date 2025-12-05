<template>
  <div class="page">
    <div class="card">
      <h2>创建账户</h2>
      <form @submit.prevent="onSubmit" class="form">
        <label>用户ID<input v-model.number="form.userId" required type="number" placeholder="请输入用户ID" /></label>
        <label>账户类型
          <select v-model="form.type" required>
            <option disabled value="">请选择账户类型</option>
            <option value="SAVINGS">储蓄账户</option>
            <option value="CHECKING">支票账户</option>
          </select>
        </label>
        <button type="submit" class="primary">创建</button>
      </form>
    </div>

    <div v-if="current" class="card info">
      <h3>账户信息</h3>
      <div class="grid">
        <p><strong>账户号：</strong>{{ current.accountNumber }}</p>
        <p><strong>类型：</strong>{{ current.type }}</p>
        <p><strong>余额：</strong>{{ current.balance }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { storeToRefs } from 'pinia'
import { useAccountStore } from '../store/account'
import { useUserStore } from '../store/user'

const accountStore = useAccountStore()
const userStore = useUserStore()
const { current } = storeToRefs(accountStore)

const form = reactive({ userId: null, type: '' })

async function onSubmit() {
  try {
    await accountStore.create(form, userStore.token)
  } catch (e) {
    alert(e.message || '创建失败')
  }
}
</script>

<style scoped>
.page { padding: 16px; display: grid; gap: 16px; }
.card { background:#fff; border-radius:12px; box-shadow:0 1px 3px rgba(0,0,0,.08), 0 8px 24px rgba(0,0,0,.06); padding:18px; }
.form { display:grid; grid-template-columns: 1fr 1fr; gap:12px 16px; align-items:end; }
.form > label { color:#000; font-size:14px; display:flex; flex-direction:column; gap:6px; }
.form > button { grid-column: 1 / -1; justify-self:start; }
select { padding:10px 12px; border:1px solid #e2e8f0; border-radius:8px; outline:none; background:#fff; color:#000; }
select:focus { border-color:#93c5fd; box-shadow:0 0 0 3px rgba(147,197,253,.35); }
button { padding:10px 12px; border-radius:8px; border:1px solid #d1d5db; cursor:pointer; }
button.primary { background:#0d6efd; border-color:#0d6efd; color:#000; }
button.primary:hover { background:#0b5ed7; }
.info .grid { display:grid; grid-template-columns: repeat(3, 1fr); gap:8px 12px; }
</style>