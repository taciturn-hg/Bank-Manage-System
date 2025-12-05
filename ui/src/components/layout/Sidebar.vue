<template>
  <aside class="sidebar">
    <ul>
      <li><router-link to="/accounts/create">创建账户</router-link></li>
      <li><router-link to="/deposit">存款</router-link></li>
      <li><router-link to="/withdraw">取款</router-link></li>
      <li><router-link to="/transfer">转账</router-link></li>
      <li><router-link to="/transactions">交易记录</router-link></li>
    </ul>
  </aside>
</template>

<script setup>
</script>

<style scoped>
.sidebar { width:240px; background:#fff; padding:16px; border-radius:12px; box-shadow: 0 1px 3px rgba(0,0,0,.08), 0 8px 24px rgba(0,0,0,.06); }
ul { list-style:none; padding:0; margin:0; }
li { margin-bottom:8px; }
li a { display:block; padding:8px 10px; border-radius:8px; color:#000; text-decoration:none; }
li a:hover { background:#f1f5f9; }
</style>