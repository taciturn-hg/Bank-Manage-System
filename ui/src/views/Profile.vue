<template>
  <div class="page">
    <h2>个人信息</h2>
    <div v-if="user" class="card">
      <p>用户名：{{ user.username }}</p>
      <p>邮箱：{{ user.email }}</p>
      <p>手机号：{{ user.phone }}</p>
    </div>
    <div v-else>未登录或未加载用户信息。</div>

    <div class="card" v-if="accounts.length">
      <h3>我的账户</h3>
      <ul class="list">
        <li v-for="acc in accounts" :key="acc.accountNumber">
          <span>账户号：{{ acc.accountNumber }}</span>
          <span>类型：{{ acc.type }}</span>
          <span>余额：{{ acc.balance }}</span>
        </li>
      </ul>
    </div>
    <div class="actions">
      <button @click="loadUser" :disabled="loading">刷新用户信息</button>
      <button @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '../store/user'
import { useAccountStore } from '../store/account'

const userStore = useUserStore()
const { user } = storeToRefs(userStore)
const loading = ref(false)

const loadUser = async () => {
  loading.value = true
  try {
    await userStore.fetchCurrentUser()
  } finally {
    loading.value = false
  }
}

const accountStore = useAccountStore()
const accounts = storeToRefs(accountStore).myAccounts

const loadAccounts = async () => {
  try {
    await accountStore.fetchMyAccounts()
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  if (!user.value) {
    loadUser()
  }
  loadAccounts()
})

const logout = () => {
  userStore.logout()
}
</script>

<style scoped>
.page { padding: 16px; }
.card { border: 1px solid #ddd; border-radius: 8px; padding: 12px; margin: 12px 0; }
.actions { display: flex; gap: 8px; }
button { padding: 6px 12px; }
.list { display:flex; flex-direction:column; gap:8px; }
.list li { display:grid; grid-template-columns: repeat(3, 1fr); gap:8px; }
</style>