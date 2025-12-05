// src/api/transaction.js
import request from './index'

export const depositApi = (data) => request.post('/transactions/deposit', data)
export const withdrawApi = (data) => request.post('/transactions/withdraw', data)
export const transferApi = (data) => request.post('/transactions/transfer', data)

// 获取当前用户的交易记录（可选按类型过滤）
export const getMyTransactionsApi = (type) => request.get(type ? `/transactions/my?type=${encodeURIComponent(type)}` : '/transactions/my')
export const deposit = depositApi
export const withdraw = withdrawApi
export const transfer = transferApi