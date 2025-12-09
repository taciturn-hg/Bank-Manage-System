// src/api/index.js
import axios from 'axios'

const instance = axios.create({
  baseURL: '/api',
  timeout: 10000
})

instance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

instance.interceptors.response.use(
  (res) => {
    // 统一兼容后端返回 { code, data, message } 或直接返回实体/字符串
    const body = res.data
    return body && Object.prototype.hasOwnProperty.call(body, 'data') ? body.data : body
  },
  (err) => {
    // 规范化错误对象，保证有 message 字段
    const msg = err?.response?.data?.message || err?.message || '请求失败'
    return Promise.reject(new Error(msg))
  }
)

export default instance