<template>
  <div class="layout">
    <Header />
    <div class="main">
      <Sidebar />
      <div class="content">
        <router-view />
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import Header from './components/layout/Header.vue'
import Sidebar from './components/layout/Sidebar.vue'
import Footer from './components/layout/Footer.vue'
</script>

<style scoped>
.layout { display:flex; flex-direction:column; min-height:100vh; background:#f5f7fb; }
.main { display:flex; flex:1; gap:16px; padding:16px; }
.content { flex:1; padding:24px; overflow:auto; background:#fff; border-radius:12px; box-shadow: 0 1px 3px rgba(0,0,0,.08), 0 8px 24px rgba(0,0,0,.06); }
</style>

.sidebar { width:240px; }
