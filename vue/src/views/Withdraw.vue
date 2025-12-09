<template>
  <div class="page">
    <h2>取款</h2>
    <form @submit.prevent="submit">
      <label>
        账户号：
        <input v-model="form.accountNumber" required />
      </label>
      <label>
        金额：
        <input v-model.number="form.amount" type="number" min="0" step="0.01" required />
      </label>
      <button type="submit" :disabled="loading">提交</button>
    </form>
    <p v-if="message" class="success">{{ message }}</p>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { withdraw } from '../api/transaction'

const form = ref({ accountNumber: '', amount: null })
const loading = ref(false)
const message = ref('')
const error = ref('')

const submit = async () => {
  loading.value = true
  message.value = ''
  error.value = ''
  try {
    const payload = { ...form.value, txId: `WITHDRAW_${Date.now()}_${Math.random().toString(36).slice(2,8)}` }
    await withdraw(payload)
    message.value = '取款成功'
    form.value.amount = null
  } catch (e) {
    error.value = e?.message || '取款失败'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page { padding: 16px; }
form { display: flex; gap: 8px; align-items: center; }
label { display: flex; gap: 6px; align-items: center; }
input { padding: 6px 8px; }
button { padding: 6px 12px; }
.success { color: #000; }
.error { color: #000; }
</style>