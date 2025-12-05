// src/store/user.js
import { defineStore } from 'pinia'
import { loginApi, registerApi, getUserByIdApi, getCurrentUserApi } from '../api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: null
  }),
  actions: {
    async login(payload) {
      const result = await loginApi(payload)
      const token = typeof result === 'string' ? result : result?.token
      if (!token) throw new Error('登录返回缺少 token')
      this.token = token
      localStorage.setItem('token', token)
      // 登录后尝试拉取当前用户信息；若登录返回包含用户信息则直接设置
      if (result && typeof result === 'object' && result.user) {
        this.user = result.user
      } else {
        try {
          this.user = await getCurrentUserApi()
        } catch (_) {
          // 后端可能不支持 /users/me，则忽略
        }
      }
      return token
    },
    async register(payload) {
      const user = await registerApi(payload)
      this.user = user
      return user
    },
    async fetchUser(id) {
      const user = await getUserByIdApi(id)
      this.user = user
      return user
    },
    async fetchCurrentUser() {
      // 通过 /users/me 刷新当前用户信息
      const user = await getCurrentUserApi()
      this.user = user
      return user
    },
    async initFromToken() {
      // 应用初始化时，如果存在 token，尝试获取当前用户信息
      if (!this.token) return
      try {
        this.user = await getCurrentUserApi()
      } catch (_) {
        // 尝试解析 JWT 或保持空，避免阻断应用
      }
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('token')
    }
  }
})