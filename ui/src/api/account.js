// src/api/account.js
import request from './index'

export const createAccountApi = (data) => request.post('/accounts/create', data)
export const getBalanceApi = (accountNumber) => request.get(`/accounts/${accountNumber}/balance`)
export const getAccountApi = (accountNumber) => request.get(`/accounts/${accountNumber}`)

// 新增：获取当前登录用户的账户列表
export const getMyAccountsApi = () => request.get('/accounts/my')

export const getAccount = getAccountApi
export const getBalance = getBalanceApi