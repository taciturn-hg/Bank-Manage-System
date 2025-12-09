// src/api/user.js
import request from './index'

export const registerApi = (data) => request.post('/users/register', data)
export const loginApi = (data) => request.post('/users/login', data)
export const getUserByIdApi = (id) => request.get(`/users/${id}`)
// 新增：获取当前登录用户信息（依据后端实现，常见为 /users/me）
export const getCurrentUserApi = () => request.get('/users/me')