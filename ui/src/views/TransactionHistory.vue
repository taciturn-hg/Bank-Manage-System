<template>
  <div class="page">
    <h2>交易记录</h2>

    <div class="toolbar">
      <label>
        类型筛选：
        <select v-model="type">
          <option value="">全部</option>
          <option value="deposit">存款</option>
          <option value="withdraw">取款</option>
          <option value="transfer">转账</option>
        </select>
      </label>
      <button @click="load">刷新</button>
    </div>

    <div v-if="loading" class="hint">正在加载...</div>
    <div v-else>
      <table class="table" v-if="list.length">
        <thead>
          <tr>
            <th>时间</th>
            <th>类型</th>
            <th>转出账户</th>
            <th>转入账户</th>
            <th>金额</th>
            <th>状态</th>
            <th>备注</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in list" :key="item.txId">
            <td>{{ formatTime(item.time) }}</td>
            <td>{{ formatType(item.type) }}</td>
            <td>{{ item.fromAccount || '-' }}</td>
            <td>{{ item.toAccount || '-' }}</td>
            <td>{{ item.amount }}</td>
            <td>
              <span :class="['status', item.status?.toLowerCase()]">{{ item.status }}</span>
            </td>
            <td>{{ item.remark || '-' }}</td>
          </tr>
        </tbody>
      </table>
      <div v-else class="hint">暂无记录</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyTransactionsApi } from '../api/transaction'

const list = ref([])
const loading = ref(false)
const type = ref('')

const formatType = (t) => ({
  deposit: '存款',
  withdraw: '取款',
  transfer: '转账'
}[t] || t)

const formatTime = (time) => {
  try {
    return new Date(time).toLocaleString()
  } catch (e) { return time }
}

async function load() {
  loading.value = true
  try {
    const data = await getMyTransactionsApi(type.value)
    list.value = Array.isArray(data) ? data : []
  } catch (e) {
    alert(e.message || '加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<style scoped>
.page { padding: 16px; }
.toolbar { display:flex; gap:12px; align-items:center; margin: 12px 0 16px; }
.table { width: 100%; border-collapse: collapse; }
.table th, .table td { border: 1px solid #ddd; padding: 8px; }
.table th { background: #f7f7f7; }
.status.success { color: #22c55e; }
.status.fail { color: #ef4444; }
.status.pending { color: #f59e0b; }
.hint { color: #666; }
</style>